package com.example.first.mapper;

import com.example.first.dto.BoardDto;
import com.example.first.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BoardMapper {

    List<BoardDto> getAllBoards();

    BoardDto getBoardById(Long boardId);

    Long createBoard(BoardDto boardDto);

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
}

