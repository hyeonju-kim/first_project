package com.example.first.service;


import com.example.first.dto.BoardDto;
import com.example.first.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
//    private final List<BoardDto> boards = new ArrayList<>();
    private final BoardMapper boardMapper;
    private Long nextBoardId = 1L;


    // 게시판 글 조회
    @Override
    public List<BoardDto> getAllBoards() {
        return boardMapper.getAllBoards();
    }

    // 특정 게시글 조회  ( 스트림 사용 )
//    @Override
//    public BoardDto getBoardById(Long boardId) {
//        return boards.stream()
//                .filter(board -> board.getBoardId().equals(boardId))
//                .findFirst()
//                .orElse(null);
//    }

    // 특정 게시글 조회
    @Override
    public BoardDto getBoardById(Long boardId) {

        BoardDto boardDto = boardMapper.getBoardById(boardId);

        System.out.println(" 보드 서비스 임플 / 특정 게시글 조회 - boardDto = " + boardDto);
        System.out.println(" 보드 서비스 임플 / 특정 게시글 조회 - boardDto.getTitle() = " + boardDto.getTitle());
        System.out.println(" 보드 서비스 임플 / 특정 게시글 조회 - boardDto.getContent() = " + boardDto.getContent());

        return boardDto;
//        for (BoardDto board : boards) {
//            if (board.getBoardId().equals(boardId)) {
//                return board;
//            }
//        }
    }

    // 글 작성
    @Override
    public Long createBoard(BoardDto boardDto) {
        List<BoardDto> allBoards = boardMapper.getAllBoards();
        Long size = (long) allBoards.size();
        boardDto.setBoardId(++size);

        // 현재 시간 지정
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = now.format(formatter);
        boardDto.setCreatedAt(formattedDateTime);

        boardMapper.createBoard(boardDto);
        return boardDto.getBoardId();
    }

    // 글 수정
    @Override
    public void updateBoard(Long boardId, BoardDto boardDto) {
        boardMapper.updateBoard(boardDto);

    }

    // 글 삭제
//    @Override
//    public void deleteBoard(Long boardId) {
//        boards.removeIf(board -> board.getBoardId().equals(boardId));
//    }

    @Override
    public void deleteBoard(Long boardId) {
        boardMapper.deleteBoard(boardId);
    }


}
