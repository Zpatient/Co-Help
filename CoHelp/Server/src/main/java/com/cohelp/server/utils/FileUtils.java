package com.cohelp.server.utils;

import com.cohelp.server.constant.StatusCode;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Value("${spring.tengxun.SecretId}")
    private String secretId;

    @Value("${spring.tengxun.SecretKey}")
    private String secretKey;

    @Value("${spring.tengxun.region}")
    private String region;

    @Value("${spring.tengxun.bucketName}")
    private String bucketName;

    @Value("${spring.tengxun.url}")
    private String path;


    /**
     * COS 客户端
     * @return
     */
    public COSClient initCOSClient(){
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region(this.region);
        ClientConfig clientConfig = new ClientConfig(region);
        // 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        return cosClient;
    }

    public File multipartFileToFile(MultipartFile mulFile) throws IOException {
        InputStream ins = mulFile.getInputStream();
        String fileName = mulFile.getOriginalFilename();
        String prefix = getFileNameNoEx(fileName)+ UUID.randomUUID().toString().replace("-", "");
        String suffix = "."+getExtensionName(fileName);
        File toFile = File.createTempFile(prefix,suffix);
        OutputStream os = new FileOutputStream(toFile);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
        return toFile;
    }

    /**
     * 获取文件扩展名
     *
     */
    public String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * 获取不带扩展名的文件名
     *
     */
    public String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }


    /**
     * 上传图片
     * @param multipartFile
     * @return
     */
    public String fileUpload(@NotNull MultipartFile multipartFile) {
        try {

            File file = multipartFileToFile(multipartFile);
            // 获取文件上传的流
            byte[] fileBytes = multipartFile.getBytes();

            // 创建日期目录分隔
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String datePath = dateFormat.format(new Date());

            // 获取文件名
            String originalFilename = multipartFile.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filename = datePath+"/"+ UUID.randomUUID().toString().replace("-", "")+ suffix;

            // 上传
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filename, file);
            COSClient cosClient1 = initCOSClient();
            cosClient1.putObject(putObjectRequest);
            // 设置权限(公开读)
            cosClient1.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);

            return filename;
        } catch (IOException e) {
            return null;
        }
    }
}
