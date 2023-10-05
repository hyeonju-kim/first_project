<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>댓글 수정</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <h2>댓글 수정</h2>
    <form action="/boards/${boardId}/editComment/${commentId}" method="post">
        <div class="form-group">
            <label for="commentContent">댓글 내용:</label>
            <textarea class="form-control" id="commentContent" name="content" rows="3">${comment.content}</textarea>
        </div>
        <button type="submit" class="btn btn-primary">댓글 수정</button>
    </form>
</div>
</body>
</html>
