package com.example.first.scheduling;

import com.example.first.dto.UserDto;
import com.example.first.mapper.HomeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AfterRunSchedule {
    private final HomeMapper homeMapper;

    // 매일 자정마다 모든 유저의 마지막 로그인 시간을 불러와서 , 60일이 지난 경우 휴면처리(status = "N") 한다.

    @Scheduled(fixedRate = 100000) // 100초
    public void inactiveDetector() throws Exception {
        List<UserDto> allUsers = homeMapper.findAllUsers();

        // 현재 시간
        LocalDateTime currentTime = LocalDateTime.now();
        System.out.println("currentTime (현재 시간) =============== " + currentTime);


        // TODO 반복분 돌리지말고 쿼리에서 바로 가져오도록 수정 ( )

        for (UserDto user : allUsers) {
            if (user.getLastLoginDate() != null) {

                // 마지막 접속 시간 (예: 데이터베이스에서 가져온 유저의 마지막 접속 시간)
                Timestamp lastLoginTimestamp = Timestamp.valueOf(user.getLastLoginDate());
                Instant lastLoginInstant = lastLoginTimestamp.toInstant();
                LocalDateTime lastLoginTime = lastLoginInstant.atZone(ZoneId.systemDefault()).toLocalDateTime();

                System.out.println("lastLoginTime (유저의 마지막 접속 시간) ================ " + lastLoginTime);

                // 두 시간 간격 계산
                long secondsElapsed = ChronoUnit.SECONDS.between((Temporal) lastLoginTime, currentTime);
                System.out.println("secondsElapsed (미 접속 시간) ================= " + secondsElapsed);

                // 미접속 기간이 60일 이상인지 확인
                if (secondsElapsed >= 600) { // 100분(6000초)  // 60일을 초로 계산 (60*60*24*60)
                    // 휴면 처리 로직을 실행
                    homeMapper.updateUserStatusToN(user);
                    System.out.println( "================"+ user.getNickname() + " 님을 휴면처리 함 ===============");
                }
            }
        }
    }
}
