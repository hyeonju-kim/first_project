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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto foundUser = homeMapper.findByUsername(username);

        System.out.println("username === " + username);
        System.out.println("homeMapper.findByUsername(username) === " + homeMapper.findByUsername(username));

        if (foundUser == null) {
            throw new UsernameNotFoundException("해당 계정이 존재하지 않습니다.");
        }

        return new UserDetailsImpl(foundUser);
    }
}
