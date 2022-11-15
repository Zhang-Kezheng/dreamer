package com.zkz.dreamer.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class PageResult<T> {
    private long pageNum;
    private long pageSize;
    private long total;
    private Iterable<T> rows;

}
