package com.dullfan.common.utils.encryption;

import com.dullfan.common.utils.sign.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * MessageDigest加密工具类
 */
public class MessageDigestUtil {

    private static final String MD5_STR = "MD5";
    private static final String SHA1_STR = "SHA1";
    private static final String SHA256_STR = "SHA-256";
    public static final String ZERO_STR = "0";
    private static final Logger log = LoggerFactory.getLogger(MessageDigestUtil.class);

    /**
     * 采用指定的单向散列模式加密
     */
    public static byte[] encrypt(byte[] originBytes, String mode) throws Exception {
        if (ArrayUtils.isEmpty(originBytes) || StringUtils.isBlank(mode)) {
            return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        MessageDigest md = MessageDigest.getInstance(mode);
        byte[] digestBytes = md.digest(originBytes);
        return digestBytes;
    }

    /**
     * 单向散列指定模式的字符串
     */
    public static byte[] encrypt(String originContent, String mode) throws Exception {
        if (StringUtils.isBlank(originContent)) {
            return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        return encrypt(originContent.getBytes(StandardCharsets.UTF_8), mode);
    }

    /**
     * 采用指定模式散列字符串
     */
    public static String encryptString(String originContent, String mode) {
        String result = StringUtils.EMPTY;
        try {
            byte[] encryptBytes = encrypt(originContent, mode);
            if (ArrayUtils.isEmpty(encryptBytes)) {
                return result;
            }
            result = Base64.encode(encryptBytes);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

    /**
     * 获取md5加密串
     */
    public static String md5(String originContent) {
        return encryptString(originContent, MD5_STR);
    }

    /**
     * 获取sha1加密串
     */
    public static String sha1(String originContent) {
        return encryptString(originContent, SHA1_STR);
    }

    /**
     * 获取sha256加密串
     */
    public static String sha256(String originContent) {
        return encryptString(originContent, SHA256_STR);
    }

}
