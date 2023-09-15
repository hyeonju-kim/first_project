package com.example.first.service;


import com.example.first.dto.BoardDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
    private final List<BoardDto> boards = new ArrayList<>();
    private Long nextBoardId = 1L;

    @Override
    public List<BoardDto> getAllBoards() {
        return boards;
    }

    @Override
    public BoardDto getBoardById(Long boardId) {
        return boards.stream()
                .filter(board -> board.getBoardId().equals(boardId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Long createBoard(BoardDto boardDto) {
        boardDto.setBoardId(nextBoardId++);
        boards.add(boardDto);
        return boardDto.getBoardId();
    }

    @Override
    public void updateBoard(Long boardId, BoardDto boardDto) {
        boards.stream()
                .filter(board -> board.getBoardId().equals(boardId))
                .findFirst()
                .ifPresent(existingBoard -> {
                    existingBoard.setTitle(boardDto.getTitle());
                    existingBoard.setContent(boardDto.getContent());
                    existingBoard.setStatus(boardDto.getStatus());
                    existingBoard.setCreatedAt(boardDto.getCreatedAt());
                    existingBoard.setUserId(boardDto.getUserId());
                });
    }

    @Override
    public void deleteBoard(Long boardId) {
        boards.removeIf(board -> board.getBoardId().equals(boardId));
    }
}
