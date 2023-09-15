package com.example.first.service;

import com.example.first.dto.BoardDto;

import java.util.List;

public interface BoardService {
    List<BoardDto> getAllBoards();
    BoardDto getBoardById(Long boardId);
    Long createBoard(BoardDto boardDto);
    void updateBoard(Long boardId, BoardDto boardDto);
    void deleteBoard(Long boardId);
}
