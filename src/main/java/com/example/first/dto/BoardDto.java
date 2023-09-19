package com.example.first.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BoardDto {
    private Long boardId;
    private String title;
    private String content;
    private String status = "N";
    private String createdAt;
    private Long userId;
    private MultipartFile file;






    public BoardDto(String title, String content) {
        this.title = title;
        this.content = content;
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();

        this.userId = 1L; // TODO 수정



    }

}
