package com.example.first.controller;


import com.example.first.dto.ErrorDto;
import com.example.first.dto.MenuDto;
import com.example.first.dto.UserDto;
import com.example.first.mapper.AdminMapper;
import com.example.first.mapper.HomeMapper;
import com.example.first.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RequiredArgsConstructor
@Controller
@Slf4j
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {// 🎯🎯🎯🎯🎯 7개 API
    private final AdminService adminService;
    private final HomeMapper homeMapper;
    private final AdminMapper adminMapper;
    public String getUsername() {
        String username = null;
        UserDto userDto = null;
        // ============== 현재 로그인한 사용자 정보 가져오기 ===============
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            username = authentication.getName(); // 사용자 이메일

            userDto = homeMapper.findByUsername(username);
            if (userDto != null) {
                String role = userDto.getRole();
                System.out.println("role ===== " + role);
            }
        } else {
            // 로그인하지 않은 경우, username을 비워두거나 다른 값을 넣어서 전달
        }
        // ==============================================================
        return username;
    }

    public UserDto getUserDto() {
        String username = null;
        UserDto userDto = null;
        // ============== 현재 로그인한 사용자 정보 가져오기 ===============
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            username = authentication.getName(); // 사용자 이메일

            userDto = homeMapper.findByUsername(username);
            if (userDto != null) {
                String role = userDto.getRole();
                System.out.println("role ===== " + role);
            }
        } else {
            // 로그인하지 않은 경우, username을 비워두거나 다른 값을 넣어서 전달
        }
        // ==============================================================
        return userDto;
    }


    // 🎯 1. 사용자 정보가 모두 담긴 리스트 조회
    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<UserDto> users = adminService.getAllUsers(); // UserService에서 모든 사용자 정보 가져오기


        // ============== 현재 로그인한 사용자 정보 가져오기 ===============
        String username = getUsername();
        UserDto userDto = getUserDto();

        model.addAttribute("username", username);
        model.addAttribute("nickname", userDto.getNickname());
        model.addAttribute("role", userDto.getRole());
        // ==============================================================


        model.addAttribute("users", users);
        return "admin/users";
    }

    // 🎯 2. 마이페이지 화면 - 사용자 이름 클릭 시 사용자 정보 보이도록
    @GetMapping("/userDetails")
    public ModelAndView userDetails(Model model, @RequestParam String username) {

        UserDto userDto = homeMapper.findByUsername(username);

        // 프로필 사진 경로 가져와서 저장하기 (미리 저장 못해서 일단 이렇게 가져와서 넣어주자,,)
        String originalName = homeMapper.findProfilePictureOriginalName(username);
        String profilePictureSavePath = homeMapper.findProfilePictureSavePath(username);
        String profilePictureFileName = homeMapper.findProfilePictureFileName(username);

        userDto.setOriginalName(originalName);
        userDto.setProfilePictureLocation(profilePictureSavePath);

        Map<String, Object> params = new HashMap<>();
        params.put("savePath", profilePictureSavePath);
        params.put("userDto", userDto);

        homeMapper.updateUserInsertSavePath(params);

        File file = new File("/img/" + profilePictureFileName);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/userDetails");
        mv.addObject("file", file);
        mv.addObject("user", userDto);
        return mv;
    }

    // 🎯 3. 사용자 정보 엑셀 다운로드
    @GetMapping("/downloadUsers")
    public AbstractXlsxView downloadUsers(HttpServletResponse response) throws IOException {
        // 사용자 목록을 데이터베이스 또는 서비스에서 가져오는 코드를 작성
        List<UserDto> users = adminService.getAllUsers();

        // 엑셀 워크북 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("사용자 목록");

        // 열 헤더 스타일 생성
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // 열 헤더 생성
        Row headerRow = sheet.createRow(0);
        Cell cell0 = headerRow.createCell(0);
        cell0.setCellValue("사용자 ID");
        cell0.setCellStyle(headerCellStyle);

        Cell cell1 = headerRow.createCell(1);
        cell1.setCellValue("이름");
        cell1.setCellStyle(headerCellStyle);

        Cell cell2 = headerRow.createCell(2);
        cell2.setCellValue("이메일");
        cell2.setCellStyle(headerCellStyle);

        Cell cell3 = headerRow.createCell(3);
        cell3.setCellValue("닉네임");
        cell3.setCellStyle(headerCellStyle);

        Cell cell4 = headerRow.createCell(4);
        cell4.setCellValue("전화번호");
        cell4.setCellStyle(headerCellStyle);

        Cell cell5 = headerRow.createCell(5);
        cell5.setCellValue("우편번호");
        cell5.setCellStyle(headerCellStyle);

        Cell cell6 = headerRow.createCell(6);
        cell6.setCellValue("도로명주소");
        cell6.setCellStyle(headerCellStyle);

        Cell cell7 = headerRow.createCell(7);
        cell7.setCellValue("상세주소");
        cell7.setCellStyle(headerCellStyle);

        Cell cell8 = headerRow.createCell(8);
        cell8.setCellValue("가입일");
        cell8.setCellStyle(headerCellStyle);

// 데이터 행 생성
        int rowNum = 1; // 현재 행
        String prevRegDate = null; // 이전 행 가입일 저장
        int mergeStartRow = 1; // 병합 시작 행
        int mergeEndRow = 1; // 병합 끝 행

        for (UserDto user : users) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(user.getUserId());
            row.createCell(1).setCellValue(user.getName());
            row.createCell(2).setCellValue(user.getUsername());
            row.createCell(3).setCellValue(user.getNickname());
            row.createCell(4).setCellValue(user.getPhoneNumber());
            row.createCell(5).setCellValue(user.getZipcode());
            row.createCell(6).setCellValue(user.getStreetAdr());
            row.createCell(7).setCellValue(user.getDetailAdr());
            row.createCell(8).setCellValue(user.getRegDate());

            // 가입일이 이전과 같으면 병합 범위 확장
            if (prevRegDate != null && prevRegDate.equals(user.getRegDate())) {
                mergeEndRow = rowNum; // 병합 끝 행 업데이트
            } else {
                // 이전과 다른 가입일이 나왔으므로 이전 병합 범위를 추가
                if (mergeStartRow < mergeEndRow) {
                    sheet.addMergedRegion(new CellRangeAddress(mergeStartRow, mergeEndRow - 1, 8, 8)); // 가입일 열 병합
                }
                mergeStartRow = rowNum; // 병합 시작 행 업데이트
                mergeEndRow = rowNum; // 병합 끝 행 초기화
            }

            prevRegDate = user.getRegDate();
        }


        // 마지막 남은 병합 영역 추가
        if (mergeStartRow < mergeEndRow) {
            sheet.addMergedRegion(new CellRangeAddress(mergeStartRow, mergeEndRow - 1, 8, 8)); // 가입일 열 병합
        }

//        sheet.addMergedRegion(new CellRangeAddress(2, 3, 8, 8)); // 병합 테스트
//        sheet.addMergedRegion(new CellRangeAddress(2, 4, 8, 8)); // 병합 테스트


        // HTTP 응답 헤더 설정
        response.setHeader("Content-Disposition", "attachment; filename=users.xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        // 엑셀 파일을 HTTP 응답으로 전송
        workbook.write(response.getOutputStream());

        // 엑셀 파일 생성 완료 후 워크북을 닫아줘야 합니다.
        workbook.close();

        // AbstractXlsxView를 반환합니다.
        return new AbstractXlsxView() {
            @Override
            protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
                // 여기에서는 아무 작업을 하지 않습니다.
            }
        };
    }

    // 🎯 4. 사용자 정보 업로드
    @PostMapping("/uploadUsers")
    public String uploadUsers(@RequestParam("file") MultipartFile file) throws IOException {
        // 디비에 저장할 유저 리스트 만들어 놓기
        ArrayList<UserDto> userlist = new ArrayList<>();

        // 업로드된 엑셀 파일을 읽기 위한 Workbook 객체 생성
        InputStream inputStream = file.getInputStream();
        Workbook workbook = new XSSFWorkbook(inputStream);

        // 첫 번째 시트 선택
        Sheet sheet = workbook.getSheetAt(0);

        // 헤더를 제외한 열 데이터 읽기
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() == 0) {
                continue; // 헤더 행 스킵
            }

            // 각 열의 데이터 읽기 (셀 번호에 따라서 데이터 추출)
            String name = row.getCell(0).getStringCellValue();
            String username = row.getCell(1).getStringCellValue();
            String nickname = row.getCell(2).getStringCellValue();
            String phoneNumber = row.getCell(3).getStringCellValue();
//            String zipcode = row.getCell(4).getStringCellValue();
            String zipcode = String.valueOf((long) row.getCell(4).getNumericCellValue()); // 숫자 셀을 문자열로 변환
            String streetAdr = row.getCell(5).getStringCellValue();
            String detailAdr = row.getCell(6).getStringCellValue();
            String regDate = row.getCell(7).getStringCellValue();

            UserDto userDto = new UserDto(name, username, nickname, phoneNumber, zipcode, streetAdr, detailAdr, regDate);
            userlist.add(userDto);
            adminMapper.insertUploadUsers(userlist);
        }

        workbook.close();
        inputStream.close();

        return "redirect:/admin/users";
    }

    // 🎯 5. 월별 가입자 수 통계 조회
    @GetMapping("/statistics")
    public String getUsersStatisticsPerMonth(Model model) {
        // 월별 가입자 수 통계 데이터를 서비스에서 가져옴
        List<Map<String, Integer>> statistics = adminService.getUsersStatisticsByMonth();

        // ============== 현재 로그인한 사용자 정보 가져오기 ===============
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // 사용자 이메일
            UserDto userDto = homeMapper.findByUsername(username);
            if (userDto != null) {
                String role = userDto.getRole();
                System.out.println("role ===== " + role);
                model.addAttribute("role", role);
            }
            // 모델에 사용자 정보 추가
            model.addAttribute("username", username);
            model.addAttribute("nickname", userDto.getNickname());
        } else {
            // 로그인하지 않은 경우, username을 비워두거나 다른 값을 넣어서 전달
            model.addAttribute("username", "");
        }
        // ==============================================================

        // 모델에 데이터를 추가
        model.addAttribute("statistics", statistics);

        return "admin/statistics";
    }

    // 🎯 6. 메뉴관리 조회
    @GetMapping("/menu")
    public String viewMenuTable(Model model) {
        List<MenuDto> menuDtoList = adminService.getMenuTable();
        model.addAttribute("menu", menuDtoList);

        // ============== 현재 로그인한 사용자 정보 가져오기 ===============
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // 사용자 이메일
            UserDto userDto = homeMapper.findByUsername(username);
            if (userDto != null) {
                String role = userDto.getRole();
                System.out.println("role ===== " + role);
                model.addAttribute("role", role);
            }
            // 모델에 사용자 정보 추가
            model.addAttribute("username", username);
            model.addAttribute("nickname", userDto.getNickname());

        } else {
            // 로그인하지 않은 경우, username을 비워두거나 다른 값을 넣어서 전달
            model.addAttribute("username", "");
        }
        // ==============================================================


        return "admin/menu";
    }

    // 🎯 7. 에러 목록 조회
    @GetMapping("/error")
    public String viewError(Model model) {
        // ============== 현재 로그인한 사용자 정보 가져오기 ===============
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // 사용자 이메일
            UserDto userDto = homeMapper.findByUsername(username);
            if (userDto != null) {
                String role = userDto.getRole();
                System.out.println("role ===== " + role);
                model.addAttribute("role", role);
            }
            // 모델에 사용자 정보 추가
            model.addAttribute("username", username);
            model.addAttribute("nickname", userDto.getNickname());

        } else {
            // 로그인하지 않은 경우, username을 비워두거나 다른 값을 넣어서 전달
            model.addAttribute("username", "");
        }

        List<ErrorDto> errorList = adminMapper.getAllError();

        for (ErrorDto errorDto : errorList) {
            String errorTime = errorDto.getErrorTime();
            String formattedTime = errorTime.substring(0, 19); // 처음부터 19번째 문자까지 추출
            errorDto.setErrorTime(formattedTime);
        }

        model.addAttribute("errorList", errorList);
        return "admin/error";

    }
}
