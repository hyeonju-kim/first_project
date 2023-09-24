package com.example.first.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@RequiredArgsConstructor
public class BoardMultiFile {
    private Long fileId;
    private Long boardId;
    private String fileName;
    private String savePath; // profilePictureLocation
    private String regDate;
    private byte[] fileSize;
    private String flagDel = "N";
    private String fileExt;
    private String username;
    private String fileOriginalName;
    private MultipartFile file;



    public BoardMultiFile(String fileName, String savePath, String regDate, byte[] fileSize, String fileExt) {
        this.fileName = fileName;
        this.savePath = savePath;
        this.regDate = regDate;
        this.fileSize = fileSize;
        this.fileExt = fileExt;
    }



    public BoardMultiFile(String fileName, String savePath, String regDate, byte[] fileSize, String fileExt, String username, String originalName) {
        this.fileName = fileName;
        this.savePath = savePath;
        this.regDate = regDate;
        this.fileSize = fileSize;
        this.fileExt = fileExt;
        this.username = username;
        this.fileOriginalName = originalName;
    }

    public BoardMultiFile(Long boardId, String fileName, String savePath, String regDate,  String fileExt, String username, String originalName) {
        this.boardId = boardId;
        this.fileName = fileName;
        this.savePath = savePath;
        this.regDate = regDate;
        this.fileExt = fileExt;
        this.username = username;
        this.fileOriginalName = originalName;
    }
}