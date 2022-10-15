package com.cohelp.server.service.impl;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.model.domain.LoginRequest;
import com.cohelp.server.model.domain.RegisterRequest;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.domain.ResultUtil;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.service.UserService;
import com.cohelp.server.mapper.UserMapper;
import com.cohelp.server.utils.RegexUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.cohelp.server.constant.NumberConstant.*;
import static com.cohelp.server.model.domain.StatusCode.*;

/**
* @author jianping5
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
            return ResultUtil.fail(ERROR_PARAMS, "账号密码不匹配");
        }

        // 3. 除去敏感信息
        User safetyUser = getSafetyUser(user);

        // 4. 记录用户的登录态
        request.getSession().setAttribute("user", user);

        // 5. 返回数据
        return ResultUtil.ok(SUCCESS_LOGIN, safetyUser, "登陆成功");
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

        // 检验是否为空
        if (StringUtils.isAnyBlank(userAccount, userPassword, userConfirmPassword, phoneNumber)) {
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

        // 校验密码和确认密码是否一致
        if (!userPassword.equals(userConfirmPassword)) {
            return ResultUtil.fail(ERROR_PARAMS, "两次密码不一致");
        }

        // 判断用户账户是否重复
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_account", userAccount);
        long count = this.count(queryWrapper);
        if (count != 0) {
            return ResultUtil.fail(ERROR_PARAMS, "用户账号不能重复");
        }

        // 2. 加密
        String encryptedPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 3. 插入数据（并初始化一些数据）
        User user = getOriginUser(userAccount, encryptedPassword, phoneNumber);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            return ResultUtil.fail(ERROR_PARAMS, "注册失败");
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
    private User getOriginUser(String userAccount, String encryptedPassword, String phoneNumber) {
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptedPassword);
        user.setPhoneNumber(phoneNumber);
        user.setUserName(RandomStringUtils.random(12, true, true));
        user.setAvatar(1);
        user.setAge(18);
        user.setAnimalSign(getAnimalSign(LocalDateTime.now().getYear() - 18));
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
}




