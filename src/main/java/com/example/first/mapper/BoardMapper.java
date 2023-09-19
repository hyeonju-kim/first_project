package com.example.first.mapper;

import com.example.first.dto.BoardDto;
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
}

