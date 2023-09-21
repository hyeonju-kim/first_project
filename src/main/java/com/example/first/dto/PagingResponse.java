package com.example.first.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PagingResponse<T> {

    private List<BoardDto> list = new ArrayList<>();
    private Pagination pagination;

    public PagingResponse(List<BoardDto> list, Pagination pagination) {
        this.list.addAll(list);
        this.pagination = pagination;
    }

}

