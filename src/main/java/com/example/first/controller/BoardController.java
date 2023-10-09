package com.example.first.controller;


import com.example.first.dto.*;
import com.example.first.mapper.BoardMapper;
import com.example.first.mapper.HomeMapper;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Slf4j
@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
//@CrossOrigin
public class BoardController { // 🎯🎯🎯🎯🎯 11개 API
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


    // 🎯 1. 게시판 글 조회 (페이징 처리 된)
    @GetMapping
    public String getAllBoards(Model model, @RequestParam(defaultValue = "1") int currentPage) {
        int pageSize = 15; // 페이지당 게시물 수
        int totalPages = boardService.getTotalPages(pageSize);
        List<BoardDto> boards = boardService.getBoardsByPage(currentPage, pageSize);

        model.addAttribute("boards", boards);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);

        String username = getUsername();
        UserDto userDto = getUserDto();
        model.addAttribute("username", username);
        model.addAttribute("nickname", userDto.getNickname());
        model.addAttribute("role", userDto.getRole());
        return "board";
    }

    // 🎯 2. 게시글 상세 조회
    @GetMapping("/{boardId}")
    public String getBoardById(@PathVariable Long boardId, Model model) {
        String username = getUsername();
        UserDto userDto = getUserDto();

        model.addAttribute("username", username);
        model.addAttribute("nickname", userDto.getNickname());
        model.addAttribute("role", userDto.getRole());

        BoardDto boardDto = boardService.getBoardById(boardId);
        boardDto.setBoardId(boardId);

        // 현재 로그인 유저가 해당 게시물을 좋아요를 했다면 , 모델에 "like" = true 넣어주기
        if (isAlreadyLiked(boardId, username)) {
            model.addAttribute("like", true);
        }else {
            model.addAttribute("like", false);
        }

        // 게시글의 멀티 파일 정보를 가져옵니다.
        List<BoardMultiFile> multiFiles = boardService.getBoardMultiFilesByBoardId(boardId);

        model.addAttribute("board", boardDto);
        model.addAttribute("multiFiles", multiFiles); // 멀티 파일 리스트를 모델에 추가합니다.
        return "board/detail";
    }

    // 🎯 3. 첨부파일 다운로드
    @ResponseBody
    @GetMapping("/posts/{postId}/files/{fileId}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable final Long postId, @PathVariable final Long fileId) {
        BoardMultiFile file = boardService.findBoardMultiFileBySeq(fileId);// 파일 정보를 조회합니다.
        Resource resource = fileUtils.readFileAsResource(file); // 파일을 리소스로 변환하는 핵심 로직입니다.

        try {
            String filename = URLEncoder.encode(file.getFileName(), "UTF-8"); //파일 이름에 특수 문자나 공백이 포함되어 있을 때도 안전하게 처리됩니다.

            System.out.println(" 보드컨트롤러 / 첨부파일 다운로드 / filename = " + filename);
            System.out.println(" 보드컨트롤러 / 첨부파일 다운로드 / resource.getFilename() = " + resource.getFilename());
            System.out.println(" 보드컨트롤러 / 첨부파일 다운로드 / resource.getFile() = " + resource.getFile());
            System.out.println(" 보드컨트롤러 / 첨부파일 다운로드 / resource.getURI() = " + resource.getURI());
            System.out.println(" 보드컨트롤러 / 첨부파일 다운로드 / resource.getURL() = " + resource.getURL());

            // 파일 크기가 null이면 0으로 처리합니다.
//            long fileSize = (file.getFileSize() != null) ? file.getFileSize().length : 0;

            // 파일 크기를 계산하고, 파일 다운로드 응답을 구성합니다.
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\";")  //이를 통해 브라우저는 파일을 다운로드하도록 지시
//                    .header(HttpHeaders.CONTENT_LENGTH, fileSize + "")
                    .body(resource);

        } catch (UnsupportedEncodingException e) {
            // 파일 이름 인코딩 오류 시 예외 처리합니다.
            throw new RuntimeException("filename encoding failed : " + file.getFileOriginalName());
        } catch (IOException e) {
            // 입출력 예외 시 예외 처리합니다.
            throw new RuntimeException(e);
        }
    }


    // 🎯 4. 특정 게시글의 댓글 조회 - Test용
    @ResponseBody
    @GetMapping("/onlyComment/{boardId}")
    public List<CommentDto> getAllComments(@PathVariable Long boardId) {
        return boardMapper.getHierarchicalCommentsByBoardId(boardId);
    }

    // 🎯 5. 글 작성 폼
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        // 현재 로그인한 사용자 정보 가져오기
        String username = getUsername();
        UserDto userDto = getUserDto();
        model.addAttribute("username", username);
        model.addAttribute("nickname", userDto.getNickname());
        model.addAttribute("role", userDto.getRole());
        return "board/create";
    }

    // 🎯 6. 글 작성 (다중 멀티파일 업로드)
    // 컨트롤러에서 인자로 MultiFileDto 리스트를 받도록 수정
    @PostMapping("/create")
    public String createBoard(@RequestParam("title") String title,  @RequestParam("content") String content,
                              @RequestParam("files") List<MultipartFile> files, Model model) throws IOException {
        String username = getUsername();
        UserDto userDto = getUserDto();
        model.addAttribute("username", username);
        model.addAttribute("nickname", userDto.getNickname());
        model.addAttribute("role", userDto.getRole());

        BoardDto boardDto = new BoardDto(title, content);
        Long boardId = boardService.createBoard(boardDto, files);

        return "redirect:/boards/" + boardId;
    }


    // 🎯 7. 글 수정 폼
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

    // 🎯 8. 글 수정  TODO 멀티파일 수정 되도록 고치기  ( )
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

        String username = getUsername();
        UserDto userDto = getUserDto();
        boardDto.setNickname(userDto.getNickname());
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

    // 🎯 9. 글 삭제 (status 만 Y로 업데이트)
    @GetMapping ("/{boardId}/delete")
    public String deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return "redirect:/boards"; // 게시글 삭제 후 목록 페이지로 리다이렉트합니다.
    }

    // 🎯 10. 글 검색
    @GetMapping("/search")
    public String searchBoard(@RequestParam String keyword, Model model, @RequestParam(defaultValue = "1") int currentPage) {
        int pageSize = 15; // 페이지당 게시물 수
        List<BoardDto> boards = boardService.getSearchBoardsByPage(keyword, currentPage, pageSize);
        int totalPages = boardService.getSearchBoardsTotalPages(keyword, pageSize);

        // 현재 로그인한 사용자 정보 가져오기
        String username = getUsername();
        UserDto userDto = getUserDto();

        model.addAttribute("username", username);
        model.addAttribute("nickname", userDto.getNickname());
        model.addAttribute("role", userDto.getRole());

        model.addAttribute("boards", boards);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);

        return "board";
    }


//    // 🎯 11. 좋아요 추가 / 취소 API  // TODO form 형식이 아닌 ajax로 수정!!!
//    @RequestMapping("/likes/{boardId}")
//    public String addLike(@PathVariable Long boardId) {
//        String username = getUsername();
//        // 좋아요 수를 증가시키는 로직을 구현
//        BoardDto board = boardMapper.getBoardById(boardId);
//
//        // 현재 사용자가 해당 게시글에 좋아요를 누르지 않은 경우에만 좋아요 수를 증가시키고 좋아요를 추가
//        if (!isAlreadyLiked(boardId, username)) {
//            board.setCntLike(board.getCntLike() + 1);
//            boardMapper.updateBoardLikes(board);
//            boardMapper.saveBoardLike(new BoardLikeDto(boardId, username));
//        }else {
//            board.setCntLike(board.getCntLike() - 1);
//            boardMapper.updateBoardLikes(board);
//            boardMapper.deleteBoardLike(boardId, username);
//        }
//        return "redirect:/boards/" + boardId;
//    }

    // 🎯 11. 좋아요 추가 / 취소 API
    @ResponseBody
    @PostMapping("/likes/{boardId}")
    public BoardDto addLike(@PathVariable Long boardId) {
        String username = getUsername();
        // 좋아요 수를 증가시키는 로직을 구현
        BoardDto boardDto = boardMapper.getBoardById(boardId);

        // 현재 사용자가 해당 게시글에 좋아요를 누르지 않은 경우에만 좋아요 수를 증가시키고 좋아요를 추가
        if (!isAlreadyLiked(boardId, username)) {
            boardDto.setCntLike(boardDto.getCntLike() + 1);
            boardDto.setLike(true);
            boardMapper.updateBoardLikes(boardDto);
            boardMapper.saveBoardLike(new BoardLikeDto(boardId, username));
        }else {
            boardDto.setCntLike(boardDto.getCntLike() - 1);
            boardDto.setLike(false);
            boardMapper.updateBoardLikes(boardDto);
            boardMapper.deleteBoardLike(boardId, username);
        }
        return boardDto;
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
}
