package com.example.first.dto;

import lombok.Data;

@Data
public class BoardLikeDto {
    private Long seq;
    private Long boardId;
    private String username;

    public BoardLikeDto(Long boardId, String username) {
        this.boardId = boardId;
        this.username = username;
    }
}
