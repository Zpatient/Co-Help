package com.cohelp.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cohelp.server.model.domain.*;
import com.cohelp.server.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author jianping5
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2022-09-19 21:36:51
*/
public interface UserService extends IService<User> {

    /**
     * 用户登录
     * @param loginRequest
     * @param request
     * @return
     */
    Result userLogin(LoginRequest loginRequest, HttpServletRequest request);

    /**
     * 用户注册
     * @param registerRequest
     * @return
     */
    Result userRegister(RegisterRequest registerRequest, HttpServletRequest request);

    /**
     * 根据用户账号获取用户邮箱
     * @author: ZGY
     * @param userAccount
     * @return com.cohelp.server.model.domain.Result
     */
    Result getUserEmail(String userAccount);

    /**
     * 向用户邮箱发送验证码
     * @author: ZGY
     * @param userEmail
     * @return com.cohelp.server.model.domain.Result
     */
    Result sendConfirmCode(String userEmail,HttpServletRequest request);

    /**
     * 更改用户密码(一定要带上Cookie)
     * @author: ZGY
     * @param changePasswordRequest
     * @return com.cohelp.server.model.domain.Result
     */
    Result userChangePassword(ChangePasswordRequest changePasswordRequest,HttpServletRequest request);

    /**
     * 获取当前用户
     * @return
     */
    Result<User> getCurrentUser();

    /**
     * 点击头像查看用户主页
     * @param userAccount
     * @return
     */
    Result<User> viewPage(String userAccount);

    /**
     * 修改个人资料
     * @param user
     * @return
     */
    Result<Boolean> changeUserInfo(User user);

    /**
     * 退出登录
     * @return
     */
    Result<Boolean> userLogout(HttpServletRequest request);

    /**
     * 删除发布
     * @param publishDeleteRequest
     * @return
     */
    Result<Boolean> deletePublish(PublishDeleteRequest publishDeleteRequest);

    /**
     * 查询发布
     * @return
     */
    Result searchPublish(String userAccount);

    /**
     * 修改头像
     * @param file
     * @return
     */
    Result<Boolean> changeAvatar(MultipartFile file);

    /**
     * 查询某组织所有成员信息
     * @param teamId 组织id
     * @param currentPage 当前页码
     * @param pageSize m每页数据最大个数
     * @return java.util.List<com.cohelp.server.model.entity.User>
     */
    List<User> listTeamUser(Integer teamId,Integer currentPage,Integer pageSize);
    /**
     * 管理用户信息
     * @param user 待修改的用户信息
     * @return java.lang.Boolean
     */
    String adminUserInfo(User user);
    /**
     * 根据昵称搜索某组织成员
     * @param teamId 组织id
     * @param currentPage 当前页码
     * @param pageSize m每页数据最大个数
     * @param key 关键词
     * @return java.util.List<com.cohelp.server.model.entity.User>
     */
    List<User> listUserByName(Integer teamId,Integer currentPage,Integer pageSize,String key);

}
