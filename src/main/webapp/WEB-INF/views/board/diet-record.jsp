<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>식단 기록</title>

    <!-- FullCalendar 스타일 시트 및 스크립트 로드 -->
    <link href="/fullcalendar/main.css" rel="stylesheet" />
    <script src="/fullcalendar/main.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>


</head>

<body>
<!-- 만들어 놓은 헤더 포함 !!!!  -->
<%@ include file="../header.jsp" %>

<div class="container">
    <br>
    <h2 class="center-title">식단 기록</h2>
    <div class="row justify-content-center">
        <div class="col-md-8">
            <!-- 달력을 표시할 div 요소 -->
<%--            <div id="calendar"></div>--%>
        </div>
    </div>

    <c:set var="dietResult" value="${dietMap[date]}" />
    <div id="calendar" data-dietResult="<c:out value="${dietResult}" />"></div>
</div>

<script>
    // JavaScript로 달력을 생성하고 표시하는 코드
    document.addEventListener('DOMContentLoaded', function () {
        var calendarEl = document.getElementById('calendar');
        var dietResult = calendarEl.getAttribute('data-dietResult'); // 데이터 속성에서 dietResult 값을 읽어옴
        var calendar = new FullCalendar.Calendar(calendarEl, {
            // 달력 옵션 설정
            height: 1000, // 달력의 높이 설정 (픽셀 단위)
            initialView: 'dayGridMonth', // 월별 달력
            headerToolbar: {
                left: 'prev,next today',
                center: 'title',
                right: 'dayGridMonth'
            },
            selectable: true,
            selectMirror: true,
            navLinks: true, // 클릭 시 세부 화면으로 들어감
            editable: true,

            // // dayRender 이벤트 추가
            // dayRender: function (info) {
            //     var dayCell = info.el.getElementsByClassName('fc-daygrid-day-number')[0]; // 날짜 칸 요소
            //     var helloElement = document.createElement('div');
            //     helloElement.className = 'hello-text';
            //     helloElement.textContent = 'hello';
            //     dayCell.appendChild(helloElement);
            // },

            // // dayCellContent 콜백을 사용하여 날짜 칸에 dietResult 표시
            // dayCellContent: function (arg) {
            //     var dateHtml = '<span class="fc-daygrid-day-number">' + arg.dayNumberText + '</span>';
            //     var dietResultHtml = '<div class="diet-result">' + dietResult + '</div>';
            //     return { html: dateHtml + dietResultHtml };
            // },

            // dayCellContent 콜백을 사용하여 날짜 칸에 dietResult 표시
            dayCellContent: function (arg) {
                var dateHtml = '<span class="fc-daygrid-day-number">' + arg.dayNumberText + '</span>';

                // 현재 날짜
                var currentDate = new Date(arg.date);
                // 날짜를 YYYY-MM-DD 형식의 문자열로 변환
                var formattedDate = currentDate.toISOString().split('T')[0];

                // map에서 dietResult 가져오기
                var dietResult = '<c:out value="${dietMap[' + formattedDate + ']}" />';

                var dietResultHtml = '<div class="diet-result">' + dietResult + '</div>';
                return { html: dateHtml + dietResultHtml };
            },



            // Create new event (달력 숫자 클릭 시, 아침/점심/저녁 정보 입력)
            select: function (arg) {
                Swal.fire({
                    html: `<form id="dietForm"  method="post" action="/boards/diet-record">
                           <div class='mb-7'>식사를 기록하세요!</div>
                           <div class='mt-3'>아침: <input type='number' class='form-control' name='intakeCaloriesMorning' /></div>
                           <div class='mt-3'>점심: <input type='number' class='form-control' name='intakeCaloriesLunch' /></div>
                           <div class='mt-3'>저녁: <input type='number' class='form-control' name='intakeCaloriesDinner' /></div>
                            <input type="submit" style="display: none;" /> <!-- Submit 버튼을 숨김 -->
                        </form>`,
                    icon: "info",
                    showCancelButton: true,
                    buttonsStyling: true,
                    confirmButtonText: "저장",
                    cancelButtonText: "취소",
                    customClass: {
                        confirmButton: "btn btn-primary",
                        cancelButton: "btn btn-active-light"
                    }
                }).then(function (result) {
                    if (result.value) {
                        // var title = document.querySelector("input[name='event_name']").value;
                        var morning = document.querySelector("input[name='intakeCaloriesMorning']").value;
                        var lunch = document.querySelector("input[name='intakeCaloriesLunch']").value;
                        var dinner = document.querySelector("input[name='intakeCaloriesDinner']").value;

                        // 폼을 서버로 제출
                        document.getElementById("dietForm").submit();
                        console.log('폼을 서버로 제출 성공!')

                        // 시간대 정보를 포함한 이벤트 생성
                        var eventTitle = title + `\n아침: ${morning}\n점심: ${lunch}\n저녁: ${dinner}`;
                        if (title) {
                            calendar.addEvent({
                                title: eventTitle,
                                start: arg.start,
                                end: arg.end,
                                allDay: arg.allDay
                            })
                        }
                        calendar.unselect()
                    } else if (result.dismiss === "cancel") {
                        // Swal.fire({
                            // text: "Event creation was declined!.",
                            // icon: "error",
                            // buttonsStyling: false,
                            // confirmButtonText: "Ok, got it!",
                            // customClass: {
                            //     confirmButton: "btn btn-primary",
                            // }
                        // });
                    }
                });
            },


            // Delete event
            eventClick: function (arg) {
                Swal.fire({
                    text: "Are you sure you want to delete this event?",
                    icon: "warning",
                    showCancelButton: true,
                    buttonsStyling: false,
                    confirmButtonText: "Yes, delete it!",
                    cancelButtonText: "No, return",
                    customClass: {
                        confirmButton: "btn btn-primary",
                        cancelButton: "btn btn-active-light"
                    }
                }).then(function (result) {
                    if (result.value) {
                        arg.event.remove()
                    } else if (result.dismiss === "cancel") {
                        Swal.fire({
                            text: "Event was not deleted!.",
                            icon: "error",
                            buttonsStyling: false,
                            confirmButtonText: "Ok, got it!",
                            customClass: {
                                confirmButton: "btn btn-primary",
                            }
                        });
                    }
                });
            },
            dayMaxEvents: true, // allow "more" link when too many events
            // 이벤트 객체 필드 document : https://fullcalendar.io/docs/event-object
            events: [
            ]
        });

        calendar.render();

    });
</script>
</body>

</html>
