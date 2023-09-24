<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>마이페이지 화면</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <style>
        .center-title {
            text-align: center;
        }
    </style>
</head>

<body>
<div class="container mt-5">
    <h2 class="text-center mb-4">사용자 상세 페이지</h2>
    <a href="/boards" class="btn btn-primary mt-3">게시판 목록</a>
    <br>
    <br>
    <div class="input-form-backgroud row">
        <div class="input-form col-md-12 mx-auto">
            <form class="validation-form" action="/login" method="post" novalidate>
                <div class="mb-3">
                    <label for="name">이름</label>
                    <input type="text" class="form-control" id="name" name="name" value="${user.name}" readonly>
                </div>
                <div class="mb-3">
                    <label for="username">이메일</label>
                    <input type="email" class="form-control" id="username" name="email" value="${user.username}" readonly>
                </div>
                <div class="mb-3">
                    <label for="nickname">닉네임</label>
                    <input type="text" class="form-control" id="nickname" name="nickname" value="${user.nickname}" readonly>
                </div>
                <div class="mb-3">
                    <label for="phoneNumber">휴대폰 번호</label>
                    <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}" readonly>
                </div>
                <div class="mb-3">
                    <label for="profilePicture">프로필 사진</label>
                </div>
                <img src="${file}" alt="프로필 사진"  height="200" id="profilePicture"/>

                <div class="mb-3">
                    <label for="streetAdr">주소</label>
                    <input type="text" class="form-control" id="streetAdr" name="streetAdr" value="${user.streetAdr}" readonly>
                </div>
                <div class="mb-3">
                    <label for="detailAdr">상세 주소</label>
                    <input type="text" class="form-control" id="detailAdr" name="detailAdr" value="${user.detailAdr}" readonly>
                </div>


<%--                <hr class="mb-4">--%>
<%--                <div class="text-center">--%>
<%--                    <a class="btn btn-primary" href="/change-password">비밀번호 변경하기</a>--%>
<%--                </div>--%>
            </form>
        </div>
    </div>
    <footer class="my-3 text-center text-small">
        <p class="mb-1">&copy; 2021 Your Company</p>
    </footer>
</div>


<!-- 필요한 스크립트와 스타일을 추가하려면 head 부분에 추가합니다. -->

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
</script>

</body>

</html>