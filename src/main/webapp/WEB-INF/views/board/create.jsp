<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 생성</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <%-- 스마트 에디터 추가 --%>
    <script type="text/javascript" src="/smarteditor/js/HuskyEZCreator.js" charset="utf-8"></script>
<%--    <script type="text/javascript" src="../../../resources/static/smarteditor/js/HuskyEZCreator.js" charset="utf-8"></script>--%>
<%--    <script type="application/javascript" src="/resources/static/smarteditor/js/HuskyEZCreator.js" charset="utf-8"></script>--%>

</head>
<body>
<div class="container">
    <h2 class="text-center mt-4"> 글 작성 </h2>
    <form action="/boards/create" method="post" enctype="multipart/form-data">
        <div class="form-group mt-4">
            <label for="title"><strong>제목</strong></label>
            <input type="text" class="form-control" id="title" name="title" required>
        </div>
        <div class="form-group mt-4">
            <label for="content"><strong>내용</strong></label>
            <!-- 스마트 에디터 추가: 에디터를 넣을 위치 -->
            <div id="smarteditor">
                <textarea name="content" id="content" rows="20" cols="10" placeholder="내용을 입력해주세요" style="width: 500px"></textarea>
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
        <button type="submit" class="btn btn-primary mt-4">게시글 생성</button>
    </form>
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


    // 4. 스마트 에디터 설정 함수
    let oEditors = [];

    function smartEditor() {
        console.log("Naver SmartEditor");

        // 에디터를 생성하기 위한 설정
        nhn.husky.EZCreator.createInIFrame({
            oAppRef: oEditors,
            // 에디터를 넣을 위치의 textarea의 id를 지정
            elPlaceHolder: "content",
            // 스킨 파일의 경로 (static 폴더 하위 경로로 설정)
            sSkinURI: "/smarteditor/SmartEditor2Skin.html",
            fCreator: "createSEditor2"
        });
    }

    // 문서가 준비되면 스마트 에디터 설정 함수 호출
    $(document).ready(function() {
        smartEditor();
    });
</script>

</body>
</html>
