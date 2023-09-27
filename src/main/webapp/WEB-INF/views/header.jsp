<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- 헤더 영역 -->
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" href="/boards">자유게시판</a>
                        </li>
                        <!-- 사용자 역할이 "admin"인 경우에만 아래 메뉴 표시 -->
                        <c:if test="${role == 'admin'}">
                            <li class="nav-item">
                                <a class="nav-link" href="/admin/statistics">사용자 통계</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/admin/users">사용자 정보</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/admin/menu">메뉴 관리</a>
                            </li>
                        </c:if>
                    </ul>
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item">
                            <c:if test="${not empty username && username != 'anonymousUser'}">
                                <span class="nav-link">${nickname}님 안녕하세요!</span>
                                <c:if test="${role == 'admin'}">
                                    <span class="admin-mode">관리자모드</span>
                                </c:if>
                                <a href="/logout" class="btn btn-danger ml-2">로그아웃</a>
                                <a href="/mypage" class="btn btn-primary ml-2">마이페이지</a>
                            </c:if>
                            <c:if test="${empty username || username == 'anonymousUser'}">
                                <a href="/login" class="btn btn-primary">로그인</a>
                            </c:if>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
    </div>
</div>


<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
      integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

<!-- Your custom styles -->
<style>
    .center-title {
        text-align: center;
    }

    .pagination {
        margin: 20px auto;
    }

    .current-page {
        border: 1px solid #ccc; /* 테두리 스타일 설정 */
        box-shadow: 2px 2px 5px #888; /* 그림자 설정 */
        padding: 5px 10px; /* 내부 여백 설정 */
        border-radius: 5px; /* 모서리를 둥글게 만들기 위한 설정 */
    }

    .menu-tab {
        border-bottom: 1px solid #ccc; /* 구분 선 추가 */
        margin-bottom: 20px; /* 구분 선과 메뉴 탭 사이 여백 */
        padding-bottom: 10px; /* 메뉴 탭과 메뉴 이름 사이 여백 */
    }

    .menu-name {
        text-align: center; /* 메뉴 이름 가운데 정렬 */
        font-weight: bold; /* 굵은 글꼴 */
    }

    /* 세로 구분선 스타일 */
    .vertical-divider {
        border-left: 1px solid #8C8C8C;
        height: 100%;
    }
    .admin-mode {
        background-color: #ccc; /* 배경색을 회색(#ccc)으로 지정 */
        padding: 5px 10px; /* 내부 여백 설정 */
        border-radius: 10px; /* 둥근 모서리를 위한 설정 */
        display: inline-block; /* 텍스트 크기와 일치하는 너비로 설정 */
    }

</style>
