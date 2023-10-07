package com.example.first.controller;


import com.example.first.dto.BoardDto;
import com.example.first.dto.CommentDto;
import com.example.first.dto.UserDto;
import com.example.first.mapper.BoardMapper;
import com.example.first.mapper.HomeMapper;
import com.example.first.service.BoardService;
import com.example.first.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class CommentController { // 🎯🎯🎯🎯🎯 4개 API
    private final BoardService boardService;
    private final BoardMapper boardMapper;
    private final HomeMapper homeMapper;
    private final FileUtils fileUtils;

    public String getUsername() {
        String username = null;
        UserDto userDto = null;
        // ============== 현재 로그인한 사용자 정보 가져오기 ===============
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            username = authentication.getName(); // 사용자 이메일

            userDto = homeMapper.findByUsername(username);
            if (userDto != null) {
                String role = userDto.getRole();
                System.out.println("role ===== " + role);
            }
        } else {
            // 로그인하지 않은 경우, username을 비워두거나 다른 값을 넣어서 전달
        }
        // ==============================================================
        return username;
    }
    public UserDto getUserDto() {
        String username = null;
        UserDto userDto = null;
        // ============== 현재 로그인한 사용자 정보 가져오기 ===============
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            username = authentication.getName(); // 사용자 이메일

            userDto = homeMapper.findByUsername(username);
            if (userDto != null) {
                String role = userDto.getRole();
                System.out.println("role ===== " + role);
            }
        } else {
            // 로그인하지 않은 경우, username을 비워두거나 다른 값을 넣어서 전달
        }
        // ==============================================================
        return userDto;
    }

    // 🎯 1. 댓글 작성
    @PostMapping("/{boardId}/addComment")
    public String addComment(@PathVariable Long boardId, @RequestParam String content, Model model) {
        // 현재 로그인한 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName().equals("anonymousUser")) {
            // 로그인하지 않은 사용자에게 알림을 표시
            model.addAttribute("message", "로그인을 해주세요!");
            return "board/alert";
        }
        CommentDto commentDto = new CommentDto();
        commentDto.setBoardId(boardId);
        commentDto.setContent(content);

        boardService.createComment(commentDto);
        return "redirect:/boards/" + boardId; // 댓글 추가 후 게시글 상세 페이지로 리다이렉트
    }

    // 🎯 2. 댓글 수정 폼
    @GetMapping("/{boardId}/editComment/{commentId}")
    public String editCommentForm(@PathVariable Long boardId, @PathVariable Long commentId, @RequestParam String content, Model model) {
        CommentDto comment = boardMapper.findCommentByCommentId(commentId);

        BoardDto board = boardService.getBoardById(boardId);
        board.setBoardId(boardId);
        model.addAttribute("board", board);
        model.addAttribute("comment", comment);
        String username = getUsername();
        UserDto userDto = getUserDto();

        model.addAttribute("username", username);
        model.addAttribute("nickname", userDto.getNickname());
        model.addAttribute("role", userDto.getRole());

        return "board/comment_edit"; // 댓글 수정 후 게시글 상세 페이지로 리다이렉트
    }

    // 🎯 3. 댓글 수정 처리
    @PostMapping("/{boardId}/editComment/{commentId}")
    public String editComment(@PathVariable Long boardId, @PathVariable Long commentId, @RequestParam String content) {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentId(commentId);
        commentDto.setContent(content);

        boardService.updateComment(commentDto);

        return "redirect:/boards/" + boardId; // 댓글 수정 후 게시글 상세 페이지로 리다이렉트
    }

    // 🎯 4. 댓글 삭제 (status 만 Y로 업데이트)
    @GetMapping("/{boardId}/deleteComment/{commentId}")
    public String deleteComment(@PathVariable Long boardId, @PathVariable Long commentId) {
        boardService.deleteComment(commentId);

        return "redirect:/boards/" + boardId; // 댓글 삭제 후 게시글 상세 페이지로 리다이렉트
    }

    // ======= 대댓글 =======

    // 🎯 5. 대댓글 작성 처리
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
