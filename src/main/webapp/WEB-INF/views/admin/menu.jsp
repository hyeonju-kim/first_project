<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>메뉴 관리</title>

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
</script>
</body>

</html>
