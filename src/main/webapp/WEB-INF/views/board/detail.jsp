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
<div class="container">
    <h2 class="text-center">게시글 상세 정보</h2>
    <div class="card">
        <div class="card-body">
            <h5 class="card-title"><%= board.getTitle() %></h5>
            <p class="card-text"><%= board.getContent() %></p>
            <p class="card-text">작성자: <%= board.getUserId() %></p>
            <p class="card-text">작성일: <%= board.getCreatedAt() %></p>
        </div>
    </div>
</div>
</body>
</html>
