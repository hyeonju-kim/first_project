<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시판</title>
    <style>
    .mytitle {
        width: 100%;
        height: 150px;
        background-image: linear-gradient(0deg, rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3)), url('/images/board_back.png');
        background-position: center;
        background-size: cover;
        color: white;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        margin-bottom: 20px;
    }

    /* 각 셀의 높이를 2배로 고정 */
    .table td {
        line-height: 2.5;
    }

    </style>
</head>

<body>
<!-- 만들어 놓은 헤더 포함 !!!!  -->
<%@ include file="header.jsp" %>

<div class="container">
    <div class="mytitle">
        <h4>다양한 사람들과 소통해보세요</h4>
    </div>


    <div class="row justify-content-center">
        <div class="col-md-9">


            <!-- 글 작성 버튼 -->
            <a href="/boards/create" class="btn btn-primary mb-3">글 작성</a>

            <!-- 글 목록 -->
            <table class="table table-striped mt-4" style="table-layout: fixed;">
                <colgroup>
                    <col style="width: 10%;" /> <!-- 제목 컬럼 너비를 40%로 고정 -->
                    <col style="width: 40%;" /> <!-- 제목 컬럼 너비를 40%로 고정 -->
                    <col style="width: 20%;" /> <!-- 작성자 컬럼 너비를 30%로 고정 -->
                    <col style="width: 20%;" /> <!-- 작성일 컬럼 너비를 30%로 고정 -->
                    <col style="width: 10%;" /> <!-- 작성일 컬럼 너비를 30%로 고정 -->
                </colgroup>
                <thead>
                <tr>
                    <th scope="col">번호</th>
                    <th scope="col">제목</th>
                    <th scope="col">작성자</th>
                    <th scope="col">작성일</th>
                    <th scope="col">like</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${boards}" var="board">
                    <tr>
                        <td>${board.boardId}</td>
                        <td><a href="/boards/${board.boardId}">${board.title}</a></td>
                        <td>${board.nickname}</td>
                        <td>${board.createdAt}</td>
                        <td>${board.cntLike}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <!-- 글 목록 아래 검색창 -->
            <form action="/boards/search" method="GET" style="max-width: 300px; margin: 0 auto;">
                <div class="input-group mb-3">
                    <input type="text" class="form-control" placeholder="검색어를 입력하세요" name="keyword" aria-label="검색어"
                           aria-describedby="basic-addon2"  value="${keyword}">
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
                                <c:if test="${empty keyword}">
                                    <a class="page-link" href="/boards?currentPage=${currentPage - 1}">이전</a>
                                </c:if>
                                <c:if test="${not empty keyword}">
                                    <a class="page-link" href="/boards/search?keyword=${keyword}&currentPage=${currentPage - 1}">이전</a>
                                </c:if>
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
                                    <c:if test="${empty keyword}">
                                        <a class="page-link" href="/boards?currentPage=${pageNumber}">${pageNumber}</a>
                                    </c:if>
                                    <c:if test="${not empty keyword}">
                                        <a class="page-link" href="/boards/search?keyword=${keyword}&currentPage=${pageNumber}">${pageNumber}</a>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </li>
                    </c:forEach>
                    <li class="page-item">
                        <c:choose>
                            <c:when test="${currentPage < totalPages}">
                                <c:if test="${empty keyword}">
                                    <a class="page-link" href="/boards?currentPage=${currentPage + 1}">다음</a>
                                </c:if>
                                <c:if test="${not empty keyword}">
                                    <a class="page-link" href="/boards/search?keyword=${keyword}&currentPage=${currentPage + 1}">다음</a>
                                </c:if>
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
</script>
</body>

</html>
