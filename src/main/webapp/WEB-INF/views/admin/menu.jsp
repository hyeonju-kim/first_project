<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>메뉴 관리</title>

<%--    <!-- Bootstrap CSS -->--%>
<%--    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"--%>
<%--          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">--%>

<%--    <!-- Your custom styles -->--%>
<%--    <style>--%>
<%--        .center-title {--%>
<%--            text-align: center;--%>
<%--        }--%>

<%--        .pagination {--%>
<%--            margin: 20px auto;--%>
<%--        }--%>

<%--        .current-page {--%>
<%--            border: 1px solid #ccc; /* 테두리 스타일 설정 */--%>
<%--            box-shadow: 2px 2px 5px #888; /* 그림자 설정 */--%>
<%--            padding: 5px 10px; /* 내부 여백 설정 */--%>
<%--            border-radius: 5px; /* 모서리를 둥글게 만들기 위한 설정 */--%>
<%--        }--%>

<%--        .menu-tab {--%>
<%--            border-bottom: 1px solid #ccc; /* 구분 선 추가 */--%>
<%--            margin-bottom: 20px; /* 구분 선과 메뉴 탭 사이 여백 */--%>
<%--            padding-bottom: 10px; /* 메뉴 탭과 메뉴 이름 사이 여백 */--%>
<%--        }--%>

<%--        .menu-name {--%>
<%--            text-align: center; /* 메뉴 이름 가운데 정렬 */--%>
<%--            font-weight: bold; /* 굵은 글꼴 */--%>
<%--        }--%>

<%--        /* 세로 구분선 스타일 */--%>
<%--        .vertical-divider {--%>
<%--            border-left: 1px solid #8C8C8C;--%>
<%--            height: 100%;--%>
<%--        }--%>
<%--        .admin-mode {--%>
<%--            background-color: #ccc; /* 배경색을 회색(#ccc)으로 지정 */--%>
<%--            padding: 5px 10px; /* 내부 여백 설정 */--%>
<%--            border-radius: 10px; /* 둥근 모서리를 위한 설정 */--%>
<%--            display: inline-block; /* 텍스트 크기와 일치하는 너비로 설정 */--%>
<%--        }--%>
<%--    </style>--%>
</head>

<body>
<!-- 만들어 놓은 헤더 포함 !!!!  -->
<%@ include file="../header.jsp" %>
<div class="container">
    <br>
    <h2 class="center-title">메뉴 관리</h2>
    <br>
    <div class="row justify-content-center">
        <div class="col-md-8">
<%--    <div class="row">--%>
<%--        <!-- 왼쪽 메뉴 탭 -->--%>
<%--        <div class="col-md-3">--%>
<%--            <div class="menu-tab">--%>
<%--                <div class="menu-name">메뉴 관리</div>--%>
<%--            </div>--%>
<%--            <ul class="nav flex-column">--%>
<%--                <li class="nav-item">--%>
<%--                    <a class="nav-link" href="/boards">자유게시판</a>--%>
<%--                </li>--%>
<%--                <!-- 사용자 역할이 "admin"인 경우에만 아래 두 메뉴 표시 -->--%>
<%--                <c:if test="${role == 'admin'}">--%>
<%--                    <li class="nav-item">--%>
<%--                        <a class="nav-link" href="/admin/statistics">사용자 통계</a>--%>
<%--                    </li>--%>
<%--                    <li class="nav-item">--%>
<%--                        <a class="nav-link" href="/admin/users">사용자 정보</a>--%>
<%--                    </li>--%>
<%--                    <li class="nav-item">--%>
<%--                        <a class="nav-link" href="/admin/menu">메뉴 관리</a>--%>
<%--                    </li>--%>
<%--                </c:if>--%>
<%--            </ul>--%>
<%--        </div>--%>
<%--        <!-- 세로 구분선 -->--%>
<%--        <div class="col-md-1 vertical-divider"></div>--%>
<%--        <div class="col-md-8">--%>
<%--            <div class="text-right mt-2">--%>
<%--                <c:if test="${not empty username && username != 'anonymousUser'}">--%>
<%--                    <h6><span>${nickname}님 안녕하세요!</span></h6>--%>
<%--                    <c:if test="${role == 'admin'}">--%>
<%--                        <h6><span class="admin-mode">관리자모드</span></h6>--%>
<%--                    </c:if>--%>
<%--                    <a href="/logout" class="btn btn-danger mr-2">로그아웃</a>--%>
<%--                    <a href="/mypage" class="btn btn-primary mr-2">마이페이지</a>--%>
<%--                </c:if>--%>
<%--                <c:if test="${empty username || username == 'anonymousUser'}">--%>
<%--                    <a href="/login" class="btn btn-primary">로그인</a>--%>
<%--                </c:if>--%>
<%--            </div>--%>


<%--            <br>--%>
<%--            <h2 class="center-title">메뉴 목록</h2>--%>


            <!-- 글 목록 -->
            <table class="table table-striped mt-4">
                <thead>
                <tr>
                    <th>메뉴 이름</th>
                    <th>URL</th>
                    <th>권한</th>
                    <th>사용 여부</th>
                    <th>순서</th>
                    <th>등록 일자</th>
                    <th>등록자</th>
                </tr>
                </thead>
                <tbody>
                <!-- 메뉴 목록을 반복해서 표시 -->
                <c:forEach var="menu" items="${menu}">
                    <tr>
                        <td><c:out value="${menu.menuName}" /></td>
                        <td><c:out value="${menu.url}" /></td>
                        <td><c:out value="${menu.auth}" /></td>
                        <td><c:out value="${menu.useYN}" /></td>
                        <td><c:out value="${menu.order}" /></td>
                        <td><c:out value="${menu.regDate}" /></td>
                        <td><c:out value="${menu.regId}" /></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div>
    </div>
</div>

<script>
    // 사용자 이름을 가져와서 화면에 표시
    // var userName = "John"; // 여기에 실제 사용자 이름을 가져오는 코드를 추가해야 합니다.
    // document.getElementById("userName").textContent = userName;
</script>
</body>

</html>
