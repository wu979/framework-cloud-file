package com.framework.cloud.file.domain.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.HexUtil;
import com.framework.cloud.common.enums.GlobalNumber;
import com.framework.cloud.file.common.enums.CompanyEnum;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具
 *
 * @author wusiwei
 */
public class FileUtil {

    private static final String DECIMAL_FORMAT = "#.00";

    /**
     * 检查文件
     */
    public static boolean checkFile(MultipartFile file) {
        return !file.isEmpty();
    }

    /**
     * 检查文件
     */
    public static boolean checkFile(File file) {
        return file.exists() && file.isFile();
    }

    /**
     * 检查文件名称
     */
    public static boolean checkFileName(String fileName) {
        return StringUtils.isNotBlank(fileName);
    }

    /**
     * 检查文件大小
     */
    public static boolean checkSize(long size, Integer maxSize) {
        return maxSize >= size;
    }

    /**
     * 检查文件后缀
     */
    public static boolean checkSuffix(List<String> allowedExtension, String suffixName) {
        if (CollectionUtil.isEmpty(allowedExtension)) {
            return Boolean.TRUE;
        }
        return allowedExtension.contains(suffixName.toLowerCase());
    }

    /**
     * 转换文件大小为字符串
     */
    public static String switchSize(Integer size) {
        DecimalFormat df = new DecimalFormat(DECIMAL_FORMAT);
        String sizeStr = "";
        if (size == 0) {
            return 0 + CompanyEnum.B.name();
        }
        if (size < 1024) {
            sizeStr = df.format((double) size) + CompanyEnum.B.name();
        } else if (size < 1048576) {
            sizeStr = df.format((double) size / 1024) + CompanyEnum.KB.name();
        } else if (size < 1073741824) {
            sizeStr = df.format((double) size / 1048576) + CompanyEnum.MB.name();
        } else {
            sizeStr = df.format((double) size / 1073741824) + CompanyEnum.GB.name();
        }
        return sizeStr;
    }

    /**
     * 获取文件新名称
     */
    public static String newName(String originalName, String suffixName) {
        String random = RandomStringUtils.randomNumeric(GlobalNumber.SIX.getIntValue());
        String newName = originalName + System.nanoTime() + random;
        return DigestUtils.md5Hex(newName) + suffixName;
    }

    /**
     * 文件摘要（比对文件是否重复）
     *
     * @param path      文件路径
     * @param algorithm 算法名 例如 MD5、SHA-1、SHA-256等
     */
    public static String summary(Path path, String algorithm) {
        try {
            //根据算法名称初始化摘要算法
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            //读取文件的所有比特
            byte[] fileBytes = Files.readAllBytes(path);
            //摘要更新
            digest.update(fileBytes);
            //完成哈希摘要计算并返回特征值
            byte[] digested = digest.digest();
            //进行十六进制的输出
            return HexUtil.encodeHexStr(digested);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 文件转换
     * @param filePath 文件路径
     */
    public static MultipartFile fileToMultipartFile(String filePath){
        String fieldName = "file";
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        File newFile = new File(filePath);
        FileItem item = factory.createItem(fieldName, "text/plain", false, newFile.getName());
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try (FileInputStream fis = new FileInputStream(newFile);
             OutputStream os = item.getOutputStream()) {
            while ((bytesRead = fis.read(buffer, 0, 8192))!= -1)
            {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new CommonsMultipartFile(item);
    }

    public static String summary(String path, String algorithm) {
        return summary(Paths.get(path), algorithm);
    }

    public static String summary(File file, String algorithm) {
        return summary(file.toPath(), algorithm);
    }
}
