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

            <c:url value="/login" var="loginUrl"/>
            <form name="login_form" class="validation-form" action="/login" method="post">
                <div class="mb-3">
                    <input type="email" class="form-control" id="username" name="username" placeholder="이메일" required>
                    <div class="invalid-feedback">
                        이메일을 입력해주세요.
                    </div>
                </div>

                <div class="mb-3">
                    <input type="password" class="form-control" id="password" name="password" placeholder="비밀번호" required>
                    <div class="invalid-feedback">
                        비밀번호를 입력해주세요.
                    </div>
                </div>

                <hr class="mb-4">
                <button class="btn btn-primary btn-lg btn-block" type="button" onclick="login()">로그인</button>
                <div class="mt-3 text-center">
                    <a href="/forgot-password" class="mr-2 text-primary">비밀번호를 잊으셨나요?</a>|&nbsp;
                    <a href="/register" class="text-primary">회원가입</a>
                </div>
            </form>
        </div>
    </div>
    <footer class="my-3 text-center text-small">
        <p class="mb-1">&copy; 2021 Your Company</p>
    </footer>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    function login() {
        var username = $('#username').val()
        var password = $('#password').val()

        // 가져온 정보를 data로 묶기
        let data = {
            "username" : username,
            "password" : password
        }

        // 4. 클라에서 가져온 데이터를 서버로 전송 (이 예시에서는 URL이 '/register'로 가정)
        $.ajax({
            url: '/login',
            type: 'POST',
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: function (response, status, xhr) {
                console.log(response);
                console.log(status);
                console.log(xhr);
                if (xhr.status === 200) {
                    // 서버 응답의 상태 코드가 200일 때만 실행
                    alert(username + '님 환영합니다!');
                    location.href = "/boards";
                } else {
                    // 가입 실패 처리
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
        return true;
    }

</script>
</body>

</html>
