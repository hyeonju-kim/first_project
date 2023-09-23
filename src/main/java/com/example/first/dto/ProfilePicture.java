package com.example.first.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class ProfilePicture {
    private Long id;
    private String fileName;
    private String savePath; // profilePictureLocation
    private String regDate;
    private byte[] fileSize;
    private String flagDel = "N";
    private String fileExt;
    private String username;
    private String originalName;



    public ProfilePicture(String fileName, String savePath, String regDate, byte[] fileSize, String fileExt) {
        this.fileName = fileName;
        this.savePath = savePath;
        this.regDate = regDate;
        this.fileSize = fileSize;
        this.fileExt = fileExt;
    }

    public ProfilePicture(String fileName, String savePath, String regDate, byte[] fileSize, String fileExt, String username, String originalName) {
        this.fileName = fileName;
        this.savePath = savePath;
        this.regDate = regDate;
        this.fileSize = fileSize;
        this.fileExt = fileExt;
        this.username = username;
        this.originalName = originalName;
    }
}
