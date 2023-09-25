package com.example.first.dto;

import lombok.Data;

@Data
public class MenuDto {
    private int seq;
    private String menuName;
    private String url;
    private String auth;
    private String useYN;
    private int order;
    private String regDate;
    private String regId;
}
