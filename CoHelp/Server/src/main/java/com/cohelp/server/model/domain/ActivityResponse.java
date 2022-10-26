package com.cohelp.server.model.domain;

import com.cohelp.server.model.entity.Activity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * 活动返回类
 *
 * @author jianping5
 * @createDate 2022/10/23 23:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityResponse {

    private Activity activity;

    private ArrayList<String> fileNameList;
}
