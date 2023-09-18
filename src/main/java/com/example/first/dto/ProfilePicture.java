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

//    private MultipartFile profilePicture;

    public ProfilePicture(String fileName, String savePath, LocalDateTime regDate, byte[] fileSize, String flagDel, String fileExt) {
        this.fileName = fileName;
        this.savePath = savePath;
        this.regDate = regDate;
        this.fileSize = fileSize;
        this.flagDel = flagDel;
        this.fileExt = fileExt;
    }

    public ProfilePicture(String fileName, String savePath, LocalDateTime regDate, byte[] fileSize, String fileExt) {
        this.fileName = fileName;
        this.savePath = savePath;
        this.regDate = regDate;
        this.fileSize = fileSize;
        this.fileExt = fileExt;
    }
}
