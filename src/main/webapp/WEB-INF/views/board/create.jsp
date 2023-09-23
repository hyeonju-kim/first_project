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
            <textarea class="form-control" id="content" name="content" rows="8" required></textarea>
        </div>
        <div class="form-group mt-4">
            <label for="file"><strong>첨부 파일</strong></label>
            <input type="file" class="form-control-file" id="file" name="file">
        </div>
        <button type="submit" class="btn btn-primary mt-4">게시글 생성</button>
    </form>
</div>


<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    // $(document).ready(function() {
    //     $("#submitBtn").click(function() {
    //         var title = $("#title").val();
    //         var content = $("#content").val();
    //         // var file = $("#file")[0].files[0]; // 파일 업로드
    //
    //         var formData = new FormData();
    //         formData.append("title", title);
    //         formData.append("content", content);
    //         // formData.append("file", file);
    //
    //         $.ajax({
    //             type: "POST",
    //             url: "/boards/create",
    //             data: formData,
    //             processData: false,
    //             contentType: false,
    //             success: function(response) {
    //                 // 성공 시 처리
    //                 alert("게시글이 생성되었습니다.");
    //                 // 원하는 리다이렉트 처리
    //             },
    //             error: function(error) {
    //                 // 오류 시 처리
    //                 alert("게시글 생성에 실패했습니다.");
    //             }
    //         });
    //     });
    // });
</script>


</body>
</html>
