package com.dullfan.common.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 */
@Component
@ConfigurationProperties(prefix = "dull")
public class DullConfig {
    /**
     * 项目名称
     * */
    private String name;

    /**
     * 版本
     * */
    private String version;

    /**
     * 上传路径
     * */
    @Getter
    private static String profile;

    /**
     * 获取地址开关
     * */
    @Getter
    private static boolean addressEnabled;

    /**
     * 验证码类型
     * */
    @Getter
    private static String captchaType;

    public void setProfile(String profile) {
        DullConfig.profile = profile;
    }

    public void setAddressEnabled(boolean addressEnabled) {
        DullConfig.addressEnabled = addressEnabled;
    }

    public void setCaptchaType(String captchaType) {
        DullConfig.captchaType = captchaType;
    }

    /**
     * 获取导入上传路径
     */
    public static String getImportPath() {
        return getProfile() + "/import";
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath() {
        return getProfile() + "/avatar";
    }

    /**
     * 获取图片上传路径
     */
    public static String getImagesPath() {
        return getProfile() + "/images";
    }

    /**
     * 获取文件上传路径
     */
    public static String getFilesPath() {
        return getProfile() + "/files";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath() {
        return getProfile() + "/download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath() {
        return getProfile() + "/upload";
    }
}
