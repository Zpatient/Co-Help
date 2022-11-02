package com.cohelp.server.model.domain;

import com.cohelp.server.model.entity.Hole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * 树洞返回类
 *
 * @author jianping5
 * @createDate 2022/10/23 23:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoleResponse {

    private Hole hole;

    private ArrayList<String> fileNameList;
    /**
     * 文件的URL
     */
    private ArrayList<String> fileUrlList;

    public HoleResponse(Hole hole, ArrayList<String> fileUrlList) {
        this.hole = hole;
        this.fileUrlList = fileUrlList;
    }
}
