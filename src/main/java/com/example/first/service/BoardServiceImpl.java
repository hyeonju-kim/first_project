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
import java.util.Collections;
import java.util.List;

import static com.example.first.service.UserServiceImpl.getFileExtension;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardMapper boardMapper;
    private final HomeMapper homeMapper;


    // 게시판 글 조회
    @Override
    public List<BoardDto> getAllBoards() {
        return boardMapper.getAllBoards();
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
    @Override
    public Long createBoard(BoardDto boardDto, MultipartFile file, String fileName, String originalName) throws IOException {

        String savePath =  "C:\\multifile\\" + fileName;
        String fileExt = getFileExtension(fileName);

        // 현재 시간 지정
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String regDate = now.format(formatter);
        boardDto.setCreatedAt(regDate);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        System.out.println(" 보드 서비스 임플 / 글 작성 - authentication =  " + authentication);
        System.out.println(" 보드 서비스 임플 / 글 작성 - authentication.getName() =  " + authentication.getName());

        UserDto user = homeMapper.findByUsername(username);
        String nickname = user.getNickname();
        System.out.println(" 보드 서비스 임플 / 글 작성 - nickname =  " + nickname);

        boardDto.setNickname(nickname);
        boardDto.setUsername(username);
        boardDto.setFileLocation(savePath);
        boardDto.setFileOriginalName(originalName);

        Long boardId = boardMapper.createBoard(boardDto);

        BoardMultiFile boardMultiFile = new BoardMultiFile(boardId, fileName, savePath, regDate, fileExt, username, originalName);
        String s = boardMapper.storeBoardMultiFile(boardMultiFile);

        System.out.println(" 보드 서비스 임플 / 글 작성 - boardMapper.createBoard(boardDto) / boardId =  " + boardId);
        System.out.println(" 보드 서비스 임플 / 글 작성 - boardMapper.createBoard(boardDto) / picture.getFileName()=  " + boardMultiFile.getFileName());

        return boardId;
    }

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

    // 글 검색
    @Override
    public List<BoardDto> getSearchBoards(String keyword) {
        return boardMapper.getSearchBoards(keyword);
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

    /**
     * 게시글 리스트 조회
     * @param params - search conditions
     * @return list & pagination information
     */
    @Override
    public PagingResponse<BoardDto> findAllBoards(final SearchDto params) {

        // 조건에 해당하는 데이터가 없는 경우, 응답 데이터에 비어있는 리스트와 null을 담아 반환
        int count = boardMapper.countBoards(params);
        if (count < 1) {
            return new PagingResponse<>(Collections.emptyList(), null);
        }

        // Pagination 객체를 생성해서 페이지 정보 계산 후 SearchDto 타입의 객체인 params에 계산된 페이지 정보 저장
        Pagination pagination = new Pagination(count, params);
        params.setPagination(pagination);

        // 계산된 페이지 정보의 일부(limitStart, recordSize)를 기준으로 리스트 데이터 조회 후 응답 데이터 반환
        List<BoardDto> list = boardMapper.findAllBoards(params);
        for (BoardDto boardDto : list) {
            System.out.println(" 보드 서비스 임플 / 전체 게시글 조회 (페이징) - boardDto.getBoardId() = " + boardDto.getBoardId());
            System.out.println(" 보드 서비스 임플 / 전체 게시글 조회 (페이징) - boardDto.getTitle() = " + boardDto.getTitle());
            System.out.println(" 보드 서비스 임플 / 전체 게시글 조회 (페이징) - boardDto.getNickname() = " + boardDto.getNickname());
            System.out.println(" 보드 서비스 임플 / 전체 게시글 조회 (페이징) - boardDto.getCreatedAt() = " + boardDto.getCreatedAt());
        }
        return new PagingResponse<>(list, pagination);
    }

    @Override
    public BoardMultiFile findBoardMultiFileBySeq(Long fileId) {
        return boardMapper.findBoardMultiFileBySeq(fileId);
    }


}
