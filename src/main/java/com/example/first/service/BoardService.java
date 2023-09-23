package com.example.first.service;

import com.example.first.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BoardService {
    List<BoardDto> getAllBoards();

    BoardDto getBoardById(Long boardId);

    Long createBoard(BoardDto boardDto, MultipartFile file, String fileName, String originalName) throws IOException;

    void updateBoard(Long boardId, BoardDto boardDto);

    void deleteBoard(Long boardId);

    List<BoardDto> getSearchBoards(String keyword);

    // 특정 게시글의 댓글 리스트 가져오기
    List<CommentDto> getAllCommentsByBoardId(Long boardId);

    // 댓글 생성
    CommentDto createComment(CommentDto commentDto);

    // 댓글 수정
    void updateComment(CommentDto commentDto);

    // 댓글 삭제
    void deleteComment(Long commentId);

    // 게시글 리스트 조회
    PagingResponse<BoardDto> findAllBoards(final SearchDto params);

    // 멀티 파일 객체 가져오기
    BoardMultiFile findBoardMultiFileBySeq(Long fileId);
}
