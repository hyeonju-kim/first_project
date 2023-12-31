<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 생성</title>
    <style>
        .mytitle {
            width: 100%;
            height: 150px;
            background-image: linear-gradient(0deg, rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3)), url('/images/board_back.png');
            background-position: center;
            background-size: cover;
            color: white;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            margin-bottom: 20px;
        }

    </style>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <%-- smart Editor 위해 추가 --%>
    <script type="text/javascript" src="/smarteditor/js/HuskyEZCreator.js" charset="utf-8"></script>

</head>
<body>
<!-- 만들어 놓은 헤더 포함 !!!!  -->
<%@ include file="../header.jsp" %>
<div class="container">
    <div class="mytitle">
        <h4>다양한 사람들과 소통해보세요</h4>
    </div>

    <div class="row justify-content-center">
        <div class="col-md-8">
            <form action="/boards/create" method="post" enctype="multipart/form-data">
                <div class="form-group mt-4">
                    <label for="title"><strong>제목</strong></label>
                    <input type="text" class="form-control" id="title" name="title"  required>
                </div>
                <div class="form-group mt-4">
                    <label><strong>내용</strong></label>

                    <!-- smart Editor 위해 추가 -->
                    <div id="smarteditor">
                        <textarea name="content" id="editorTxt" rows="20" cols="10" placeholder="내용을 입력해주세요" style="width: 500px"></textarea>
                    </div>
                </div>
                <div class="form-group mt-4">
                    <label for="files"><strong>첨부 파일</strong></label>
                    <div class="file_list">
                        <div class="file_input_group">
                            <div class="file_input">
                                <input type="text" readonly />
                                <label> 첨부파일
                                    <input type="file" name="files" id="files" onchange="selectFile(this);" />
                                </label>
                                <button type="button" onclick="removeFile(this);" class="btns del_btn"><span>삭제</span></button>
                                <button type="button" onclick="addFile();" class="btns fn_add_btn"><span>파일 추가</span></button>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- smart Editor 위해 추가 ( onclick="submitPost();" 부분만)-->
                <button type="submit" onclick="submitPost();" class="btn btn-primary mt-4">게시글 생성</button>
            </form>
        </div>
    </div>


</div>

<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    // 1. 파일 선택 함수
    function selectFile(element) {
        const file = element.files[0];
        const filename = element.closest('.file_input').firstElementChild;

        // 1. 파일 선택 창에서 취소 버튼이 클릭된 경우
        if (!file) {
            filename.value = '';
            return false;
        }

        // 2. 파일 크기가 10MB를 초과하는 경우
        const fileSize = Math.floor(file.size / 1024 / 1024);
        if (fileSize > 10) {
            alert('10MB 이하의 파일로 업로드해 주세요.');
            filename.value = '';
            element.value = '';
            return false;
        }

        // 3. 파일명 지정
        filename.value = file.name;
    }

    // 2. 파일 추가 함수
    function addFile() {
        const fileDiv = document.createElement('div');
        fileDiv.innerHTML = `
            <div class="file_input_group">
                <div class="file_input">
                    <input type="text" readonly />
                    <label> 첨부파일
                        <input type="file" name="files" onchange="selectFile(this);" />
                    </label>
                    <button type="button" onclick="removeFile(this);" class="btns del_btn"><span>삭제</span></button>
                    <button type="button" onclick="addFile();" class="btns fn_add_btn"><span>파일 추가</span></button>
                </div>
            </div>
        `;
        document.querySelector('.file_list').appendChild(fileDiv);
    }

    // 3. 파일 삭제 함수
    function removeFile(element) {
        element.parentElement.remove();
    }

    // smart Editor 위해 추가
    //  4. 스마트 에디터 설정을 위한 전역 변수 및 함수
    let oEditors = [];

    // Naver SmartEditor 설정 함수
    function smartEditor() {
        console.log("Naver SmartEditor");

        // 에디터를 생성하기 위한 설정
        nhn.husky.EZCreator.createInIFrame({
            oAppRef: oEditors,
            // 에디터를 넣을 위치의 textarea의 id를 지정
            elPlaceHolder: "editorTxt",
            // 스킨 파일의 경로 (static 폴더 하위 경로로 설정)
            sSkinURI: "/smarteditor/SmartEditor2Skin.html",
            fCreator: "createSEditor2"
        });
    }

    // smart Editor 위해 추가
    // 문서가 준비되면 스마트 에디터 설정 함수 호출
    $(document).ready(function() {
        smartEditor();
    });

    // smart Editor 위해 추가
    // 5. 에디터에 입력한 내용 가져오기
    function submitPost() {
        // 에디터 내용 업데이트
        oEditors.getById["editorTxt"].exec("UPDATE_CONTENTS_FIELD", [])
        let content = document.getElementById("editorTxt").value

        if(content == '') {
            // 내용이 비어있는 경우 경고 메시지 출력
            alert("내용을 입력해주세요.")
            oEditors.getById["editorTxt"].exec("FOCUS")
            return
        } else {
            // 내용이 입력되었을 경우 내용을 콘솔에 출력
            console.log(content)
        }
    }
</script>

</body>
</html>
