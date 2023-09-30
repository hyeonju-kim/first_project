package com.example.first.controller;


import com.example.first.dto.*;
import com.example.first.mapper.BoardMapper;
import com.example.first.mapper.HomeMapper;
import com.example.first.service.BoardService;
import com.example.first.utils.FileUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
//@CrossOrigin
public class BoardController {
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


    // 게시판 글 조회 (페이징 처리 된)
    @GetMapping
    public String getAllBoards(Model model, @RequestParam(defaultValue = "1") int currentPage) {
        int pageSize = 10; // 페이지당 게시물 수
        List<BoardDto> boards = boardService.getBoardsByPage(currentPage, pageSize);
        int totalPages = boardService.getTotalPages(pageSize);

        model.addAttribute("boards", boards);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);

        System.out.println(" 보드 컨트롤러 / 게시판 글목록 조회 - currentPage = " + currentPage);
        System.out.println(" 보드 컨트롤러 / 게시판 글목록 조회 - totalPages = " + totalPages);

//        for (BoardDto board : boards) {
//            System.out.println("보드 컨트롤러 / 글목록 for문 - board.getBoardId() ::" + board.getBoardId());
//            System.out.println("보드 컨트롤러 / 글목록 for문 - board.getTitle() ::" + board.getTitle());
//            System.out.println("보드 컨트롤러 / 글목록 for문 - board.getContent() ::" + board.getContent());
//            System.out.println("보드 컨트롤러 / 글목록 for문 - board.getCreatedAt() ::" + board.getCreatedAt());
//        }

        // 현재 로그인한 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // 사용자 이메일
            UserDto userDto = homeMapper.findByUsername(username);
            if (userDto != null) {
                String role = userDto.getRole();
                System.out.println("role ===== " + role);
                model.addAttribute("role", role);
            }
            // 모델에 사용자 정보 추가
            model.addAttribute("username", username);
            model.addAttribute("nickname", userDto.getNickname());
        } else {
            // 로그인하지 않은 경우, username을 비워두거나 다른 값을 넣어서 전달
            model.addAttribute("username", "");
        }


        return "board";
    }




    // 특정 게시글 조회
    @GetMapping("/{boardId}")
    public String getBoardById(@PathVariable Long boardId, Model model) {

        String username = "";
        // 현재 로그인한 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName().equals("anonymousUser")) {
            // 로그인하지 않은 사용자에게 알림을 표시
            model.addAttribute("message", "로그인을 해주세요!");
            return "board/alert"; // 알림을 보여줄 JSP 페이지 경로
        }

        if (authentication != null && authentication.isAuthenticated()) {
            username = authentication.getName(); // 사용자 이메일
            UserDto userDto = homeMapper.findByUsername(username);
            if (userDto != null) {
                String role = userDto.getRole();
                System.out.println("role ===== " + role);
                model.addAttribute("role", role);
            }
            // 모델에 사용자 정보 추가
            model.addAttribute("username", username);
        }

        BoardDto boardDto = boardService.getBoardById(boardId);
        System.out.println("boardDto.getStatus() ============================== " + boardDto.getStatus());
        boardDto.setBoardId(boardId);

        System.out.println("boardDto.getUsername() ============================== " + boardDto.getUsername());
        System.out.println("username ============================================ " + username);

        // 게시글의 멀티 파일 정보를 가져옵니다.
        List<BoardMultiFile> multiFiles = boardService.getBoardMultiFilesByBoardId(boardId);

        System.out.println("multiFiles.toString() = " + multiFiles.toString());
        System.out.println("multiFiles.size() = " + multiFiles.size());

        model.addAttribute("board", boardDto);
        model.addAttribute("multiFiles", multiFiles); // 멀티 파일 리스트를 모델에 추가합니다.

        return "board/detail";
    }


    // 첨부파일 다운로드
    @ResponseBody
    @GetMapping("/posts/{postId}/files/{fileId}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable final Long postId, @PathVariable final Long fileId) {
        BoardMultiFile file = boardService.findBoardMultiFileBySeq(fileId);

        Resource resource = fileUtils.readFileAsResource(file); // 핵심 로직 !!!!

        try {
            String filename = URLEncoder.encode(file.getFileName(), "UTF-8"); //파일 이름에 특수 문자나 공백이 포함되어 있을 때도 안전하게 처리됩니다.

            System.out.println(" 보드컨트롤러 / 첨부파일 다운로드 / filename = " + filename);
            System.out.println(" 보드컨트롤러 / 첨부파일 다운로드 / resource.getFilename() = " + resource.getFilename());
            System.out.println(" 보드컨트롤러 / 첨부파일 다운로드 / resource.getFile() = " + resource.getFile());
            System.out.println(" 보드컨트롤러 / 첨부파일 다운로드 / resource.getURI() = " + resource.getURI());
            System.out.println(" 보드컨트롤러 / 첨부파일 다운로드 / resource.getURL() = " + resource.getURL());

            // 파일 크기가 null이면 0으로 처리합니다.
//            long fileSize = (file.getFileSize() != null) ? file.getFileSize().length : 0;

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\";")  //이를 통해 브라우저는 파일을 다운로드하도록 지시
//                    .header(HttpHeaders.CONTENT_LENGTH, fileSize + "")
                    .body(resource);

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("filename encoding failed : " + file.getFileOriginalName());
        } catch (IOException e) {
            throw new RuntimeException(e);
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
    public String showCreateForm(HttpServletRequest request, Model model) {
        // 현재 로그인한 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName().equals("anonymousUser")) {
            // 로그인하지 않은 사용자에게 알림을 표시
            model.addAttribute("message", "로그인을 해주세요!");
            return "board/alert"; // 알림을 보여줄 JSP 페이지 경로
        }

        return "board/create"; // 로그인한 사용자에게는 글 작성 폼을 보여줄 JSP 페이지 경로
    }


    // 글 작성 (다중 멀티파일 업로드)
    // 컨트롤러에서 인자로 MultiFileDto 리스트를 받도록 수정
    @PostMapping("/create")
    public String createBoard(@RequestParam("title") String title,
                              @RequestParam("content") String content,
                              @RequestParam("files") List<MultipartFile> files) throws IOException {

        BoardDto boardDto = new BoardDto(title, content);

        Long boardId = boardService.createBoard2(boardDto, files);
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

    // 글 삭제 (status 만 Y로 업데이트)
    @GetMapping ("/{boardId}/delete")
    public String deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return "redirect:/boards"; // 게시글 삭제 후 목록 페이지로 리다이렉트합니다.
    }

    // 글 검색
    @GetMapping("/search")
    public String searchBoard(@RequestParam String keyword, Model model, @RequestParam(defaultValue = "1") int currentPage) {
        int pageSize = 10; // 페이지당 게시물 수
        List<BoardDto> boards = boardService.getSearchBoardsByPage(keyword, currentPage, pageSize);
        int totalPages = boardService.getSearchBoardsTotalPages(keyword, pageSize);

        // 현재 로그인한 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // 사용자 이메일
            UserDto userDto = homeMapper.findByUsername(username);
            if (userDto != null) {
                String role = userDto.getRole();
                System.out.println("role ===== " + role);
                model.addAttribute("role", role);
            }
            // 모델에 사용자 정보 추가
            model.addAttribute("username", username);
            model.addAttribute("nickname", userDto.getNickname());
        } else {
            // 로그인하지 않은 경우, username을 비워두거나 다른 값을 넣어서 전달
            model.addAttribute("username", "");
        }

        model.addAttribute("boards", boards);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);

        return "board";

    }

    //======== 댓글 ==========

    // 댓글 작성
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

    // 댓글 수정 처리
    @PostMapping("/{boardId}/editComment/{commentId}")
    public String editComment(@PathVariable Long boardId, @PathVariable Long commentId, @RequestParam String content) {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentId(commentId);
        commentDto.setContent(content);

        boardService.updateComment(commentDto);

        return "redirect:/boards/" + boardId; // 댓글 수정 후 게시글 상세 페이지로 리다이렉트
    }

    // 댓글 삭제 (status 만 Y로 업데이트)
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

    // ========= 다이어트 레코드 ==============
    // 식단 기록 폼
    @GetMapping ("/diet-record")
    public String dietRecord(Model model) {

        String username = getUsername();
        UserDto userDto = getUserDto();
        List<DietDto> dietDtoList = boardMapper.findDietListByUsername(username);
        System.out.println("dietDtoList.size() = " + dietDtoList.size());

        Map<LocalDate, String> map = new HashMap<>();
        for (DietDto dietDto : dietDtoList) {
            System.out.println("dietDto.getIntakeDate() = " + dietDto.getIntakeDate());
            System.out.println("dietDto.getIntakeResult() = " + dietDto.getIntakeResult());
            map.put(dietDto.getIntakeDate(), dietDto.getIntakeResult());
        }

        model.addAttribute("dietMap", map);
        model.addAttribute("role", userDto.getRole());
        model.addAttribute("username", username);
        model.addAttribute("nickname", userDto.getNickname());

        return "board/diet-record";
    }

    // 식단 기록 달력에 식단 기록하기
    @PostMapping ("/diet-record")
    public String dietRecord(DietDto dietDto) {
        System.out.println("dietDto.getIntakeCaloriesMorning() = " + dietDto.getIntakeCaloriesMorning());
        System.out.println("dietDto.getIntakeCaloriesLunch() = " + dietDto.getIntakeCaloriesLunch());
        System.out.println("dietDto.getIntakeCaloriesDinner() = " + dietDto.getIntakeCaloriesDinner());


        DietDto dto = new DietDto(dietDto.getIntakeCaloriesMorning(), dietDto.getIntakeCaloriesLunch(), dietDto.getIntakeCaloriesDinner());
        boardService.insertDietRecord(dietDto);

        return "board/diet-record";
    }

    // 통계 폼
    @GetMapping("/statistics")
    public String showStatistics(Model model) throws JsonProcessingException {
        String username = getUsername();
        UserDto userDto = getUserDto();
        model.addAttribute("role", userDto.getRole());
        model.addAttribute("username", username);
        model.addAttribute("nickname", userDto.getNickname());

        DietDto dietListByUsernameDaily = boardMapper.findDietListByUsernameDaily(username);
        model.addAttribute("dietDaily" , dietListByUsernameDaily);

        List<DietDto> dietListByUsernameWeekly = boardMapper.findDietListByUsernameWeekly(username);

        // Java 객체 목록을 JSON 문자열로 변환하여 모델에 추가합니다.
        // jsp에서 사용할거면 그냥 오브젝터매퍼 없이 모델에 담아도 되지만,
        // 자바스크립트에서 동적으로 차트만들기로 쓸거라서 오브젝트매퍼로 자바객체 -> json 문자열로 변환하여 모델로 넘겨야 한다.
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String dietWeeklyListJson = objectMapper.writeValueAsString(dietListByUsernameWeekly);

        model.addAttribute("dietWeeklyList", dietWeeklyListJson);


        return "board/statistics";
    }

}
