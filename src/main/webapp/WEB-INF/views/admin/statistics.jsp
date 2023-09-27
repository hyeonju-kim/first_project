<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시판</title>


    <!-- Highcharts -->
    <script src="https://code.highcharts.com/highcharts.js"></script>
</head>

<body>
<!-- 만들어 놓은 헤더 포함 !!!!  여기서 왜 에러나지? 파일 'header.jsp'을(를) 해결할 수 없습니다 -->
<%@ include file="../header.jsp" %>

<div class="container">
    <br>
    <h2 class="center-title">회원 통계</h2>
    <div class="row justify-content-center">
        <div class="col-md-8">

            <!-- Highcharts 차트 컨테이너 -->
            <div class="text-center" style="margin-top: 20px;">
                <div id="chartContainer" style="width: 800px; height: 600px;"></div>
            </div>


            <!-- Highcharts 스크립트 -->
            <script>
                Highcharts.chart('chartContainer', {
                    title: {
                        text: '가입자 통계',
                        align: 'center'
                    },
                    yAxis: {
                        title: {
                            text: '가입자 수'
                        },
                        allowDecimals: false // 소수점 표시 여부 설정
                    },
                    xAxis: {
                        accessibility: {
                            rangeDescription: 'Range: 1 to 12'
                        }
                    },
                    legend: {
                        layout: 'vertical',
                        align: 'right',
                        verticalAlign: 'middle'
                    },
                    plotOptions: {
                        series: {
                            label: {
                                connectorAllowed: false
                            },
                            pointStart: 1
                        }
                    },
                    series: [{
                        name: '월별 가입자 수',
                        data: [
                            <c:forEach items="${statistics}" var="stat" varStatus="loop">
                            ${stat.userCount}<c:if test="${!loop.last}">, </c:if>
                            </c:forEach>
                        ]
                    }],
                    responsive: {
                        rules: [{
                            condition: {
                                maxWidth: 1
                            },
                            chartOptions: {
                                legend: {
                                    layout: 'horizontal',
                                    align: 'center',
                                    verticalAlign: 'bottom'
                                }
                            }
                        }]
                    }
                });
            </script>
        </div>
    </div>
</div>
</div>
</div>
</body>

</html>
