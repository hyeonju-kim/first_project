package com.example.first.service;

import com.example.first.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BoardService {
//    List<BoardDto> getAllBoards();

    // 페이징 처리 (현재 페이지와 페이지 사이즈 넣고, 해당 게시물 가져오기 - 예) 현재 2페이지고 페이지 사이즈 10이면 11~20 번째 게시물 반환)
    List<BoardDto> getBoardsByPage(int currentPage, int pageSize);

    // 페이징 처리 (페이지 사이즈 넣고, 총 페이지 개수 가져오기 - 예) 페이지 사이즈 10이고, 토탈 게시물 수 101 이면 페이지 총 수는 11)
    int getTotalPages(int pageSize);

    BoardDto getBoardById(Long boardId);

    // 글 작성 (단일 멀티 파일)
//    Long createBoard(BoardDto boardDto, MultipartFile file, String fileName, String originalName) throws IOException;

    // 글 작성 (다중 멀티 파일)
    Long createBoard2(BoardDto boardDto, List<MultipartFile> files) throws IOException;

    List<BoardMultiFile> getBoardMultiFilesByBoardId(Long boardId);

    void updateBoard(Long boardId, BoardDto boardDto);

    void deleteBoard(Long boardId);

    // 글 검색 (페이징 적용)
    List<BoardDto> getSearchBoardsByPage(String keyword, int currentPage, int pageSize);

    // 검색된 게시물의 총 페이지 수 조회
    int getSearchBoardsTotalPages(String keyword, int pageSize);

    // 특정 게시글의 댓글 리스트 가져오기
    List<CommentDto> getAllCommentsByBoardId(Long boardId);

    // 댓글 생성
    CommentDto createComment(CommentDto commentDto);

    // 댓글 수정
    void updateComment(CommentDto commentDto);

    // 댓글 삭제
    void deleteComment(Long commentId);


    // 멀티 파일 객체 가져오기
    BoardMultiFile findBoardMultiFileBySeq(Long fileId);

    void insertDietRecord(DietDto dietDto);
}
