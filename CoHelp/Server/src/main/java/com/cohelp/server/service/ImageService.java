package com.cohelp.server.service;

import com.cohelp.server.model.domain.DetailRequest;
import com.cohelp.server.model.domain.ImageChangeRequest;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.Image;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;

/**
* @author jianping5
* @description 针对表【image(图片表)】的数据库操作Service
* @createDate 2022-09-19 21:36:20
*/
public interface ImageService extends IService<Image> {
    /**
     * 根据图片ID查询图片信息
     * @author: ZGY
     * @param id 图片ID
     * @return com.cohelp.server.model.domain.Result
     */
    Result getImageById(Integer id);
    /**
     * 根据类型和id获取符合条件的当前使用图片
     * @author: ZGY
     * @param detailRequest 获取详情请求参数
     * @return com.cohelp.server.model.domain.Result
     */
    Result getImageList(DetailRequest detailRequest);
    /**
     * 根据类型和id获取符合条件的所有图片
     * @author: ZGY
     * @param detailRequest 获取详情请求参数
     * @return com.cohelp.server.model.domain.Result
     */
    Result getAllImage(DetailRequest detailRequest);
    /**
     * 根据传回的参数对符合条件的图片状态进行变更
     * @author: ZGY
     * @param imageIntegerMap 更改图片状态请求参数
     * @return com.cohelp.server.model.domain.Result
     */
    Result setImageState(ImageChangeRequest imageChangeRequest);

}
