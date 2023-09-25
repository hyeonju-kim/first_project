<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시판</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <!-- Your custom styles -->
    <style>
        .center-title {
            text-align: center;
        }

        .pagination {
            margin: 20px auto;
        }

        .current-page {
            border: 1px solid #ccc; /* 테두리 스타일 설정 */
            box-shadow: 2px 2px 5px #888; /* 그림자 설정 */
            padding: 5px 10px; /* 내부 여백 설정 */
            border-radius: 5px; /* 모서리를 둥글게 만들기 위한 설정 */
        }

        .menu-tab {
            border-bottom: 1px solid #ccc; /* 구분 선 추가 */
            margin-bottom: 20px; /* 구분 선과 메뉴 탭 사이 여백 */
            padding-bottom: 10px; /* 메뉴 탭과 메뉴 이름 사이 여백 */
        }

        .menu-name {
            text-align: center; /* 메뉴 이름 가운데 정렬 */
            font-weight: bold; /* 굵은 글꼴 */
        }

        /* 세로 구분선 스타일 */
        .vertical-divider {
            border-left: 5px solid #8C8C8C;
            height: 100%;
        }
        .admin-mode {
            background-color: #ccc; /* 배경색을 회색(#ccc)으로 지정 */
            padding: 5px 10px; /* 내부 여백 설정 */
            border-radius: 10px; /* 둥근 모서리를 위한 설정 */
            display: inline-block; /* 텍스트 크기와 일치하는 너비로 설정 */
        }
    </style>

    <!-- Highcharts -->
    <script src="https://code.highcharts.com/highcharts.js"></script>
</head>

<body>
<div class="container">
    <div class="row">
        <!-- 왼쪽 메뉴 탭 -->
        <div class="col-md-3">
            <div class="menu-tab">
                <div class="menu-name">메뉴</div>
            </div>
            <ul class="nav flex-column">
                <li class="nav-item">
                    <a class="nav-link" href="/boards">자유게시판</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/admin/statistics">사용자 통계</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/admin/users">사용자 정보</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/admin/menu">메뉴 관리</a>
                </li>
            </ul>
        </div>
        <!-- 세로 구분선 -->
        <div class="col-md-1 vertical-divider"></div>
        <div class="col-md-8">
            <div class="text-right mt-2">
                <c:if test="${not empty username && username != 'anonymousUser'}">
                    <h6><span>${nickname}님 안녕하세요!</span></h6>
                    <c:if test="${role == 'admin'}">
                        <h6><span class="admin-mode">관리자모드</span></h6>
                    </c:if>
                    <a href="/logout" class="btn btn-danger mr-2">로그아웃</a>
                    <a href="/mypage" class="btn btn-primary mr-2">마이페이지</a>
                </c:if>
                <c:if test="${empty username || username == 'anonymousUser'}">
                    <a href="/login" class="btn btn-primary">로그인</a>
                </c:if>
            </div>
            <br>

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
</body>

</html>
