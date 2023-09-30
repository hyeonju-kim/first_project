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

        </div>
    </div>
</div>

<script>
    // 차트 데이터
    var chartData = [
        {
            name: 'Chrome',
            y: 63.06,
            drilldown: 'Chrome'
        },
        {
            name: 'Safari',
            y: 19.84,
            drilldown: 'Safari'
        },
        {
            name: 'Firefox',
            y: 4.18,
            drilldown: 'Firefox'
        },
        {
            name: 'Edge',
            y: 4.12,
            drilldown: 'Edge'
        },
        {
            name: 'Opera',
            y: 2.33,
            drilldown: 'Opera'
        },
        {
            name: 'Internet Explorer',
            y: 0.45,
            drilldown: 'Internet Explorer'
        },
        {
            name: 'Other',
            y: 1.582,
            drilldown: null
        }
    ];

    // 차트 생성 함수
    function createChart() {
        // Data retrieved from https://gs.statcounter.com/browser-market-share#monthly-202201-202201-bar

// Create the chart
        Highcharts.chart('container', {
            chart: {
                type: 'column'
            },
            title: {
                align: 'center',
                text: 'September, 2023'
            },
            accessibility: {
                announceNewData: {
                    enabled: true
                }
            },
            xAxis: {
                type: 'category'
            },
            yAxis: {
                title: {
                    text: '실제 섭취 칼로리 / 하루 적정 섭취 칼로리 * 100'
                }

            },
            legend: {
                enabled: false
            },
            plotOptions: {
                series: {
                    borderWidth: 0,
                    dataLabels: {
                        enabled: true,
                        format: '{point.y:.1f}%'
                    }
                }
            },

            tooltip: {
                headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
            },

            series: [
                {
                    name: 'Browsers',
                    colorByPoint: true,
                    data: [
                        {
                            name: 'Chrome',
                            y: 63.06,
                            drilldown: 'Chrome'
                        },
                        {
                            name: 'Safari',
                            y: 19.84,
                            drilldown: 'Safari'
                        },
                        {
                            name: 'Firefox',
                            y: 4.18,
                            drilldown: 'Firefox'
                        },
                        {
                            name: 'Edge',
                            y: 4.12,
                            drilldown: 'Edge'
                        },
                        {
                            name: 'Opera',
                            y: 2.33,
                            drilldown: 'Opera'
                        },
                        {
                            name: 'Internet Explorer',
                            y: 0.45,
                            drilldown: 'Internet Explorer'
                        },
                        {
                            name: 'Other',
                            y: 1.582,
                            drilldown: null
                        }
                    ]
                }
            ],
            drilldown: {
                breadcrumbs: {
                    position: {
                        align: 'right'
                    }
                },
                series: [
                    {
                        name: 'Chrome',
                        id: 'Chrome',
                        data: [
                            [
                                'v65.0',
                                0.1
                            ],
                            [
                                'v64.0',
                                1.3
                            ],
                            [
                                'v63.0',
                                53.02
                            ],
                            [
                                'v62.0',
                                1.4
                            ],
                            [
                                'v61.0',
                                0.88
                            ],
                            [
                                'v60.0',
                                0.56
                            ],
                            [
                                'v59.0',
                                0.45
                            ],
                            [
                                'v58.0',
                                0.49
                            ],
                            [
                                'v57.0',
                                0.32
                            ],
                            [
                                'v56.0',
                                0.29
                            ],
                            [
                                'v55.0',
                                0.79
                            ],
                            [
                                'v54.0',
                                0.18
                            ],
                            [
                                'v51.0',
                                0.13
                            ],
                            [
                                'v49.0',
                                2.16
                            ],
                            [
                                'v48.0',
                                0.13
                            ],
                            [
                                'v47.0',
                                0.11
                            ],
                            [
                                'v43.0',
                                0.17
                            ],
                            [
                                'v29.0',
                                0.26
                            ]
                        ]
                    },
                    {
                        name: 'Firefox',
                        id: 'Firefox',
                        data: [
                            [
                                'v58.0',
                                1.02
                            ],
                            [
                                'v57.0',
                                7.36
                            ],
                            [
                                'v56.0',
                                0.35
                            ],
                            [
                                'v55.0',
                                0.11
                            ],
                            [
                                'v54.0',
                                0.1
                            ],
                            [
                                'v52.0',
                                0.95
                            ],
                            [
                                'v51.0',
                                0.15
                            ],
                            [
                                'v50.0',
                                0.1
                            ],
                            [
                                'v48.0',
                                0.31
                            ],
                            [
                                'v47.0',
                                0.12
                            ]
                        ]
                    },
                    {
                        name: 'Internet Explorer',
                        id: 'Internet Explorer',
                        data: [
                            [
                                'v11.0',
                                6.2
                            ],
                            [
                                'v10.0',
                                0.29
                            ],
                            [
                                'v9.0',
                                0.27
                            ],
                            [
                                'v8.0',
                                0.47
                            ]
                        ]
                    },
                    {
                        name: 'Safari',
                        id: 'Safari',
                        data: [
                            [
                                'v11.0',
                                3.39
                            ],
                            [
                                'v10.1',
                                0.96
                            ],
                            [
                                'v10.0',
                                0.36
                            ],
                            [
                                'v9.1',
                                0.54
                            ],
                            [
                                'v9.0',
                                0.13
                            ],
                            [
                                'v5.1',
                                0.2
                            ]
                        ]
                    },
                    {
                        name: 'Edge',
                        id: 'Edge',
                        data: [
                            [
                                'v16',
                                2.6
                            ],
                            [
                                'v15',
                                0.92
                            ],
                            [
                                'v14',
                                0.4
                            ],
                            [
                                'v13',
                                0.1
                            ]
                        ]
                    },
                    {
                        name: 'Opera',
                        id: 'Opera',
                        data: [
                            [
                                'v50.0',
                                0.96
                            ],
                            [
                                'v49.0',
                                0.82
                            ],
                            [
                                'v12.1',
                                0.14
                            ]
                        ]
                    }
                ]
            }
        });

    }

    // 차트 생성
    createChart();
</script>
<!-- Bootstrap JavaScript 및 jQuery 추가 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap/dist/js/bootstrap.min.js"></script>
</body>

</html>
