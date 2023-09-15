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
    <div class="input-form-backgroud row">
        <div class="input-form col-md-12 mx-auto">
            <h4 class="mb-3">로그인</h4>
            <form name="login_form" class="validation-form" action="/login" method="post">
                <div class="mb-3">
                    <label for="username">이메일</label>
                    <input type="email" class="form-control" id="username" name="username" placeholder="weaver123@example.com" required>
                    <div class="invalid-feedback">
                        이메일을 입력해주세요.
                    </div>
                </div>

                <div class="mb-3">
                    <label for="password">비밀번호</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="" required>
                    <div class="invalid-feedback">
                        비밀번호를 입력해주세요.
                    </div>
                </div>

                <hr class="mb-4">
                <button class="btn btn-primary btn-lg btn-block" type="button" onclick="loginTest()">로그인</button>
                <div class="mt-3 text-center">
                    <a href="/forgot-password" class="mr-2 text-primary">비밀번호 찾기</a>|&nbsp;
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
    // window.addEventListener('load', () => {
    //     const forms = document.getElementsByClassName('validation-form');
    //
    //     Array.prototype.filter.call(forms, (form) => {
    //         form.addEventListener('submit', function (event) {
    //             if (form.checkValidity() === false) {
    //                 event.preventDefault();
    //                 event.stopPropagation();
    //             }
    //
    //             form.classList.add('was-validated');
    //         }, false);
    //     });
    // }, false);


    function loginTest() {
        // document.login_form.submit();


        var username = $('#username').val()
        var password = $('#password').val()

        console.log(username);
        console.log(password);




        // 가져온 정보를 data로 묶기
        let data = {
            "username" : username,
            "password" : password
        }

        // 4. 클라에서 가져온 데이터를 서버로 전송 (이 예시에서는 URL이 '/register'로 가정)
        $.ajax({
            type: 'POST',
            url: '/loginTest',
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: function (response, status, xhr) {
                console.log(response);
                console.log(status);
                console.log(xhr);
                if (xhr.status === 200) {
                    // 서버 응답의 상태 코드가 200일 때만 실행
                    alert(username + '님 환영합니다!');
                    location.href = "/board";
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

        // // 5. 모든 필드가 유효한 경우 폼을 서버로 제출할 수 있습니다.
        // if (isValid) {
        //     $("#registrationForm")[0].submit();
        // }
    }

</script>
</body>

</html>
