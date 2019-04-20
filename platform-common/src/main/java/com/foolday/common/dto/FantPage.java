package com.foolday.common.dto;


import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class FantPage<T> implements Serializable {
    private static final long serialVersionUID = -1904616193145050158L;
    private List<T> rows;
    private long totalCount;
    private long pageSize = 10;
    private long pageNo = 1;

    public FantPage() {
    }

    public FantPage(List<T> rows) {
        this.rows = rows;
        if (this.rows != null) {
            this.totalCount = (long) this.rows.size();
        }

    }

    public FantPage(List<T> rows, long totalCount, long pageNo) {
        this.rows = rows;
        this.totalCount = totalCount;
        this.pageNo = pageNo;
    }

    public FantPage(List<T> rows, long totalCount, long pageSize, long pageNo) {
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

    public long getTotalPage() {
        return this.rows != null && !this.rows.isEmpty() ? (long) Math.ceil((double) this.totalCount / (double) this.rows.size()) : 0;
    }

    public long getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(long pageNo) {
        this.pageNo = pageNo;
    }

    @SuppressWarnings("unchecked")
    public <R> FantPage<R> map(Function<? super T, ? extends R> mapper) {
        List<R> list = null;
        if (this.rows != null) {
            list = (List) this.rows.stream().map(mapper).collect(Collectors.toList());
        }

        return new FantPage(list, this.getTotalCount(), this.pageSize, this.pageNo);
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

    /**
     * builder模式
     *
     * @param <T>
     */
    public static class Builder<T> {
        private List<T> rows;
        private long totalCount;
        private long pageSize = 10;
        private long pageNo = 1;

        public Builder<T> setRows(List<T> rows) {
            this.rows = rows;
            return this;
        }

        public Builder<T> setTotalCount(long totalCount) {
            this.totalCount = totalCount;
            return this;
        }

        public Builder<T> setPageSize(long pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder<T> setPageNo(long pageNo) {
            this.pageNo = pageNo;
            return this;
        }

        public List<T> getRows() {
            return rows;
        }

        public long getTotalCount() {
            return totalCount;
        }

        public long getPageSize() {
            return pageSize;
        }

        public long getPageNo() {
            return pageNo;
        }

        public FantPage<T> build() {
            return new FantPage<>(getRows(), getTotalCount(), getPageSize(), getPageNo());
        }

        public static <T> FantPage<T> ofPage(IPage<T> selectPage) {
            return new FantPage.Builder()
                    .setPageNo(selectPage.getCurrent())
                    .setPageSize(selectPage.getSize()).setRows(selectPage.getRecords()).build();
        }
    }
}

