<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>통계</title>
    <style>
        .mytitle {
            width: 100%;
            height: 150px;
            background-image: linear-gradient(0deg, rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3)), url('/images/statistics_back.png');
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
    <!-- jQuery 라이브러리 추가 -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <!-- Highcharts 라이브러리 추가 -->
    <script src="https://code.highcharts.com/highcharts.js"></script>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">


</head>

<body>
<!-- 만들어 놓은 헤더 포함 !!!!  -->
<%@ include file="../header.jsp" %>

<div class="container">
    <div class="mytitle">
        <h4>최근 7일 간의 섭취량을 비교해보세요</h4>
    </div>
    <div class="row justify-content-center">
        <div class="col-md-8 text-center">

            <!-- 섭취량 선택 라디오 버튼 -->
<%--            <form id="caloriesForm">--%>
<%--                <label class="mr-3">--%>
<%--                    <input type="radio" name="caloriesType" value="today" checked> 오늘 섭취량--%>
<%--                </label>--%>
<%--                <label class="mr-3">--%>
<%--                    <input type="radio" name="caloriesType" value="thisWeek"> 이번 주 섭취량--%>
<%--                </label>--%>
<%--                <label>--%>
<%--                    <input type="radio" name="caloriesType" value="thisMonth"> 이번 달 섭취량--%>
<%--                </label>--%>
<%--            </form>--%>

            <!-- 아침, 점심, 저녁 섭취량 조회 -->
            <h3>오늘의 섭취량</h3>
            <div class="mt-3">
                <p><strong>아침 섭취량:</strong> ${dietDaily.intakeCaloriesMorning} kcal</p>
                <p><strong>점심 섭취량:</strong> ${dietDaily.intakeCaloriesLunch} kcal</p>
                <p><strong>저녁 섭취량:</strong> ${dietDaily.intakeCaloriesDinner} kcal</p>
                <p><strong>합계:</strong> ${dietDaily.intakeCaloriesMorning + dietDaily.intakeCaloriesLunch + dietDaily.intakeCaloriesDinner} kcal</p>
                <p><strong>오늘의 식이 점수:</strong> ${dietDaily.intakeResult} </p>
            </div>

            <!-- 차트를 표시할 div -->
            <div id="container" style="height: 400px; margin-top: 20px;"></div>
            <%-- 적정부족과다 이미지 추가--%>
            <div class="logo-container  text-center">
                <img src="/images/적정부족과다.png" alt="적정/부족/과다">
            </div>

            <!-- 모델에서 담아온 dietWeekList, userDto -->
            <div id="dietWeeklyList" data-diet-weekly-list="${dietWeeklyList}" data-required-calories="${userDto.requiredCalories}"></div>


        </div>
    </div>
</div>

<script>
   //자바 스크립트 객체가 아닌 자바 객체의 문자열 표현을 출력 한다.
    console.log('리스트를 그냥 찍어보자')
    console.log(${dietWeeklyList})

    // 자바 객체를 ''로 감싸서 문자열로 출력 한다.
    console.log('리스트에 \'\'를 붙여서 찍어보자')
    console.log('${dietWeeklyList}')

   // jsp에서 문자열로 전달된 데이터를 드디어 자바스크립트 객체로 변환한다.
    console.log('리스트를 파싱한 다음에 찍어보자')
    let dietWeeklyList = JSON.parse('${dietWeeklyList}');
    console.log(dietWeeklyList)

    let requiredCalories = $('#dietWeeklyList').data('required-calories');
    console.log('requiredCalories 는 얼마인가??')
    console.log(requiredCalories)

    // Create an array to hold the chart data
    let chartData = [];

    // Populate chartData with the diet data
    dietWeeklyList.forEach(function(item) {
        let intakeDate = item.intakeDate;
        let totalCalories = item.intakeCaloriesMorning + item.intakeCaloriesLunch + item.intakeCaloriesDinner;

        // Push data for each date
        chartData.push({
            name: intakeDate, // 가로축은 날짜
            y: totalCalories // 세로축은 섭취 칼로리
        });
    });

    // Create the chart
    function createChart() {
        Highcharts.chart('container', {
            chart: {
                type: 'column'
            },
            title: {
                align: 'center',
                text: ''
            },
            xAxis: {
                categories: chartData.map(function(item) { return item.name; })
            },
            yAxis: {
                title: {
                    text: 'Total Calories'
                },
                plotLines: [{
                    // 유저의 하루 섭취 필요한 칼로리를 표시하는 가로 선 만들기
                    color: 'red', // 빨간색
                    width: 2, // 두께
                    value: requiredCalories, // 표시할 값 (유저의 하루 섭취 필요한 칼로리)
                    zIndex: 5, // 다른 그래프와 겹치지 않도록 zIndex 설정
                    label: {
                        text: '내가 하루에 섭취해야 할 kcal', // 라벨 텍스트
                        align: 'right',
                        x: -10 // 라벨 위치 조정
                    }
                }]
            },
            legend: { // 범례
                enabled: false
            },
            plotOptions: {
                column: {
                    dataLabels: {
                        enabled: true,
                        format: '{point.y:.0f} kcal'
                    },
                    colorByPoint: true,
                    colors: chartData.map(function(item) {
                        let totalCalories = item.y;
                        //하루 권장 칼로리보다 15%이상 많이 섭취하면 과다 , 15% 미만으로 섭취하면 부족, 그 외에는 적정
                        if (totalCalories >= requiredCalories * 1.15) {
                            return 'purple'; // 2000 칼로리 이상은 보라색
                        } else if (totalCalories >= requiredCalories * 0.85) {
                            return 'green'; // 1600 칼로리 이상은 초록색
                        } else {
                            return 'red'; // 나머지는 빨간색
                        }
                    })
                }
            },
            series: [
                {
                data: chartData
                }
            ]
        });

    }

    // Call the createChart function to generate the chart
    createChart();
</script>
<!-- Bootstrap JavaScript 및 jQuery 추가 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap/dist/js/bootstrap.min.js"></script>
</body>

</html>
