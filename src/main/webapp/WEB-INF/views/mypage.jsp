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
    <h2 class="text-center mb-4">마이페이지</h2>
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
<%--                    <input type="file" class="form-control" id="profilePicture" name="profilePicture">--%>
<%--                    <img src="${user.profilePictureLocation}" alt="프로필 사진" width="200" height="200" id="profilePicture">--%>
<%--                    <img src="C:/Users/weaver-gram-002/Desktop/고슴도치사진.jpg" alt="프로필 사진" width="200" height="200" id="profilePicture"/>--%>
                    <img src="${file}" alt="프로필 사진" width="200" height="200" id="profilePicture"/>
<%--                    <img src="<spring:url value = 'file:///C:/Users/weaver-gram-002/Desktop/고슴도치사진.jpg' />" alt="프로필 사진" width="200" height="200" id="profilePicture"/>--%>

                </div>
                <div class="mb-3">
                    <label for="streetAdr">주소</label>
                    <input type="text" class="form-control" id="streetAdr" name="streetAdr" value="${user.streetAdr}" readonly>
                </div>
                <div class="mb-3">
                    <label for="detailAdr">상세 주소</label>
                    <input type="text" class="form-control" id="detailAdr" name="detailAdr" value="${user.detailAdr}" readonly>
                </div>


                <hr class="mb-4">
                <div class="text-center">
                    <a class="btn btn-primary" href="/change-password">비밀번호 변경하기</a>
                </div>
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
    // $(document).ready(function () {
    //     // 서버에서 사용자 정보 가져오기
    //     $.ajax({
    //         url: '/mypage', // 컨트롤러 URL 설정
    //         type: 'GET',
    //         dataType: 'json',
    //         success: function (data) {
    //             // 가져온 데이터를 페이지에 채웁니다.
    //             $('#name').text(data.name);
    //             $('#username').text(data.username);
    //             $('#nickname').text(data.nickname);
    //             $('#phoneNumber').text(data.phoneNumber);
    //         },
    //         error: function (error) {
    //             console.error('Error:', error);
    //         }
    //     });
    // });


    // function sendTempPwToEmail() {
    //     // 1. 작성한 이메일 주소 가져오기
    //     var username = $('#username').val();
    //     console.log(username);
    //
    //     // 2. 가져온 정보를 data로 묶기
    //     let data = {
    //         "username" : username
    //     }
    //
    //     // 3. 클라에서 가져온 데이터를 서버로 전송
    //     $.ajax({
    //         type: 'POST',
    //         url: '/forgot-password',
    //         data: JSON.stringify(data),
    //         contentType: 'application/json', // JSON 형식의 데이터를 전송
    //         success: function (response, status, xhr) { // response 객체에 success, msg가 json형식으로 존재함(컨트롤러에서 반환한 값이 json으로 들어옴)
    //             console.log(response); //응답 body부 데이터
    //             console.log(status); //"succes"로 고정인듯함
    //             console.log(xhr);
    //             if (xhr.status === 200) {
    //                 // 서버 응답의 상태 코드가 200일 때만 실행
    //                 alert('메일이 전송 되었습니다!');
    //                 location.href = "/change-password";
    //
    //                 // 인증번호 입력 칸 표시
    //                 $('#verificationCodeDiv').show();
    //
    //             } else {
    //                 // 실패 처리
    //                 alert('서버에서 오류가 발생했습니다.');
    //             }
    //         },
    //         error: function (response, status, xhr) {
    //             // 서버 요청 실패 시 실행
    //             console.log('실패했다...')
    //             console.log(response); //응답 body부 데이터
    //
    //             alert('서버 요청 실패');
    //         }
    //     });
    // }
</script>

</body>

</html>