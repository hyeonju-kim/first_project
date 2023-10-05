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
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


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
        int pageSize = 15; // 페이지당 게시물 수
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
            model.addAttribute("nickname", userDto.getNickname());
        }

        BoardDto boardDto = boardService.getBoardById(boardId);
        System.out.println("boardDto.getStatus() ============================== " + boardDto.getStatus());
        boardDto.setBoardId(boardId);

        System.out.println("boardDto.getUsername() ============================== " + boardDto.getUsername());
        System.out.println("username ============================================ " + username);

        // 현재 로그인 유저가 해당 게시물을 좋아요를 했다면 , 모델에 "like" = true 넣어주기
        if (isAlreadyLiked(boardId, username)) {
            model.addAttribute("like", true);
        }else {
            model.addAttribute("like", false);
        }
//        List<BoardLikeDto> boardLikeDtoList = boardMapper.findBoardLike(username);
//        for (BoardLikeDto boardLikeDto : boardLikeDtoList) {
//            if (boardLikeDto.getBoardId() == boardId) {
//                model.addAttribute("likeY", "likeY");
//            }
//        }

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

        String username = getUsername();
        UserDto userDto = getUserDto();

        model.addAttribute("username", username);
        model.addAttribute("nickname", userDto.getNickname());
        model.addAttribute("role", userDto.getRole());

        return "board/create"; // 로그인한 사용자에게는 글 작성 폼을 보여줄 JSP 페이지 경로
    }


    // 글 작성 (다중 멀티파일 업로드)
    // 컨트롤러에서 인자로 MultiFileDto 리스트를 받도록 수정
    @PostMapping("/create")
    public String createBoard(@RequestParam("title") String title,
                              @RequestParam("content") String content,
                              @RequestParam("files") List<MultipartFile> files,
                              Model model) throws IOException {

        String username = getUsername();
        UserDto userDto = getUserDto();

        model.addAttribute("username", username);
        model.addAttribute("nickname", userDto.getNickname());
        model.addAttribute("role", userDto.getRole());

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
        model.addAttribute("board", board);
        String username = getUsername();
        UserDto userDto = getUserDto();

        model.addAttribute("username", username);
        model.addAttribute("nickname", userDto.getNickname());
        model.addAttribute("role", userDto.getRole());
        // 게시글의 멀티 파일 정보를 가져옵니다.
        List<BoardMultiFile> multiFiles = boardService.getBoardMultiFilesByBoardId(boardId);

        System.out.println("multiFiles.toString() = " + multiFiles.toString());
        System.out.println("multiFiles.size() = " + multiFiles.size());

        model.addAttribute("multiFiles", multiFiles); // 멀티 파일 리스트를 모델에 추가합니다.
        return "board/edit";
    }

    // 글 수정  TODO 멀티파일 수정 되도록 고치기  ( )
    @PostMapping("/{boardId}/edit")
    public String updateBoard(@PathVariable Long boardId, @ModelAttribute BoardDto boardDto, @RequestParam("files") MultipartFile[] files) throws IOException {

        System.out.println("files?????????????????? " + files.toString());

        for (int i = 0; i < files.length; i++) {
            System.out.println("files???????" + files[i]);
        }


        // 게시글의 멀티 파일 정보를 가져옵니다.
        List<BoardMultiFile> multiFiles = boardService.getBoardMultiFilesByBoardId(boardId);

        System.out.println("multiFiles.toString() = " + multiFiles.toString());
        System.out.println("multiFiles.size() = " + multiFiles.size());

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String regDate = now.format(formatter);
        boardDto.setCreatedAt(regDate);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserDto user = homeMapper.findByUsername(username);
        String nickname = user.getNickname();
        boardDto.setNickname(nickname);
        boardDto.setUsername(username);

        List<String> allMultiFilesOriginalNameByBoardId = boardMapper.findBoardMultiFileOriginalName(boardId); // boardId로 멀티파일 원본 이름 가져오기

        List<String> newMultiFilesOriginalNameList;
        newMultiFilesOriginalNameList = new ArrayList<>(); // 새로 수정된 멀티파일 원본이름 리스트 생성
        for (MultipartFile newFiles : files) {
            newMultiFilesOriginalNameList.add(newFiles.getOriginalFilename());
        }


        // 기존 멀티 첨부 파일에 없는 파일이 있으면 새로 첨부 파일 업로드 및 정보 저장
//        for (int i = 0; i < files.size(); i++) {
////          MultipartFile file = files.get(i);
//            if (!allMultiFilesOriginalNameByBoardId.contains(files.get(i).getOriginalFilename())) { // 게시글의 기존 파일들 중에 새로운 파일명이 없으면
//                if (files.get(i) != null && !files.get(i).isEmpty()) { // 파일이 존재하고 비어있지 않은 경우에만 처리
//                    // 내가 업로드 파일을 저장할 경로
//                    String originalName = files.get(i).getOriginalFilename();
//                    String fileName = System.currentTimeMillis() + "_" + originalName;
//
//                    String saveRootPath =  "C:\\multifile"; // 업로드할 디렉토리 경로 설정
//                    String savePath =  "C:\\multifile\\" + fileName; // 업로드할 디렉토리 경로 + 파일명 설정
//
//                    // 저장할 파일 경로와 파일명 생성
//                    File saveFile = new File(saveRootPath, fileName);
//
////                    filesToRemove.add(files.get(i)); // 새로 생성한 새로운 멀티 파일은 제거할 파일 리스트에 추가
//                    newMultiFilesOriginalNameList.remove(files.get(i).getOriginalFilename()); // 새로 만든 파일은 새로 수정된 멀티파일 원본이름 리스트에서 제거
//
//                    // 파일 업로드 및 정보 저장
//                    try {
//                        files.get(i).transferTo(saveFile);
//
//                        String fileExt = getFileExtension(fileName);
//
//
//                        BoardMultiFile multiFile = new BoardMultiFile(boardId, fileName, savePath, regDate, fileExt, username, originalName);
//                        System.out.println("multiFile.getFlagDel() =============== " + multiFile.getFlagDel());
//
//                        boardMapper.storeBoardMultiFile(multiFile);
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        throw new IOException("파일 업로드 실패 ㅠㅠ");
//                    }
//                }
//            }
//        }

        // 새로운 파일 업로드
//        for (MultipartFile newFile : newFiles) {
//            if (newFile != null && !newFile.isEmpty()) { // 파일이 존재하고 비어있지 않은 경우에만 처리
//                // 내가 업로드 파일을 저장할 경로
//                String originalName = newFile.getOriginalFilename();
//                String fileName = System.currentTimeMillis() + "_" + originalName;
//
//                String saveRootPath =  "C:\\multifile"; // 업로드할 디렉토리 경로 설정
//                String savePath =  "C:\\multifile\\" + fileName; // 업로드할 디렉토리 경로 + 파일명 설정
//
//                // 저장할 파일 경로와 파일명 생성
//                File saveFile = new File(saveRootPath, fileName);
//
//                // 파일 업로드 및 정보 저장
//                try {
//                    newFile.transferTo(saveFile);
//                    String fileExt = getFileExtension(fileName);
//
//                    BoardMultiFile multiFile = new BoardMultiFile(boardId, fileName, savePath, regDate, fileExt, username, originalName);
//                    System.out.println("multiFile.getFlagDel() =============== " + multiFile.getFlagDel());
//                    boardMapper.storeBoardMultiFile(multiFile);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    throw new IOException("파일 업로드 실패 ㅠㅠ");
//                }
//            }
//
//        }


        // 제거할 파일 리스트가 있으면 for문 돌면서 FlagDel = "Y" 로 업데이트하기

        if (!allMultiFilesOriginalNameByBoardId.isEmpty()) {
            for (String fileOriginalName : allMultiFilesOriginalNameByBoardId) {
                if (!allMultiFilesOriginalNameByBoardId.contains(fileOriginalName)){ // 기존 게시글의 파일명 중에, 수정된 게시글의 파일명이 포함되어 있지 않으면, flagDel = "Y" 설정
                    boardMapper.updateMultiFilesFlagDel(fileOriginalName);
                }
            }
        }

        boardDto.setBoardId(boardId);
        boardService.updateBoard(boardId, boardDto);
        System.out.println(" 보드 컨트롤러 / 게시글 수정 - board = " + boardDto);
        System.out.println(" 보드 컨트롤러 / 게시글 수정 - board.getTitle() = " + boardDto.getTitle());
        System.out.println(" 보드 컨트롤러 / 게시글 수정 - board.getContent() = " + boardDto.getContent());
        System.out.println(" 보드 컨트롤러 / 게시글 수정 - board.getBoardId() = " + boardDto.getBoardId());
        return "redirect:/boards/" + boardId; // 게시글 수정 후 해당 게시글 상세 페이지로 리다이렉트합니다.
//        return "boards/" + boardId;
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


    // 좋아요 추가 / 취소 API
    @RequestMapping("/likes/{boardId}")
    public String addLike(@PathVariable Long boardId) {
        String username = getUsername();
        // 좋아요 수를 증가시키는 로직을 구현
        BoardDto board = boardMapper.getBoardById(boardId);

        // 현재 사용자가 해당 게시글에 좋아요를 누르지 않은 경우에만 좋아요 수를 증가시키고 좋아요를 추가
        if (!isAlreadyLiked(boardId, username)) {
            board.setCntLike(board.getCntLike() + 1);
            boardMapper.updateBoardLikes(board);
            boardMapper.saveBoardLike(new BoardLikeDto(boardId, username));
        }else {
            board.setCntLike(board.getCntLike() - 1);
            boardMapper.updateBoardLikes(board);
            boardMapper.deleteBoardLike(username);
        }
        return "redirect:/boards/" + boardId;
    }

    // 좋아요 되어있는지 확인하는 메소드 (좋아요 되어있으면 true, 안되어있으면 false)
    public boolean isAlreadyLiked(Long boardId, String username) {
        List<BoardLikeDto> boardLike = boardMapper.findBoardLike(username);
        for (BoardLikeDto boardLikeDto : boardLike) {
            if (Objects.equals(boardLikeDto.getBoardId(), boardId)) {
                return true;
            }
        }
        return false;
    }

//    // 좋아요 삭제 API
//    @PostMapping("/likes/{boardId}")
//    public void removeLike(@PathVariable Long boardId) {
//        boardMapper.removeLike(boardId);
//    }



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

    // 댓글 수정 폼
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
    // 1. 식단 기록 페이지
    // 식단 기록 폼
    @GetMapping ("/diet-record")
    public String dietRecord(Model model) throws JsonProcessingException {

        String username = getUsername();
        UserDto userDto = getUserDto();

        // 그냥 map 으로 받자... List<Map<컬럼명, 값>>  -> 한 row씩 하나의 map으로 읽어온다.
        List<HashMap<String, Object>> hashMapList = boardMapper.findDietListByUsername(username);//PSQLException: Bad value for type int : 삼겹살 ->csv 데이터타입문제
        System.out.println("hashMapList.size() =************************** " + hashMapList.size());
        System.out.println("hashMapList = " + hashMapList);


        Map<LocalDate, String> dietMap = new HashMap<>();
        for (HashMap<String, Object> map : hashMapList) {
            System.out.println("map.entrySet() =***************** " + map.entrySet());
            for (Map.Entry<String, Object> entrySet : map.entrySet()) {
                System.out.println("entrySet.getKey() 😊= " + entrySet.getKey() + ", ✨ entrySet.getValue() 😊= " + entrySet.getValue());
            }

            Date localDate = (Date) map.get("intake_date");
            Object intakeResult = map.get("intake_result");

            if (localDate != null) {
                LocalDate intakeDate = localDate.toLocalDate();
                dietMap.put(intakeDate, (String) intakeResult);
            } else {
                throw new IllegalArgumentException("날짜가 널이에요~~~~~~~~~ㅠㅠ");
            }



        }

        // 날짜별로 총 섭취량을 map으로 . 달력 날짜마다 칼로리 나오도록
        Map<LocalDate, Integer> dietMap2 = new HashMap<>();
        for (HashMap<String, Object> map : hashMapList) {

            Object intakeCaloriesMorning = map.get("intake_calories_morning");
            Object intakeCaloriesLunch = map.get("intake_calories_lunch");
            Object intakeCaloriesDinner = map.get("intake_calories_dinner");
            Object intakeCaloriesSnack = map.get("intake_calories_snack");

            double intakeCaloriesMorning1 = (double)intakeCaloriesMorning;
            double intakeCaloriesLunch1 = (double) intakeCaloriesLunch;
            double intakeCaloriesDinner1 = (double) intakeCaloriesDinner;
//            double intakeCaloriesSnack1 = (double) intakeCaloriesSnack;

//            double morning = (int)intakeCaloriesMorning1 != null ? intakeCaloriesMorning1 : (int) 0.0;
//            double lunch = intakeCaloriesLunch1 != null ? intakeCaloriesLunch1 : (int) 0.0;
//            double dinner = intakeCaloriesDinner1 != null ? intakeCaloriesDinner1 : (int) 0.0;
//            double snack = intakeCaloriesSnack1 != null ? intakeCaloriesSnack1 : (int) 0.0;

//            double totalIntake = intakeCaloriesMorning1 + intakeCaloriesLunch1 + intakeCaloriesDinner1 + intakeCaloriesSnack1;
            double totalIntake = intakeCaloriesMorning1 + intakeCaloriesLunch1 + intakeCaloriesDinner1;

            Date localDate = (Date) map.get("intake_date");
            LocalDate intakeDate = localDate.toLocalDate();

            dietMap2.put( intakeDate, (int) totalIntake);
            System.out.println("intakeDate : totalIntake ===============🤣 " + intakeDate + " ✨: " + totalIntake);
        }

        System.out.println("userDto.getRequiredCalories() =🤣🤣 " + userDto.getRequiredCalories());

//        model.addAttribute("dietMap", dietMap);
        model.addAttribute("user", userDto);
        model.addAttribute("role", userDto.getRole());
        model.addAttribute("username", username);
        model.addAttribute("nickname", userDto.getNickname());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String dietRecordMap = objectMapper.writeValueAsString(dietMap);
        String dietRecordMap2 = objectMapper.writeValueAsString(dietMap2);


        model.addAttribute("dietRecordMap", dietRecordMap);
        model.addAttribute("dietRecordMap2", dietRecordMap2);

        return "board/diet-record";
    }

    // 식단 기록 달력에 식단 기록하기
    @PostMapping ("/diet-record")
    public String dietRecord(DietDto dietDto) {
        System.out.println("dietDto.getIntakeCaloriesMorning() = " + dietDto.getIntakeCaloriesMorning());
        System.out.println("dietDto.getIntakeCaloriesLunch() = " + dietDto.getIntakeCaloriesLunch());
        System.out.println("dietDto.getIntakeCaloriesDinner() = " + dietDto.getIntakeCaloriesDinner());
        System.out.println("dietDto.getIntakeDate() = " + dietDto.getIntakeDate());

        boardService.insertDietRecord(dietDto);

        return "redirect:diet-record";
    }

    // 2. 통계 페이지
    // 통계 폼
    @GetMapping("/statistics")
    public String showStatistics(Model model) throws JsonProcessingException {
        String username = getUsername();
        UserDto userDto = getUserDto();
        model.addAttribute("role", userDto.getRole());
        model.addAttribute("username", username);
        model.addAttribute("nickname", userDto.getNickname());
        model.addAttribute("userDto", userDto);

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

    // 3. 랭킹 페이지
    @GetMapping("/rank")
    public String showRank(Model model) throws JsonProcessingException {
        String username = getUsername();
        UserDto userDto = getUserDto();
        model.addAttribute("role", userDto.getRole());
        model.addAttribute("username", username);
        model.addAttribute("nickname", userDto.getNickname());
        model.addAttribute("userDto", userDto);

        List<HashMap<String, Object>> dietList = boardMapper.findAllUserDietListWeekly();

        // 랭킹에 보여줄 해시맵 생성 (닉네임, 적정 식사 횟수를 담은 맵)
        HashMap<String, Integer> rankMap = new HashMap<>();

        for (HashMap<String, Object> map : dietList) {
            String mapUsername = (String) map.get("username");
            UserDto mapUser = homeMapper.findByUsername(mapUsername);
            String nickname = mapUser != null ? mapUser.getNickname() : "Unknown"; // mapUser가 null인 경우 처리
            Long resultGoodCount = (Long) map.get("result_good_count");
            System.out.println("resultGoodCount = " + resultGoodCount);

            // 일주일 간
            // intakeResult = "적정" 인 횟수가 가로축, 세로축이 횟수가 가장 많은 사람의 nickname 의 랭킹차트 생성
            rankMap.put(nickname, Math.toIntExact(resultGoodCount));
        }

        // rankMap을 resultGoodCount를 기준으로 내림차순으로 정렬
        List<Map.Entry<String, Integer>> sortedRankList = rankMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());

        // 정렬된 데이터를 LinkedHashMap으로 변환
        LinkedHashMap<String, Integer> sortedRankMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : sortedRankList) {
            sortedRankMap.put(entry.getKey(), entry.getValue());
        }

        // 직렬화 한 후에 모델에 담아 넘기기
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String rankingMap = objectMapper.writeValueAsString(sortedRankMap);

        System.out.println("rankingMap = " + rankingMap);
        for (Map.Entry<String, Integer> stringIntegerEntry : sortedRankMap.entrySet()) {
            System.out.println("stringIntegerEntry = " + stringIntegerEntry);
        }

        model.addAttribute("rankingMap", rankingMap); // 이름 지정 안해줌 ;;;;;

        return "board/rank";
    }

}
