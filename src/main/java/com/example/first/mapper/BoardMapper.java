package com.example.first.mapper;

import com.example.first.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
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

    // 특정 게시물 조회
    BoardDto getBoardById(Long boardId);

    // 글 작성
    Long createBoard(BoardDto boardDto);


    // 멀티파일 업로드 (단일)
    String storeBoardMultiFile(BoardMultiFile file);

    // 다중 멀티 파일 업로드
    void uploadMultiFiles(List<BoardMultiFile> fileList);

    // 특정 게시글의 멀티파일 fileName 조회 쿼리
    List<String> findAllMultiFilesName(Long boardId);

    //멀티파일 삭제 (flagDel = "Y" 로)
    void updateMultiFilesFlagDel(String fileName);

    // 게시글 수정
    void updateBoard(BoardDto boardDto);

    // 게시글 좋아요 후 업데이트
    void updateBoardLikes(BoardDto boardDto);

    // 게시글 좋아요 테이블에 인서트
    void saveBoardLike(BoardLikeDto boardLikeDto);

    // 게시글 좋아요 테이블에서 삭제 (좋아요 취소)
    void deleteBoardLike(String username);

    // 게시글 좋아요 되어있는지 확인
    List<BoardLikeDto> findBoardLike(String username);

    void deleteBoard(Long boardId);


    //======== 검색 ==========

    List<BoardDto> getSearchBoardsByPage(Map<String, Object> params);

    int getSearchBoardsTotalPages(Map<String, Object> params);

    //======== 댓글 ==========

    List<CommentDto> getAllCommentsByBoardId(Long boardId);

    List<CommentDto> getAllComments();

    CommentDto findCommentByCommentId(Long commentId);



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

    //  fileId로 멀티파일 객체 가져와서 다운로드 (게시물에서 클릭 시 )
    BoardMultiFile findBoardMultiFileBySeq(Long fileId);

    // 식이 관련
    void insertDietRecord(DietDto dietDto);

    // 해당 유저 식이 기록 모두 조회
    List<HashMap<String, Object>> findDietListByUsername(String username);

    // 해당 유저 식이 기록 최근 7일 조회
    List<DietDto> findDietListByUsernameWeekly(String username);

    // 해당 유저 식이 기록 오늘 것만 조회
    DietDto findDietListByUsernameDaily(String username);

    // 모든 유저 식이 기록 최근 7일 조회 (적정 횟수 카운트, 유저네임, 적정 횟수가 많은 순으로 조회)
    List<HashMap<String, Object>> findAllUserDietListWeekly();
}

