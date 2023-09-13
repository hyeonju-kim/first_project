<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입 화면 샘플 - Bootstrap</title>

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
            <h4 class="mb-3">회원가입</h4>
            <form  method="post">
                <div class="mb-3">
                    <label for="email">이메일</label>
                    <input type="email" class="form-control" id="email" placeholder="weaver123@example.com" required>
                    <div class="invalid-feedback">
                        이메일을 입력해주세요.
                    </div>
                </div>

                <div class="mb-3">
                    <label for="password">비밀번호</label>
                    <input type="password" class="form-control" id="password" placeholder="비밀번호는 8~16자 영문, 숫자, 특수문자를 사용하세요" required>
                    <div class="invalid-feedback">
                        비밀번호를 입력해주세요.
                    </div>
                </div>

                <div class="mb-3">
                    <label for="passwordConfirm">비밀번호 확인</label>
                    <input type="password" class="form-control" id="passwordConfirm" placeholder="비밀번호를 다시 입력해주세요" required>
                    <div class="invalid-feedback">
                        비밀번호를 다시 입력해주세요.
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="username">이름</label>
                        <input type="text" class="form-control" id="username" placeholder="홍길동" value="" required>
                        <div class="invalid-feedback">
                            이름을 입력해주세요.
                        </div>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="nickname">별명</label>
                        <input type="text" class="form-control" id="nickname" placeholder="별명" value="" required>
                        <div class="invalid-feedback">
                            별명을 입력해주세요.
                        </div>
                    </div>
                </div>

                <div class="mb-3">
                    <label for="phoneNumber">휴대폰 번호</label>
                    <input type="text" class="form-control" id="phoneNumber" placeholder="비밀번호를 다시 입력해주세요" required>
                    <div class="invalid-feedback">
                        휴대폰 번호를 입력해주세요.
                    </div>
                </div>





<%--                <div class="mb-3">--%>
<%--                    <label for="address">주소</label>--%>
<%--                    <input type="text" class="form-control" id="address" placeholder="서울특별시 강남구" required>--%>
<%--                    <div class="invalid-feedback">--%>
<%--                        주소를 입력해주세요.--%>
<%--                    </div>--%>
<%--                </div>--%>

<%--                <div class="mb-3">--%>
<%--                    <label for="address2">상세주소<span class="text-muted">&nbsp;(필수 아님)</span></label>--%>
<%--                    <input type="text" class="form-control" id="address2" placeholder="상세주소를 입력해주세요.">--%>
<%--                </div>--%>

<%--                <div class="row">--%>
<%--                    <div class="col-md-8 mb-3">--%>
<%--                        <label for="root">가입 경로</label>--%>
<%--                        <select class="custom-select d-block w-100" id="root">--%>
<%--                            <option value=""></option>--%>
<%--                            <option>검색</option>--%>
<%--                            <option>카페</option>--%>
<%--                        </select>--%>
<%--                        <div class="invalid-feedback">--%>
<%--                            가입 경로를 선택해주세요.--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                    <div class="col-md-4 mb-3">--%>
<%--                        <label for="code">추천인 코드</label>--%>
<%--                        <input type="text" class="form-control" id="code" placeholder="" required>--%>
<%--                        <div class="invalid-feedback">--%>
<%--                            추천인 코드를 입력해주세요.--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--                <hr class="mb-4">--%>
<%--                <div class="custom-control custom-checkbox">--%>
<%--                    <input type="checkbox" class="custom-control-input" id="aggrement" required>--%>
<%--                    <label class="custom-control-label" for="aggrement">개인정보 수집 및 이용에 동의합니다.</label>--%>
<%--                </div>--%>
                <div class="mb-4"></div>
                <button class="btn btn-primary btn-lg btn-block" type="button" id="registrationForm" onclick="test()">가입 완료</button>
            </form>
        </div>
    </div>
    <footer class="my-3 text-center text-small">
        <p class="mb-1">&copy; 2021 YD</p>
    </footer>
</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>

    function test() {
            // e.preventDefault(); // 폼 제출 방지

            // 1. 여기서 내가 화면에서 post 로 입력하는 정보를 하나하나 가져오기
            var username = $('#username').val()
            var nickname = $('#nickname').val()
            var email = $('#email').val()
            var password = $('#password').val()
            var passwordConfirm = $('#passwordConfirm').val()
            var phoneNumber = $('#phoneNumber').val()

            console.log(username);
            console.log(nickname);
            console.log(email);
            console.log(password);
            console.log(passwordConfirm);
            console.log(phoneNumber);

            // aggrement: $('#aggrement').prop('checked')
            // 폼 데이터 수집 -> 옛날 방식
            // var formData = new FormData();
            // formData.append("username", username);
            // formData.append("nickname", nickname);
            // formData.append("email", email);

           // 2. 가져온 정보를 data로 묶기
            let data = {
                "username" : username,
                "nickname" : nickname,
                "email" : email,
                "password" : password,
                "passwordConfirm" : passwordConfirm,
                "phoneNumber" : phoneNumber
            }

            // 3. 클라에서 가져온 데이터를 서버로 전송 (이 예시에서는 URL이 '/register'로 가정)
            $.ajax({
                type: 'POST',
                url: '/register', // 가입완료 버튼을 누르면 이 URL로 매핑!!! 마지막에 가는게xx
                data: JSON.stringify(data),
                // dataType: 'json', // JSON 형식의 응답을 요청
                contentType: 'application/json', // JSON 형식의 데이터를 전송
                success: function (response, status, xhr) { // response 객체에 success, msg가 json형식으로 존재함(컨트롤러에서 반환한 값이 json으로 들어옴)
                    console.log(response); //응답 body부 데이터
                    console.log(status); //"succes"로 고정인듯함
                    console.log(xhr);
                    if (xhr.status === 200) {
                        // 서버 응답의 상태 코드가 200일 때만 실행
                        alert('가입이 완료되었습니다!!!!.');
                        // location.href = response.url;
                        location.href = "/home";
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

    }


</script>
</body>
</html>