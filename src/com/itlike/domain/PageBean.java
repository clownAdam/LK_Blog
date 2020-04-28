package com.itlike.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class PageBean<E> {
    /*当前页数*/
    private Integer currentPage;
    /*一页多少条数据*/
    private Integer pageSize;
    /*当前查询的角标*/
    private Integer index;
    /*总记录数*/
    private Integer totalCount;
    /*总页数*/
    private Integer totalPage;
    /*当前页的数据*/
    private List<E> list;

    public void setCurrentPage(Integer currentPage) {
        if(currentPage ==null){
            currentPage = 1;
        }
        this.currentPage = currentPage;
    }

    public void setPageSize(Integer pageSize) {
        if(pageSize == null){
            pageSize = 5;
        }
        this.pageSize = pageSize;
    }

    public Integer getIndex() {
        return (currentPage-1)*pageSize;
    }

    public Integer getTotalPage() {
        double ceil = Math.ceil(totalCount*1.0/pageSize);
        return (int)ceil;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", index=" + index +
                ", totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", list=" + list +
                '}';
    }
}
