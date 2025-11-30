package com.smartcity.common.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * 安全处理工具类
 * 提供加密、解密、UUID生成等功能
 * 使用JDK 25特性优化，包括模式匹配和简化的null处理
 */
public class SecurityUtils {
    
    /**
     * 生成UUID
     * @return UUID字符串，去除了连字符
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    
    /**
     * 生成带连字符的UUID
     * @return UUID字符串，格式为xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
     */
    public static String generateUUIDWithHyphen() {
        return UUID.randomUUID().toString();
    }
    
    /**
     * 生成MD5哈希值
     * @param str 要哈希的字符串
     * @return MD5哈希值，32位小写
     */
    public static String md5(String str) {
        // 修复：使用传统的if-else判断替代不支持的case标签组合
        if (str == null || str.isEmpty() || str.trim().isEmpty()) {
            return null;
        }
        return hash(str, "MD5");
    }
    
    /**
     * 生成SHA-256哈希值
     * @param str 要哈希的字符串
     * @return SHA-256哈希值，64位小写
     */
    public static String sha256(String str) {
        // 修复：使用传统的if-else判断替代不支持的case标签组合
        if (str == null || str.isEmpty() || str.trim().isEmpty()) {
            return null;
        }
        return hash(str, "SHA-256");
    }
    
    /**
     * 生成SHA-512哈希值
     * @param str 要哈希的字符串
     * @return SHA-512哈希值，128位小写
     */
    public static String sha512(String str) {
        // 修复：使用传统的if-else判断替代不支持的case标签组合
        if (str == null || str.isEmpty() || str.trim().isEmpty()) {
            return null;
        }
        return hash(str, "SHA-512");
    }
    
    /**
     * 通用哈希算法
     * @param str 要哈希的字符串
     * @param algorithm 哈希算法名称，如MD5、SHA-256、SHA-512等
     * @return 哈希值，小写十六进制字符串
     */
    private static String hash(String str, String algorithm) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hashBytes = digest.digest(str.getBytes(StandardCharsets.UTF_8));
            
            // 将字节数组转换为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("不支持的哈希算法: " + algorithm, e);
        }
    }
    
    /**
     * 生成随机字符串
     * @param length 字符串长度
     * @return 随机字符串，包含字母和数字
     */
    public static String generateRandomString(int length) {
        // 修复：使用传统的if-else判断替代不支持的基元模式
        if (length <= 0) {
            return "";
        }
        
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        
        return sb.toString();
    }
    
    /**
     * 生成随机数字字符串
     * @param length 字符串长度
     * @return 随机数字字符串
     */
    public static String generateRandomNumberString(int length) {
        // 修复：使用传统的if-else判断替代不支持的基元模式
        if (length <= 0) {
            return "";
        }
        
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(length);
        
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        
        return sb.toString();
    }
    
    /**
     * 密码强度检查
     * @param password 密码
     * @return true：密码强度符合要求（至少8位，包含字母和数字），false：密码强度不符合要求
     */
    public static boolean checkPasswordStrength(String password) {
        // 修复：使用传统的if-else判断替代不支持的case标签组合
        if (password == null || password.isEmpty() || password.trim().isEmpty() || password.length() < 8) {
            return false;
        }
        
        // 检查是否包含字母和数字
        boolean hasLetter = false;
        boolean hasDigit = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
            
            if (hasLetter && hasDigit) {
                return true;
            }
        }
        
        return false;
    }
}