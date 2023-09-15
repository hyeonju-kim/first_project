package com.example.first.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BoardDto {
    private Long boardId;
    private String title;
    private String content;
    private String status;
    private LocalDateTime createdAt;
    private Long userId;

}
