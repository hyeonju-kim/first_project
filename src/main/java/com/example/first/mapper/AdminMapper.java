package com.example.first.mapper;


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
}
