package com.smartcity.common;

/**
 * 通用分页请求参数类
 * 用于接收前端传递的分页参数
 */
public class PageRequest {
    
    /**
     * 当前页码，默认第1页
     */
    private int pageNum = 1;
    
    /**
     * 每页大小，默认10条
     */
    private int pageSize = 10;
    
    /**
     * 排序字段
     */
    private String sortField;
    
    /**
     * 排序方向：asc（升序）或 desc（降序）
     */
    private String sortDirection = "asc";
    
    /**
     * 默认构造方法
     */
    public PageRequest() {
    }
    
    /**
     * 构造方法
     * @param pageNum 当前页码
     * @param pageSize 每页大小
     */
    public PageRequest(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
    
    /**
     * 构造方法
     * @param pageNum 当前页码
     * @param pageSize 每页大小
     * @param sortField 排序字段
     * @param sortDirection 排序方向
     */
    public PageRequest(int pageNum, int pageSize, String sortField, String sortDirection) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.sortField = sortField;
        this.sortDirection = sortDirection;
    }
    
    // getter和setter方法
    public int getPageNum() {
        return pageNum;
    }
    
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum > 0 ? pageNum : 1;
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize > 0 ? pageSize : 10;
    }
    
    public String getSortField() {
        return sortField;
    }
    
    public void setSortField(String sortField) {
        this.sortField = sortField;
    }
    
    public String getSortDirection() {
        return sortDirection;
    }
    
    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
    
    /**
     * 获取起始行号
     * @return 起始行号
     */
    public int getOffset() {
        return (pageNum - 1) * pageSize;
    }
    
    /**
     * 获取结束行号
     * @return 结束行号
     */
    public int getLimit() {
        return pageSize;
    }
    
    @Override
    public String toString() {
        return "PageRequest{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", sortField='" + sortField + '\'' +
                ", sortDirection='" + sortDirection + '\'' +
                '}';
    }
}