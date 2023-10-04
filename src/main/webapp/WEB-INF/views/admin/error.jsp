<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>에러</title>

</head>

<body>
<!-- 만들어 놓은 헤더 포함 !!!!  -->
<%@ include file="../header.jsp" %>
<div class="container">
    <br>
    <h2 class="center-title">에러 목록</h2>
    <br>
    <div class="row justify-content-center">
        <div class="col-md-10">

            <!-- 에러 목록 -->
            <table class="table table-striped mt-4">
                <thead>
                <tr>
                    <th>번호</th>
                    <th>계정</th>
                    <th>에러 시간</th>
                    <th>에러 타입</th>
                    <th>에러 메시지</th>
                </tr>
                </thead>
                <tbody>
                <!-- 에러 목록을 반복해서 표시 -->
                <c:forEach var="error" items="${errorList}">
                    <tr>
                        <td><c:out value="${error.seq}" /></td>
                        <td><c:out value="${error.username}" /></td>
                        <td><c:out value="${error.errorTime}" /></td>
                        <td style="max-width: 250px; overflow: hidden; text-overflow: ellipsis; word-wrap: break-word;">
                            <c:out value="${error.errorType}" />
                        </td>
                        <td style="max-width: 250px; overflow: hidden; text-overflow: ellipsis; word-wrap: break-word;">
                            <c:out value="${error.errorMessage}" />
                        </td>

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
