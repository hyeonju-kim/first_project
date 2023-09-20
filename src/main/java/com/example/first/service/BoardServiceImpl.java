package com.example.first.service;


import com.example.first.dto.BoardDto;
import com.example.first.dto.CommentDto;
import com.example.first.dto.UserDto;
import com.example.first.mapper.BoardMapper;
import com.example.first.mapper.HomeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
//    private final List<BoardDto> boards = new ArrayList<>();
    private final BoardMapper boardMapper;
    private final HomeMapper homeMapper;
//    private Long nextBoardId = 1L;


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
//        List<CommentDto> allCommentsByBoardId = boardMapper.getAllCommentsByBoardId(boardId);
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
//        for (BoardDto board : boards) {
//            if (board.getBoardId().equals(boardId)) {
//                return board;
//            }
//        }
    }

    // 글 작성
    @Override
    public Long createBoard(BoardDto boardDto) {
//        List<BoardDto> allBoards = boardMapper.getAllBoards();
//        Long size = (long) allBoards.size();
//        boardDto.setBoardId(++size);

        // 현재 시간 지정
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        boardDto.setCreatedAt(formattedDateTime);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();


        System.out.println(" 보드 서비스 임플 / 글 작성 - authentication =  " + authentication);
        System.out.println(" 보드 서비스 임플 / 글 작성 - authentication.getName() =  " + authentication.getName());

        UserDto user = homeMapper.findByUsername(username);
        String nickname = user.getNickname();
        System.out.println(" 보드 서비스 임플 / 글 작성 - nickname =  " + nickname);

        boardDto.setNickname(nickname);
        boardDto.setUsername(username);


        Long boardId = boardMapper.createBoard(boardDto);

        System.out.println(" 보드 서비스 임플 / 글 작성 - boardMapper.createBoard(boardDto) =  " + boardId);


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


}
