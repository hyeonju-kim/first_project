<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>식단 기록</title>
    <style>
        .mytitle {
            width: 100%;
            height: 150px;
            background-image: linear-gradient(0deg, rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3)), url('/images/diet-record_back.png');
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

    <!-- FullCalendar 스타일 시트 및 스크립트 로드 -->
    <link href="/fullcalendar/main.css" rel="stylesheet" />
    <script src="/fullcalendar/main.js"></script>
    <%--    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>--%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.js"></script>

</head>

<body>
<!-- 만들어 놓은 헤더 포함 !!!!  -->
<%@ include file="../header.jsp" %>

<div class="container">
    <div class="mytitle">
        <h4>매일 식단을 기록하고 체크하세요</h4>
    </div>
    <div class="row justify-content-center">
        <div class="col-md-8">
            <!-- 달력을 표시할 div 요소 -->
            <div id="calendar"></div>
            <%-- 적정부족과다2 이미지 추가--%>
            <div class="logo-container  text-center">
                <img src="/images/적정부족과다2.png" alt="적정/부족/과다">
            </div>
            <div class="text-center mt-3" style="font-size: 20px; font-weight: bold; text-align: center; color: dodgerblue;">
                나의 하루 권장 칼로리: ${user.requiredCalories} kcal
            </div>
        </div>
    </div>
    <!-- 모델에서 담아온 dietRecordMap -->
    <%--    날짜와 섭취상태(적정/과다/부족)--%>
    <div id="dietRecordMap" data-diet-map="${dietRecordMap}" ></div>
    <%--    날짜와 하루 총 섭취 칼로리--%>
    <div id="dietRecordMap2" data-diet-map2="${dietRecordMap2}" ></div>
</div>

<script>
    // JavaScript로 달력을 생성하고 표시하는 코드
    document.addEventListener('DOMContentLoaded', function () {
        let calendarEl = document.getElementById('calendar');

        // 😊 1) jsp에서 문자열로 전달된 데이터를 드디어 자바스크립트 객체로 변환한다.
        console.log(' map들을 파싱해보자~~')
        let dietRecordMap = JSON.parse('${dietRecordMap}');
        let dietRecordMap2 = JSON.parse('${dietRecordMap2}');

        console.log(' dietRecordMap을 파싱한 다음에 찍어보자')
        console.log(dietRecordMap)
        console.log(dietRecordMap2)

        // 특정 키에 대한 값을 가져오기
        // const value = dietRecordMap.get("2023-09-29");
        const value = dietRecordMap["2023-09-29"];
        const value2 = dietRecordMap2["2023-09-29"];
        console.log(value); // "부족"
        console.log(value2); // 700

        // dietRecordMap 객체의 모든 키를 가져오기
        const keys = Object.keys(dietRecordMap);
        const keys2 = Object.keys(dietRecordMap2);

        // 키를 정렬 (예: 오름차순)
        keys.sort();
        keys2.sort();

        // 키 배열을 반복하여 값을 출력
        console.log('keys 모든 날짜와 해당 값 출력');
        for (const key of keys) {
            const value = dietRecordMap[key];
            console.log(key + ': ' + value);
        }
        console.log('keys2 모든 날짜와 하루 총 섭취량 출력');
        for (const key of keys2) {
            const value = dietRecordMap2[key];
            console.log(key + ': ' + value);
        }

        let calendar = new FullCalendar.Calendar(calendarEl, {
            // 달력 옵션 설정
            // height: 1000, // 달력의 높이 설정 (픽셀 단위)
            initialView: 'dayGridMonth', // 월별 달력
            headerToolbar: {
                left: 'prev,next today',
                center: 'title',
                right: 'dayGridMonth'
            },
            selectable: true,
            selectMirror: true,
            navLinks: true, // can click day/week names to navigate views
            editable: true,

            // 😊 2) 해당 날짜에 있는 value 값을 달력에 넣어주기
            dayCellContent: function (arg) {
                let dateHtml = '<span class="fc-daygrid-day-number">' + arg.dayNumberText + '</span>';
                let isoDate = arg.date.toISOString();
                // let dateStr = isoDate.split('T')[0];
                let date = new Date(isoDate);
                date.setDate(date.getDate());
                let year = date.getFullYear();
                let month = (date.getMonth() + 1).toString().padStart(2, '0'); // 월을 2자리로 표시
                let day = date.getDate().toString().padStart(2, '0'); // 일을 2자리로 표시
                let formattedDate = year + '-' + month + '-' + day;

                let intakeResult = dietRecordMap[formattedDate]; // 섭취 결과 (적정/부족/과다)
                let totalIntake = dietRecordMap2[formattedDate]; // 하루 섭취 칼로리 (kcal)
                let imageHtml = '';

                if (intakeResult === '적정') {
                    imageHtml = '<img src="/images/good.png" alt="적정" class="diet-image" style="width: 50%;" />';
                } else if (intakeResult === '부족') {
                    imageHtml = '<img src="/images/low.png" alt="부족" class="diet-image" style="width: 50%;" />';
                } else if (intakeResult === '과다') {
                    imageHtml = '<img src="/images/over.png" alt="과다" class="diet-image" style="width: 50%;" />';
                }
                // 토탈 칼로리 계산
                let totalCalories = parseFloat(totalIntake) || 0; // value2를 숫자로 파싱, 실패하면 0으로 처리

                // 칼로리 정보 표시
                let caloriesHtml = '<div class="calories-info" style="font-size: 12px; font-weight: bold; text-align: center; color: darkgray;">' + totalCalories + 'kcal </div>';

                return {
                    html: '<div style="width: 100%; height: 100%;">' +
                        dateHtml +
                        '<div style="display: flex; justify-content: center; align-items: center;">' + imageHtml + '</div>' +
                        caloriesHtml + // 칼로리 정보 추가
                        '</div>'
                };
            },

            // 😊 3) Create new event (달력 숫자 클릭 시, 아침/점심/저녁 정보 입력)
            select: function (arg) {
                let dateStart = arg.start; // arg.date 대신 arg.start를 사용합니다.
                let isoDate = dateStart.toISOString();
                let date = new Date(isoDate);
                date.setDate(date.getDate());
                let year = date.getFullYear();
                let month = (date.getMonth() + 1).toString().padStart(2, '0'); // 월을 2자리로 표시
                let day = date.getDate().toString().padStart(2, '0'); // 일을 2자리로 표시
                let formattedDate = year + '-' + month + '-' + day;
                let value = dietRecordMap[formattedDate];
                let value2 = dietRecordMap2[formattedDate];

                if (value) {
                    // 해당 날짜에 기록한 값이 있으면 그 날의 입력 값을 노출
                    Swal.fire({
                        title: formattedDate,
                        text: value,value2, // 이렇게 text로 변수를 다시 지정해줘야 알람창에서 보이게 할 수 있다!!!! ㅠㅠ
                        html: ` <div>식사 상태: `+ value+ `</div>
                            <div>하루 총 섭취 칼로리: `+ value2+ ` kcal</div>`,
                        icon: "info",
                        showCancelButton: false, // 취소 버튼을 표시하지 않습니다.
                        confirmButtonText: "확인",
                        customClass: {
                            confirmButton: "btn btn-primary"
                        }
                    });
                }else {
                    // 해당 날짜에 기록한 값이 없으면 기록하는 인풋 알람창이 노출
                    Swal.fire({
                        title: "식사를 기록하세요!",
                        text: formattedDate,
                        html: `<div class='mb-7'>`+formattedDate +`</div><br>
                               <form id="dietForm"  method="post" action="/boards/diet-record">
                                   <div class='mt-3'>아침: <input type='number' class='form-control' name='intakeCaloriesMorning' /></div>
                                   <div class='mt-3'>점심: <input type='number' class='form-control' name='intakeCaloriesLunch' /></div>
                                   <div class='mt-3'>저녁: <input type='number' class='form-control' name='intakeCaloriesDinner' /></div>
                                    <input type='hidden' name='intakeDate' value=`+formattedDate+` />
                                    <input type="submit" style="display: none;" /> <!-- Submit 버튼을 숨김 -->
                               </form>
                            <div><img src="/images/calories_table.jpg" alt="식단 테이블" /></div> <!-- 이미지 추가 -->`
                        ,
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
                            // let title = document.querySelector("input[name='event_name']").value;
                            let morning = document.querySelector("input[name='intakeCaloriesMorning']").value;
                            let lunch = document.querySelector("input[name='intakeCaloriesLunch']").value;
                            let dinner = document.querySelector("input[name='intakeCaloriesDinner']").value;

                            // 폼을 서버로 제출!!!!
                            document.getElementById("dietForm").submit();
                            console.log('폼을 서버로 제출 성공!')

                            // 시간대 정보를 포함한 이벤트 생성
                            let eventTitle = title + `\n아침: ${morning}\n점심: ${lunch}\n저녁: ${dinner}`;
                            if (title) {
                                calendar.addEvent({
                                    title: eventTitle,
                                    start: arg.start,
                                    end: arg.end,
                                    allDay: arg.allDay
                                })
                            }
                            calendar.unselect()
                        }
                    });
                }
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