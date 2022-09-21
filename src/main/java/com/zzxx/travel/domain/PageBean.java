package com.zzxx.travel.domain;


import java.util.List;
import java.util.Objects;

public class PageBean<T> {
    private Integer totalCount;
    private Integer totalPage;
    private List<T> list;
    private Integer currentPage;
    private Integer rows;

    public PageBean() {
    }

    public PageBean(int totalCount, int totalPage, List list, int currentPage, int rows) {
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.list = list;
        this.currentPage = currentPage;
        this.rows = rows;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageBean pageBean = (PageBean) o;
        return totalCount == pageBean.totalCount && totalPage == pageBean.totalPage && currentPage == pageBean.currentPage && rows == pageBean.rows && Objects.equals(list, pageBean.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalCount, totalPage, list, currentPage, rows);
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", list=" + list +
                ", currentPage=" + currentPage +
                ", rows=" + rows +
                '}';
    }
}
