package com.cohelp.server.service.impl;
import java.time.LocalDateTime;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.model.domain.*;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.service.UserService;
import com.cohelp.server.mapper.UserMapper;
import com.cohelp.server.utils.MailUtils;
import com.cohelp.server.utils.RegexUtils;
import com.cohelp.server.utils.ResultUtil;
import com.cohelp.server.utils.UserHolder;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.cohelp.server.constant.NumberConstant.*;
import static com.cohelp.server.constant.StatusCode.*;

/**
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2022-09-19 21:36:51
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Resource
    private UserMapper userMapper;

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
    private User getSafetyUser(User user) {
        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setUserName(user.getUserName());
        safetyUser.setAvatar(user.getAvatar());
        safetyUser.setSex(user.getSex());
        safetyUser.setPhoneNumber(user.getPhoneNumber());
        safetyUser.setAge(user.getAge());
        safetyUser.setSchool(user.getSchool());
        safetyUser.setUserRole(user.getUserRole());
        safetyUser.setState(user.getState());
        safetyUser.setUserCreateTime(user.getUserCreateTime());
        safetyUser.setUserEmail(user.getUserEmail());
        safetyUser.setAnimalSign(getAnimalSign(LocalDateTime.now().getYear() - user.getAge()));

        return safetyUser;
    }

    @Override
    public Result userRegister(RegisterRequest registerRequest) {

        // 1. 校验
        if (registerRequest == null) {
            return ResultUtil.fail(ERROR_PARAMS, "参数为空");
        }
        String userAccount = registerRequest.getUserAccount();
        String userPassword = registerRequest.getUserPassword();
        String userConfirmPassword = registerRequest.getUserConfirmPassword();
        String phoneNumber = registerRequest.getPhoneNumber();
        String userEmail = registerRequest.getUserEmail();

        // 检验是否为空
        if (StringUtils.isAnyBlank(userAccount, userPassword, userConfirmPassword, phoneNumber, userEmail)) {
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

        // 检验手机号格式
        if (!RegexUtils.isUserAccountValid(phoneNumber)) {
            return ResultUtil.fail(ERROR_PARAMS, "用户手机号格式不规范");
        }

        // 检验邮箱格式
        if (!RegexUtils.isEmailValid(userEmail)) {
            return ResultUtil.fail(ERROR_PARAMS, "用户邮箱格式不规范");
        }

        // 校验密码和确认密码是否一致
        if (!userPassword.equals(userConfirmPassword)) {
            return ResultUtil.fail(ERROR_PARAMS, "两次密码不一致");
        }

        // 判断用户账户是否重复
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_account", userAccount);
        long count = this.count(queryWrapper);
        if (count != 0) {
            return ResultUtil.fail(ERROR_REGISTER, "用户账号不能重复");
        }

        // 2. 加密
        String encryptedPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 3. 插入数据（并初始化一些数据）
        User user = getOriginUser(userAccount, encryptedPassword, phoneNumber, userEmail);
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
     * @param phoneNumber
     * @return
     */
    private User getOriginUser(String userAccount, String encryptedPassword, String phoneNumber, String userEmail) {
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptedPassword);
        user.setPhoneNumber(phoneNumber);
        user.setUserName(RandomStringUtils.random(12, true, true));
        user.setAvatar(1);
        user.setAge(18);
        user.setAnimalSign(getAnimalSign(LocalDateTime.now().getYear() - 18));
        user.setUserEmail(userEmail);
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
        if(null == user){
            return ResultUtil.fail(ERROR_GET_DATA,"用户不存在！");
        }
        else if(null != (email = user.getUserEmail()))
            return ResultUtil.ok(SUCCESS_GET_DATA,email,"邮箱获取成功！");
        else
            return ResultUtil.fail(ERROR_GET_DATA,"邮箱获取失败！");
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
        if(status)
            return ResultUtil.ok(SUCCESS_REQUEST,"验证码发送成功！");
        else
            return ResultUtil.fail(ERROR_REQUEST,"验证码发送失败！");
    }

    @Override
    public Result userChangePassword(ChangePasswordRequest changePasswordRequest,HttpServletRequest request) {
        //检查参数是否有效
        if(null==changePasswordRequest) {
            return ResultUtil.fail(ERROR_PARAMS, "参数为空");
        }
        String userAccount = changePasswordRequest.getUserAccount();
        String confirmCode = changePasswordRequest.getConfirmCode();
        String newPassword = changePasswordRequest.getNewPassword();
        String confirmNewPassword = changePasswordRequest.getConfirmNewPassword();
        if(StringUtils.isAnyBlank(userAccount,confirmCode,newPassword,confirmNewPassword)) {
            return ResultUtil.fail(ERROR_PARAMS, "参数为空");
        }
        //判断账号是否存在
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>().eq("user_account", userAccount);
        User user = this.getOne(userQueryWrapper);
        if(null==user) {
            return ResultUtil.fail(ERROR_GET_DATA, "账号不存在");
        }
        //校验验证码
        String checkCode =(String)request.getSession().getAttribute(user.getUserEmail());
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
    public Result<User> viewPage(Integer userId) {
        User user = this.getById(userId);
        if (user == null) {
            return ResultUtil.fail(ERROR_USER_EXIST, "该用户不存在");
        }
        User safetyUser = getSafetyUser(user);
        return ResultUtil.ok(safetyUser);
    }

    @Override
    public Result<Boolean> changeUserInfo(User user) {
        User currentUser = UserHolder.getUser();
        if (!currentUser.getId().equals(user.getId())) {
            return ResultUtil.fail(ERROR_CHANGE_USER_INFO, false, "修改失败");
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
}




