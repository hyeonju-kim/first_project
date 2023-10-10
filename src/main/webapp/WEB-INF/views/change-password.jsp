<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
            background: url("/images/background_salad.png"); /* 이미지 폴더에 있는 이미지를 사용 */
            background-size: cover;

            /*background: -webkit-gradient(linear, left bottom, right top, from(#92b5db), to(#1d466c));*/
            /*background: -webkit-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);*/
            /*background: -moz-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);*/
            /*background: -o-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);*/
            /*background: linear-gradient(to top right, #92b5db 0%, #1d466c 100%);*/
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
        /* 로고 이미지 크기 조정 스타일 추가 */
        .logo-container img {
            max-width: 80%;
            height: auto;
            margin-bottom: 40px;
        }
    </style>
</head>

<body>
<div class="container">
    <div class="input-form-backgroud row">
        <div class="input-form col-md-5 mx-auto  text-center">
            <div class="logo-container">
                <img src="images/diet-record_logo.png" alt="Diet Record 로고"><%-- 로고 추가--%>
            </div>

            <form class="validation-form" method="post" action="/login">
                <div class="mb-3">
                    <input type="password" class="form-control" id="password" name="password" placeholder="임시 비밀번호를 입력해주세요." required>
                    <div class="invalid-feedback">
                        임시 비밀번호를 입력해주세요.
                    </div>
                </div>
                <div class="mb-3">
                    <input type="password" class="form-control" id="newPassword" name="newPassword" placeholder="새로운 비밀번호를 입력해주세요." required>
                    <div class="invalid-feedback">
                        새로운 비밀번호를 입력해주세요.
                    </div>
                </div>
                <hr class="mb-4">
                <button class="btn btn-primary btn-lg btn-block" type="button" onclick="changePassword()">비밀번호 변경</button>
            </form>
        </div>
    </div>
    <footer class="my-3 text-center text-small">
        <p class="mb-1">&copy; 2021 Your Company</p>
    </footer>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    function changePassword() {
        // 1. 입력한 임시 비밀번호와 새로운 비밀번호 가져오기
        let password = $('#password').val();
        let newPassword = $('#newPassword').val();

        // 2. 가져온 정보를 data로 묶기
        let data = {
            "password": password,
            "newPassword": newPassword
        }

        // 3. 클라이언트에서 가져온 데이터를 서버로 전송
        $.ajax({
            type: 'POST',
            url: '/change-password',
            data: JSON.stringify(data),
            contentType: 'application/json', // JSON 형식의 데이터를 전송
            success: function (response, status, xhr) {
                console.log(response); // 응답 body의 데이터
                console.log(status); // "success"로 고정인듯함
                console.log(xhr);
                if (xhr.status === 200) {
                    // 서버 응답의 상태 코드가 200일 때만 실행
                    alert('비밀번호 변경 완료!');
                    location.href = "/login"; // 로그인 페이지로 이동
                } else {
                    // 실패 처리
                    alert('서버에서 오류가 발생했습니다.');
                }
            },
            error: function (response, status, xhr) {
                // 클라이언트에서 서버 요청 실패 시 실행
                console.log(response); // 응답 body의 데이터
                alert('서버 요청 실패');
            }
        });
    }
</script>
</body>

</html>
