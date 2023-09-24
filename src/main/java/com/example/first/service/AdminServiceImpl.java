package com.example.first.service;

import com.example.first.dto.UserDto;
import com.example.first.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
    private final AdminMapper adminMapper;

    @Override
    public List<UserDto> getAllUsers() {
        return adminMapper.getAllUsers();
    }

    // 월별 가입자 수를 map으로 만들기
    @Override
    public List<Map<String, Integer>> getUsersStatisticsByMonth() {
        List<Map<String, Integer>> statisticsMap = new ArrayList<>();

        // 월별 가입자 수 계산
        for (int month = 1; month <= 12; month++) {
            // 월을 두 자리 문자열로 포맷팅
            String formattedMonth = String.format("%02d", month);
            System.out.println(" 어드민 서비스 임플/ formattedMonth = " + formattedMonth);

            int userCount = adminMapper.getUserCountByMonth(formattedMonth);
            Map<String, Integer> monthStats = new HashMap<>();
            monthStats.put("month", month);
            monthStats.put("userCount", userCount);
            statisticsMap.add(monthStats);
        }
        return statisticsMap;
    }
}
