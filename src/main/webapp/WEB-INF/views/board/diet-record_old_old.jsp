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
            <div id="calendar"></div>
        </div>
    </div>
</div>

<script>
    // JavaScript로 달력을 생성하고 표시하는 코드
    document.addEventListener('DOMContentLoaded', function () {
        var calendarEl = document.getElementById('calendar');

        var calendar = new FullCalendar.Calendar(calendarEl, {
            // 달력 옵션 설정
            initialView: 'dayGridMonth', // 월별 달력
            headerToolbar: {
                left: 'prev,next today',
                center: 'title',
                right: 'dayGridMonth'
            },
            selectable: true,
            selectMirror: true,

            navLinks: true, // 기본적으로 true로 설정

            // 날짜를 클릭할 때 호출되는 콜백 함수
            dateClick: function (info) {
                // info.date는 클릭한 날짜의 Date 객체입니다.
                var selectedDate = info.date.getDate();

                // 짝수 날짜에 대해 select 이벤트 활성화
                if (selectedDate % 2 === 0) {
                    Swal.fire({
                        html: `<form id="dietForm"  method="post" action="/boards/diet-record">
                               <div class='mb-7'>식사를 기록하세요!</div>
                               <div class='mt-3'>아침: <input type='number' class='form-control' name='intakeCaloriesMorning' /></div>
                               <div class='mt-3'>점심: <input type='number' class='form-control' name='intakeCaloriesLunch' /></div>
                               <div class='mt-3'>저녁: <input type='number' class='form-control' name='intakeCaloriesDinner' /></div>
                               <div><img src="/images/calories_table.jpg" alt="식단 테이블" /></div> <!-- 이미지 추가 -->
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
                            var morning = document.querySelector("input[name='intakeCaloriesMorning']").value;
                            var lunch = document.querySelector("input[name='intakeCaloriesLunch']").value;
                            var dinner = document.querySelector("input[name='intakeCaloriesDinner']").value;

                            // 폼을 서버로 제출
                            document.getElementById("dietForm").submit();
                            console.log('폼을 서버로 제출 성공!')

                            // 시간대 정보를 포함한 이벤트 생성
                            var eventTitle = `아침: ${morning}\n점심: ${lunch}\n저녁: ${dinner}`;
                            calendar.addEvent({
                                title: eventTitle,
                                start: info.date,
                                allDay: true
                            });

                            calendar.unselect();
                        } else if (result.dismiss === "cancel") {
                            // 취소할 때의 동작을 정의하거나 필요에 따라 삭제하세요.
                        }
                    });
                } else {
                    // 홀수 날짜에 대해 해당 날짜로 이동 (navLinks 활성화)
                    calendar.gotoDate(info.date);
                }
            },
            dayMaxEvents: true, // allow "more" link when too many events
            // 이벤트 객체 필드 document : https://fullcalendar.io/docs/event-object
            events: []
        });
        calendar.render();
    });
</script>
</body>
</html>
