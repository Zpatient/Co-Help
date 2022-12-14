package com.cohelp.server.model.domain;

import com.cohelp.server.model.entity.Activity;
import com.cohelp.server.model.entity.Help;
import com.cohelp.server.model.entity.Hole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * @author jianping5
 * @createDate 2022/10/26 15:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchPublishResponse {

    /**
     * 活动list
     */
    private ArrayList<Activity> activityList;

    /**
     * 互助list
     */
    private ArrayList<Help> helpList;

    /**
     * 树洞list
     */
    private ArrayList<Hole> holeList;
}
