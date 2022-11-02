package com.cohelp.server.service;

import com.cohelp.server.model.domain.ActivityResponse;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.Activity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cohelp.server.model.vo.ActivityVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* @author zgy
* @description 针对表【activity(活动表)】的数据库操作Service
* @createDate 2022-10-25 12:06:50
*/
public interface ActivityService extends IService<Activity> {

    /**
     * 发布活动
     * @param activityJson
     * @param files
     * @return
     */
    Result<Boolean> publishActivity(String activityJson, MultipartFile[] files);

    /**
     * 修改活动
     * @param activityJson
     * @param files
     * @return
     */
    Result updateActivity(String activityJson, MultipartFile[] files);

    /**
     * 根据条件展示活动
     * @param conditionType
     * @param dayNum
     * @return
     */
    Result<List<ActivityVO>> listByCondition(Integer conditionType, Integer dayNum);
}
