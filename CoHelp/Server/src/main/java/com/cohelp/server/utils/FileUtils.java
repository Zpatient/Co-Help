package com.cohelp.server.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

/**
 * 文件工具类
 *
 * @author jianping5
 * @createDate 2022/10/23 23:09
 */
@Component
public class FileUtils {

    private static String UPLOAD_FOLDER = "C:/Users/靑/Desktop/项目/Co-Help/CoHelp/Server/image/";

    /**
     * 上传图片
     * @param file
     * @return
     */
    public static String fileUpload(MultipartFile file) {
        if (file == null) {
            return null;
        }
        if (file.getSize() > 1024 * 1024 * 10) {
            return null;
        }
        //获取文件后缀
        String suffix = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
        if (!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
            return null;
        }
        // 创建文件上传目录
        String savePath = UPLOAD_FOLDER;
        File savePathFile = new File(savePath);
        if (!savePathFile.exists()) {
            //若不存在该目录，则创建目录
            savePathFile.mkdir();
        }
        //通过UUID生成唯一文件名
        String fileName = UUID.randomUUID().toString().replaceAll("-","") + "." + suffix;
        try {
            //将文件保存指定目录
            file.transferTo(new File(savePath + fileName));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return fileName;
    }
}
