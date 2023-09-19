<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
</head>
<body>
<div class="container mt-4">
    <h2 class="text-center">게시글 상세 정보</h2>
    <a href="/boards" class="btn btn-primary">게시판 목록</a>
    <div class="row mt-4">
        <div class="col-md-12"> <!-- 수정된 부분 -->
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">제목: ${board.title}</h5>
                    <p class="card-text">작성자: ${board.nickname}</p>
                    <p class="card-text">작성일: ${board.createdAt}</p>
                </div>
            </div>
        </div>
    </div>
    <div class="row mt-4">
        <div class="col-md-12"> <!-- 수정된 부분 -->
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
            <a href="/boards/${board.boardId}/edit" class="btn btn-primary" >수정</a>
            <a href="/boards/${board.boardId}/delete" class="btn btn-danger" onclick="deleteBoard()">삭제</a>
        </div>
    </div>

    <script>


        function deleteBoard() {
            if (confirm('게시글을 삭제하시겠습니까?')) {
                // 게시글 삭제를 위한 AJAX 요청 또는 서버로의 요청을 수행합니다.
                // 성공 시 아래의 알림 메시지를 표시합니다.
                alert('게시글이 삭제되었습니다!');
            }
        }
    </script>


</div>
</body>
</html>