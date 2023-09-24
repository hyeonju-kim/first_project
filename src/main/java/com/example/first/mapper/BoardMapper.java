package com.example.first.mapper;

import com.example.first.dto.BoardDto;
import com.example.first.dto.BoardMultiFile;
import com.example.first.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface BoardMapper {
    // 토탈 게시판 글목록 조회 (페이징 x)
    List<BoardDto> getAllBoards();

    // 페이지 별 게시판 글목록 조회
    List<BoardDto> getBoardsByPage(Map<String, Integer> params);

    // 총 페이지 수 조회
    int getTotalPages(int pageSize);

    BoardDto getBoardById(Long boardId);

    Long createBoard(BoardDto boardDto);


    // 멀티파일 업로드 (단일)
    String storeBoardMultiFile(BoardMultiFile file);

    // 다중 멀티 파일 업로드
    void uploadMultiFiles(List<BoardMultiFile> fileList);

    void updateBoard(BoardDto boardDto);

    void deleteBoard(Long boardId);

    List<BoardDto> getSearchBoards(String keyword);

    //======== 댓글 ==========

    List<CommentDto> getAllCommentsByBoardId(Long boardId);

    List<CommentDto> getAllComments();



    // 계층형 댓글 조회
    List<CommentDto> getHierarchicalCommentsByBoardId(Long boardId);

    void updateComment(CommentDto commentDto);

    Long createComment(CommentDto commentDto);

    void deleteComment(Long commentId);


    // 코멘트 테이블에 레벨 업데이트
    void updateBoardLevel(CommentDto commentDto);

    // boardId로 멀티 파일 원본이름 가져오기
    List<String> findBoardMultiFileOriginalName(Long boardId);

    // boardId로 멀티 파일 경로 가져오기
    List<String> findBoardMultiFileSavePath(Long boardId);

    // boardId로 멀티 파일 파일명 가져오기
    List<String> findBoardMultiFileFileName(Long boardId);


    // fileId로 멀티 파일 리스트 가져오기
    List<BoardMultiFile> findBoardMultiFileByBoardId(Long boardId);

    // fileId로 멀티 파일 객체 가져오기
    BoardMultiFile findBoardMultiFileBySeq(Long fileId);
}

