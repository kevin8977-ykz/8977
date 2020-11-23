package com.bjpowernode.p2p.entity;

import java.io.Serializable;
import java.util.List;

/**
 *  公共的分页返回的结果集
 */
public class PageListResult<T> implements Serializable {

    //总记录数
    private Integer total;

    //总页数
    private Integer totalPages;

    //当前页
    private Integer pageNo;

    //每页条数
    private Integer pageSize;

    //分页集合列表数据
    private List<T> data;


    @Override
    public String toString() {
        return "PageListResult{" +
                "total=" + total +
                ", totalPages=" + totalPages +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", data=" + data +
                '}';
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
