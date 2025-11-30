package com.smartcity.common.utils;

/**
 * 字符串处理工具类
 * 提供字符串判空、去除空格、脱敏等功能
 * 使用JDK 25特性优化，包括模式匹配和简化的null处理
 */
public class StringUtils {
    
    /**
     * 判断字符串是否为null或空字符串
     * @param str 要判断的字符串
     * @return true：为null或空字符串，false：不为null且不为空字符串
     */
    public static boolean isEmpty(String str) {
        // 修复：使用传统的if-else判断替代不支持的case标签组合
        return str == null || str.isEmpty();
    }
    
    /**
     * 判断字符串是否为null或空字符串或只包含空格
     * @param str 要判断的字符串
     * @return true：为null或空字符串或只包含空格，false：不为null且不为空字符串且不只包含空格
     */
    public static boolean isBlank(String str) {
        // 修复：使用传统的if-else判断替代不支持的switch无条件模式
        if (str == null) {
            return true;
        }
        return str.trim().isEmpty();
    }
    
    /**
     * 判断字符串是否不为null且不为空字符串
     * @param str 要判断的字符串
     * @return true：不为null且不为空字符串，false：为null或空字符串
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    
    /**
     * 判断字符串是否不为null且不为空字符串且不只包含空格
     * @param str 要判断的字符串
     * @return true：不为null且不为空字符串且不只包含空格，false：为null或空字符串或只包含空格
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
    
    /**
     * 去除字符串两端的空格
     * @param str 要处理的字符串
     * @return 去除两端空格后的字符串，若原字符串为null则返回null
     */
    public static String trim(String str) {
        // 使用JDK 25模式匹配简化null检查
        return switch (str) {
            case null -> null;
            case String s -> s.trim();
        };
    }
    
    /**
     * 去除字符串所有空格
     * @param str 要处理的字符串
     * @return 去除所有空格后的字符串，若原字符串为null则返回null
     */
    public static String removeWhitespace(String str) {
        // 使用JDK 25模式匹配简化null检查
        return switch (str) {
            case null -> null;
            case String s -> s.replaceAll("\\s+", "");
        };
    }
    
    /**
     * 首字母大写
     * @param str 要处理的字符串
     * @return 首字母大写后的字符串，若原字符串为null或空则返回原字符串
     */
    public static String capitalize(String str) {
        // 修复：使用传统的if-else判断替代不支持的case标签组合
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    
    /**
     * 首字母小写
     * @param str 要处理的字符串
     * @return 首字母小写后的字符串，若原字符串为null或空则返回原字符串
     */
    public static String uncapitalize(String str) {
        // 修复：使用传统的if-else判断替代不支持的case标签组合
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
    
    /**
     * 字符串脱敏处理
     * @param str 要脱敏的字符串
     * @param start 保留的起始长度
     * @param end 保留的结束长度
     * @param mask 掩码字符
     * @return 脱敏后的字符串，若原字符串为null则返回null
     */
    public static String mask(String str, int start, int end, char mask) {
        // 修复：使用传统的if-else判断替代不支持的case标签组合
        if (str == null || str.isEmpty()) {
            return str;
        }
        
        int length = str.length();
        if (start + end >= length) {
            return str;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, start));
        sb.append(String.valueOf(mask).repeat(length - start - end));
        sb.append(str.substring(length - end));
        return sb.toString();
    }
    
    /**
     * 字符串脱敏处理，使用默认掩码字符* 
     * @param str 要脱敏的字符串
     * @param start 保留的起始长度
     * @param end 保留的结束长度
     * @return 脱敏后的字符串，若原字符串为null则返回null
     */
    public static String mask(String str, int start, int end) {
        return mask(str, start, end, '*');
    }
    
    /**
     * 手机号脱敏，保留前3位和后4位
     * @param phone 手机号
     * @return 脱敏后的手机号，格式为138****8888
     */
    public static String maskPhone(String phone) {
        // 修复：使用传统的if-else判断替代不支持的case标签组合
        if (phone == null || phone.isEmpty()) {
            return phone;
        }
        return mask(phone, 3, 4);
    }
    
    /**
     * 身份证号脱敏，保留前6位和后4位
     * @param idCard 身份证号
     * @return 脱敏后的身份证号，格式为110101********1234
     */
    public static String maskIdCard(String idCard) {
        // 修复：使用传统的if-else判断替代不支持的case标签组合
        if (idCard == null || idCard.isEmpty()) {
            return idCard;
        }
        return mask(idCard, 6, 4);
    }
    
    /**
     * 邮箱脱敏，保留前3位和域名
     * @param email 邮箱
     * @return 脱敏后的邮箱，格式为abc****@example.com
     */
    public static String maskEmail(String email) {
        // 修复：使用传统的if-else判断替代不支持的case标签组合
        if (email == null || email.isEmpty()) {
            return email;
        }
        
        int atIndex = email.indexOf('@');
        if (atIndex <= 3) {
            return email;
        }
        return mask(email, 3, email.length() - atIndex, '*');
    }
    
    /**
     * 安全地比较两个字符串是否相等，防止空指针异常
     * @param str1 第一个字符串
     * @param str2 第二个字符串
     * @return true：两个字符串相等，false：两个字符串不相等
     */
    public static boolean equals(String str1, String str2) {
        // 修复：使用传统的if-else判断替代不支持的数组模式匹配
        if (str1 == null && str2 == null) {
            return true;
        }
        if (str1 == null || str2 == null) {
            return false;
        }
        return str1.equals(str2);
    }
    
    /**
     * 安全地比较两个字符串是否相等，忽略大小写，防止空指针异常
     * @param str1 第一个字符串
     * @param str2 第二个字符串
     * @return true：两个字符串相等（忽略大小写），false：两个字符串不相等
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        // 修复：使用传统的if-else判断替代不支持的数组模式匹配
        if (str1 == null && str2 == null) {
            return true;
        }
        if (str1 == null || str2 == null) {
            return false;
        }
        return str1.equalsIgnoreCase(str2);
    }
    
    /**
     * 限制字符串长度
     * @param str 要处理的字符串
     * @param maxLength 最大长度
     * @return 限制长度后的字符串，若原字符串为null则返回null
     */
    public static String limitLength(String str, int maxLength) {
        // 使用JDK 25模式匹配简化null检查
        return switch (str) {
            case null -> null;
            case String s -> {
                if (s.length() <= maxLength) {
                    yield s;
                }
                yield s.substring(0, maxLength);
            }
        };
    }
    
    /**
     * 限制字符串长度，并在末尾添加省略号
     * @param str 要处理的字符串
     * @param maxLength 最大长度
     * @return 限制长度后的字符串，若原字符串为null则返回null，超过最大长度则添加省略号
     */
    public static String limitLengthWithEllipsis(String str, int maxLength) {
        // 使用JDK 25模式匹配简化null检查
        return switch (str) {
            case null -> null;
            case String s -> {
                if (s.length() <= maxLength) {
                    yield s;
                }
                yield s.substring(0, maxLength) + "...";
            }
        };
    }
}