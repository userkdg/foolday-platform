package com.foolday.common.dto;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FantPage<T> implements Serializable {
    private static final long serialVersionUID = -1904616193145050158L;
    private List<T> rows;
    private long totalCount;
    private int pageSize = 10;
    private int pageNo = 1;

    public FantPage() {
    }

    public FantPage(List<T> rows) {
        this.rows = rows;
        if (this.rows != null) {
            this.totalCount = (long)this.rows.size();
        }

    }

    public FantPage(List<T> rows, long totalCount, int pageNo) {
        this.rows = rows;
        this.totalCount = totalCount;
        this.pageNo = pageNo;
    }

    public FantPage(List<T> rows, long totalCount, int pageSize, int pageNo) {
        this.rows = rows;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.pageNo = pageNo;
    }

    public List<T> getRows() {
        return this.rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return this.rows != null && !this.rows.isEmpty() ? (int)Math.ceil((double)this.totalCount / (double)this.rows.size()) : 0;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public <R> FantPage<R> map(Function<? super T, ? extends R> mapper) {
        List<R> list = null;
        if (this.rows != null) {
            list = (List)this.rows.stream().map(mapper).collect(Collectors.toList());
        }

        return new FantPage(list, this.getTotalCount(), this.pageSize, this.pageNo);
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}

