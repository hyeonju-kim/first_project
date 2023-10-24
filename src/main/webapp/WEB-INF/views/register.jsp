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
            background: url("/images/background_salad.png"); /* 이미지 폴더에 있는 이미지를 사용 */
            background-size: cover;
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
        /* 남자, 여자 / 사용자, 관리자 사이의 간격 조정 */
        .form-check-label {
            margin-right: 30px; /* 원하는 간격으로 조정 */
        }
    </style>
</head>

<body>
<div class="container">
    <div class="input-form-backgroud row">
        <div class="input-form col-md-5 mx-auto text-center">
            <%-- 로고 추가--%>
            <div class="logo-container" >
                <img src="images/diet-record_logo.png" alt="Diet Record 로고">
            </div>
            <h5 class="mb-3">가입하고 매일 식단을 기록하고 소통하세요!</h5>
            <form class="validation-form" novalidate onsubmit="return register();">
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <input type="text" class="form-control" id="name" placeholder="이름" value="" required>
                        <span id="nameError" style="color: red;"></span>
                    </div>
                    <div class="col-md-6 mb-3">
                        <input type="text" class="form-control" id="nickname" placeholder="닉네임" value="" required>
                        <span id="nicknameError" style="color: red;"></span>
                    </div>
                </div>

                <!-- 주소 입력 버튼 -->
                <div class="mb-3">
                    <!-- 함수명이랑 id값이랑 같아서 계속 에러났었음.... -->
                    <button class="btn btn-primary" type="button" id="findAddress" onclick="findAddr()">주소 입력</button>
                </div>
                <div class="form-group">
                    <input type="text" name="zipcode" id="zipcode" class="form-control" placeholder="우편번호" readonly onclick="findAddr()">
                </div>
                <div class="form-group">
                    <input type="text" name="streetAdr" id="streetAdr" class="form-control" placeholder="도로명 주소" readonly>
                </div>
                <div class="form-group">
                    <input type="text" name="detailAdr" id="detailAdr" class="form-control" placeholder="상세주소">
                </div>
                <div class="mb-3">
                    <div class="mb-3" style="display: flex; align-items: center;">
                        <input type="email" class="form-control username_input" id="username" placeholder="이메일" check_result="fail" required style="flex: 1; margin-right: 5px;">
                        <button class="btn btn-primary" type="button" id="checkValidEmailButton" onclick="checkValidEmail()">중복확인</button>
                    </div>
                    <span id="usernameError" style="color: red;"></span>
                    <span id="usernameUnusable" style="color: red;"></span>
                    <span id="usernameUsable" style="color: blue;"></span>
                </div>
                <!-- 이메일 인증 버튼 추가 -->
                <div class="mb-3">
                    <button class="btn btn-primary" type="button" id="emailVerificationButton" onclick="sendEmailVerification()">인증</button>
                </div>

                <!-- 인증번호 입력 칸 (숨겨진 상태로 시작) -->
                <div class="mb-3" id="verificationCodeDiv" style="display: none;">
                    <input type="text" class="form-control" id="authNumber" placeholder="인증번호" required>
                    <div class="invalid-feedback">
                        인증번호를 입력해주세요.
                    </div>
                </div>
                <div class="mb-3">
                    <input type="password" class="form-control" id="password" placeholder="비밀번호(8~16자 영문, 숫자, 특수문자)" required>
                    <span id="passwordError" style="color: red;"></span>
                </div>
                <div class="mb-3">
                    <input type="password" class="form-control" id="passwordConfirm" placeholder="비밀번호 확인" required>
                    <span id="passwordConfirmError" style="color: red;"></span>
                </div>
                <div class="mb-3">
                    <input type="text" class="form-control" id="phoneNumber" placeholder="휴대폰번호" required>
                    <span id="phoneNumberError" style="color: red;"></span>
                </div>
                <div class="mb-3">
                    <label for="profilePicture">프로필 사진</label>
                    <input type="file" class="form-control-file" id="profilePicture" name="uploadFile"  accept=".jpg, .jpeg, .png">
                    <div class="invalid-feedback">
                        이미지 파일을 업로드해주세요.
                    </div>
                </div>
                <!-- 키, 몸무게, 성별 추가 -->
                <div class="mb-3">
                    <input type="number" class="form-control" id="height" placeholder="키 (cm)" required>
                    <span id="heightError" style="color: red;"></span>
                </div>
                <div class="mb-3">
                    <input type="number" class="form-control" id="weight" placeholder="몸무게 (kg)" required>
                    <span id="weightError" style="color: red;"></span>
                </div>
                <div class="mb-6">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="gender" id="male" value="male" checked>
                        <label class="form-check-label" for="male">
                            남자
                        </label>
                        <input class="form-check-input" type="radio" name="gender" id="female" value="female">
                        <label class="form-check-label" for="female">
                            여자
                        </label>
                    </div>

                </div>
                <!-- 관리자 체크박스 -->
                <div class="form-group">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" id="roleUser" name="role" value="user" checked>
                        <label class="form-check-label" for="roleUser">사용자</label>
                        <input class="form-check-input" type="radio" id="roleAdmin" name="role" value="admin">
                        <label class="form-check-label" for="roleAdmin">관리자</label>
                    </div>
                </div>
                <div class="mb-4"></div>
                <button class="btn btn-primary btn-lg btn-block" type="button" id="registrationForm" onclick="register()">가입 완료</button>
                <div class="mt-3 text-center">
                    계정이 있으신가요? <a href="/login" class="text-primary">로그인</a>
                </div>
            </form>
        </div>
    </div>
    <footer class="my-3 text-center text-small">
        <p class="mb-1">&copy; 2021 YD</p>
    </footer>
</div>
<!-- Bootstrap JS (팝업 메시지 등을 위해 필요) -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXnALea6EFD5E/5O5w5Pj5Bf5"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYlT"
        crossorigin="anonymous"></script>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>

    // 1. 😊 회원가입 완료 메서드
    function register() {
        // e.preventDefault(); // 폼 제출 방지

        // 1. 여기서 내가 화면에서 post 로 입력하는 정보를 하나하나 가져오기
        let name = $('#name').val()
        let nickname = $('#nickname').val()
        let username = $('#username').val()
        let password = $('#password').val()
        let passwordConfirm = $('#passwordConfirm').val()
        let phoneNumber = $('#phoneNumber').val()
        let zipcode = $('#zipcode').val()
        let streetAdr = $('#streetAdr').val()
        let detailAdr = $('#detailAdr').val()
        let authNumber = $('#authNumber').val()
        let role = $('input[name="role"]:checked').val();  //name 속성이 "role"인 체크된(선택된) 체크박스의 값을 role 변수에 저장
        let weight = $('#weight').val()
        let height = $('#height').val()
        let gender = document.querySelector('input[name="gender"]:checked').value;

        // 2. 유효성 검사
        // 각 필드의 유효성을 검사합니다.
        let isValid = true;

        // 비밀번호가 8~16자 영문, 숫자, 특수문자를 사용하도록 검증
        let passwordRegex = /^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\W)(?=\S+$).{8,16}$/;
        if (!passwordRegex.test(password)) {
            // alert('비밀번호는 8~16자 영문, 숫자, 특수문자를 사용해야 합니다.');
            $("#passwordError").text("비밀번호는 8~16자 영문, 숫자, 특수문자를 사용해야 합니다.");
            isValid = false;
        }else {
            $("#passwordError").text("");
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

        // 닉네임 유효성 검사
        let nicknamePattern = /^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$/;
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

        // 키, 몸무게, 성별 유효성 검사
        if (!height || isNaN(height)) {
            $("#heightError").text("키를 입력하세요.");
            isValid = false;
        }else {
            $("#heightError").text("");
        }
        if ( !weight || isNaN(weight)) {
            $("#weightError").text("몸무게를 입력하세요.");
            isValid = false;
        }else {
            $("#weightError").text("");
        }
        if (!gender) {
            $("#gender").text("성별을 선택하세요.");
            isValid = false;
        }else {
            $("#gender").text("");
        }

        // 3. 가져온 정보를 data로 묶기
        // let fileInput = $("input[name=uploadFile]")[0];
        // let fileObj = fileInput.files[0];
        let data = {
            "name" : name,
            "nickname" : nickname,
            "username" : username,
            "password" : password,
            "passwordConfirm" : passwordConfirm,
            "phoneNumber" : phoneNumber,
            "profilePicture" : this.profilePicture,
            "zipcode" : zipcode,
            "streetAdr" : streetAdr,
            "detailAdr" : detailAdr,
            "authNumber" : authNumber,
            "role" : role,
            "height": height,
            "weight": weight,
            "gender": gender
        }

        // 4. 클라에서 가져온 데이터를 서버로 전송
        $.ajax({
            type: 'POST',
            url: '/register',
            data: JSON.stringify(data),
            contentType: 'application/json', // JSON 형식의 데이터를 전송
            success: function (response, status, xhr) { // response 객체에 success, msg가 json형식으로 존재함(컨트롤러에서 반환한 값이 json으로 들어옴)
                console.log(response); //서버에서 반환된 응답 body부 데이터. {readyState: 4, getResponseHeader: ƒ, getAllResponseHeaders: ƒ, setRequestHeader: ƒ, overrideMimeType: ƒ, …}
                console.log(status); //  success
                console.log(xhr.status); // 2xx

                //서버 응답의 상태 코드가 2xx일 때만 실행
                alert('가입이 완료되었습니다!');
                location.href = "/login";
            },
            error: function (jqXHR) { // 서버 요청 실패 시 실행 (4xx, 5xx)
                console.log(jqXHR); //{readyState: 4, getResponseHeader: ƒ, getAllResponseHeaders: ƒ, setRequestHeader: ƒ, overrideMimeType: ƒ, …}
                console.log(jqXHR.statusText); // "error" . console.log(status);도 같은 결과.
                console.log(jqXHR.status); // 500

                if (jqXHR.status === 400) {
                    alert("400 에러")
                }else if (jqXHR.status === 500 && $('.username_input').attr("check_result") === "success") {
                    alert("가입 정보를 모두 작성해주세요.")
                }else {

                }
            }
        });
        if ($('.username_input').attr("check_result") === "fail"){
            $("#usernameError").text("아이디 중복체크를 해주세요.");
            $('.username_input').focus();
            return false;
        }
        return true;
    }

    // 2. 😊 파일 업로드 메소드
    function storeProfilePicture() {
        // 파일 업로드 입력 필드를 선택합니다.
        let fileInput = $("input[name=uploadFile]");
        // 선택한 파일 업로드 입력 필드에서 첫 번째 파일을 가져옵니다.
        let fileObj = fileInput.prop('files')[0];
        let username = $('#username').val()

        if (fileObj) {
            let formData = new FormData();
            formData.append("uploadFile", fileObj);
            formData.append("username", username);

            $.ajax({
                url: '/upload-profilePicture',
                processData: false,
                contentType: false,
                data: formData,
                type: 'POST',
                // dataType: 'json',
                success: function (response) {
                    console.log(response);
                },
                error: function (xhr, status, error) {
                    console.log(xhr);
                    console.log(status);
                    console.log(error);
                }
            });
        }
    }

    // 가입 완료 버튼 클릭 시 바로 파일 업로드 함수 호출하는 이벤트 핸들러
    $("#registrationForm").click(function() {
        // 가입 완료 버튼 클릭 시 파일 업로드 함수 호출
        storeProfilePicture();
    });


    // 3. 😊 메일로 인증번호 전송 메소드
    function sendEmailVerification() {
        // 1. 작성한 이메일 주소 가져오기
        let username = $('#username').val();
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

    //4 . 😊 메일 중복확인 메소드
    function checkValidEmail() {
        // 중복확인을 했더라도 아이디를 다시 수정한다면, check_result를 fail 로 바꿔서 뒤에서 제출을 못하게 막습니다.
        $('.username_input').change(function () {
            $('.username_input').attr("check_result", "fail");
        })

        // 1. 작성한 이메일 주소 가져오기
        let username = $('#username').val();

        // 2. 가져온 정보를 data로 묶기
        let data = {
            "username" : username
        }

        // 3. 클라에서 가져온 데이터를 서버로 전송
        $.ajax({

            type: 'POST',
            url: '/checkValidEmail',
            data: JSON.stringify(data),
            contentType: 'application/json', // JSON 형식의 데이터를 전송
            success: function (response) { // response 객체에 success, msg가 json형식으로 존재함(컨트롤러에서 반환한 값이 json으로 들어옴)
                console.log("response= " , response); // -1, 0, 1, 2

                if (response === 1) {
                    $('#usernameUnusable').text("이미 사용중인 이메일입니다.");
                    $('#usernameUsable').text(""); // 다른 메시지를 제거합니다.
                    $('#usernameError').text("");
                } else if(response === 0) {
                    $('#usernameUnusable').text(""); // 이미 사용 중인 이메일 메시지를 제거합니다.
                    $('#usernameUsable').text("사용가능한 이메일입니다.");
                    $('.username_input').attr("check_result", "success"); //check_result 를 success로 바꿔줘서 후에 submit 시 통과 하도록 해줍니다.
                } else if(response === -1){
                    $('#usernameUsable').text("");
                    $('#usernameUnusable').text("");
                    $("#usernameError").text("이메일을 입력해주세요.");
                } else if(response === 2){
                    $('#usernameUsable').text("");
                    $('#usernameUnusable').text("");
                    $('#usernameError').text("");
                    $("#usernameError").text("올바른 이메일 형식이 아닙니다.");
                } else {
                    alert("알 수 없는 에러");
                }
            },
            error: function (response) {
                // 서버 요청 실패 시 실행
                console.log('실패했다...')
                console.log(response); //응답 body부 데이터

                alert('서버 요청 실패');
            }
        });
    }

    // 5. 😊 카카오 주소 api 사용해서 우편번호로 주소찾기 메서드
    function findAddr() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                let addr = ''; // 주소 변수
                let extraAddr = ''; // 참고항목 변수

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
                let zipcode = data.zonecode;
                let streetAdr = data.roadAddress;
                let detailAdr = $("#detailAdr").val();

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
                        // alert('서버 요청 실패');
                    }
                });
            }
        }).open();
    }
</script>

</body>
</html>