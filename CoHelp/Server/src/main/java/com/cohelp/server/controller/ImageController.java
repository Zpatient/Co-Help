package com.cohelp.server.controller;

import com.cohelp.server.model.domain.DetailRequest;
import com.cohelp.server.model.domain.ImageChangeRequest;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.service.ImageService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zgy
 */
@RestController
public class ImageController {
    @Resource
    ImageService imageService;

    @RequestMapping("/image/getimagelist")
    public Result getImageList(@RequestBody DetailRequest detailRequest){
        return imageService.getImageList(detailRequest);
    }
    @RequestMapping("/image/getallimage")
    public Result getAllList(@RequestBody DetailRequest detailRequest){
        return imageService.getAllImage(detailRequest);
    }
    @RequestMapping("/setimagestate")
    public Result setImageState(@RequestBody ImageChangeRequest imageChangeRequest){
        return imageService.setImageState(imageChangeRequest);
    }
}
