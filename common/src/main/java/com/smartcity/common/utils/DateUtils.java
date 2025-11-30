package com.smartcity.common.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 日期处理工具类
 * 提供日期格式化、解析、计算等功能
 * 使用JDK 25特性优化，包括模式匹配和简化的null处理
 */
public class DateUtils {
    
    /**
     * 默认日期格式：yyyy-MM-dd
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    
    /**
     * 默认时间格式：HH:mm:ss
     */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    
    /**
     * 默认日期时间格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 通用格式化方法，支持LocalDate、LocalDateTime、LocalTime
     * 使用JDK 25模式匹配简化重载逻辑
     * @param temporal 日期时间对象
     * @param pattern 日期时间格式
     * @return 格式化后的字符串，null输入返回null
     */
    public static String format(Object temporal, String pattern) {
        // 使用JDK 25模式匹配简化类型判断
        return switch (temporal) {
            case null -> null;
            case LocalDate date -> date.format(DateTimeFormatter.ofPattern(pattern));
            case LocalDateTime dateTime -> dateTime.format(DateTimeFormatter.ofPattern(pattern));
            case LocalTime time -> time.format(DateTimeFormatter.ofPattern(pattern));
            default -> throw new IllegalArgumentException("不支持的日期时间类型: " + temporal.getClass().getName());
        };
    }
    
    /**
     * 格式化LocalDate为字符串
     * @param date LocalDate对象
     * @return 格式化后的字符串，格式为yyyy-MM-dd
     */
    public static String format(LocalDate date) {
        return format(date, DEFAULT_DATE_FORMAT);
    }
    
    /**
     * 格式化LocalDateTime为字符串
     * @param dateTime LocalDateTime对象
     * @return 格式化后的字符串，格式为yyyy-MM-dd HH:mm:ss
     */
    public static String format(LocalDateTime dateTime) {
        return format(dateTime, DEFAULT_DATE_TIME_FORMAT);
    }
    
    /**
     * 格式化LocalTime为字符串
     * @param time LocalTime对象
     * @return 格式化后的字符串，格式为HH:mm:ss
     */
    public static String format(LocalTime time) {
        return format(time, DEFAULT_TIME_FORMAT);
    }
    
    /**
     * 解析字符串为LocalDate
     * @param dateStr 日期字符串，格式为yyyy-MM-dd
     * @return LocalDate对象，null或空字符串返回null
     */
    public static LocalDate parseDate(String dateStr) {
        return parseDate(dateStr, DEFAULT_DATE_FORMAT);
    }
    
    /**
     * 解析字符串为LocalDate
     * @param dateStr 日期字符串
     * @param pattern 日期格式
     * @return LocalDate对象，null或空字符串返回null
     */
    public static LocalDate parseDate(String dateStr, String pattern) {
        // 修复：使用传统的if-else判断替代不支持的case标签组合
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }
    
    /**
     * 解析字符串为LocalDateTime
     * @param dateTimeStr 日期时间字符串，格式为yyyy-MM-dd HH:mm:ss
     * @return LocalDateTime对象，null或空字符串返回null
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        return parseDateTime(dateTimeStr, DEFAULT_DATE_TIME_FORMAT);
    }
    
    /**
     * 解析字符串为LocalDateTime
     * @param dateTimeStr 日期时间字符串
     * @param pattern 日期时间格式
     * @return LocalDateTime对象，null或空字符串返回null
     */
    public static LocalDateTime parseDateTime(String dateTimeStr, String pattern) {
        // 修复：使用传统的if-else判断替代不支持的case标签组合
        if (dateTimeStr == null || dateTimeStr.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
    }
    
    /**
     * 将Date转换为LocalDateTime
     * @param date Date对象
     * @return LocalDateTime对象，null输入返回null
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        // 使用JDK 25模式匹配简化null检查
        return switch (date) {
            case null -> null;
            default -> {
                Instant instant = date.toInstant();
                ZoneId zoneId = ZoneId.systemDefault();
                yield instant.atZone(zoneId).toLocalDateTime();
            }
        };
    }
    
    /**
     * 将LocalDateTime转换为Date
     * @param dateTime LocalDateTime对象
     * @return Date对象，null输入返回null
     */
    public static Date localDateTimeToDate(LocalDateTime dateTime) {
        // 使用JDK 25模式匹配简化null检查
        return switch (dateTime) {
            case null -> null;
            default -> {
                ZoneId zoneId = ZoneId.systemDefault();
                Instant instant = dateTime.atZone(zoneId).toInstant();
                yield Date.from(instant);
            }
        };
    }
    
    /**
     * 获取当前日期
     * @return 当前日期，格式为yyyy-MM-dd
     */
    public static String getCurrentDate() {
        return format(LocalDate.now());
    }
    
    /**
     * 获取当前时间
     * @return 当前时间，格式为HH:mm:ss
     */
    public static String getCurrentTime() {
        return format(LocalTime.now());
    }
    
    /**
     * 获取当前日期时间
     * @return 当前日期时间，格式为yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDateTime() {
        return format(LocalDateTime.now());
    }
    
    /**
     * 计算两个日期之间的天数差
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 天数差，null输入返回0
     */
    public static long daysBetween(LocalDate startDate, LocalDate endDate) {
        // 修复：使用传统的if-else判断替代不支持的数组模式匹配
        if (startDate == null || endDate == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(startDate, endDate);
    }
    
    /**
     * 计算两个日期时间之间的小时差
     * @param startDateTime 开始日期时间
     * @param endDateTime 结束日期时间
     * @return 小时差，null输入返回0
     */
    public static long hoursBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        // 修复：使用传统的if-else判断替代不支持的数组模式匹配
        if (startDateTime == null || endDateTime == null) {
            return 0;
        }
        return ChronoUnit.HOURS.between(startDateTime, endDateTime);
    }
    
    /**
     * 给日期添加指定天数
     * @param date 原日期
     * @param days 要添加的天数
     * @return 添加后的日期，null输入返回null
     */
    public static LocalDate plusDays(LocalDate date, long days) {
        // 使用JDK 25模式匹配简化null检查
        return switch (date) {
            case null -> null;
            default -> date.plusDays(days);
        };
    }
    
    /**
     * 给日期时间添加指定小时数
     * @param dateTime 原日期时间
     * @param hours 要添加的小时数
     * @return 添加后的日期时间，null输入返回null
     */
    public static LocalDateTime plusHours(LocalDateTime dateTime, long hours) {
        // 使用JDK 25模式匹配简化null检查
        return switch (dateTime) {
            case null -> null;
            default -> dateTime.plusHours(hours);
        };
    }
    
    /**
     * 判断日期是否在指定范围内
     * @param date 要判断的日期
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return true：在范围内，false：不在范围内或null输入
     */
    public static boolean isBetween(LocalDate date, LocalDate startDate, LocalDate endDate) {
        // 修复：使用传统的if-else判断替代不支持的数组模式匹配
        if (date == null || startDate == null || endDate == null) {
            return false;
        }
        return date.isAfter(startDate.minusDays(1)) && date.isBefore(endDate.plusDays(1));
    }
    
    /**
     * 判断日期时间是否在指定范围内
     * @param dateTime 要判断的日期时间
     * @param startDateTime 开始日期时间
     * @param endDateTime 结束日期时间
     * @return true：在范围内，false：不在范围内或null输入
     */
    public static boolean isBetween(LocalDateTime dateTime, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        // 修复：使用传统的if-else判断替代不支持的数组模式匹配
        if (dateTime == null || startDateTime == null || endDateTime == null) {
            return false;
        }
        return dateTime.isAfter(startDateTime.minusNanos(1)) && dateTime.isBefore(endDateTime.plusNanos(1));
    }
}