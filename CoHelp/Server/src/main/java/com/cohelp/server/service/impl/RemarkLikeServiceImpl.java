package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.constant.TypeEnum;
import com.cohelp.server.mapper.RemarkLikeMapper;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.*;
import com.cohelp.server.service.RemarkActivityService;
import com.cohelp.server.service.RemarkHelpService;
import com.cohelp.server.service.RemarkHoleService;
import com.cohelp.server.service.RemarkLikeService;
import com.cohelp.server.utils.ResultUtil;
import com.cohelp.server.utils.UserHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.cohelp.server.constant.StatusCode.*;

/**
* @author zgy
* @description 针对表【remark_like(评论点赞记录表)】的数据库操作Service实现
*/
@Service
public class RemarkLikeServiceImpl extends ServiceImpl<RemarkLikeMapper, RemarkLike>
    implements RemarkLikeService{

    @Resource
    RemarkActivityService remarkActivityService;
    @Resource
    RemarkHelpService remarkHelpService;
    @Resource
    RemarkHoleService remarkHoleService;
    @Resource
    RemarkLikeService remarkLikeService;
    @Override
    public Result likeRemark(Integer type, Integer id) {
        User loginUser = UserHolder.getUser();

        if (TypeEnum.isActivity(type)) {
            RemarkActivity remarkActivity = (RemarkActivity) getRemarkById(type, id);
            // 获取当前用户对该主题的点赞记录
            if (loginUser == null) {
                return ResultUtil.fail(INTERCEPTOR_LOGIN, "未登录");
            }
            Integer loginUserId = loginUser.getId();
            QueryWrapper<RemarkLike> likeQueryWrapper = new QueryWrapper<RemarkLike>();
            likeQueryWrapper.eq("user_id", loginUserId);
            likeQueryWrapper.eq("remark_id", remarkActivity.getId());
            likeQueryWrapper.eq("remark_type", TypeEnum.ACTIVITY.ordinal());
            RemarkLike like = remarkLikeService.getOne(likeQueryWrapper);
            // 之前未点赞且无记录
            if (like == null) {
                RemarkLike newLike = new RemarkLike();
                newLike.setUserId(loginUserId);
                newLike.setRemarkId(id);
                newLike.setRemarkType(TypeEnum.ACTIVITY.ordinal());
                newLike.setIsLiked(1);
                boolean saveResult = remarkLikeService.save(newLike);
                if (!saveResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                // 增加对应主题的点赞量到
                remarkActivity.setRemarkLike(remarkActivity.getRemarkLike() + 1);
                boolean updateResult = remarkActivityService.updateById(remarkActivity);
                if (!updateResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                return ResultUtil.ok(true, "点赞成功");
            }

            // 更新条件（对应主题对应用户id）
            UpdateWrapper<RemarkLike> likeUpdateWrapper = new UpdateWrapper<>();
            likeUpdateWrapper.eq("user_id", loginUserId);
            likeUpdateWrapper.eq("topic_id", remarkActivity.getId());
            likeUpdateWrapper.eq("topic_type", TypeEnum.ACTIVITY.ordinal());

            // 之前未点赞（但有记录）
            if (like.getIsLiked() == 0) {
                likeUpdateWrapper.set("is_liked", 1);
                boolean updateResult1 = remarkLikeService.update(likeUpdateWrapper);
                if (!updateResult1) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                // 增加对应主题的点赞量到
                remarkActivity.setRemarkLike(remarkActivity.getRemarkLike() + 1);
                boolean updateResult = remarkActivityService.updateById(remarkActivity);
                if (!updateResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                return ResultUtil.ok(true, "点赞成功");
            }
            // 之前已点赞
            if (like.getIsLiked() == 1) {
                likeUpdateWrapper.set("is_liked", 0);
                boolean updateResult2 = remarkLikeService.update(likeUpdateWrapper);
                if (!updateResult2) {
                    return ResultUtil.fail(ERROR_SYSTEM, "取消点赞失败");
                }
                // 减少对应主题的点赞量到
                remarkActivity.setRemarkLike(remarkActivity.getRemarkLike() - 1);
                boolean updateResult = remarkActivityService.updateById(remarkActivity);
                if (!updateResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "取消点赞失败");
                }
                return ResultUtil.ok(true, "取消点赞成功");
            }
        } else if (TypeEnum.isHelp(type)) {
            RemarkHelp remarkHelp = (RemarkHelp) getRemarkById(type, id);
            // 获取当前用户对该主题的点赞记录
            if (loginUser == null) {
                return ResultUtil.fail(INTERCEPTOR_LOGIN, "未登录");
            }
            Integer loginUserId = loginUser.getId();
            QueryWrapper<RemarkLike> likeQueryWrapper = new QueryWrapper<RemarkLike>();
            likeQueryWrapper.eq("user_id", loginUserId);
            likeQueryWrapper.eq("remark_id", remarkHelp.getId());
            likeQueryWrapper.eq("remark_type", TypeEnum.HELP.ordinal());
            RemarkLike like = remarkLikeService.getOne(likeQueryWrapper);
            // 之前未点赞且无记录
            if (like == null) {
                RemarkLike newLike = new RemarkLike();
                newLike.setUserId(loginUserId);
                newLike.setRemarkId(id);
                newLike.setRemarkType(TypeEnum.HELP.ordinal());
                newLike.setIsLiked(1);
                boolean saveResult = remarkLikeService.save(newLike);
                if (!saveResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                // 增加对应主题的点赞量到
                remarkHelp.setRemarkLike(remarkHelp.getRemarkLike() + 1);
                boolean updateResult = remarkHelpService.updateById(remarkHelp);
                if (!updateResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                return ResultUtil.ok(true, "点赞成功");
            }

            // 更新条件（对应主题对应用户id）
            UpdateWrapper<RemarkLike> likeUpdateWrapper = new UpdateWrapper<>();
            likeUpdateWrapper.eq("user_id", loginUserId);
            likeUpdateWrapper.eq("topic_id", remarkHelp.getId());
            likeQueryWrapper.eq("topic_type", TypeEnum.HELP.ordinal());

            // 之前未点赞（但有记录）
            if (like.getIsLiked() == 0) {
                likeUpdateWrapper.set("is_liked", 1);
                boolean updateResult1 = remarkLikeService.update(likeUpdateWrapper);
                if (!updateResult1) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                // 增加对应主题的点赞量到
                remarkHelp.setRemarkLike(remarkHelp.getRemarkLike() + 1);
                boolean updateResult = remarkHelpService.updateById(remarkHelp);
                if (!updateResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                return ResultUtil.ok(true, "点赞成功");
            }
            // 之前已点赞
            if (like.getIsLiked() == 1) {
                likeUpdateWrapper.set("is_liked", 0);
                boolean updateResult2 = remarkLikeService.update(likeUpdateWrapper);
                if (!updateResult2) {
                    return ResultUtil.fail(ERROR_SYSTEM, "取消点赞失败");
                }
                // 减少对应主题的点赞量到
                remarkHelp.setRemarkLike(remarkHelp.getRemarkLike() - 1);
                boolean updateResult = remarkHelpService.updateById(remarkHelp);
                if (!updateResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "取消点赞失败");
                }
                return ResultUtil.ok(true, "取消点赞成功");
            }
        } else {
            RemarkHole remarkHole = (RemarkHole) getRemarkById(type, id);
            // 获取当前用户对该主题的点赞记录
            if (loginUser == null) {
                return ResultUtil.fail(INTERCEPTOR_LOGIN, "未登录");
            }
            Integer loginUserId = loginUser.getId();
            QueryWrapper<RemarkLike> likeQueryWrapper = new QueryWrapper<RemarkLike>();
            likeQueryWrapper.eq("user_id", loginUserId);
            likeQueryWrapper.eq("remark_id", remarkHole.getId());
            likeQueryWrapper.eq("remark_type", TypeEnum.HOLE.ordinal());
            RemarkLike like = remarkLikeService.getOne(likeQueryWrapper);
            // 之前未点赞且无记录
            if (like == null) {
                RemarkLike newLike = new RemarkLike();
                newLike.setUserId(loginUserId);
                newLike.setRemarkId(id);
                newLike.setRemarkType(TypeEnum.HOLE.ordinal());
                newLike.setIsLiked(1);
                boolean saveResult = remarkLikeService.save(newLike);
                if (!saveResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                // 增加对应主题的点赞量到
                remarkHole.setRemarkLike(remarkHole.getRemarkLike() + 1);
                boolean updateResult = remarkHoleService.updateById(remarkHole);
                if (!updateResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                return ResultUtil.ok(true, "点赞成功");
            }

            // 更新条件（对应主题对应用户id）
            UpdateWrapper<RemarkLike> likeUpdateWrapper = new UpdateWrapper<>();
            likeUpdateWrapper.eq("user_id", loginUserId);
            likeUpdateWrapper.eq("topic_id", remarkHole.getId());
            likeQueryWrapper.eq("topic_type", TypeEnum.HOLE.ordinal());

            // 之前未点赞（但有记录）
            if (like.getIsLiked() == 0) {
                likeUpdateWrapper.set("is_liked", 1);
                boolean updateResult1 = remarkLikeService.update(likeUpdateWrapper);
                if (!updateResult1) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                // 增加对应主题的点赞量到
                remarkHole.setRemarkLike(remarkHole.getRemarkLike() + 1);
                boolean updateResult = remarkHoleService.updateById(remarkHole);
                if (!updateResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                return ResultUtil.ok(true, "点赞成功");
            }
            // 之前已点赞
            if (like.getIsLiked() == 1) {
                likeUpdateWrapper.set("is_liked", 0);
                boolean updateResult2 = remarkLikeService.update(likeUpdateWrapper);
                if (!updateResult2) {
                    return ResultUtil.fail(ERROR_SYSTEM, "取消点赞失败");
                }
                // 减少对应主题的点赞量到
                remarkHole.setRemarkLike(remarkHole.getRemarkLike() - 1);
                boolean updateResult = remarkHoleService.updateById(remarkHole);
                if (!updateResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "取消点赞失败");
                }
                return ResultUtil.ok(true, "取消点赞成功");
            }
        }
        return ResultUtil.ok(false, "操作失败");
    }
    /**
     * 根据id和类型获取对应评论
     * @author: ZGY
     * @param type 评论类型
     * @param id 评论id
     * @return java.lang.Object
     */
    private Object getRemarkById(Integer type, Integer id) {
        if (type == null || type <= 0 || id == null || id <= 0) {
            return ResultUtil.fail(ERROR_PARAMS);
        }
        Object remark;
        if (TypeEnum.isActivity(type)) {
            remark = remarkActivityService.getById(id);
        } else if (TypeEnum.isHelp(type)) {
            remark = remarkHelpService.getById(id);
        } else {
            remark = remarkHoleService.getById(id);
        }
        if (remark == null) {
            return ResultUtil.fail(ERROR_PARAMS, "评论不存在");
        }
        return remark;
    }
}




