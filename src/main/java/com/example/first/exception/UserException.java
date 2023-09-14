package com.example.first.exception;

import com.example.first.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserException extends Throwable {
    private String message;
    private HttpStatus status;
//    private UserResponseDto userResponseDto;
    private UserDto userDto;

    private ErrorBox errorBox;
    public UserException(ErrorBox errorBox) {
        this.errorBox = errorBox;
    }

    public UserException(String s, HttpStatus badRequest, Object o) {
        this.message = s;
        this.status = badRequest;
        this.userDto = (UserDto) o;
    }
}