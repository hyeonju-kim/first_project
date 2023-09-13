<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        /* Add your custom styles here */
    </style>
</head>

<body>
<div class="container">
    <h1 class="mt-5">게시판</h1>

    <!-- 로그인 사용자 이름 표시 -->
<%--    <div class="text-right mt-2">--%>
<%--        <span id="userName"></span> 님 안녕하세요!--%>
<%--    </div>--%>

    <!-- 글 작성 버튼 -->
    <a href="/write" class="btn btn-primary mb-3">글 작성</a>

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
        <tr>
            <td><a href="/view/1">첫 번째 글 제목</a></td>
            <td>작성자1</td>
            <td>2023-09-12</td>
        </tr>
        <tr>
            <td><a href="/view/2">두 번째 글 제목</a></td>
            <td>작성자2</td>
            <td>2023-09-13</td>
        </tr>
        <!-- 기타 글 목록 아이템 추가 -->
        </tbody>
    </table>

    <!-- 페이징 처리 -->
    <ul class="pagination">
        <li class="page-item"><a class="page-link" href="#">이전</a></li>
        <li class="page-item active"><a class="page-link" href="#">1</a></li>
        <li class="page-item"><a class="page-link" href="#">2</a></li>
        <li class="page-item"><a class="page-link" href="#">3</a></li>
        <li class="page-item"><a class="page-link" href="#">다음</a></li>
    </ul>
</div>

<script>
    // 사용자 이름을 가져와서 화면에 표시
    // var userName = "John"; // 여기에 실제 사용자 이름을 가져오는 코드를 추가해야 합니다.
    // document.getElementById("userName").textContent = userName;
</script>
</body>

</html>
