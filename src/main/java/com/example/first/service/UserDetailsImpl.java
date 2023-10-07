package com.example.first.service;

import com.example.first.dto.UserDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private UserDto userDto;

    // UserDetailsImpl 클래스의 생성자로 UserDto 객체를 받아와서 저장합니다.
    public UserDetailsImpl(UserDto userDto) {
        this.userDto = userDto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
        // 사용자의 권한 정보를 반환합니다. 현재는 null을 반환하고 권한 정보를 사용하지 않는 것으로 설정되어 있습니다.
        //  role도 필요했었나..?
    }

    @Override
    public String getPassword() {
        return userDto.getPassword();
        // 사용자의 암호(비밀번호)를 반환합니다. UserDto에서 비밀번호 정보를 가져와 반환합니다.
    }

    @Override
    public String getUsername() {
        return userDto.getUsername();
        // 사용자의 고유한 식별자인 "username"을 반환합니다. UserDto에서 사용자 이름 정보를 가져와 반환합니다.
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
        // 사용자 계정의 만료 여부를 반환합니다. 현재는 항상 true를 반환하여 계정이 만료되지 않음을 나타냅니다.
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
        // 사용자 계정의 잠금 상태 여부를 반환합니다. 현재는 항상 true를 반환하여 계정이 잠겨있지 않음을 나타냅니다.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
        // 사용자 자격 증명(비밀번호)의 만료 여부를 반환합니다. 현재는 항상 true를 반환하여 자격 증명이 만료되지 않음을 나타냅니다.
    }

    @Override
    public boolean isEnabled() {
        return true;
        // 사용자 계정의 활성화 상태 여부를 반환합니다. 현재는 항상 true를 반환하여 계정이 활성화되어 있음을 나타냅니다.
    }
}