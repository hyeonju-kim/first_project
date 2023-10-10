<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>랭킹 페이지</title>
    <style>
        .mytitle {
            width: 100%;
            height: 150px;
            background-image: linear-gradient(0deg, rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.4)), url('/images/rank_back.png');
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

<!-- 랭킹 차트를 그릴 div 요소 -->
<%--<div id="rankingChart"></div>--%>

<div class="container">
    <div class="mytitle">
        <h4>건강한 식사 랭킹을 확인하세요</h4>
    </div>
    <div class="row justify-content-center">
        <div class="col-md-9 text-center">
            <!-- Highcharts 차트를 넣을 div 요소 추가 -->
            <div id="container"></div>

            <!-- 모델에서 담아온 rankingMap -->
            <%-- 닉네임, 적정 식사 횟수를 담은 맵 --%>
            <div id="rankingMap" data-ranking-map="${rankingMap}" ></div>
        </div>
    </div>
</div>

<script>

    // 모델에 담은 rankingMap 데이터를 가져옵니다.
    let rankingMap = ${rankingMap}
        console.log(rankingMap);

    // 차트 데이터를 담을 배열
    let chartData = [];

    // 자바스크립트 객체가 아닌 자바 객체의 문자열 표현을 출력한다.
    <%--console.log('맵을 그냥 찍어보자')--%>
    <%--console.log(${rankingMap})--%>

    // jsp에서 문자열로 전달된 데이터를 드디어 자바스크립트 객체로 변환한다.
    // console.log('맵을 파싱한 다음에 찍어보자');
    // let rankingMap = JSON.parse(document.getElementById('rankingMap').getAttribute('data-ranking-map'));
    // console.log(rankingMap);


    // rankingMap의 데이터를 차트 데이터 형식으로 변환
    for (let nickname in rankingMap) {
        if (rankingMap.hasOwnProperty(nickname)) {
            let barColor = '#3e5ccf'; // 기본 막대 색상
            if (nickname == '${nickname}') {
                // user.nickname과 닉네임이 같으면 형광색으로 변경
                barColor = '#00f194';
            }
            chartData.push({
                name: nickname,
                y: rankingMap[nickname],
                color: barColor // 해당 막대의 색상 설정
            });
        }
    }

    // Create the chart
    function createChart() {
        Highcharts.chart('container', {
            chart: {
                type: 'bar', // 'bar' 으로 변경하여 세로 막대 그래프로 설정
                width: 800, // 너비 조정
                height: 600, // 높이 조정
            },
            title: {
                text: '일주일 간 적정 식사를 가장 많이 기록한 유저 순으로 나열됩니다.'
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                // categories: chartData.map(function(item) { return item.nickname; }),
                type: 'category',
                title: {
                    text: ''
                },
                labels: {
                    rotation: 0,
                    style: {
                        fontSize: '16px',
                        fontFamily: 'Verdana, sans-serif',
                        fontWeight: 'bold'
                    }
                }
            },
            yAxis: {
                title: {
                    text: '적정 식사 횟수' // y 축 레이블
                },
                min: 0,
                plotLines: [{
                    value: chartData.map(function(item) { return item.resultGoodCount; }), // 표시할 값 (유저의 적정 식사 횟수)
                }]
            },
            legend: {
                enabled: false
            },
            // tooltip: {
            //     pointFormat: '일주일 간 \'적정\' 식사 횟수 : <b>{point.y:.1f} millions</b>'
            // },
            series: [{
                name: '적정 식사 횟수',
                // colors: [
                //     '#9b20d9', '#9215ac', '#861ec9', '#7a17e6', '#7010f9', '#691af3',
                //     '#6225ed', '#5b30e7', '#533be1', '#4c46db', '#4551d5', '#3e5ccf',
                //     '#3667c9', '#2f72c3', '#277dbd', '#1f88b7', '#1693b1', '#0a9eaa',
                //     '#03c69b',  '#00f194'
                // ],
                colorByPoint: true,
                groupPadding: 0,
                data: chartData,
                dataLabels: {
                    enabled: true,
                    rotation: 0, // 0도로 설정하여 수평으로 표시
                    color: '#FFFFFF',
                    align: 'center', // 가운데 정렬로 변경
                    // format: '{point.y:.1f}', // one decimal
                    y: 0, // 가운데로 정렬하기 위해 y값을 0으로 설정
                    style: {
                        fontSize: '13px',
                        fontFamily: 'Verdana, sans-serif'
                    }
                }
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
