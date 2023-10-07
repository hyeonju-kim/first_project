package com.example.first.service;

import com.example.first.dto.UserDto;
import com.example.first.mapper.HomeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final HomeMapper homeMapper;

    // UserDetailsService를 구현한 UserDetailsServiceImpl 클래스입니다.
    // Spring Security에서 사용자 정보를 불러오는 역할을 합니다.

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto foundUser = homeMapper.findByUsername(username);

        if (foundUser == null) {
            throw new UsernameNotFoundException("해당 계정이 존재하지 않습니다.");
        }

        // 조회된 사용자 정보를 기반으로 UserDetailsImpl 객체를 생성하여 반환합니다.
        // UserDetailsImpl 클래스는 UserDetails 인터페이스를 구현하고, 사용자 정보를 포함하고 있습니다.
        return new UserDetailsImpl(foundUser);
    }
}
