<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입 화면</title>

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
<%--            <form  method="post">--%>
            <form class="validation-form" novalidate onsubmit="return register();">
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="name">이름</label>
                        <input type="text" class="form-control" id="name" placeholder="홍길동" value="" required>
                        <span id="nameError" style="color: red;"></span>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="nickname">별명</label>
                        <input type="text" class="form-control" id="nickname" placeholder="별명" value="" required>
                        <span id="nicknameError" style="color: red;"></span>
                    </div>
                </div>

                <!-- 주소 입력 버튼 -->
                <div class="mb-3">
                    <!-- 함수명이랑 id값이랑 같아서 계속 에러났었음.... -->
                    <button class="btn btn-primary" type="button" id="findAddress" onclick="findAddr()">주소 입력</button>
                </div>

                <div class="form-group">
                    <label for="zipcode">우편번호</label>
                    <input type="text" name="zipcode" id="zipcode" class="form-control" placeholder="우편번호를 입력하세요" readonly onclick="findAddr()">
                </div>
                <div class="form-group">
                    <label for="streetAdr">도로명 주소</label>
                    <input type="text" name="streetAdr" id="streetAdr" class="form-control" placeholder="도로명 주소를 입력하세요" readonly>
                </div>
                <div class="form-group">
                    <label for="detailAdr">상세주소</label>
                    <input type="text" name="detailAdr" id="detailAdr" class="form-control" placeholder="상세주소를 입력하세요">
                </div>

                <div class="mb-3">
                    <label for="username">이메일</label>
<%--                    인증하기 버튼을 누르면 작성한 email이 사라지는 것을 방지하기 위해 readonly 추가 --%>
                    <input type="email" class="form-control" id="username" placeholder="weaver123@example.com" required >
                    <span id="usernameError" style="color: red;"></span>
                </div>
                <!-- 이메일 인증 버튼 추가 -->
                <div class="mb-3">
                    <button class="btn btn-primary" type="button" id="emailVerificationButton" onclick="sendEmailVerification()">인증</button>
                </div>

                <!-- 인증번호 입력 칸 (숨겨진 상태로 시작) -->
                <div class="mb-3" id="verificationCodeDiv" style="display: none;">
                    <label for="authNumber">인증번호</label>
                    <input type="text" class="form-control" id="authNumber" placeholder="인증번호를 입력하세요" required>
                    <div class="invalid-feedback">
                        인증번호를 입력해주세요.
                    </div>
                </div>

                <div class="mb-3">
                    <label for="password">비밀번호</label>
                    <input type="password" class="form-control" id="password" placeholder="비밀번호는 8~16자 영문, 숫자, 특수문자를 사용하세요" required>
                    <span id="passwordError" style="color: red;"></span>
                </div>

                <div class="mb-3">
                    <label for="passwordConfirm">비밀번호 확인</label>
                    <input type="password" class="form-control" id="passwordConfirm" placeholder="비밀번호를 다시 입력해주세요" required>
                    <span id="passwordConfirmError" style="color: red;"></span>
                </div>


                <div class="mb-3">
                    <label for="phoneNumber">휴대폰 번호</label>
                    <input type="text" class="form-control" id="phoneNumber" placeholder="휴대폰번호를 입력해주세요" required>
                    <span id="phoneNumberError" style="color: red;"></span>
                </div>

                <div class="mb-3">
                    <label for="profilePicture">프로필 사진</label>
                    <input type="file" class="form-control-file" id="profilePicture" name="uploadFile" accept=".jpg, .jpeg, .png">
                    <div class="invalid-feedback">
                        이미지 파일을 업로드해주세요.
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


                <div class="mb-4"></div>
                <button class="btn btn-primary btn-lg btn-block" type="button" id="registrationForm" onclick="register(); storeProfilePicture()">가입 완료</button>

            </form>
        </div>
    </div>
    <footer class="my-3 text-center text-small">
        <p class="mb-1">&copy; 2021 YD</p>
    </footer>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>


    // 1. 😊 유효성 검사 로직
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

    // 1. 😊 가입완료 메서드
    function register() {
            // e.preventDefault(); // 폼 제출 방지

            // 1. 여기서 내가 화면에서 post 로 입력하는 정보를 하나하나 가져오기
            var name = $('#name').val()
            var nickname = $('#nickname').val()
            var username = $('#username').val()
            var password = $('#password').val()
            var passwordConfirm = $('#passwordConfirm').val()
            var phoneNumber = $('#phoneNumber').val()
            var zipcode = $('#zipcode').val()
            var streetAdr = $('#streetAdr').val()
            var detailAdr = $('#detailAdr').val()
            var authNumber = $('#authNumber').val()

            console.log(name);
            console.log(nickname);
            console.log(username);
            console.log(password);
            console.log(passwordConfirm);
            console.log(phoneNumber);
            console.log(zipcode);
            console.log(streetAdr);
            console.log(detailAdr);
            console.log(authNumber);

            // 2. 유효성 검사

            // 각 필드의 유효성을 검사합니다.
            var isValid = true;

            // 비밀번호가 8~16자 영문, 숫자, 특수문자를 사용하도록 검증
            var passwordRegex = /^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\W)(?=\S+$).{8,16}$/;

            if (!passwordRegex.test(password)) {
                alert('비밀번호는 8~16자 영문, 숫자, 특수문자를 사용해야 합니다.');
                return;
            }

            // 비밀번호 확인 유효성 검사
            if (passwordConfirm.trim() === "") {
                $("#passwordConfirmError").text("비밀번호 확인을 입력하세요.");
                isValid = false;
            } else if (password !== passwordConfirm) {
                $("#passwordConfirmError").text("비밀번호가 일치하지 않습니다.");
                isValid = false;
            } else {
                $("#passwordConfirmError").text("");
            }

            // 이름 유효성 검사
            if (name.trim() === "") {
                $("#nameError").text("이름을 입력하세요.");
                isValid = false;
            } else {
                $("#nameError").text("");
            }

            // 이메일 유효성 검사 (이메일 형식 체크)
            var emailPattern = /^(?:\w+\.?)*\w+@(?:\w+\.)+\w+$/;
            if (!emailPattern.test(username)) {
                $("#usernameError").text("올바른 이메일 형식이 아닙니다.");
                isValid = false;
            } else {
                $("#usernameError").text("");
            }

            // 닉네임 유효성 검사
            var nicknamePattern = /^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$/;
            if (!nicknamePattern.test(nickname)) {
                $("#nicknameError").text("닉네임은 특수문자를 제외한 2~10자리여야 합니다.");
                isValid = false;
            } else {
                $("#nicknameError").text("");
            }

            // 휴대폰 번호 유효성 검사
            if (phoneNumber.trim() === "") {
                $("#phoneNumberError").text("휴대폰 번호를 입력하세요.");
                isValid = false;
            } else {
                $("#phoneNumberError").text("");
            }



            if (!isValid) {
                // 유효성 검사에 실패한 경우 경고 메시지만 표시하고 폼을 서버로 제출하지 않음
                return;
            }

           // 3. 가져온 정보를 data로 묶기
            let data = {
                "name" : name,
                "nickname" : nickname,
                "username" : username,
                "password" : password,
                "passwordConfirm" : passwordConfirm,
                "phoneNumber" : phoneNumber,
                "profilePicture" : profilePicture,
                "zipcode" : zipcode,
                "streetAdr" : streetAdr,
                "detailAdr" : detailAdr,
                "authNumber" : authNumber

            }

            // 4. 클라에서 가져온 데이터를 서버로 전송 (이 예시에서는 URL이 '/register'로 가정)
            $.ajax({
                type: 'POST',
                url: '/register', // 가입완료 버튼을 누르면 이 URL로 매핑!!! 마지막에 가는게xx

                // 사용자가 입력한 정보들이 위에 변수로 수집되고, 그 정보는 아래의 data라는 객체에 저장된다.
                // 이 객체는 json 데이터형식을 가지며, 각 입력필드의 값을 해당 필드의 이름으로 매핑한다!!
                // 이 요청은 /register url로 보내지며, 서버의 컨트롤러 중에 @PostMappling("/register")가 달린 메소드가 호출된다.
                // 이 메서드는 json형식의 데이터인 'userDto' 객체를 파라미터로 받는다.

                data: JSON.stringify(data),
                contentType: 'application/json', // JSON 형식의 데이터를 전송
                success: function (response, status, xhr) { // response 객체에 success, msg가 json형식으로 존재함(컨트롤러에서 반환한 값이 json으로 들어옴)
                    console.log(response); //응답 body부 데이터
                    console.log(status); //"succes"로 고정인듯함
                    console.log(xhr);
                    if (xhr.status === 200) {
                        // 서버 응답의 상태 코드가 200일 때만 실행
                        alert('가입이 완료되었습니다!');
                        location.href = "/login";
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

    // 파일 업로드 메소드
    function storeProfilePicture() {
        let fileInput = $("input[name=uploadFile]")[0];
        let fileObj = fileInput.files[0];

        if (fileObj) {
            let formData = new FormData();
            formData.append("uploadFile", fileObj);

            console.log("fileObj: " + fileObj);
            console.log("fileObj.name: " + fileObj.name);
            console.log("fileObj.size: " + fileObj.size);
            console.log("fileObj.type: " + fileObj.type);

            $.ajax({
                url: '/upload-profilePicture',
                processData: false,
                contentType: false,
                data: formData,
                type: 'POST',
                // dataType: 'json',
                success: function (response) {
                    console.log(response);
                    // 파일 업로드 성공 처리를 추가할 수 있습니다.
                },
                error: function (xhr, status, error) {
                    console.log(xhr);
                    console.log(status);
                    console.log(error);
                    // 파일 업로드 실패 처리를 추가할 수 있습니다.
                }
            });
        }
    }

    // 파일 업로드 버튼(input[type=file])에 change 이벤트 핸들러 등록
    $("input[type=file]").on("change", storeProfilePicture);




    // 3. 😊 메일전송 메소드
    function sendEmailVerification() {
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
            url: '/email-confirm',
            data: JSON.stringify(data),
            contentType: 'application/json', // JSON 형식의 데이터를 전송
            success: function (response, status, xhr) { // response 객체에 success, msg가 json형식으로 존재함(컨트롤러에서 반환한 값이 json으로 들어옴)
                console.log(response); //응답 body부 데이터
                console.log(status); //"succes"로 고정인듯함
                console.log(xhr);
                if (xhr.status === 200) {
                    // 서버 응답의 상태 코드가 200일 때만 실행
                    alert('메일이 전송 되었습니다!');

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

    // 4. 😊 카카오 주소 api 사용해서 우편번호로 주소찾기 메서드
    function findAddr() {
        console.log('주소찾기 메서드 findAddr() 실행')
        new daum.Postcode({
            oncomplete: function(data) {
                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }
                console.log('addr');

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }

                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }

                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }

                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("detailAdr").value = extraAddr;
                } else {
                    document.getElementById("detailAdr").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('zipcode').value = data.zonecode;
                document.getElementById("streetAdr").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("detailAdr").focus();

                // 주소 정보를 JavaScript 변수에 저장
                var zipcode = data.zonecode;
                var streetAdr = data.roadAddress;
                var detailAdr = $("#detailAdr").val();

                // 주소 정보를 서버로 전송
                $.ajax({
                    type: 'POST',
                    // url: '/save-address', // 주소 정보를 저장하는 URL로 변경 (서버에서 처리해야 함)
                    data: JSON.stringify({ zipcode: zipcode, streetAdr: streetAdr, detailAdr: detailAdr }),
                    contentType: 'application/json',
                    success: function(response, status, xhr) {
                        if (xhr.status === 200) {
                            alert('주소 정보가 저장되었습니다.');
                        } else {
                            alert('주소 정보 저장에 실패했습니다.');
                        }
                    },
                    error: function(response, status, xhr) {
                        console.log('실패했다...');
                        console.log(response);
                        alert('서버 요청 실패');
                    }
                });
            }
        }).open();
    }

</script>

</body>
</html>