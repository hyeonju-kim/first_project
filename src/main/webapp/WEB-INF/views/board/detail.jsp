<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 상세 정보</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <style>
        /* 댓글 스타일링 */
        .card-title1 {
            font-weight: bold; /* 굵게 표시 */
            font-size: 20px; /* 폰트 크기 지정 (원하는 크기로 수정) */
        }
        .comment-box {
            background-color: #f2f2f2;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ddd;
        }
        .file-info {
            background-color: #f2f2f2;
            padding: 10px;
            margin-bottom: 20px;
            margin-left: 20px;
            margin-right: 20px;
            border: 1px solid #ddd;
        }

        .comment-meta {
            font-size: 14px; /* 글꼴 크기 조절 */
            color: #BDBDBD; /* 회색으로 설정 */
            margin-bottom: 5px;
        }

        .comment-nickname {
            color: #555;
        }

        .comment-date {
            color: #555;
            margin-left: 10px;
        }

        .comment-content {
            font-size: 16px; /* 글꼴 크기 조절 */
            color: #333;
        }
    </style>

</head>
<body>
<div class="container mt-4">
    <h2 class="text-center">게시글 상세 정보</h2>
    <a href="/boards" class="btn btn-primary">게시판 목록</a>
    <div class="row mt-4">
        <div class="col-md-12">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title1">제목: ${board.title}</h5>
                    <p class="card-text">작성자: ${board.nickname}</p>
                    <p class="card-text">작성일: ${board.createdAt}</p>
                </div>
            </div>
        </div>
    </div>
    <div class="row mt-4">
        <div class="col-md-12">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">내용</h5>
                    <p class="card-text">${board.content}</p>
                </div>
            </div>
        </div>
    </div>


    <div class="row mt-4">
        <div class="col-md-12">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">첨부 파일</h5>
                </div>


                <c:forEach var="multiFile" items="${multiFiles}">
                    <div class="file-info">
                        <span class="glyphicon glyphicon-camera" aria-hidden="true"></span>
                        <a href="/boards/posts/${multiFile.boardId}/files/${multiFile.fileId}/download">${multiFile.fileOriginalName}</a>
                        <span>${multiFile.fileSize}kb</span>
                    </div>
                </c:forEach>

            </div>
        </div>
    </div>


    <!-- 댓글 목록 표시 -->
    <div class="row mt-4">
        <div class="col-md-12">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">댓글</h5>
                    <c:forEach items="${board.comments}" var="comment">
                        <div class="comment-box" style="margin-left: ${(comment.level-1) * 20}px;"> <!-- 들여쓰기 넓이 조절 -->
                            <!-- 댓글 내용 표시 -->
                            <div class="comment-meta">
                                <span class="comment-nickname">${comment.nickname}</span>
                                <span class="comment-date">${comment.createdAt}</span>
                                <a href="/boards/${board.boardId}/editComment?commentId=${comment.commentId}" class="btn btn-sm btn-primary ml-2">수정</a>
                                <a href="#" class="btn btn-sm btn-danger ml-2" onclick="deleteComment(${comment.commentId})">삭제</a>
                                <a href="#" class="btn btn-sm btn-success ml-2" onclick="toggleReplyForm(${comment.commentId})">답글 작성</a>
                            </div>
                            <div class="comment-content">${comment.content}</div>
                            <br>
                            <!-- 대댓글 입력 폼 -->
                            <div id="replyForm_${comment.commentId}" style="display: none;">
                                <form id="recommentForm" action="/boards/${comment.boardId}/addReply/${comment.commentId}" method="post">
                                    <div class="form-group">
                                        <textarea class="form-control" name="content" placeholder="댓글을 작성해주세요" rows="3"></textarea>
                                    </div>
                                    <button type="submit" class="btn btn-primary">댓글 작성</button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>


                </div>
            </div>
        </div>
    </div>


    <!-- 댓글 작성 폼 -->
    <div class="row mt-4">
        <div class="col-md-12">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">댓글 작성</h5>
                    <form id="commentForm" action="/boards/${board.boardId}/addComment" method="post">
                        <div class="form-group">
                            <textarea class="form-control" name="content" placeholder="댓글을 입력하세요" rows="3"></textarea>
                        </div>
                        <button type="submit" class="btn btn-primary">댓글 작성</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <br>
    <br>
    <br>
    <div class="row mt-4">
        <div class="col-md-12">
            <a href="/boards/${board.boardId}/edit" class="btn btn-primary" >수정</a>
            <a href="/boards/${board.boardId}/delete" class="btn btn-danger" onclick="deleteBoard()">삭제</a>
        </div>
        <br>
        <br>
        <br>
        <br>
        <br>
    </div>


    <script>

        function deleteBoard() {
            // 게시글 삭제를 위한 AJAX 요청 또는 서버로의 요청을 수행합니다.
            // 성공 시 아래의 알림 메시지를 표시합니다.
            alert('게시글이 삭제되었습니다!');
            // 여기에서 삭제 동작을 수행하세요.
            window.location.reload();
        }


        function deleteComment(commentId) {
            if (confirm('댓글을 삭제하시겠습니까?')) {
                // 확인 버튼을 눌렀을 때만 아래 코드가 실행됩니다.

                // AJAX를 사용하여 삭제 요청을 서버로 보냅니다.
                var xhr = new XMLHttpRequest();
                xhr.open('GET', '/boards/${board.boardId}/deleteComment/'+ commentId, true);
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4) {
                        if (xhr.status === 200) {
                            // 삭제 요청이 성공적으로 처리되었을 때의 동작
                            alert('댓글이 삭제되었습니다!');
                            // 삭제 후 필요한 추가 동작을 수행할 수 있습니다.
                        } else {
                            // 삭제 요청이 실패했을 때의 동작
                            alert('댓글 삭제에 실패했습니다.');
                        }
                    }
                };
                xhr.send();
            }
            window.location.reload();
        }

        // 대댓글 입력 폼 토글 함수
        function toggleReplyForm(commentId) {
            var replyForm = document.getElementById(`replyForm_` + commentId);
            replyForm.style.display = replyForm.style.display === 'none' ? 'block' : 'none';
        }

        // // 대댓글 입력 폼 토글 함수
        // function toggleReplyForm(commentId, recommentId) {
        //     var replyForm = document.getElementById(`replyForm_` + commentId + `_` + recommentId);
        //     replyForm.style.display = replyForm.style.display === 'none' ? 'block' : 'none';
        // }
    </script>


</div>
</body>
</html>