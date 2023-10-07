//package com.example.first.controller;
//
//
//import com.example.first.dto.DietDto;
//import com.example.first.dto.UserDto;
//import com.example.first.mapper.BoardMapper;
//import com.example.first.mapper.HomeMapper;
//import com.example.first.service.BoardService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Controller
//@RequestMapping("/boards")
//@RequiredArgsConstructor
//public class RankController {
//    private final BoardService boardService;
//    private final BoardMapper boardMapper;
//    private final HomeMapper homeMapper;
//
//    public String getUsername() {
//        String username = null;
//        UserDto userDto = null;
//        // ============== 현재 로그인한 사용자 정보 가져오기 ===============
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            username = authentication.getName(); // 사용자 이메일
//
//            userDto = homeMapper.findByUsername(username);
//            if (userDto != null) {
//                String role = userDto.getRole();
//                System.out.println("role ===== " + role);
//            }
//        } else {
//            // 로그인하지 않은 경우, username을 비워두거나 다른 값을 넣어서 전달
//        }
//        // ==============================================================
//        return username;
//    }
//
//    public UserDto getUserDto() {
//        String username = null;
//        UserDto userDto = null;
//        // ============== 현재 로그인한 사용자 정보 가져오기 ===============
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            username = authentication.getName(); // 사용자 이메일
//
//            userDto = homeMapper.findByUsername(username);
//            if (userDto != null) {
//                String role = userDto.getRole();
//                System.out.println("role ===== " + role);
//            }
//        } else {
//            // 로그인하지 않은 경우, username을 비워두거나 다른 값을 넣어서 전달
//        }
//        // ==============================================================
//        return userDto;
//    }
//
//    // 통계 페이지
//    // 통계 폼
//    @GetMapping("/statistics")
//    public String showStatistics(Model model) throws JsonProcessingException {
//        String username = getUsername();
//        UserDto userDto = getUserDto();
//        model.addAttribute("role", userDto.getRole());
//        model.addAttribute("username", username);
//        model.addAttribute("nickname", userDto.getNickname());
//        model.addAttribute("userDto", userDto);
//
//        DietDto dietListByUsernameDaily = boardMapper.findDietListByUsernameDaily(username);
//        model.addAttribute("dietDaily" , dietListByUsernameDaily);
//
//        List<DietDto> dietListByUsernameWeekly = boardMapper.findDietListByUsernameWeekly(username);
//
//        // Java 객체 목록을 JSON 문자열로 변환하여 모델에 추가합니다.
//        // jsp에서 사용할거면 그냥 오브젝터매퍼 없이 모델에 담아도 되지만,
//        // 자바스크립트에서 동적으로 차트만들기로 쓸거라서 오브젝트매퍼로 자바객체 -> json 문자열로 변환하여 모델로 넘겨야 한다.
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        String dietWeeklyListJson = objectMapper.writeValueAsString(dietListByUsernameWeekly);
//
//        model.addAttribute("dietWeeklyList", dietWeeklyListJson);
//
//
//        return "board/statistics";
//    }
//
//    // 랭킹 페이지
//    @GetMapping("/rank")
//    public String showRank(Model model) throws JsonProcessingException {
//        String username = getUsername();
//        UserDto userDto = getUserDto();
//        model.addAttribute("role", userDto.getRole());
//        model.addAttribute("username", username);
//        model.addAttribute("nickname", userDto.getNickname());
//        model.addAttribute("userDto", userDto);
//
//        List<HashMap<String, Object>> dietList = boardMapper.findAllUserDietListWeekly();
//
//        // 랭킹에 보여줄 해시맵 생성 (닉네임, 적정 식사 횟수를 담은 맵)
//        HashMap<String, Integer> rankMap = new HashMap<>();
//
//        for (HashMap<String, Object> map : dietList) {
//            String mapUsername = (String) map.get("username");
//            UserDto mapUser = homeMapper.findByUsername(mapUsername);
//            String nickname = mapUser != null ? mapUser.getNickname() : "Unknown"; // mapUser가 null인 경우 처리
//            Long resultGoodCount = (Long) map.get("result_good_count");
//            System.out.println("resultGoodCount = " + resultGoodCount);
//
//            // 일주일 간
//            // intakeResult = "적정" 인 횟수가 가로축, 세로축이 횟수가 가장 많은 사람의 nickname 의 랭킹차트 생성
//            rankMap.put(nickname, Math.toIntExact(resultGoodCount));
//        }
//
//        // rankMap을 resultGoodCount를 기준으로 내림차순으로 정렬
//        List<Map.Entry<String, Integer>> sortedRankList = rankMap.entrySet().stream()
//                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
//                .collect(Collectors.toList());
//
//        // 정렬된 데이터를 LinkedHashMap으로 변환
//        LinkedHashMap<String, Integer> sortedRankMap = new LinkedHashMap<>();
//        for (Map.Entry<String, Integer> entry : sortedRankList) {
//            sortedRankMap.put(entry.getKey(), entry.getValue());
//        }
//
//        // 직렬화 한 후에 모델에 담아 넘기기
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        String rankingMap = objectMapper.writeValueAsString(sortedRankMap);
//
//        System.out.println("rankingMap = " + rankingMap);
//        for (Map.Entry<String, Integer> stringIntegerEntry : sortedRankMap.entrySet()) {
//            System.out.println("stringIntegerEntry = " + stringIntegerEntry);
//        }
//
//        model.addAttribute("rankingMap", rankingMap); // 이름 지정 안해줌 ;;;;;
//
//        return "board/rank";
//    }
//}
