
(function(jquery){
    $("#confirmBtn").on("click", function(){
        fetchRegister();
    })

    function fetchRegister(){
        //data setting
        let lastName = $("#lastName").val();
        let firstName = $("#firstName").val();
        let email = $("#email").val();
        let password = $("#password").val();
        let passwordConfirm = $("#passwordConfirm").val();
        let phoneNumber = $("#phoneNumber").val();
        let fullName = lastName + firstName;

        if(smbCom.isEmpty(lastName)){
            alert("성을 입력해주세요.");
            return;
        }
        if(smbCom.isEmpty(firstName)){
            alert("이름을 입력해주세요.");
            return;
        }
        if(smbCom.isEmpty(email)){
            alert("이메일을 입력해주세요.");
            return;
        }
        if(smbCom.isEmpty(phoneNumber)){
            alert("휴대폰번호를 입력해주세요.");
            return;
        }
        if(smbCom.isEmpty(password)){
            alert("비밀번호를 입력해주세요.");
            return;
        }
        if(smbCom.isEmpty(passwordConfirm)){
            alert("비밀번호를 재입력해주세요.");
            return;
        }

        if(password != passwordConfirm){
            alert("비밀번호가 다릅니다. 다시 입력해주세요.");
            $("#password").val("");
            $("#passwordConfirm").val("");
            return;
        }

        smbCom.createAction("/view/user/register", {
            name : fullName,
            email : email,
            password : password,
            passwordConfirm : passwordConfirm,
            phoneNumber : phoneNumber
        }, function(data){
            //등록 완료 시
            alert("회원가입이 완료되었습니다.");
            window.location.href=data.redirect;
        }, function(data){
            //등록 불발 시
            alert(data.message);

        });
    }
})($);
