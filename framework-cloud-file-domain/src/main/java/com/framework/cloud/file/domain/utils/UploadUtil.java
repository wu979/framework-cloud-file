package com.framework.cloud.file.domain.utils;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * 文件上传
 *
 * @author wusiwei
 */
@Slf4j
public class UploadUtil {

    /**
     * 文件上传方法（单个）
     *
     * @param file       文件
     * @param uploadPath 上传路径
     * @param fileName 文件名称
     */
    public static boolean upload(File file, String uploadPath, String fileName) {
        boolean flag = Boolean.FALSE;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            File path = new File(uploadPath);
            if (!path.exists()) {
                path.mkdirs();
            }
            fis = new FileInputStream(file);
            fos = new FileOutputStream(uploadPath + StringPool.SLASH + fileName);
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            flag = Boolean.TRUE;
        } catch (IOException e) {
            log.error("上传文件,文件上传异常: {}", e);
        } finally {
            close(fis, fos, inChannel, outChannel);
        }
        return flag;
    }

    /**
     * 文件上传方法（单个）
     *
     * @param file       文件
     * @param uploadPath 上传路径
     * @param fileName 文件名称
     */
    public static boolean upload(MultipartFile file, String uploadPath, String fileName) {
        boolean flag = Boolean.FALSE;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            File path = new File(uploadPath);
            if (!path.exists()) {
                path.mkdirs();
            }
            fis = (FileInputStream) file.getInputStream();
            fos = new FileOutputStream(uploadPath + StringPool.SLASH + fileName);
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            flag = Boolean.TRUE;
        } catch (IOException e) {
            log.error("上传文件,文件上传异常: {}", e);
        } finally {
            //关闭资源
            close(fis, fos, inChannel, outChannel);
        }
        return flag;
    }

    private static void close(FileInputStream fis, FileOutputStream fos, FileChannel inChannel, FileChannel outChannel) {
        try {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        } catch (IOException e) {
            log.error("上传文件,文件流关闭异常: {}", e);
        }
    }
}
