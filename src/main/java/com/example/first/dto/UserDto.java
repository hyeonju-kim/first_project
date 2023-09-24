package com.example.first.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY) //비어있지 않은 필드만 나타내는 어노테이션
public class UserDto {
    private Long userId;

    private String password;

    private String passwordConfirm;

    private String name;

    private String username; //이메일 (계정)

    private String nickname;

    private String phoneNumber;

    private String message;

    private String authNumber; // 인증번호


    // 경로를 저장하기 (파일경로명)
    private String profilePictureLocation;

    // 파일 원본명
    private String originalName;



    private String zipcode;
    private String streetAdr;
    private String detailAdr;

    private String role;
    private String regDate; // 2023-09-24


    public UserDto(String name, String username, String nickname, String phoneNumber, String zipcode, String streetAdr, String detailAdr, String regDate) {
        this.name = name;
        this.username = username;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.zipcode = zipcode;
        this.streetAdr = streetAdr;
        this.detailAdr = detailAdr;
        this.regDate = regDate;
    }

}
