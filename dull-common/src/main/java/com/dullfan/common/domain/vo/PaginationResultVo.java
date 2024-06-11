package com.dullfan.common.domain.vo;

import lombok.Data;

import java.util.List;
import java.util.ArrayList;

@Data
public class PaginationResultVo<T> {
    private Integer totalCount;
    private Integer pageSize;
    private Integer pageNum;
    private Integer pageTotal;
    private List<T> list = new ArrayList<T>();

    public PaginationResultVo(Integer totalCount, Integer pageSize, Integer pageNum, List<T> list) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.list = list;
    }

    public PaginationResultVo(Integer totalCount, Integer pageSize, Integer pageNum, Integer pageTotal, List<T> list) {
        if (pageNum == 0) {
            pageNum = 1;
        }
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.pageTotal = pageTotal;
        this.list = list;
    }
}
