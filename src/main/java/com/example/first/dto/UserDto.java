package com.example.first.dto;


import com.example.first.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY) //비어있지 않은 필드만 나타내는 어노테이션
public class UserDto {
    private Long userId;

//    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
//    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문, 숫자, 특수문자를 사용하세요.")
    private String password;

    private String passwordConfirm;

    private String username;

    private String nickname;

    private String email;

    private String phoneNumber;

    //잘못된경우에만
    private String message;

    private String authNumber;

    private String profilePicture;

    private String zipcode;
    private String streetAdr;
    private String detailAdr;

    public UserDto() {
    }

    // request -> service (requestDto를 받아서 Dto로 변환해준다)
    public UserDto(UserRequestDto requestDto) {
        this.password = requestDto.getPassword();
        this.username = requestDto.getUsername();
    }

    // repository -> service
    public UserDto(User user) {
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
    }

    // UserDto -> UserResponseDto (Dto에서 responseDto로 변환해준다)
    public UserResponseDto convertToUserResponseDto() {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUserId(this.userId);
        userResponseDto.setUsername(this.username);
        userResponseDto.setNickname(this.nickname);
        userResponseDto.setMessage(this.message);
        return userResponseDto;
    }

}
