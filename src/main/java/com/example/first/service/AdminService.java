package com.example.first.service;

import com.example.first.dto.UserDto;

import java.util.List;
import java.util.Map;

public interface AdminService {
    // 사용자 정보 모두 조회
    List<UserDto> getAllUsers();

    // 월별 가입자 수 통계 조회
    List<Map<String, Integer>> getUsersStatisticsByMonth();
}
