package com.example.first.service;


import com.example.first.dto.*;
import com.example.first.mapper.BoardMapper;
import com.example.first.mapper.HomeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.first.service.UserServiceImpl.getFileExtension;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardMapper boardMapper;
    private final HomeMapper homeMapper;


//    // 토탈 게시판 글목록 조회 (페이징 x)
//    @Override
//    public List<BoardDto> getAllBoards() {
//        return boardMapper.getAllBoards();
//    }

    // 페이지 별 게시판 글목록 조회
    @Override
    public List<BoardDto> getBoardsByPage(int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize; // 현재 2페이지고 페이지사이즈가 10이면 offset은 10

        Map<String, Integer> params = new HashMap<>();
        params.put("pageSize", pageSize);
        params.put("offset", offset);
        return boardMapper.getBoardsByPage(params);
    }

    // 총 페이지 수 조회
    @Override
    public int getTotalPages(int pageSize) {
        return boardMapper.getTotalPages(pageSize); // 총 게시물 수가 101이고, 페이지사이즈가 10이면 -> 총 페이지 수는 11
    }


    // 특정 게시글 조회
    @Override
    public BoardDto getBoardById(Long boardId) {
        BoardDto boardDto = boardMapper.getBoardById(boardId);
        List<CommentDto> hierarchicalCommentsByBoardId = boardMapper.getHierarchicalCommentsByBoardId(boardId);

        // 1. 계층형 댓글 데이터를 가져온 후 각 댓글의 level 값을 설정
        for (CommentDto commentDto : hierarchicalCommentsByBoardId) {
            System.out.println(" 보드 서비스 임플 / 특정 게시글 조회 / 댓글 내용 확인 - commentDto.getContent() = " + commentDto.getContent());
            System.out.println(" 보드 서비스 임플 / 특정 게시글 조회 / 레벨 확인 - commentDto.getLevel() = " + commentDto.getLevel());
        }

        boardDto.setComments(hierarchicalCommentsByBoardId);
        System.out.println(" 보드 서비스 임플 / 특정 게시글 조회 - boardDto = " + boardDto);
        System.out.println(" 보드 서비스 임플 / 특정 게시글 조회 - boardDto.getTitle() = " + boardDto.getTitle());
        System.out.println(" 보드 서비스 임플 / 특정 게시글 조회 - boardDto.getContent() = " + boardDto.getContent());

        return boardDto;
    }

    // 게시판 아이디로 멀티파일들 조회
    @Override
    public List<BoardMultiFile> getBoardMultiFilesByBoardId(Long boardId) {
        return boardMapper.findBoardMultiFileByBoardId(boardId);
    }

    // 글 작성 (단일 멀티 파일)
//    @Override
//    public Long createBoard(BoardDto boardDto, MultipartFile file, String fileName, String originalName) throws IOException {
//
//        String savePath =  "C:\\multifile\\" + fileName;
//        String fileExt = getFileExtension(fileName);
//
//        // 현재 시간 지정
//        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String regDate = now.format(formatter);
//        boardDto.setCreatedAt(regDate);
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//
//        System.out.println(" 보드 서비스 임플 / 글 작성 - authentication =  " + authentication);
//        System.out.println(" 보드 서비스 임플 / 글 작성 - authentication.getName() =  " + authentication.getName());
//
//        UserDto user = homeMapper.findByUsername(username);
//        String nickname = user.getNickname();
//        System.out.println(" 보드 서비스 임플 / 글 작성 - nickname =  " + nickname);
//
//        boardDto.setNickname(nickname);
//        boardDto.setUsername(username);
//        boardDto.setFileLocation(savePath);
//        boardDto.setFileOriginalName(originalName);
//
//        Long boardId = boardMapper.createBoard(boardDto);
//
//        BoardMultiFile boardMultiFile = new BoardMultiFile(boardId, fileName, savePath, regDate, fileExt, username, originalName);
//        String s = boardMapper.storeBoardMultiFile(boardMultiFile);
//
//        System.out.println(" 보드 서비스 임플 / 글 작성 - boardMapper.createBoard(boardDto) / boardId =  " + boardId);
//        System.out.println(" 보드 서비스 임플 / 글 작성 - boardMapper.createBoard(boardDto) / picture.getFileName()=  " + boardMultiFile.getFileName());
//
//        return boardId;
//    }

    // 글 작성 (다중 멀티 파일)
    @Override
    public Long createBoard2(BoardDto boardDto, List<MultipartFile> files) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String regDate = now.format(formatter);
        boardDto.setCreatedAt(regDate);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserDto user = homeMapper.findByUsername(username);
        String nickname = user.getNickname();
        boardDto.setNickname(nickname);
        boardDto.setUsername(username);


        // 게시글 정보 저장
        Long boardId = boardMapper.createBoard(boardDto);

        // 멀티 첨부 파일 업로드 및 정보 저장
        if (files != null) {
            for (MultipartFile file : files) {
                if (file != null && !file.isEmpty()) { // 파일이 존재하고 비어있지 않은 경우에만 처리
                    // 내가 업로드 파일을 저장할 경로
                    String originalName = file.getOriginalFilename();
                    String fileName = System.currentTimeMillis() + "_" + originalName;

                    String saveRootPath =  "C:\\multifile"; // 업로드할 디렉토리 경로 설정
                    String savePath =  "C:\\multifile\\" + fileName; // 업로드할 디렉토리 경로 + 파일명 설정

                    // 저장할 파일 경로와 파일명 생성
                    File saveFile = new File(saveRootPath, fileName);

                    // 파일 업로드 및 정보 저장
                    try {
                        file.transferTo(saveFile);

                        String fileExt = getFileExtension(fileName);


                        BoardMultiFile multiFile = new BoardMultiFile(boardId, fileName, savePath, regDate, fileExt, username, originalName);
                        System.out.println("multiFile.getStatus() =============== " + multiFile.getStatus());

                        boardMapper.storeBoardMultiFile(multiFile);

                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new IOException("파일 업로드 실패 ㅠㅠ");
                    }
                }
            }
        }

        return boardId;
    }

    // 글 수정
    @Override
    public void updateBoard(Long boardId, BoardDto boardDto) {
        boardMapper.updateBoard(boardDto);
    }

    // 글 삭제
    @Override
    public void deleteBoard(Long boardId) {
        boardMapper.deleteBoard(boardId);
    }

    // 글 검색 (페이징 적용)
    @Override
    public List<BoardDto> getSearchBoardsByPage(String keyword, int currentPage, int pageSize) {

        int offset = (currentPage - 1) * pageSize; // 현재 2페이지고 페이지사이즈가 10이면 offset은 10

        Map<String, Object> params = new HashMap<>();
        params.put("pageSize", pageSize);
        params.put("offset", offset);
        params.put("keyword", keyword);

        return boardMapper.getSearchBoardsByPage(params);
    }

    // 검색된 게시물의 총 페이지 수 조회
    @Override
    public int getSearchBoardsTotalPages(String keyword, int pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("pageSize", pageSize);

        return boardMapper.getSearchBoardsTotalPages(params); // 총 게시물 수가 101이고, 페이지사이즈가 10이면 -> 총 페이지 수는 11
    }

    // 특정 게시글의 댓글 리스트 가져오기
    @Override
    public List<CommentDto> getAllCommentsByBoardId(Long boardId) {
        return null;
    }

    // 댓글 생성
    @Override
    public CommentDto createComment(CommentDto commentDto) {

        // 작성일 및 닉네임 넣어주기
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        System.out.println(" 보드 서비스 임플 / 댓글 작성 / 작성일 - formattedDateTime =  " + formattedDateTime);
        commentDto.setCreatedAt(formattedDateTime);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserDto userDto = homeMapper.findByUsername(username);
        String nickname = userDto.getNickname();
        System.out.println(" 보드 서비스 임플 / 댓글 작성 / 닉네임 - nickname =  " + nickname);


        commentDto.setUsername(username);
        commentDto.setNickname(nickname);

        Long commentId = boardMapper.createComment(commentDto);
        commentDto.setCommentId(commentId);

        return commentDto;
    }

    // 댓글 수정
    @Override
    public void updateComment(CommentDto commentDto) {
        boardMapper.updateComment(commentDto);
    }

    // 댓글 삭제
    @Override
    public void deleteComment(Long commentId) {
        boardMapper.deleteComment(commentId);
    }


    // fileId로 멀티파일 객체 가져와서 다운로드 (게시물에서 클릭 시 )
    @Override
    public BoardMultiFile findBoardMultiFileBySeq(Long fileId) {
        return boardMapper.findBoardMultiFileBySeq(fileId);
    }

    // 식단 정보 저장
    @Override
    public void insertDietRecord(DietDto dietDto) {
        String username = "";
        // ============== 현재 로그인한 사용자 정보 가져오기 ===============
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            username = authentication.getName(); // 사용자 이메일

            UserDto userDto = homeMapper.findByUsername(username);
            if (userDto != null) {
                String role = userDto.getRole();
                System.out.println("role ===== " + role);
            }
        } else {
            // 로그인하지 않은 경우, username을 비워두거나 다른 값을 넣어서 전달
        }
        // ==============================================================

        UserDto userDto = homeMapper.findByUsername(username);
        double requiredCalories = userDto.getRequiredCalories();

        Integer totalIntake = dietDto.getIntakeCaloriesMorning() + dietDto.getIntakeCaloriesLunch() + dietDto.getIntakeCaloriesDinner();

        String intakeResult;

        //하루 권장 칼로리보다 15%이상 많이 섭취하면 과다 , 15% 미만으로 섭취하면 부족, 그 외에는 적정
        if (totalIntake > requiredCalories * 1.15) {
            intakeResult = "과다";
        } else if (totalIntake < requiredCalories * 0.85) {
            intakeResult = "부족";
        } else {
            intakeResult = "적정";
        }

        DietDto dto = new DietDto(dietDto.getIntakeCaloriesMorning(), dietDto.getIntakeCaloriesLunch(), dietDto.getIntakeCaloriesDinner(), intakeResult, username);
        System.out.println("하루 섭취 상태: " + intakeResult);
        System.out.println("하루 권장 칼로리 :" + requiredCalories);
        System.out.println("하루에 총 섭취한 칼로리 :" + totalIntake);
        System.out.println("dto.getIntakeDate() : " + dto.getIntakeDate());
        System.out.println("username = " + username);

        boardMapper.insertDietRecord(dto);
    }


}
