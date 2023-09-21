<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>메인 화면</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <!-- Your custom styles -->
    <style>
        .center-title {
            text-align: center;
        }
    </style>
</head>

<body>
<div class="container">
    <!-- 마이페이지 버튼 -->
    <div class="text-right mt-2">
        <a href="/logout" class="btn btn-danger mr-2">로그아웃</a>
        <a href="/mypage" class="btn btn-primary mr-2">마이페이지</a>
    </div>
    <br>
    <br>
    <br>
    <br>
    <h2 class="center-title">게시판</h2>


    <!-- 로그인 사용자 이름 표시 -->
    <%--    <div class="text-right mt-2">--%>
    <%--        <span id="userName"></span> 님 안녕하세요!--%>
    <%--    </div>--%>

    <!-- 글 작성 버튼 -->
    <a href="/boards/create" class="btn btn-primary mb-3">글 작성</a>

    <!-- 글 목록 -->
    <table class="table">
        <thead>
        <tr>
            <th scope="col">제목</th>
            <th scope="col">작성자</th>
            <th scope="col">작성일</th>
        </tr>
        </thead>
<%--        <tbody>--%>
<%--        <c:forEach items="${boards.list}" var="board">--%>
<%--            <tr>--%>
<%--                <td><a href="/boards/${board.boardId}">${board.title}</a></td>--%>
<%--                <td>${board.nickname}</td>--%>
<%--                <td>${board.createdAt}</td>--%>
<%--            </tr>--%>
<%--        </c:forEach>--%>
<%--        </tbody>--%>

        <!--/* 리스트 데이터 렌더링 영역 */-->
        <tbody id="list">

        </tbody>
    </table>

    <!--/* 페이지네이션 렌더링 영역 */-->
    <div class="paging">

    </div>

    <br>
    <br>

    <!-- 글 목록 위 검색창 -->
    <form action="/boards/search" method="GET" style="max-width: 300px; margin: 0 auto;">
        <div class="input-group mb-3">
            <input type="text" class="form-control" placeholder="검색어를 입력하세요" name="keyword" aria-label="검색어"
                   aria-describedby="basic-addon2">
            <div class="input-group-append">
                <button class="btn btn-primary" type="submit">검색</button>
            </div>
        </div>
    </form>

    <br>
    <br>

    <!-- 페이징 처리 -->
    <div class="text-center" style="position: fixed; bottom: 60px; left: 50%; transform: translateX(-50%);">
        <ul class="pagination">
            <c:if test="${boards.pagination.existPrevPage}">
                <li class="page-item"><a class="page-link" href="/boards?page=${boards.pagination.startPage - 1}">이전</a>
                </li>
            </c:if>
            <c:forEach begin="${boards.pagination.startPage}" end="${boards.pagination.endPage}" var="page">
                <li class="page-item <c:if test='${page == boards.pagination.currentPage}'>active</c:if>'">
                    <a class="page-link" href="/boards?page=${page}">${page}</a>
                </li>
            </c:forEach>
            <c:if test="${boards.pagination.existNextPage}">
                <li class="page-item"><a class="page-link" href="/boards?page=${boards.pagination.endPage + 1}">다음</a>
                </li>
            </c:if>
        </ul>
    </div>


</div>



<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> <!-- jQuery 추가 -->
<script>

window.onload = function () {
        // 페이지가 로드되었을 때, 딱 한 번만 함수를 실행
        findAllPost();
    };

    // 😊😊😊😊😊😊😊😊😊😊😊😊😊😊😊 함수 1. 게시글 리스트 조회 😊😊😊😊😊😊😊😊😊😊😊😊😊😊😊
    <%--function findAllPost() {--%>
    <%--    // 1. PagingResponse의 멤버인 List<T> 타입의 list를 의미--%>
    <%--    var list = '${boards.list}'; // 렌더링이 안됨!!!!!!!!!!!!!!!!!!!!--%>



    <%--    // 2. 리스트가 비어있는 경우, 행에 "검색 결과가 없다"는 메시지를 출력하고, 페이지 번호(페이지네이션) HTML을 제거(초기화)한 후 로직을 종료--%>
    <%--    if (list.length === 0) {--%>
    <%--        document.getElementById('list').innerHTML = '<td colspan="6"><div class="no_data_msg">검색된 결과가 없습니다.</div></td>';--%>
    <%--        drawPage();--%>
    <%--        return;--%>
    <%--    }--%>

    <%--    // 3. PagingResponse의 멤버인 pagination을 의미--%>
    <%--    var pagination = '${boards.pagination}';--%>

    <%--    // 4. @ModelAttribute를 이용해서 뷰(HTML)로 전달한 SearchDto 타입의 객체인 params를 의미--%>
    <%--    var params = '${params}';--%>

    <%--    // 5. 리스트에 출력되는 게시글 번호를 처리하기 위해 사용되는 변수 (리스트에서 번호는 페이지 정보를 이용해서 계산해야 함)--%>
    <%--    var num = pagination.totalRecordCount - ((params.currentPage - 1) * params.recordSize); // 전체 데이터 수 - ( 현재 페이지 수 - 1 ) * 페이지당 출력 게시물 수  // 현재가 1페이지이면 20, 2페이지이면 10--%>

    <%--    // 6. 리스트 데이터 렌더링--%>
    <%--    drawList(list, num);--%>

    <%--    // 7. 페이지 번호 렌더링--%>
    <%--    drawPage(pagination, params);--%>
    <%--}--%>

    // 함수 1 . 게시글 리스트 조회 함수
    function findAllPost() {

        $.ajax({
            type: "GET",
            url: "/boards", // 데이터를 가져올 엔드포인트 URL 설정
            data: JSON.stringify(data),
            dataType: "json", // 받아올 데이터의 형식 (JSON)
            success: function (data) {
                // 성공 시 실행되는 콜백 함수
                var num = (data.pagination.totalRecordCount) - ((data.params.currentPage - 1) * data.params.recordSize);
                drawList(data.boards.list, num); // 게시글 데이터를 화면에 출력하는 함수 호출
                drawPage(data.boards.pagination, data.params); // 페이지 번호를 업데이트하는 함수 호출
            },
            error: function () {
                alert("게시글을 불러오는 데 실패했습니다.");
            }
        });

        // 함수 2. 리스트 HTML draw
        function drawList(list, num) {
            // 1. 렌더링 할 HTML을 저장할 변수
            var html = '';

            // 2. 리스트 데이터를 JavaScript로 동적으로 생성
            for (var i = 0; i < list.length; i++) {
                var board = list[i];
                html += '<tr>';
                html += '<td class="tl"><a href="/boards/' + board.boardId + '">' + board.title + '</a></td>';
                html += '<td>' + board.nickname + '</td>';
                html += '<td>' + board.createdAt + '</td>';
                html += '</tr>';
            }

            // 3. id가 "list"인 요소를 찾아 HTML을 렌더링
            document.getElementById('list').innerHTML = html;
        }

        // 함수 3. 페이지 HTML draw
        function drawPage(pagination, params) {
            // 1. 필수 파라미터가 없는 경우, 페이지 번호(페이지네이션) HTML을 제거(초기화)한 후 로직 종료
            if (!pagination || !params) {
                document.querySelector('.paging').innerHTML = '';
                throw new Error('Missing required parameters...');
            }

            // 2. 렌더링 할 HTML을 저장할 변수
            var html = '';

            // 3. 이전 페이지가 있는 경우, 시작 페이지(startPage)가 1이 아닌 경우 첫 페이지 버튼과 이전 페이지 버튼을 HTML에 추가
            if (pagination.existPrevPage) {
                html += '<a href="javascript:void(0);" onclick="movePage(1)" class="page_bt first">첫 페이지</a>';
                html += '<a href="javascript:void(0);" onclick="movePage(' + (pagination.startPage - 1) + ')" class="page_bt prev">이전 페이지</a>';
            }

            // 4. 시작 페이지(startPage)와 끝 페이지(endPage) 사이의 페이지 번호(i)를 넘버링하는 로직
            html += '<p>';
            for (var i = pagination.startPage; i <= pagination.endPage; i++) {
                html += (i !== params.currentPage)
                    ? '<a href="javascript:void(0);" onclick="movePage(' + i + ');">' + i + '</a>'
                    : '<span class="on">' + i + '</span>';
            }
            html += '</p>';

            // 5. 현재 위치한 페이지 뒤에 데이터가 더 있는 경우, 다음 페이지 버튼과 끝 페이지 버튼을 HTML에 추가
            if (pagination.existNextPage) {
                html += '<a href="javascript:void(0);" onclick="movePage(' + (pagination.endPage + 1) + ');" class="page_bt next">다음 페이지</a>';
                html += '<a href="javascript:void(0);" onclick="movePage(' + pagination.totalPageCount + ');" class="page_bt last">마지막 페이지</a>';
            }

            // 6. class가 "paging"인 요소를 찾아 HTML을 렌더링
            document.querySelector('.paging').innerHTML = html;
        }


    }

    // 함수 4. 페이지 이동
    function movePage(page) {
        // 1. drawPage()의 각 버튼에 선언된 onclick 이벤트를 통해 전달받는 page(페이지 번호)를 기준으로 객체 생성
        var queryParams = {
            page: (page) ? page : 1,
            recordSize: 10,
            pageSize: 10
        };

        /*
         *    new URLSearchParams(queryParams).toString(): queryParams의 모든 프로퍼티(key-value)를 쿼리 스트링으로 변환
         *    URI + 쿼리 스트링에 해당하는 주소로 이동
         *    (해당 함수가 리턴해주는 값을 브라우저 콘솔(console)에 찍어보시면 쉽게 이해하실 수 있습니다.)
         */
        location.href = '/boards?' + new URLSearchParams(queryParams).toString();
    }


</script>

</body>

</html>
