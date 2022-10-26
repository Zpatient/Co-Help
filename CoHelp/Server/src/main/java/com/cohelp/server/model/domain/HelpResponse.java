package com.cohelp.server.model.domain;

import com.cohelp.server.model.entity.Help;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * 互助返回类
 *
 * @author jianping5
 * @createDate 2022/10/23 22:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelpResponse {

    /**
     * 互助
     */
    private Help help;

    /**
     * 文件名组成的数组
     */
    private ArrayList<String> list;

}
