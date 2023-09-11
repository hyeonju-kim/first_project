package com.example.first.exception;

import com.example.first.dto.UserResponseDto;
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
    private UserResponseDto userResponseDto;
}
