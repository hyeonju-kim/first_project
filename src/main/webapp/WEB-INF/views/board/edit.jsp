<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 상세 정보</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <style>
        /* 댓글 스타일링 */
        .card-title1 {
            font-weight: bold; /* 굵게 표시 */
            font-size: 20px; /* 폰트 크기 지정 (원하는 크기로 수정) */
        }
        .comment-box {
            background-color: #f2f2f2;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ddd;
        }
        .file-info {
            background-color: #f2f2f2;
            padding: 10px;
            margin-bottom: 20px;
            margin-left: 20px;
            margin-right: 20px;
            border: 1px solid #ddd;
        }

        .comment-meta {
            font-size: 14px; /* 글꼴 크기 조절 */
            color: #BDBDBD; /* 회색으로 설정 */
            margin-bottom: 5px;
        }

        .comment-nickname {
            color: #555;
        }

        .comment-date {
            color: #555;
            margin-left: 10px;
        }

        .comment-content {
            font-size: 16px; /* 글꼴 크기 조절 */
            color: #333;
        }
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
            <a href="/boards" class="btn btn-primary">게시판 목록</a>
            <br>
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title1">
                        제목: ${board.title}
                    </h5>
                    <p class="card-text">작성자: ${board.nickname}</p>
                    <p class="card-text">작성일: ${board.createdAt}</p>
                </div>
                <div class="card">
                    <div class="card-body">
                        <form id="editForm" action="/boards/${board.boardId}/edit" method="post" enctype="multipart/form-data">
                            <div class="form-group">
                                <label for="title">제목</label>
                                <input type="text" class="form-control" id="title" name="title" value="${board.title}">
                            </div>

                            <div class="form-group mt-4">
                                <label><strong>내용</strong></label>

                                <!-- smart Editor 위해 추가 -->
                                <div id="smarteditor">
                                    <textarea name="content" id="editorTxt" rows="20" cols="10"style="width: 500px">${board.content}</textarea>
                                </div>
                            </div>
                            <div class="form-group mt-4">
                                <label><strong>첨부 파일</strong></label>
                                <div class="file_list">
                                    <c:forEach var="multiFile" items="${multiFiles}" varStatus="status">
                                        <div class="file_input_group">
                                            <div class="file_input">
                                                <!-- 첨부파일의 현재 정보를 표시 -->
                                                <p>${multiFile.fileOriginalName} (${multiFile.fileSize}KB)</p>
                                                <input type="hidden" name="files" value="${multiFile.fileId}">
                                                <button type="button" onclick="removeFile(this);" class="btns del_btn"><span>기존 파일 삭제</span></button>

                                            </div>
                                        </div>
                                    </c:forEach>
                                    <label>새로운 파일 선택
                                        <input type="file" name="files" id="newFile${status.index}" onchange="selectFile(this);" />
                                    </label>
                                </div>
                            </div>

                            <!-- smart Editor 위해 추가 ( onclick="submitPost();" 부분만)-->
                            <button type="submit" onclick="submitPost();" class="btn btn-primary mt-4">게시글 수정</button>
                        </form>
                    </div>
                </div>
<%--                <div class="card">--%>
<%--                    <div class="card-body">--%>
<%--                        <h5 class="card-title">첨부 파일</h5>--%>
<%--                    </div>--%>

<%--                    <c:forEach var="multiFile" items="${multiFiles}">--%>
<%--                        <div class="file-info">--%>
<%--                            <span class="glyphicon glyphicon-camera" aria-hidden="true"></span>--%>
<%--                            <a href="/boards/posts/${multiFile.boardId}/files/${multiFile.fileId}/download">${multiFile.fileOriginalName}</a>--%>
<%--                            <span>${multiFile.fileSize}kb</span>--%>
<%--                        </div>--%>
<%--                    </c:forEach>--%>
<%--                </div>--%>
                <!-- ... (댓글 및 삭제 버튼 등의 코드) ... -->
            </div>

<%--            <a href="/boards/${board.boardId}" class="btn btn-primary" > 게시글 수정</a>--%>


            <br>
            <br>
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
        // 4. 스마트 에디터 설정 함수
        let oEditors = [];

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
            oEditors.getById["editorTxt"].exec("UPDATE_CONTENTS_FIELD", [])
            let content = document.getElementById("editorTxt").value

            if(content == '') {
                alert("내용을 입력해주세요.")
                oEditors.getById["editorTxt"].exec("FOCUS")
                return
            } else {
                console.log(content)
            }
        }
    </script>


</div>
</body>
</html>