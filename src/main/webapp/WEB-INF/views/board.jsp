<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>메인 화면</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <!-- Your custom styles -->
    <style>
        .center-title {
            text-align: center;
        }
    </style>
</head>

<body>
<div class="container">
    <!-- 마이페이지 버튼 -->
    <div class="text-right mt-2">
        <a href="/logout" class="btn btn-danger mr-2">로그아웃</a>
        <a href="/mypage" class="btn btn-primary mr-2">마이페이지</a>
    </div>
    <br>
    <br>
    <br>
    <br>
    <h2 class="center-title">게시판</h2>



    <!-- 로그인 사용자 이름 표시 -->
    <%--    <div class="text-right mt-2">--%>
    <%--        <span id="userName"></span> 님 안녕하세요!--%>
    <%--    </div>--%>

    <!-- 글 작성 버튼 -->
    <a href="/boards/create" class="btn btn-primary mb-3">글 작성</a>

    <!-- 글 목록 -->
    <table class="table">
        <thead>
        <tr>
            <th scope="col">제목</th>
            <th scope="col">작성자</th>
            <th scope="col">작성일</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${boards}" var="board">
            <tr>
                <td><a href="/boards/${board.boardId}">${board.title}</a></td>
                <td>${board.nickname}</td>
                <td>${board.createdAt}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <br>
    <br>

    <!-- 글 목록 아래 검색창 -->
    <form action="/boards/search" method="GET" style="max-width: 300px; margin: 0 auto;">
        <div class="input-group mb-3">
            <input type="text" class="form-control" placeholder="검색어를 입력하세요" name="keyword" aria-label="검색어" aria-describedby="basic-addon2">
            <div class="input-group-append">
                <button class="btn btn-primary" type="submit">검색</button>
            </div>
        </div>
    </form>

    <br>
    <br>

    <!-- 페이징 처리 -->
    <div class="text-center" style="position: fixed; bottom: 40px; left: 50%; transform: translateX(-50%);">
        <ul class="pagination">
            <li class="page-item"><a class="page-link" href="#">이전</a></li>
            <li class="page-item active"><a class="page-link" href="#">1</a></li>
            <li class="page-item"><a class="page-link" href="#">2</a></li>
            <li class="page-item"><a class="page-link" href="#">3</a></li>
            <li class="page-item"><a class="page-link" href="#">다음</a></li>
        </ul>
    </div>
</div>

<script>
    // 사용자 이름을 가져와서 화면에 표시
    // var userName = "John"; // 여기에 실제 사용자 이름을 가져오는 코드를 추가해야 합니다.
    // document.getElementById("userName").textContent = userName;
</script>
</body>

</html>