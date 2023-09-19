package com.example.first.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@RequiredArgsConstructor
public class ProfilePicture {
    private Long id;
    private String fileName;
    private String savePath; // profilePictureLocation
    private LocalDateTime regDate;
    private byte[] fileSize;
    private String flagDel = "N";
    private String fileExt;
    private String username;



    public ProfilePicture(String fileName, String savePath, LocalDateTime regDate, byte[] fileSize, String fileExt) {
        this.fileName = fileName;
        this.savePath = savePath;
        this.regDate = regDate;
        this.fileSize = fileSize;
        this.fileExt = fileExt;
    }

    public ProfilePicture(String fileName, String savePath, LocalDateTime regDate, byte[] fileSize, String fileExt, String username) {
        this.fileName = fileName;
        this.savePath = savePath;
        this.regDate = regDate;
        this.fileSize = fileSize;
        this.fileExt = fileExt;
        this.username = username;
    }
}
