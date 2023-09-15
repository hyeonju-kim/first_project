package com.example.first.controller;


import com.example.first.dto.BoardDto;
import com.example.first.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public String getAllBoards(Model model) {
        List<BoardDto> boards = boardService.getAllBoards();
        model.addAttribute("boards", boards);
        return "board/list"; // "board/list"는 게시판 목록을 보여줄 JSP 페이지 경로입니다.
    }

    @GetMapping("/{boardId}")
    public String getBoardById(@PathVariable Long boardId, Model model) {
        BoardDto board = boardService.getBoardById(boardId);
        if (board != null) {
            model.addAttribute("board", board);
            return "board/detail"; // "board/detail"는 게시글 상세 정보를 보여줄 JSP 페이지 경로입니다.
        } else {
            return "redirect:/boards"; // 게시글을 찾을 수 없으면 목록 페이지로 리다이렉트합니다.
        }
    }

    @GetMapping("/create")
    public String showCreateForm() {
        return "board/create"; // "board/create"는 게시글 생성 폼을 보여줄 JSP 페이지 경로입니다.
    }

    @PostMapping("/create")
    public String createBoard(@ModelAttribute BoardDto boardDto) {
        Long boardId = boardService.createBoard(boardDto);
        return "redirect:/boards/" + boardId; // 게시글 생성 후 해당 게시글 상세 페이지로 리다이렉트합니다.
    }

    @GetMapping("/{boardId}/edit")
    public String showEditForm(@PathVariable Long boardId, Model model) {
        BoardDto board = boardService.getBoardById(boardId);
        if (board != null) {
            model.addAttribute("board", board);
            return "board/edit"; // "board/edit"는 게시글 수정 폼을 보여줄 JSP 페이지 경로입니다.
        } else {
            return "redirect:/boards"; // 게시글을 찾을 수 없으면 목록 페이지로 리다이렉트합니다.
        }
    }

    @PostMapping("/{boardId}/edit")
    public String updateBoard(@PathVariable Long boardId, @ModelAttribute BoardDto boardDto) {
        boardService.updateBoard(boardId, boardDto);
        return "redirect:/boards/" + boardId; // 게시글 수정 후 해당 게시글 상세 페이지로 리다이렉트합니다.
    }

    @PostMapping("/{boardId}/delete")
    public String deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return "redirect:/boards"; // 게시글 삭제 후 목록 페이지로 리다이렉트합니다.
    }
}
