package com.cohelp.server.model.domain;

import com.cohelp.server.model.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author zgy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageChangeRequest {
    /**
     *用户id
     */
    private  Integer userId;
    /**
     * 待修改状态的图片Map
     */
    Map<Image,Integer> imageStateMap;
    private static final long serialVersionUID = 1L;
}
