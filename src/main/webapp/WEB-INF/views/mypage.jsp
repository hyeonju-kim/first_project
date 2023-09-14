<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인 화면</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <style>
        body {
            min-height: 100vh;

            background: -webkit-gradient(linear, left bottom, right top, from(#92b5db), to(#1d466c));
            background: -webkit-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
            background: -moz-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
            background: -o-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
            background: linear-gradient(to top right, #92b5db 0%, #1d466c 100%);
        }

        .input-form {
            max-width: 680px;

            margin-top: 80px;
            padding: 32px;

            background: #fff;
            -webkit-border-radius: 10px;
            -moz-border-radius: 10px;
            border-radius: 10px;
            -webkit-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
            -moz-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
            box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15)
        }
    </style>
</head>

<body>
<div class="container">
    <!-- 사용자 정보를 표시하는 부분 -->
    <div class="user-info">
        <h4 class="mb-3">마이페이지</h4>
        <p><strong>이름:</strong> ${name}</p>
        <p><strong>이메일:</strong>${username}</p>
        <p><strong>닉네임:</strong> ${nickname}</p>
        <p><strong>휴대폰 번호:</strong>${phoneNumber}</p>
        <!-- 다른 사용자 정보 항목들을 표시하려면 필요한 대로 추가합니다. -->
    </div>

    <!-- 비밀번호 변경 버튼 -->
    <div class="change-password">
        <button class="btn btn-primary" onclick="location.href='/change-password'" >비밀번호 변경하기</button>
    </div>
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
    //         headers: {
    //             'Authorization': 'Bearer ' + 'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFrYWswMDBAb…yMTl9.gKWg7whjna9DXIkNnB1jolY_cQZ_eXoMJJWGNIMFK00', // JWT 토큰 추가
    //             'Content-Type': 'application/json'
    //         },
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


    function sendTempPwToEmail() {
        // 1. 작성한 이메일 주소 가져오기
        var username = $('#username').val();
        console.log(username);

        // 2. 가져온 정보를 data로 묶기
        let data = {
            "username" : username
        }

        // 3. 클라에서 가져온 데이터를 서버로 전송
        $.ajax({
            type: 'POST',
            url: '/forgot-password',
            data: JSON.stringify(data),
            contentType: 'application/json', // JSON 형식의 데이터를 전송
            success: function (response, status, xhr) { // response 객체에 success, msg가 json형식으로 존재함(컨트롤러에서 반환한 값이 json으로 들어옴)
                console.log(response); //응답 body부 데이터
                console.log(status); //"succes"로 고정인듯함
                console.log(xhr);
                if (xhr.status === 200) {
                    // 서버 응답의 상태 코드가 200일 때만 실행
                    alert('메일이 전송 되었습니다!');
                    location.href = "/change-password";

                    // 인증번호 입력 칸 표시
                    $('#verificationCodeDiv').show();

                } else {
                    // 실패 처리
                    alert('서버에서 오류가 발생했습니다.');
                }
            },
            error: function (response, status, xhr) {
                // 서버 요청 실패 시 실행
                console.log('실패했다...')
                console.log(response); //응답 body부 데이터

                alert('서버 요청 실패');
            }
        });
    }
</script>

</body>

</html>