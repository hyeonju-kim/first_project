package com.example.first.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY) //비어있지 않은 필드만 나타내는 어노테이션
public class UserDto {
    private Long userId;

//    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
//    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문, 숫자, 특수문자를 사용하세요.")
    private String password;

//    @NotBlank(message = "비밀번호를 재입력 해주세요.")
    private String passwordConfirm;

//    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

//    @NotBlank(message = "이메일은 필수 입력 값입니다.") // username에 email을 넣어 사용한다.
//    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    private String username; //이메일 (계정)

//    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
//    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
    private String nickname;


//    private String email;

//    @NotBlank(message = "휴대폰 번호는 필수 입력 값입니다.")
    private String phoneNumber;

    //잘못된경우에만
    private String message;

    private String authNumber; // 인증번호

    private String profilePicture;

    private String zipcode;
    private String streetAdr;
    private String detailAdr;

//    private Address address;

//    private String accessToken;
//
//    private String refreshToken;
//
//    public UserDto(String accessToken, String refreshToken){
//        this.accessToken = accessToken;
//        this.refreshToken = refreshToken;
//    }
//
//    public UserDto() {
//    }
//
//    // request -> service (requestDto를 받아서 Dto로 변환해준다)
//    public UserDto(UserRequestDto requestDto) {
//        this.password = requestDto.getPassword();
//        this.username = requestDto.getUsername();
//    }
//
//    // repository -> service
//    public UserDto(User user) {
////        this.email = user.getEmail();
//        this.username = user.getUsername();
//        this.password = user.getPassword();
//        this.nickname = user.getNickname();
//    }
//
//    // UserDto -> UserResponseDto (Dto에서 responseDto로 변환해준다)
//    public UserResponseDto convertToUserResponseDto() {
//        UserResponseDto userResponseDto = new UserResponseDto();
//        userResponseDto.setUserId(this.userId);
//        userResponseDto.setUsername(this.username);
//        userResponseDto.setNickname(this.nickname);
//        userResponseDto.setMessage(this.message);
//        return userResponseDto;
//    }

}
