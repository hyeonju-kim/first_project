package com.example.first.mapper;


import com.example.first.dto.ErrorDto;
import com.example.first.dto.MenuDto;
import com.example.first.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AdminMapper {
    List<UserDto> getAllUsers();

    int getUserCountByMonth(String formattedMonth);

    void insertUploadUsers(List<UserDto> userlist);

    // 메뉴 정보 조회
    List<MenuDto> getMenuTable();

    // 에러 정보 조회
    List<ErrorDto> getAllError();
}
