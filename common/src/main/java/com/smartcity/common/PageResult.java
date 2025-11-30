package com.smartcity.common;

import java.util.List;

/**
 * 通用分页响应结果类
 * 用于封装分页查询的结果
 * @param <T> 数据类型
 */
public class PageResult<T> {
    
    /**
     * 总记录数
     */
    private long total;
    
    /**
     * 总页数
     */
    private int pages;
    
    /**
     * 当前页码
     */
    private int pageNum;
    
    /**
     * 每页大小
     */
    private int pageSize;
    
    /**
     * 当前页数据列表
     */
    private List<T> list;
    
    /**
     * 私有构造方法，防止直接实例化
     * @param total 总记录数
     * @param pages 总页数
     * @param pageNum 当前页码
     * @param pageSize 每页大小
     * @param list 数据列表
     */
    private PageResult(long total, int pages, int pageNum, int pageSize, List<T> list) {
        this.total = total;
        this.pages = pages;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.list = list;
    }
    
    /**
     * 静态工厂方法，创建分页结果
     * @param total 总记录数
     * @param pageNum 当前页码
     * @param pageSize 每页大小
     * @param list 数据列表
     * @param <T> 数据类型
     * @return 分页结果对象
     */
    public static <T> PageResult<T> of(long total, int pageNum, int pageSize, List<T> list) {
        // 计算总页数
        int pages = (int) Math.ceil((double) total / pageSize);
        return new PageResult<>(total, pages, pageNum, pageSize, list);
    }
    
    /**
     * 静态工厂方法，创建空分页结果
     * @param <T> 数据类型
     * @return 空分页结果对象
     */
    public static <T> PageResult<T> empty() {
        return new PageResult<>(0, 0, 1, 10, null);
    }
    
    /**
     * 静态工厂方法，创建空分页结果，带分页参数
     * @param pageNum 当前页码
     * @param pageSize 每页大小
     * @param <T> 数据类型
     * @return 空分页结果对象
     */
    public static <T> PageResult<T> empty(int pageNum, int pageSize) {
        return new PageResult<>(0, 0, pageNum, pageSize, null);
    }
    
    // getter和setter方法
    public long getTotal() {
        return total;
    }
    
    public void setTotal(long total) {
        this.total = total;
    }
    
    public int getPages() {
        return pages;
    }
    
    public void setPages(int pages) {
        this.pages = pages;
    }
    
    public int getPageNum() {
        return pageNum;
    }
    
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    public List<T> getList() {
        return list;
    }
    
    public void setList(List<T> list) {
        this.list = list;
    }
    
    /**
     * 是否有上一页
     * @return true：有上一页，false：没有上一页
     */
    public boolean hasPrevious() {
        return pageNum > 1;
    }
    
    /**
     * 是否有下一页
     * @return true：有下一页，false：没有下一页
     */
    public boolean hasNext() {
        return pageNum < pages;
    }
    
    @Override
    public String toString() {
        return "PageResult{" +
                "total=" + total +
                ", pages=" + pages +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", list.size()=" + (list != null ? list.size() : 0) +
                '}';
    }
}