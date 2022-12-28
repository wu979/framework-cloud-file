package com.framework.cloud.file.domain.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import jodd.util.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传
 *
 * @author wusiwei
 */
@Slf4j
@Component
@ConditionalOnBean(OSS.class)
public class OssUtil {

    private static OSS ossClient;

    @Autowired
    @SuppressWarnings("all")
    public OssUtil(@Qualifier("ossClient") OSS ossClient) {
        OssUtil.ossClient = ossClient;
    }

    /**
     * 文件上传方法（单个）
     *
     * @param file       文件
     * @param bucketName 桶名称
     * @param uploadPath 上传文件地址
     * @param fileName 文件名称
     */
    public static boolean upload(File file, String bucketName, String uploadPath, String fileName) {
        boolean flag = Boolean.FALSE;
        InputStream fis = null;
        ByteArrayOutputStream bos = null;
        try {
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            ossClient.putObject(bucketName, uploadPath + StringPool.SLASH + fileName, new ByteArrayInputStream(bos.toByteArray()));
            flag = Boolean.TRUE;
        } catch (IOException e) {
            log.error("上传文件,文件上传异常: {}", e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (Exception e) {
                log.error("上传文件,文件流关闭异常: {}", e);
            }
        }
        return flag;
    }

    /**
     * 文件上传方法（单个）
     *
     * @param file       文件
     * @param bucketName 桶名称
     * @param uploadPath 上传文件地址
     * @param fileName 文件名称
     */
    public static boolean upload(MultipartFile file, String bucketName, String uploadPath, String fileName) {
        boolean flag = Boolean.FALSE;
        try {
            ossClient.putObject(bucketName, uploadPath + StringPool.SLASH + fileName, new ByteArrayInputStream(file.getBytes()));
            flag = Boolean.TRUE;
        } catch (IOException e) {
            log.error("上传文件,文件上传异常: {}", e);
        }
        return flag;
    }


    /**
     * 查询桶文件
     *
     * @param bucketName 桶名称
     * @param maxKeys    最大下载量
     * @return 文件列表
     */
    public static List<OSSObjectSummary> list(String bucketName, Integer maxKeys) {
        List<OSSObjectSummary> list = new ArrayList<>(maxKeys);
        try {
            list = ossClient.listObjects(new ListObjectsRequest(bucketName).withMaxKeys(maxKeys)).getObjectSummaries();
        } catch (Exception e) {
            log.error("下载文件,文件下载异常: {}", e);
        }
        return list;
    }


    /**
     * 删除文件
     *
     * @param bucketName 桶名称
     * @param objectName 文件名称
     * @return 是否删除成功
     */
    public static boolean delete(String bucketName, String objectName) {
        boolean flag = Boolean.FALSE;
        try {
            ossClient.deleteObject(bucketName, objectName);
            flag = Boolean.TRUE;
        } catch (Exception e) {
            log.error("删除文件,文件删除异常: {}", e);
        }
        return flag;
    }

}
