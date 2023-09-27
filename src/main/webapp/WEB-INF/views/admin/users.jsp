<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>사용자 목록</title>


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
    <%--            border-left: 5px solid #8C8C8C;--%>
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
    <h2 class="center-title">회원 정보 목록</h2>
    <br>
    <div class="row justify-content-center">
        <div class="col-md-8">
            <%--    <div class="row">--%>
            <%--        <!-- 왼쪽 메뉴 탭 -->--%>
            <%--        <div class="col-md-3">--%>
            <%--            <div class="menu-tab">--%>
            <%--                <div class="menu-name">메뉴</div>--%>
            <%--            </div>--%>
            <%--            <ul class="nav flex-column">--%>
            <%--                <li class="nav-item">--%>
            <%--                    <a class="nav-link" href="/boards">자유게시판</a>--%>
            <%--                </li>--%>
            <%--                <li class="nav-item">--%>
            <%--                    <a class="nav-link" href="/admin/statistics">사용자 통계</a>--%>
            <%--                </li>--%>
            <%--                <li class="nav-item">--%>
            <%--                    <a class="nav-link" href="/admin/users">사용자 정보</a>--%>
            <%--                </li>--%>
            <%--                <li class="nav-item">--%>
            <%--                    <a class="nav-link" href="/admin/menu">메뉴 관리</a>--%>
            <%--                </li>--%>
            <%--            </ul>--%>
            <%--        </div>--%>
            <!-- 세로 구분선 -->
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
<%--            <h2 class="center-title">사용자 목록</h2>--%>
<%--            <br>--%>
<%--            <br>--%>

            <!-- 사용자 목록과 표 사이에 다운로드 버튼 추가 -->
            <div class="text-right">
                <input type="file" id="fileInput" style="display: none;" onchange="handleFileUpload()">
                <button class="btn btn-success" onclick="uploadFile()">사용자 정보 업로드</button>
                <a href="/admin/downloadUsers" class="btn btn-primary">사용자 정보 다운로드</a>
            </div>


            <br>

            <!-- 사용자 목록 -->
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">사용자 ID</th>
                    <th scope="col">이름</th>
                    <th scope="col">닉네임</th>
                    <th scope="col">전화번호</th>
                    <th scope="col">가입일</th>
                    <!-- 원하는 다른 사용자 정보 컬럼들을 추가하세요 -->
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.userId}</td>
                        <td><a href="/admin/userDetails?username=${user.username}">${user.name}</a></td>
                        <td>${user.nickname}</td>
                        <td>${user.phoneNumber}</td>
                        <td>${user.regDate}</td>
                        <!-- 원하는 다른 사용자 정보 컬럼들을 추가하세요 -->
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <br>
            <br>

            <!-- 사용자 목록 아래 검색창 -->
            <form action="/users/search" method="GET" style="max-width: 300px; margin: 0 auto;">
                <div class="input-group mb-3">
                    <input type="text" class="form-control" placeholder="검색어를 입력하세요" name="keyword" aria-label="검색어"
                           aria-describedby="basic-addon2">
                    <div class="input-group-append">
                        <button class="btn btn-primary" type="submit">검색</button>
                    </div>
                </div>
            </form>

            <br>
            <br>

            <!-- 페이징 처리 -->
            <div class="text-center">
                <ul class="pagination justify-content-center">
                    <li class="page-item">
                        <c:choose>
                            <c:when test="${currentPage > 1}">
                                <a class="page-link" href="/admin/users?currentPage=${currentPage - 1}">이전</a>
                            </c:when>
                            <c:otherwise>
                                <span class="page-link">이전</span>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <c:forEach begin="1" end="${totalPages}" var="pageNumber">
                        <li class="page-item">
                            <c:choose>
                                <c:when test="${pageNumber == currentPage}">
                                    <span class="page-link current-page">${pageNumber}</span>
                                </c:when>
                                <c:otherwise>
                                    <a class="page-link" href="/admin/users?currentPage=${pageNumber}">${pageNumber}</a>
                                </c:otherwise>
                            </c:choose>
                        </li>
                    </c:forEach>
                    <li class="page-item">
                        <c:choose>
                            <c:when test="${currentPage < totalPages}">
                                <a class="page-link" href="/admin/users?currentPage=${currentPage + 1}">다음</a>
                            </c:when>
                            <c:otherwise>
                                <span class="page-link">다음</span>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>

<script>
    function uploadFile() {
        // 파일 선택 필드를 클릭합니다.
        document.getElementById('fileInput').click();
    }

    function handleFileUpload() {
        var fileInput = document.getElementById('fileInput');
        if (fileInput.files.length > 0) {
            var file = fileInput.files[0]; // 선택된 파일 가져오기

            // 파일을 서버로 업로드하는 AJAX 요청을 보냅니다.
            var formData = new FormData();
            formData.append('file', file); // 'file'은 서버에서 파일을 받을 이름입니다.

            var xhr = new XMLHttpRequest();
            xhr.open('POST', '/admin/uploadUsers', true);
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) { // 요청이 완료되었을 때
                    if (xhr.status === 200) {
                        // 서버 응답의 상태 코드가 200일 때만 실행
                        alert('사용자 정보 업로드가 완료되었습니다!');
                        location.reload();
                    } else {
                        // 가입 실패 처리
                        alert('서버에서 오류가 발생했습니다.');
                    }
                }
            };
            xhr.send(formData);

            // 이벤트 리스너를 제거합니다.
            fileInput.removeEventListener('change', handleFileUpload);
        }
    }

    // 파일 선택 필드에서 파일을 선택하면 handleFileUpload 함수가 실행됩니다.
    document.getElementById('fileInput').addEventListener('change', handleFileUpload);
</script>

</body>

</html>
