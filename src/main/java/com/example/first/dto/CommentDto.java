package com.example.first.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Long commentId;
    private Long boardId; // 댓글이 속한 게시글의 ID
    private String content;
    private String createdAt;
    private String username; // 댓글 작성자의 사용자 이름
    private String nickname; // 댓글 작성자의 닉네임
    private Long parentCommentId;
    private String status = "N";  // 기본 값은 "N", 삭제 되면 "Y"
    private int level;
}
