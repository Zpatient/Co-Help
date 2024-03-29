package com.cohelp.server.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.constant.TypeEnum;
import com.cohelp.server.mapper.UserMapper;
import com.cohelp.server.model.domain.*;
import com.cohelp.server.model.entity.*;
import com.cohelp.server.model.vo.DetailResponse;
import com.cohelp.server.service.*;
import com.cohelp.server.utils.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cohelp.server.constant.NumberConstant.*;
import static com.cohelp.server.constant.StatusCode.*;
import static com.cohelp.server.constant.TypeConstant.*;
import static com.cohelp.server.constant.TypeEnum.USER;

/**
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2022-09-19 21:36:51
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private ActivityService activityService;

    @Resource
    private HelpService helpService;

    @Resource
    private HoleService holeService;

    @Resource
    private ImageService imageService;

    @Resource
    private AskService askService;

    @Resource
    private FileUtils fileUtils;

    @Value("${spring.tengxun.url}")
    private String path;

    @Resource
    private UserService userService;

    @Resource
    private TeamService teamService;

    @Resource
    private SelectionService selectionService;

    @Resource
    private CourseService courseService;



    /**
     * 加密盐值
     */
    private static final String SALT = "CoHelp";

    @Override
    public Result userLogin(LoginRequest loginRequest, HttpServletRequest request) {

        // 1. 检验格式
        if (loginRequest == null) {
            return ResultUtil.fail(ERROR_PARAMS, "请求参数为空");

        }
        String userAccount = loginRequest.getUserAccount();
        String userPassword = loginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return ResultUtil.fail(ERROR_PARAMS, "请求参数为空");
        }

        // 检验账号格式
        if (userAccount.length() < USER_ACCOUNT_LENGTH_LOW ) {
            return ResultUtil.fail(ERROR_PARAMS, "用户账号长度过短");
        }
        if (userAccount.length() > USER_ACCOUNT_LENGTH_HIGH) {
            return ResultUtil.fail(ERROR_PARAMS, "用户账号长度过长");
        }
        if (!RegexUtils.isUserAccountValid(userAccount)) {
            return ResultUtil.fail(ERROR_PARAMS, "用户账号包含违规字符");
        }

        // 检验密码格式
        if (userPassword.length() < USER_PASSWORD_LENGTH_LOW ) {
            return ResultUtil.fail(ERROR_PARAMS, "用户密码长度过短");
        }
        if (userPassword.length() > USER_PASSWORD_LENGTH_HIGH ) {
            return ResultUtil.fail(ERROR_PARAMS, "用户密码长度过长");
        }
        if (!RegexUtils.isUserPasswordValid(userPassword)) {
            return ResultUtil.fail(ERROR_PARAMS, "用户密码包含违规字符");
        }

        // 2. 查询用户
        // 加密
        String encryptedPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount).eq("user_password", encryptedPassword);
        User user = this.getOne(queryWrapper);
        if (user == null) {
            return ResultUtil.fail(ERROR_LOGIN, "账号密码不匹配");
        }

        // 校验用户状态
        Integer state = user.getState();
        if (state != 0) {
            return ResultUtil.fail("账号异常");
        }

        // 3. 除去敏感信息
        User safetyUser = getSafetyUser(user);

        // 4. 记录用户的登录态
        request.getSession().setAttribute("user", safetyUser);

        // 5. 返回数据
        return ResultUtil.ok(SUCCESS_LOGIN, safetyUser, "登录成功");
    }

    /**
     * 获取脱去敏感信息的 user 对象
     * @param user
     * @return
     */
    @Override
    public User getSafetyUser(User user) {
        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setUserName(user.getUserName());
        safetyUser.setAvatar(user.getAvatar());
        safetyUser.setSex(user.getSex());
        safetyUser.setPhoneNumber(user.getPhoneNumber());
        safetyUser.setAge(user.getAge());
        safetyUser.setUserRole(user.getUserRole());
        safetyUser.setState(user.getState());
        safetyUser.setUserCreateTime(user.getUserCreateTime());
        safetyUser.setUserEmail(user.getUserEmail());
        safetyUser.setTeamId(user.getTeamId());
        safetyUser.setType(user.getType());
        safetyUser.setAnimalSign(getAnimalSign(LocalDateTime.now().getYear() - user.getAge()));
        safetyUser.setTeamName(teamService.getById(user.getTeamId()).getTeamName());
        return safetyUser;
    }



    @Override
    public Result userRegister(RegisterRequest registerRequest, HttpServletRequest request) {

        // 1. 校验
        if (registerRequest == null) {
            return ResultUtil.fail(ERROR_PARAMS, "参数为空");
        }
        String userAccount = registerRequest.getUserAccount();
        String userPassword = registerRequest.getUserPassword();
        String userConfirmPassword = registerRequest.getUserConfirmPassword();
        String userEmail = registerRequest.getUserEmail();
        String confirmCode = registerRequest.getConfirmCode();

        // 检验是否为空
        if (StringUtils.isAnyBlank(userAccount, userPassword, userConfirmPassword, confirmCode, userEmail)) {
            return ResultUtil.fail(ERROR_PARAMS, "参数为空");
        }

        // 检验账号格式
        if (userAccount.length() < USER_ACCOUNT_LENGTH_LOW ) {
            return ResultUtil.fail(ERROR_PARAMS, "用户账号长度过短");
        }
        if (userAccount.length() > USER_ACCOUNT_LENGTH_HIGH) {
            return ResultUtil.fail(ERROR_PARAMS, "用户账号长度过长");
        }
        if (!RegexUtils.isUserAccountValid(userAccount)) {
            return ResultUtil.fail(ERROR_PARAMS, "用户账号包含违规字符");
        }

        // 检验密码格式
        if (userPassword.length() < USER_PASSWORD_LENGTH_LOW || userConfirmPassword.length() < USER_PASSWORD_LENGTH_LOW) {
            return ResultUtil.fail(ERROR_PARAMS, "用户密码长度过短");
        }
        if (userPassword.length() > USER_PASSWORD_LENGTH_HIGH || userConfirmPassword.length() > USER_PASSWORD_LENGTH_HIGH) {
            return ResultUtil.fail(ERROR_PARAMS, "用户密码长度过长");
        }
        if (!RegexUtils.isUserPasswordValid(userPassword)) {
            return ResultUtil.fail(ERROR_PARAMS, "用户密码包含违规字符");
        }

        // 检验邮箱格式
        if (!RegexUtils.isEmailValid(userEmail)) {
            return ResultUtil.fail(ERROR_PARAMS, "用户邮箱格式不规范");
        }

        // 校验密码和确认密码是否一致
        if (!userPassword.equals(userConfirmPassword)) {
            return ResultUtil.fail(ERROR_PARAMS, "两次密码不一致");
        }

        // 校验验证码是否一致
        String checkCode = (String) request.getSession().getAttribute(userEmail);
        if (!confirmCode.equals(checkCode)) {
            return ResultUtil.fail(ERROR_PARAMS, "验证码错误");
        }

        // 判断用户账户是否重复
        QueryWrapper<User> queryWrapperAccount = new QueryWrapper();
        queryWrapperAccount.eq("user_account", userAccount);
        long countAccount = this.count(queryWrapperAccount);
        if (countAccount != 0) {
            return ResultUtil.fail(ERROR_REGISTER, "用户账号不能重复");
        }

        // 判断邮箱是否重复
        QueryWrapper<User> queryWrapperEmail = new QueryWrapper<>();
        queryWrapperEmail.eq("user_email", userEmail);
        long countEmail = this.count(queryWrapperEmail);
        if (countEmail != 0) {
            return ResultUtil.fail(ERROR_REGISTER, "用户邮箱不能重复");
        }

        // 2. 加密
        String encryptedPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 3. 插入数据（并初始化一些数据）
        User user = getOriginUser(userAccount, encryptedPassword, userEmail);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            return ResultUtil.fail(ERROR_REGISTER, "注册失败");
        }

        // 4. 返回用户id
        return ResultUtil.ok(SUCCESS_REGISTER, user.getId(), "注册成功");
    }


    /**
     * 获取原始 user 对象（包含加密的密码，并初始化部分属性）
     * @param userAccount
     * @param encryptedPassword
     * @return
     */
    private User getOriginUser(String userAccount, String encryptedPassword, String userEmail) {
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptedPassword);
        user.setUserEmail(userEmail);
        user.setUserName(RandomStringUtils.random(12, true, true));
        user.setAvatar(1);
        user.setAge(18);
        return user;
    }

    /**
     * 根据年份获取生肖
     * @param year
     * @return
     */
    public static String getAnimalSign(int year) {
        switch (year % 12) {
            case 0: return "猴";
            case 1: return "鸡";
            case 2: return "狗";
            case 3: return "猪";
            case 4: return "鼠";
            case 5: return "牛";
            case 6: return "虎";
            case 7: return "兔";
            case 8: return "龙";
            case 9: return "蛇";
            case 10: return "马";
            case 11: return "羊";
            default: return "";
        }
    }

    @Override
    public Result getUserEmail(String userAccount){
        //检查参数是否有效
        if(null==userAccount||userAccount.equals("")) {
            return ResultUtil.fail(ERROR_PARAMS, "参数为空");
        }
        //查询邮箱
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>().eq("user_account", userAccount);
        User user = userMapper.selectOne(userQueryWrapper);
        String email;
        if (null == user){
            return ResultUtil.fail(ERROR_GET_DATA,"用户不存在！");
        }
        else if (null != (email = user.getUserEmail())) {
            return ResultUtil.ok(SUCCESS_GET_DATA,email,"邮箱获取成功！");
        }
        else {
            return ResultUtil.fail(ERROR_GET_DATA,"邮箱获取失败！");
        }
    }

    @Override
    public Result sendConfirmCode(String userEmail,HttpServletRequest request){
        //检查参数是否有效
        if(null==userEmail||userEmail.equals("")) {
            return ResultUtil.fail(ERROR_PARAMS, "参数为空");
        }
        //随机生成六位数字验证码
        Random random = new Random();
        Integer confirmCode = random.nextInt(899999)+100000;
        //存放到Session域中
        HttpSession session = request.getSession();
        if(null!=session.getAttribute(userEmail)) {
            session.removeAttribute(userEmail);
        }
        session.setAttribute(userEmail,confirmCode.toString());
        //发送验证码
        String message = "您的验证码是："+confirmCode.toString()+"。请妥善保管验证码，谨防泄露，如非本人操作请忽略！";
        boolean status = MailUtils.sendMail(new Mail("Co-Help验证码", message), userEmail);
        if (status) {
            return ResultUtil.ok(SUCCESS_REQUEST,"验证码发送成功！");
        }
        else {
            return ResultUtil.fail(ERROR_REQUEST,"验证码发送失败！");
        }
    }

    @Override
    public Result userChangePassword(ChangePasswordRequest changePasswordRequest,HttpServletRequest request) {
        //检查参数是否有效
        if(null==changePasswordRequest) {
            return ResultUtil.fail(ERROR_PARAMS, "参数为空");
        }
        String userEmail = changePasswordRequest.getUserEmail();
        String confirmCode = changePasswordRequest.getConfirmCode();
        String newPassword = changePasswordRequest.getNewPassword();
        String confirmNewPassword = changePasswordRequest.getConfirmNewPassword();
        if(StringUtils.isAnyBlank(userEmail,confirmCode,newPassword,confirmNewPassword)) {
            return ResultUtil.fail(ERROR_PARAMS, "参数为空");
        }
        //判断账号是否存在
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>().eq("user_email", userEmail);
        User user = this.getOne(userQueryWrapper);
        if(null==user) {
            return ResultUtil.fail(ERROR_GET_DATA, "账号不存在");
        }
        //校验验证码
        String checkCode =(String)request.getSession().getAttribute(userEmail);
        if(null==checkCode){
            return ResultUtil.fail(ERROR_GET_DATA,"请先发送验证码");
        }
        if(!confirmCode.equals(checkCode)){
            return ResultUtil.fail(ERROR_PARAMS,"验证码错误");
        }
        // 检验密码格式
        if (newPassword.length() < USER_PASSWORD_LENGTH_LOW || confirmNewPassword.length() < USER_PASSWORD_LENGTH_LOW) {
            return ResultUtil.fail(ERROR_PARAMS, "用户密码长度过短");
        }
        if (newPassword.length() > USER_PASSWORD_LENGTH_HIGH || confirmNewPassword.length() > USER_PASSWORD_LENGTH_HIGH) {
            return ResultUtil.fail(ERROR_PARAMS, "用户密码长度过长");
        }
        if (!RegexUtils.isUserPasswordValid(newPassword)) {
            return ResultUtil.fail(ERROR_PARAMS, "用户密码包含违规字符");
        }
        //校验新密码和确认新密码是否一致
        if (!newPassword.equals(confirmNewPassword)) {
            return ResultUtil.fail(ERROR_PARAMS, "两次密码不一致");
        }
        //加密
        String encryptedPassword = DigestUtils.md5DigestAsHex((SALT + newPassword).getBytes());
        //更新数据库
        user.setUserPassword(encryptedPassword);
        boolean result = this.updateById(user);
        request.getSession().removeAttribute(userEmail);
        if(true==result){
            return ResultUtil.ok(SUCCESS_REQUEST,"密码修改成功");
        }
        else {
            return  ResultUtil.fail(ERROR_REQUEST,"密码修改失败");
        }

    }

    @Override
    public Result<User> getCurrentUser() {
        User currentUser = UserHolder.getUser();
        int userId = currentUser.getId();
        User user = this.getById(userId);
        User safetyUser = getSafetyUser(user);
        return ResultUtil.ok(safetyUser);
    }

    @Override
    public Result<User> viewPage(String userAccount) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        User user = this.getOne(queryWrapper);
        if (user == null) {
            return ResultUtil.fail(ERROR_USER_EXIST, "该用户不存在");
        }
        User safetyUser = getSafetyUser(user);
        return ResultUtil.ok(safetyUser);
    }

    @Override
    public Result<Boolean> changeUserInfo(User user) {
        User currentUser = UserHolder.getUser();

        // 将当前用户id设置到user中
        user.setId(currentUser.getId());

        // 敏感词过滤
        String userName = user.getUserName();
        if (SensitiveUtils.contains(userName)) {
            return ResultUtil.fail("文本涉及敏感词汇");
        }

        // 检验邮箱格式
        if (user.getUserEmail() != null && !RegexUtils.isEmailValid(user.getUserEmail())) {
            return ResultUtil.fail(ERROR_PARAMS, "用户邮箱格式不规范");
        }

        // 检验手机号格式
        if (user.getPhoneNumber() != null && !RegexUtils.isPhoneNumberValid(user.getPhoneNumber())) {
            return ResultUtil.fail(ERROR_PARAMS, "用户手机号格式不规范");
        }

        boolean b = this.updateById(user);
        if (b == false) {
            return ResultUtil.fail(ERROR_CHANGE_USER_INFO, false, "修改失败");
        }
        return ResultUtil.ok(SUCCESS_CHANGE_USER_INFO, true, "成功修改个人资料");
    }

    @Override
    public Result<Boolean> userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return ResultUtil.ok(SUCCESS_LOGOUT, true, "成功退出");
    }
    @Override
    public Result<Boolean> deletePublishs(List<PublishDeleteRequest> publishDeleteRequests){
        if(publishDeleteRequests==null){
            return ResultUtil.fail("参数不能为空！");
        }else {
            List<String> collect = publishDeleteRequests.stream().map(i -> deletePublish(i).getMessage()).collect(Collectors.toList());
            boolean noneMatch = collect.stream().noneMatch(i -> i.equals("删除成功"));
            if(noneMatch){
                return ResultUtil.fail("部分删除失败！");
            }else {
                return ResultUtil.ok("删除成功！");
            }
        }
    }

    @Override
    public Result<Boolean> deletePublish(PublishDeleteRequest publishDeleteRequest) {
        int typeNumber = publishDeleteRequest.getTypeNumber();
        int id = publishDeleteRequest.getId();
        int ownerId = publishDeleteRequest.getOwnerId();
        // 活动
        if (typeNumber == ACTIVITY_TYPE) {
            // 判断当前登录用户是否为该发布主题的所有者
            User user = UserHolder.getUser();
            int userId = user.getId();
            if (userId != ownerId) {
                return ResultUtil.fail("抱歉！您无权进行删除！");
            }
            // 删除该发布主题
            QueryWrapper<Activity> activityQueryWrapper = new QueryWrapper<>();
            activityQueryWrapper.eq("id", id);
            boolean remove = activityService.remove(activityQueryWrapper);
            if (!remove) {
                return ResultUtil.fail("该活动删除失败");
            }
            // 删除与之相关的图片
            QueryWrapper<Image> imageQueryWrapper = new QueryWrapper<>();
            imageQueryWrapper.eq("image_type", ACTIVITY_TYPE).eq("image_src_id", id);
            List<Image> list = imageService.list(imageQueryWrapper);
            boolean remove1 = imageService.remove(imageQueryWrapper);
            if (!remove1&&!list.isEmpty()) {
                return ResultUtil.fail("该活动相关图片删除失败");
            }
            return ResultUtil.ok(true, "删除成功");
        }
        // 互助
        if (typeNumber == HELP_TYPE) {
            // 判断当前登录用户是否为该发布主题的所有者
            User user = UserHolder.getUser();
            int userId = user.getId();
            if (userId != ownerId) {
                return ResultUtil.fail("抱歉！您无权进行删除！");
            }
            // 删除该发布主题
            QueryWrapper<Help> helpQueryWrapper = new QueryWrapper<>();
            helpQueryWrapper.eq("id", id);
            boolean remove = helpService.remove(helpQueryWrapper);
            if (!remove) {
                return ResultUtil.fail("该互助删除失败");
            }
            // 删除与之相关的图片
            QueryWrapper<Image> imageQueryWrapper = new QueryWrapper<>();
            List<Image> list = imageService.list(imageQueryWrapper);
            imageQueryWrapper.eq("image_type", HELP_TYPE).eq("image_src_id", id);
            boolean remove1 = imageService.remove(imageQueryWrapper);
            if (!remove1&&!list.isEmpty()) {
                return ResultUtil.fail("该互助相关图片删除失败");
            }
            return ResultUtil.ok(true, "删除成功");
        }
        // 树洞
        if (typeNumber == HOLE_TYPE) {
            // 判断当前登录用户是否为该发布主题的所有者
            User user = UserHolder.getUser();
            int userId = user.getId();
            if (userId != ownerId) {
                return ResultUtil.fail("抱歉！您无权进行删除！");
            }
            // 删除该发布主题
            QueryWrapper<Hole> holeQueryWrapper = new QueryWrapper<>();
            holeQueryWrapper.eq("id", id);
            boolean remove = holeService.remove(holeQueryWrapper);
            if (!remove) {
                return ResultUtil.fail("该树洞删除失败");
            }
            // 删除与之相关的图片
            QueryWrapper<Image> imageQueryWrapper = new QueryWrapper<>();
            imageQueryWrapper.eq("image_type", HOLE_TYPE).eq("image_src_id", id);
            List<Image> list = imageService.list(imageQueryWrapper);
            boolean remove1 = imageService.remove(imageQueryWrapper);
            if (!remove1&&!list.isEmpty()) {
                return ResultUtil.fail("该树洞相关图片删除失败");
            }
            return ResultUtil.ok(true, "删除成功");
        }
        // 提问
        if (typeNumber == TypeEnum.ASK.ordinal()) {
            Result<Boolean> booleanResult = courseService.deleteAsk(id);
            if (!booleanResult.getData()) {
                return ResultUtil.fail(booleanResult.getMessage());
            }
            return ResultUtil.ok(true, "删除成功");
        }
        return ResultUtil.fail("不存在该类型发布");
    }

    @Override
    public Result<List<DetailResponse>> searchPublish(Integer page,Integer limit) {
        // 查看当前登录用户id与账户所查的用户id是否一致
        User user = UserHolder.getUser();
        int userId = user.getId();
        User one = this.getById(userId);
        if (userId != one.getId()) {
            return ResultUtil.fail("抱歉！您无权查看");
        }

        // 创建视图体数组
        List<DetailResponse> detailResponseList = new ArrayList<>();

        // 查询活动
        QueryWrapper<Activity> activityQueryWrapper = new QueryWrapper<>();
        activityQueryWrapper.eq("activity_owner_id", userId);
        ArrayList<Activity> activityList = (ArrayList<Activity>) activityService.list(activityQueryWrapper);
        activityList.forEach(activity ->
                detailResponseList.add(activityService.getDetailResponse(activity))
        );
        // 查询互助
        QueryWrapper<Help> helpQueryWrapper = new QueryWrapper<>();
        helpQueryWrapper.eq("help_owner_id", userId);
        ArrayList<Help> helpList = (ArrayList<Help>) helpService.list(helpQueryWrapper);
        helpList.forEach(help ->
                detailResponseList.add(helpService.getDetailResponse(help))
        );

//        // 查询树洞
//        QueryWrapper<Hole> holeQueryWrapper = new QueryWrapper<>();
//        holeQueryWrapper.eq("hole_owner_id", userId);
//        ArrayList<Hole> holeList = (ArrayList<Hole>) holeService.list(holeQueryWrapper);
//        holeList.forEach(hole ->
//                detailResponseList.add(holeService.getDetailResponse(hole))
//        );

        //查询提问
        QueryWrapper<Ask> askQueryWrapper = new QueryWrapper<>();
        helpQueryWrapper.eq("publisher_id", userId);
        List<Ask> list = askService.list(askQueryWrapper);
        list.forEach(ask ->
                detailResponseList.add(askService.getDetailResponse(ask))
        );
        List<DetailResponse> detailResponses = PageUtil.pageByList(detailResponseList, page, limit);
        return ResultUtil.ok(detailResponses);
    }

    @Override
    public Result<Boolean> changeAvatar(MultipartFile file) {

        User user = UserHolder.getUser();

        // 上传图片获取url
        if (file != null && !"".equals(file.getOriginalFilename())) {
            String fileName = fileUtils.fileUpload(file);
            if (StringUtils.isBlank(fileName)) {
                return ResultUtil.fail("图片上传异常");
            }
            String url = path + fileName;
            Image image = new Image();
            image.setImageType(USER.ordinal());
            image.setImageSrcId(user.getId());
            image.setImageUrl(url);
            boolean save1 = imageService.save(image);
            if (!save1) {
                return ResultUtil.fail(ERROR_SAVE_IMAGE, "图片保存失败");
            }

            // 查找新图片的 id
            QueryWrapper<Image> imageQueryWrapper = new QueryWrapper<>();
            imageQueryWrapper.eq("image_url", url);
            Image newImage = imageService.getOne(imageQueryWrapper);
            Integer newImageId = newImage.getId();

            System.out.println(newImageId);

            // 设置到当前用户
            // user.setAvatar(newImageId);
            UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
            userUpdateWrapper.eq("id", user.getId());
            userUpdateWrapper.set("avatar", newImageId);
            boolean update = userService.update(userUpdateWrapper);
            if (update) {
                return ResultUtil.ok("修改图片成功");
            }
            return ResultUtil.fail("修改图片失败");
        }

        return ResultUtil.fail("未上传图片");
    }

    @Override
    public PageResponse<User> listTeamUser(Integer teamId,Integer currentPage,Integer pageSize) {
        if(teamId==null|| ObjectUtils.anyNull(currentPage,pageSize)){
            return null;
        }
        //分页查询数据
        Page<User> userPage = getBaseMapper().selectPage(new Page<>(currentPage, pageSize),
                new QueryWrapper<User>().eq("team_id",teamId));
        List<User> users = userPage.getRecords();
        ArrayList<User> safetyUsers = new ArrayList<>();
        for(User user : users){
            User safetyUser = getSafetyUser(user);
            safetyUsers.add(safetyUser);
        }
        PageResponse<User> userPageResponse = new PageResponse<>();
        userPageResponse.setResult(safetyUsers);
        long total = userPage.getTotal();
        userPageResponse.setTotal(total);
        return userPageResponse;
    }

    @Override
    public String adminUserInfo(User user) {
        // 敏感词过滤
        String userName = user.getUserName();
        if (SensitiveUtils.contains(userName)) {
            return "文本涉及敏感词汇";
        }
        // 判断组织是否存在
        QueryWrapper<Team> teamQueryWrapper = new QueryWrapper<>();
        teamQueryWrapper.eq("id", user.getTeamId());
        Team team = teamService.getOne(teamQueryWrapper);
        if (team == null) {
            return "抱歉！该组织不存在";
        }
        // 检验邮箱格式
        if (user.getUserEmail() != null && !RegexUtils.isEmailValid(user.getUserEmail())) {
            return "用户邮箱格式不规范";
        }

        // 检验手机号格式
        if (user.getPhoneNumber() != null && !RegexUtils.isPhoneNumberValid(user.getPhoneNumber())) {
            return "用户手机号格式不规范";
        }

        //修改用户资料
        boolean b = this.updateById(user);
        if (b == false) {
            return "修改失败!";
        }
        return "修改成功！";
    }

    @Override
    public PageResponse<User> listUserByName(Integer teamId, Integer currentPage, Integer pageSize, String key) {
        if(teamId==null|| ObjectUtils.anyNull(currentPage,pageSize)){
            return null;
        }
        //分页查询数据
        Page<User> userPage = getBaseMapper().selectPage(new Page<>(currentPage, pageSize),
                new QueryWrapper<User>().eq("team_id",teamId).like("user_name",key));
        List<User> users = userPage.getRecords();
        ArrayList<User> safetyUsers = new ArrayList<>();
        for(User user : users){
            User safetyUser = getSafetyUser(user);
            safetyUsers.add(safetyUser);
        }
        PageResponse<User> userPageResponse = new PageResponse<>();
        userPageResponse.setResult(safetyUsers);
        long total = userPage.getTotal();
        userPageResponse.setTotal(total);
        return userPageResponse;
    }

    @Override
    public Result<Set<String>> listSemester() {
        // 获取当前登录用户
        User user = UserHolder.getUser();
        int userId = user.getId();

        //  查询该学生的选课记录（从选课表中查）
        QueryWrapper<Selection> selectionQueryWrapper = new QueryWrapper<>();
        selectionQueryWrapper.eq("student_id", userId);
        List<Selection> list = selectionService.list(selectionQueryWrapper);

        if (list == null) {
            return ResultUtil.fail("选课记录为空！");
        }
        // 将学年抽取出来，并去重（若无，则为空集合）
        // 顺序为从小到大
        Set<String> semesterSet = list.stream().map(item -> item.getSemester()).collect(Collectors.toSet());
        return ResultUtil.ok(semesterSet);
    }
}




