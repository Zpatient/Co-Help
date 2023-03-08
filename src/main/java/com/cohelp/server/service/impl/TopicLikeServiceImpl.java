package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.constant.TypeEnum;
import com.cohelp.server.mapper.TopicLikeMapper;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.*;
import com.cohelp.server.service.ActivityService;
import com.cohelp.server.service.HelpService;
import com.cohelp.server.service.HoleService;
import com.cohelp.server.service.TopicLikeService;
import com.cohelp.server.utils.ResultUtil;
import com.cohelp.server.utils.UserHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.cohelp.server.constant.StatusCode.*;

/**
* @author jianping5
* @description 针对表【topic_like(点赞记录表)】的数据库操作Service实现
* @createDate 2022-11-02 17:12:25
*/
@Service
public class TopicLikeServiceImpl extends ServiceImpl<TopicLikeMapper, TopicLike>
    implements TopicLikeService{

    @Resource
    private ActivityService activityService;

    @Resource
    private HelpService helpService;

    @Resource
    private HoleService holeService;

    @Resource
    private TopicLikeService topicLikeService;

    @Override
    public Result likeTopic(Integer type, Integer id) {
        User loginUser = UserHolder.getUser();

        if (TypeEnum.isActivity(type)) {
            Activity activity = (Activity) getTopicById(type, id);

            // 获取当前用户对该主题的点赞记录
            if (loginUser == null) {
                return ResultUtil.fail(INTERCEPTOR_LOGIN, "未登录");
            }
            Integer loginUserId = loginUser.getId();
            QueryWrapper<TopicLike> likeQueryWrapper = new QueryWrapper<>();
            likeQueryWrapper.eq("user_id", loginUserId);
            likeQueryWrapper.eq("topic_id", activity.getId());
            likeQueryWrapper.eq("topic_type", TypeEnum.ACTIVITY.ordinal());
            TopicLike like = topicLikeService.getOne(likeQueryWrapper);
            // 之前未点赞且无记录
            if (like == null) {
                TopicLike newLike = new TopicLike();
                newLike.setUserId(loginUserId);
                newLike.setTopicId(id);
                newLike.setTopicType(TypeEnum.ACTIVITY.ordinal());
                newLike.setIsLiked(1);
                boolean saveResult = topicLikeService.save(newLike);
                if (!saveResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                // 增加对应主题的点赞量到
                activity.setActivityLike(activity.getActivityLike() + 1);
                boolean updateResult = activityService.updateById(activity);
                if (!updateResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                return ResultUtil.ok(true, "点赞成功");
            }

            // 更新条件（对应主题对应用户id）
            UpdateWrapper<TopicLike> likeUpdateWrapper = new UpdateWrapper<>();
            likeUpdateWrapper.eq("user_id", loginUserId);
            likeUpdateWrapper.eq("topic_id", activity.getId());
            likeUpdateWrapper.eq("topic_type", TypeEnum.ACTIVITY.ordinal());

            // 之前未点赞（但有记录）
            if (like.getIsLiked() == 0) {
                likeUpdateWrapper.set("is_liked", 1);
                boolean updateResult1 = topicLikeService.update(likeUpdateWrapper);
                if (!updateResult1) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                // 增加对应主题的点赞量到
                activity.setActivityLike(activity.getActivityLike() + 1);
                boolean updateResult = activityService.updateById(activity);
                if (!updateResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                return ResultUtil.ok(true, "点赞成功");
            }

            // 之前已点赞
            if (like.getIsLiked() == 1) {
                likeUpdateWrapper.set("is_liked", 0);
                boolean updateResult2 = topicLikeService.update(likeUpdateWrapper);
                if (!updateResult2) {
                    return ResultUtil.fail(ERROR_SYSTEM, "取消点赞失败");
                }
                // 减少对应主题的点赞量到
                activity.setActivityLike(activity.getActivityLike() - 1);
                boolean updateResult = activityService.updateById(activity);
                if (!updateResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "取消点赞失败");
                }
                return ResultUtil.ok(true, "取消点赞成功");
            }
        } else if (TypeEnum.isHelp(type)) {
            Help help = (Help) getTopicById(type, id);

            // 获取当前用户对该主题的点赞记录
            if (loginUser == null) {
                return ResultUtil.fail(INTERCEPTOR_LOGIN, "未登录");
            }
            Integer loginUserId = loginUser.getId();
            QueryWrapper<TopicLike> likeQueryWrapper = new QueryWrapper<>();
            likeQueryWrapper.eq("user_id", loginUserId);
            likeQueryWrapper.eq("topic_id", help.getId());
            likeQueryWrapper.eq("topic_type", TypeEnum.HELP.ordinal());
            TopicLike like = topicLikeService.getOne(likeQueryWrapper);
            // 之前未点赞且无记录
            if (like == null) {
                TopicLike newLike = new TopicLike();
                newLike.setUserId(loginUserId);
                newLike.setTopicId(id);
                newLike.setTopicType(TypeEnum.HELP.ordinal());
                newLike.setIsLiked(1);
                boolean saveResult = topicLikeService.save(newLike);
                if (!saveResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                // 增加对应主题的点赞量到
                help.setHelpLike(help.getHelpLike() + 1);
                boolean updateResult = helpService.updateById(help);
                if (!updateResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                return ResultUtil.ok(true, "点赞成功");
            }

            // 更新条件（对应主题对应用户id）
            UpdateWrapper<TopicLike> likeUpdateWrapper = new UpdateWrapper<>();
            likeUpdateWrapper.eq("user_id", loginUserId);
            likeUpdateWrapper.eq("topic_id", help.getId());
            likeUpdateWrapper.eq("topic_type", TypeEnum.HELP.ordinal());

            // 之前未点赞（但有记录）
            if (like.getIsLiked() == 0) {
                likeUpdateWrapper.set("is_liked", 1);
                boolean updateResult1 = topicLikeService.update(likeUpdateWrapper);
                if (!updateResult1) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                // 增加对应主题的点赞量到
                help.setHelpLike(help.getHelpLike() + 1);
                boolean updateResult = helpService.updateById(help);
                if (!updateResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                return ResultUtil.ok(true, "点赞成功");
            }

            // 之前已点赞
            if (like.getIsLiked() == 1) {
                likeUpdateWrapper.set("is_liked", 0);
                boolean updateResult2 = topicLikeService.update(likeUpdateWrapper);
                if (!updateResult2) {
                    return ResultUtil.fail(ERROR_SYSTEM, "取消点赞失败");
                }
                // 减少对应主题的点赞量到
                help.setHelpLike(help.getHelpLike() - 1);
                boolean updateResult = helpService.updateById(help);
                if (!updateResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "取消点赞失败");
                }
                return ResultUtil.ok(true, "取消点赞成功");
            }
        } else {
            Hole hole = (Hole) getTopicById(type, id);

            // 获取当前用户对该主题的点赞记录
            if (loginUser == null) {
                return ResultUtil.fail(INTERCEPTOR_LOGIN, "未登录");
            }
            Integer loginUserId = loginUser.getId();
            QueryWrapper<TopicLike> likeQueryWrapper = new QueryWrapper<>();
            likeQueryWrapper.eq("user_id", loginUserId);
            likeQueryWrapper.eq("topic_id", hole.getId());
            likeQueryWrapper.eq("topic_type", TypeEnum.HOLE.ordinal());
            TopicLike like = topicLikeService.getOne(likeQueryWrapper);
            // 之前未点赞且无记录
            if (like == null) {
                TopicLike newLike = new TopicLike();
                newLike.setUserId(loginUserId);
                newLike.setTopicId(id);
                newLike.setTopicType(TypeEnum.HOLE.ordinal());
                newLike.setIsLiked(1);
                boolean saveResult = topicLikeService.save(newLike);
                if (!saveResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                // 增加对应主题的点赞量到
                hole.setHoleLike(hole.getHoleLike() + 1);
                boolean updateResult = holeService.updateById(hole);
                if (!updateResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                return ResultUtil.ok(true, "点赞成功");
            }

            // 更新条件（对应主题对应用户id）
            UpdateWrapper<TopicLike> likeUpdateWrapper = new UpdateWrapper<>();
            likeUpdateWrapper.eq("user_id", loginUserId);
            likeUpdateWrapper.eq("topic_id", hole.getId());
            likeUpdateWrapper.eq("topic_type", TypeEnum.HOLE.ordinal());

            // 之前未点赞（但有记录）
            if (like.getIsLiked() == 0) {
                likeUpdateWrapper.set("is_liked", 1);
                boolean updateResult1 = topicLikeService.update(likeUpdateWrapper);
                if (!updateResult1) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                // 增加对应主题的点赞量到
                hole.setHoleLike(hole.getHoleLike() + 1);
                boolean updateResult = holeService.updateById(hole);
                if (!updateResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                return ResultUtil.ok(true, "点赞成功");
            }

            // 之前已点赞
            if (like.getIsLiked() == 1) {
                likeUpdateWrapper.set("is_liked", 0);
                boolean updateResult2 = topicLikeService.update(likeUpdateWrapper);
                if (!updateResult2) {
                    return ResultUtil.fail(ERROR_SYSTEM, "取消点赞失败");
                }
                // 减少对应主题的点赞量到
                hole.setHoleLike(hole.getHoleLike() - 1);
                boolean updateResult = holeService.updateById(hole);
                if (!updateResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "取消点赞失败");
                }
                return ResultUtil.ok(true, "取消点赞成功");
            }
        }
        return ResultUtil.ok(false, "操作失败");
    }

    /**
     * 根据id查询对应主题
     * @param id
     * @return
     */
    private Object getTopicById(Integer type, Integer id) {
        if (type == null || type <= 0 || id == null || id <= 0) {
            return ResultUtil.fail(ERROR_PARAMS);
        }
        Object topic;
        if (TypeEnum.isActivity(type)) {
            topic = activityService.getById(id);
        } else if (TypeEnum.isHelp(type)) {
            topic = helpService.getById(id);
        } else {
            topic = holeService.getById(id);
        }

        if (topic == null) {
            return ResultUtil.fail(ERROR_PARAMS, "主题不存在");
        }
        return topic;
    }
}




