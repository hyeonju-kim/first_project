<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>통계</title>
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

<div class="container mt-5">
    <h2 class="text-center mb-4">통계</h2>
    <div class="row justify-content-center">
        <div class="col-md-8 text-center">

            <!-- 섭취량 선택 라디오 버튼 -->
            <form id="caloriesForm">
                <label class="mr-3">
                    <input type="radio" name="caloriesType" value="today" checked> 오늘 섭취량
                </label>
                <label class="mr-3">
                    <input type="radio" name="caloriesType" value="thisWeek"> 이번 주 섭취량
                </label>
                <label>
                    <input type="radio" name="caloriesType" value="thisMonth"> 이번 달 섭취량
                </label>
            </form>

            <!-- 아침, 점심, 저녁 섭취량 조회 -->
            <div class="mt-3">
                <p><strong>아침 섭취량:</strong> ${dietDaily.intakeCaloriesMorning} kcal</p>
                <p><strong>점심 섭취량:</strong> ${dietDaily.intakeCaloriesLunch} kcal</p>
                <p><strong>저녁 섭취량:</strong> ${dietDaily.intakeCaloriesDinner} kcal</p>
                <p><strong>합계:</strong> ${dietDaily.intakeCaloriesMorning + dietDaily.intakeCaloriesLunch + dietDaily.intakeCaloriesDinner} kcal</p>
                <p><strong>오늘의 식이 점수:</strong> ${dietDaily.intakeResult} </p>
            </div>

            <!-- 차트를 표시할 div -->
            <div id="container" style="height: 400px; margin-top: 20px;"></div>

            <!-- 모델에서 담아온 dietWeekList. 자바 객체의 문자열 표현이다. -->
            <div id="dietWeeklyList" data-diet-weekly-list="${dietWeeklyList}"></div>


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
    var dietWeeklyList = JSON.parse('${dietWeeklyList}');
    console.log(dietWeeklyList)

    // Create an array to hold the chart data
    var chartData = [];

    // Populate chartData with the diet data
    dietWeeklyList.forEach(function(item) {
        var intakeDate = item.intakeDate;
        var totalCalories = item.intakeCaloriesMorning + item.intakeCaloriesLunch + item.intakeCaloriesDinner;

        // Push data for each date
        chartData.push({
            name: intakeDate,
            y: totalCalories
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
                text: 'Weekly Diet Statistics'
            },
            xAxis: {
                categories: chartData.map(function(item) { return item.name; })
            },
            yAxis: {
                title: {
                    text: 'Total Calories'
                }
            },
            legend: {
                enabled: false
            },
            plotOptions: {
                column: {
                    dataLabels: {
                        enabled: true,
                        format: '{point.y:.0f} kcal'
                    }
                }
            },
            series: [{
                data: chartData
            }]
        });
    }

    // Call the createChart function to generate the chart
    createChart();
</script>
<!-- Bootstrap JavaScript 및 jQuery 추가 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap/dist/js/bootstrap.min.js"></script>
</body>

</html>
