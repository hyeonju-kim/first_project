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
    private String status = "N";  // 기본 값은 "N", 삭제 되면 "Y"
    private String createdAt;
    private Long userId;
    private MultipartFile file;
    private String username;
    private String nickname;
    private List<CommentDto> comments; // 댓글 목록
    private int cntLike; // 글의 총 좋아요 수
    private boolean like = false; // 현재 사용자가 like를 했는지 임시로

    // 경로를 저장하기 (파일경로명)
    private String fileLocation;

    // 파일 원본명
    private String fileOriginalName;


    public BoardDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
