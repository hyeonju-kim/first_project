package com.example.first.controller;


import com.example.first.dto.BoardDto;
import com.example.first.dto.BoardMultiFile;
import com.example.first.dto.CommentDto;
import com.example.first.mapper.BoardMapper;
import com.example.first.service.BoardService;
import com.example.first.utils.FileUtils;
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

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;


@Slf4j
@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final BoardMapper boardMapper;
    private final FileUtils fileUtils;


    // 게시판 글 조회 1 - 기본
    @GetMapping
    public String getAllBoards(Model model) {
        List<BoardDto> boards = boardService.getAllBoards();


        System.out.println(" 보드 컨트롤러 / 게시판 글목록 조회 - boards = " + boards);
        System.out.println(" 보드 컨트롤러 / 게시판 글목록 조회 - boards.size() = " + boards.size());

        for (BoardDto board : boards) {
            System.out.println("보드 컨트롤러 / 글목록 for문 - board.getBoardId() ::" + board.getBoardId());
            System.out.println("보드 컨트롤러 / 글목록 for문 - board.getTitle() ::" + board.getTitle());
            System.out.println("보드 컨트롤러 / 글목록 for문 - board.getContent() ::" + board.getContent());
            System.out.println("보드 컨트롤러 / 글목록 for문 - board.getCreatedAt() ::" + board.getCreatedAt());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        model.addAttribute("boards", boards);
        return "board"; // "board/list"는 게시판 목록을 보여줄 JSP 페이지 경로입니다.
    }


//    // 게시판 글 조회 2 - 페이징 처리 추가
//    @GetMapping
//    public String getAllBoards2(@ModelAttribute("params") final SearchDto params, Model model) throws JsonProcessingException {
//        PagingResponse<BoardDto> response = boardService.findAllBoards(params);
//
//
//
//        List<BoardDto> list = response.getList();
//        for (BoardDto boardDto : list) {
//            System.out.println(" 보드 컨트롤러 / 전체 게시글 조회 (페이징) - boardDto.getBoardId() = " + boardDto.getBoardId());
//            System.out.println(" 보드 컨트롤러 / 전체 게시글 조회 (페이징) - boardDto.getTitle() = " + boardDto.getTitle());
//            System.out.println(" 보드 컨트롤러 / 전체 게시글 조회 (페이징) - boardDto.getNickname() = " + boardDto.getNickname());
//            System.out.println(" 보드 컨트롤러 / 전체 게시글 조회 (페이징) - boardDto.getCreatedAt() = " + boardDto.getCreatedAt());
//        }
//
////        ObjectMapper objectMapper = new ObjectMapper();
////        String stringToObjectMapper = objectMapper.writeValueAsString(response);
//
//        model.addAttribute("boards", response);
//        model.addAttribute("params", params);
//
//        return "board";
//    }



    // 특정 게시글 조회
    @GetMapping("/{boardId}")
    public String getBoardById(@PathVariable Long boardId, Model model) {

        BoardDto boardDto = boardService.getBoardById(boardId);
        boardDto.setBoardId(boardId);

        // 게시글의 멀티 파일 정보를 가져옵니다.
        List<BoardMultiFile> multiFiles = boardService.getBoardMultiFilesByBoardId(boardId);

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


    // 글 작성 (단일 멀티파일 업로드)
    @PostMapping("/create_old")
    public String createBoardOld(@RequestParam("title") String title,
                              @RequestParam("content") String content,
                              @RequestParam("file") MultipartFile file) throws IOException {

        // 내가 업로드 파일을 저장할 경로
        String originalName = file.getOriginalFilename();
        String fileName = System.currentTimeMillis() + "_" + originalName;

        // 업로드 할 디렉토리 경로 설정
        String saveRootPath = "C:\\multifile";
        // 저장할 파일, 생성자로 경로와 이름을 지정해줌.
        File saveFile2 = new File(saveRootPath, fileName);

        BoardDto boardDto = new BoardDto(title, content);

        Long boardId = boardService.createBoard(boardDto, file, fileName, originalName);
        System.out.println(" 보드 컨트롤러 / 글 작성 / boardId = " + boardId);

        try {
            // void transferTo(File dest) throws IOException 업로드한 파일 데이터를 지정한 파일에 저장
            file.transferTo(saveFile2);
            return "redirect:/boards/" + boardId; // 게시글 생성 후 해당 게시글 상세 페이지로 리다이렉트
        } catch (IOException e) {

            e.printStackTrace();
            // 파일 업로드 실패 처리를 여기에 추가할 수 있습니다.
            throw new IOException("파일 업로드 실패 ㅠㅠ");
        }
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
