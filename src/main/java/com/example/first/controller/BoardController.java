package com.example.first.controller;


import com.example.first.dto.BoardDto;
import com.example.first.dto.CommentDto;
import com.example.first.dto.PagingResponse;
import com.example.first.dto.SearchDto;
import com.example.first.mapper.BoardMapper;
import com.example.first.service.BoardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final BoardMapper boardMapper;


//    // 게시판 글 조회 1 - 기본
//    @GetMapping
//    public String getAllBoards(Model model) {
//        List<BoardDto> boards = boardService.getAllBoards();
//
//
//        System.out.println(" 보드 컨트롤러 / 게시판 글목록 조회 - boards = " + boards);
//        System.out.println(" 보드 컨트롤러 / 게시판 글목록 조회 - boards.size() = " + boards.size());
//
//        for (BoardDto board : boards) {
//            System.out.println("보드 컨트롤러 / 글목록 for문 - board.getBoardId() ::" + board.getBoardId());
//            System.out.println("보드 컨트롤러 / 글목록 for문 - board.getTitle() ::" + board.getTitle());
//            System.out.println("보드 컨트롤러 / 글목록 for문 - board.getContent() ::" + board.getContent());
//            System.out.println("보드 컨트롤러 / 글목록 for문 - board.getCreatedAt() ::" + board.getCreatedAt());
//        }
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//
//        model.addAttribute("boards", boards);
//        return "board"; // "board/list"는 게시판 목록을 보여줄 JSP 페이지 경로입니다.
//    }


    // 게시판 글 조회 2 - 페이징 처리 추가
    @GetMapping
    @ResponseBody
    public String getAllBoards2(@ModelAttribute("params") final SearchDto params, Model model) throws JsonProcessingException {
        PagingResponse<BoardDto> response = boardService.findAllBoards(params);



        List<BoardDto> list = response.getList();
        for (BoardDto boardDto : list) {
            System.out.println(" 보드 컨트롤러 / 전체 게시글 조회 (페이징) - boardDto.getBoardId() = " + boardDto.getBoardId());
            System.out.println(" 보드 컨트롤러 / 전체 게시글 조회 (페이징) - boardDto.getTitle() = " + boardDto.getTitle());
            System.out.println(" 보드 컨트롤러 / 전체 게시글 조회 (페이징) - boardDto.getNickname() = " + boardDto.getNickname());
            System.out.println(" 보드 컨트롤러 / 전체 게시글 조회 (페이징) - boardDto.getCreatedAt() = " + boardDto.getCreatedAt());
        }

//        ObjectMapper objectMapper = new ObjectMapper();
//        String stringToObjectMapper = objectMapper.writeValueAsString(response);

        model.addAttribute("boards", response);
        model.addAttribute("params", params);

        return "board";
    }



    // 특정 게시글 조회
    @GetMapping("/{boardId}")
    public String getBoardById(@PathVariable Long boardId, Model model) {
        BoardDto board = boardService.getBoardById(boardId);

        board.setBoardId(boardId);
        System.out.println(" 보드 컨트롤러 / 특정 게시글 조회 - board = " + board);
        System.out.println(" 보드 컨트롤러 / 특정 게시글 조회 - board.getTitle() = " + board.getTitle());
        System.out.println(" 보드 컨트롤러 / 특정 게시글 조회 - board.getContent() = " + board.getContent());
        System.out.println(" 보드 컨트롤러 / 특정 게시글 조회 - board.getBoardId() = " + board.getBoardId());
        System.out.println(" 보드 컨트롤러 / 특정 게시글 조회 - board.getBoardId() = " + board.getNickname());

        if (board != null) {
            model.addAttribute("board", board);
            return "board/detail"; // "board/detail"는 게시글 상세 정보를 보여줄 JSP 페이지 경로입니다.
        } else {
            return "redirect:/board"; // 게시글을 찾을 수 없으면 목록 페이지로 리다이렉트합니다.
        }
    }

    // 특정 게시글의 댓글 조회 - Test용
    @ResponseBody
    @GetMapping("/onlyComment/{boardId}")
    public List<CommentDto> getAllComments(@PathVariable Long boardId) {
        return boardMapper.getHierarchicalCommentsByBoardId(boardId);
    }

    // 글 작성 폼
    @GetMapping("/create")
    public String showCreateForm() {
        return "board/create"; // "board/create"는 게시글 생성 폼을 보여줄 JSP 페이지 경로입니다.
    }

    // 글 작성
//    @ResponseBody
//    @PostMapping("/create")
//    public String createBoard(@RequestBody BoardDto boardDto) {
//        Long boardId = boardService.createBoard(boardDto);
//        return "redirect:/boards/" + boardId; // 게시글 생성 후 해당 게시글 상세 페이지로 리다이렉트합니다.
//    }


    // 글 작성
    @PostMapping("/create")
    public String createBoard(@RequestParam("title") String title,
                              @RequestParam("content") String content,
                              @RequestParam("file") MultipartFile file) {

        BoardDto boardDto = new BoardDto(title, content);

        Long boardId = boardService.createBoard(boardDto);
        System.out.println(" 보드 컨트롤러 / 글 작성 / boardId = " + boardId);
        return "redirect:/boards/" + boardId; // 게시글 생성 후 해당 게시글 상세 페이지로 리다이렉트

    }



    // 글 수정 폼
    @GetMapping("/{boardId}/edit")
    public String showEditForm(@PathVariable Long boardId, Model model) {
        BoardDto board = boardService.getBoardById(boardId);
        board.setBoardId(boardId);
        if (board != null) {
            model.addAttribute("board", board);
            return "board/edit"; // "board/edit"는 게시글 수정 폼을 보여줄 JSP 페이지 경로입니다.
        } else {
            return "redirect:/boards"; // 게시글을 찾을 수 없으면 목록 페이지로 리다이렉트합니다.
        }
    }

    // 글 수정
    @PostMapping("/{boardId}/edit")
    public String updateBoard(@PathVariable Long boardId, @ModelAttribute BoardDto boardDto) {

        boardDto.setBoardId(boardId);
        boardService.updateBoard(boardId, boardDto);
        System.out.println(" 보드 컨트롤러 / 게시글 수정 - board = " + boardDto);
        System.out.println(" 보드 컨트롤러 / 게시글 수정 - board.getTitle() = " + boardDto.getTitle());
        System.out.println(" 보드 컨트롤러 / 게시글 수정 - board.getContent() = " + boardDto.getContent());
        System.out.println(" 보드 컨트롤러 / 게시글 수정 - board.getBoardId() = " + boardDto.getBoardId());
        return "redirect:/boards/" + boardId; // 게시글 수정 후 해당 게시글 상세 페이지로 리다이렉트합니다.
    }

    // 글 삭제
    @GetMapping ("/{boardId}/delete")
    public String deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return "redirect:/boards"; // 게시글 삭제 후 목록 페이지로 리다이렉트합니다.
    }

    // 글 검색
    @GetMapping("/search")
    public String searchBoard(@RequestParam String keyword, Model model) {
        List<BoardDto> boards = boardService.getSearchBoards(keyword);

        model.addAttribute("boards", boards);
        return "board"; // "board/list"는 게시판 목록을 보여줄 JSP 페이지 경로입니다.

    }

    //======== 댓글 ==========

    // 댓글 작성
    @PostMapping("/{boardId}/addComment")
    public String addComment(@PathVariable Long boardId, @RequestParam String content) {
        System.out.println(" 보드 컨트롤러 / 댓글 작성 - @PathVariable Long boardId = " + boardId);
        System.out.println(" 보드 컨트롤러 / 댓글 작성 - @RequestParam String content = " + content);

        CommentDto commentDto = new CommentDto();
        commentDto.setBoardId(boardId);
        commentDto.setContent(content);

        boardService.createComment(commentDto);

        return "redirect:/boards/" + boardId; // 댓글 추가 후 게시글 상세 페이지로 리다이렉트
    }

    // 댓글 수정 처리
    @PostMapping("/{boardId}/editComment/{commentId}")
    public String editComment(@PathVariable Long boardId, @PathVariable Long commentId, @RequestParam String content) {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentId(commentId);
        commentDto.setContent(content);

        boardService.updateComment(commentDto);

        return "redirect:/boards/" + boardId; // 댓글 수정 후 게시글 상세 페이지로 리다이렉트
    }

    // 댓글 삭제
    @GetMapping("/{boardId}/deleteComment/{commentId}")
    public String deleteComment(@PathVariable Long boardId, @PathVariable Long commentId) {
        boardService.deleteComment(commentId);

        return "redirect:/boards/" + boardId; // 댓글 삭제 후 게시글 상세 페이지로 리다이렉트
    }

    // ======= 대댓글 =======

    // 대댓글 작성 처리
    @PostMapping("/{boardId}/addReply/{commentId}")
    public String addReply(
            @PathVariable Long boardId,
            @PathVariable Long commentId,
            @RequestParam String content) {

        CommentDto reCommentDto = new CommentDto();
        reCommentDto.setBoardId(boardId);
        reCommentDto.setParentCommentId(commentId); // 부모 댓글의 commentId를 설정
        reCommentDto.setContent(content);

        boardService.createComment(reCommentDto);

        return "redirect:/boards/" + boardId; // 대댓글 추가 후 게시글 상세 페이지로 리다이렉트
    }
}
