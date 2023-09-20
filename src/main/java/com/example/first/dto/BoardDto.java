package com.example.first.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@RequiredArgsConstructor
public class BoardDto {
    private Long boardId;
    private String title;
    private String content;
    private String status = "N";
    private String createdAt;
    private Long userId;
    private MultipartFile file;
    private String username;
    private String nickname;
    private List<CommentDto> comments; // 댓글 목록


    public BoardDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
