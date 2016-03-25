<%--
  Created by IntelliJ IDEA.
  User: johnny
  Date: 16/3/4
  Time: 上午9:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>地区分析</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet"/>
</head>
<body style="background-color: #333333">

<jsp:include page="head.jsp"></jsp:include>
<div id="myCarousel" class="carousel slide" style="background-color: #333333;">
    <!-- 画廊游标 -->
    <ol class="carousel-indicators">
        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
        <li data-target="#myCarousel" data-slide-to="1"></li>
        <li data-target="#myCarousel" data-slide-to="2"></li>
    </ol>

    <!-- 画廊图像 -->
    <div class="carousel-inner" style="background-color: #333333">
        <div class="item active" style="height: 100%;">
            <div id="area-count" style="width: 100%;height: 100%;"></div>
        </div>

        <div class="item" style="height: 100%;">
            <div id="area-salary" style="width: 100%;height: 100%;"></div>
        </div>

        <div class="item" style="background-color: #333333;height:auto">
            <div class="container">
                <div class="row">
                    <div class="text-center" style="margin-top: 4%;">
                        <input type="text" id="detail-city" class="form-control" style="display:inline;width:40%"
                               placeholder="请输入要查询的城市"/>&nbsp;
                        <input type="button" id="search" class="btn analysis-btn" value="查询" style="display:inline"/>
                    </div>
                </div>

                <div class="row">
                    <div class="divider"></div>
                </div>


                <div class="row">
                    <div class="col-md-offset-3 col-md-6">
                        <div id="preview" class="analysis">

                        </div>
                    </div>
                </div>

                <div class="row" id="job-type-preview"></div>

                <div class="row">
                    <div class="col-md-5">
                        <div id="job-type-count" style="width: 100%;height: 100%;"></div>
                    </div>
                    <div class="col-md-7">
                        <div id="job-type-salary" style="width: 100%;height: 70%;"></div>
                    </div>
                </div>

                <div class="row" id="city-analysis-preview"></div>

                <div class="row">
                    <div class="col-md-5">
                        <div id="finance" style="width: 100%;height: 100%;"></div>
                    </div>

                    <div class="col-md-7">
                        <div id="industry" style="width: 100%;height:100%;"></div>
                    </div>
                </div>

                <div class="row" id="detail-job-preview"></div>

                <div class="row">
                    <div class="col-md-12">
                        <div id="detail-job" style="width: 100%;height: 100%;"></div>
                    </div>
                </div>

                <div class="row">
                    <div id="tip" class="col-md-12"></div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div id="detail-job-count" style="width: 100%;height: 100%;"></div>
                    </div>
                    <div class="col-md-6">
                        <div id="detail-job-salary" style="width: 100%;height: 100%;"></div>
                    </div>
                </div>


                <div class="row">
                    <div class="divider"></div>
                </div>
            </div>
        </div>
    </div>

    <!-- Controls -->
    <a class="left carousel-control" href="#myCarousel" data-slide="prev">
        <span class="glyphicon glyphicon-chevron-left"></span>
    </a>
    <a class="right carousel-control" href="#myCarousel" data-slide="next">
        <span class="glyphicon glyphicon-chevron-right"></span>
    </a>
</div>


<script src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/echarts.js"></script>
<script src="${pageContext.request.contextPath}/js/dark.js"></script>
<script src="${pageContext.request.contextPath}/js/option/area-option.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //区域名:数量 键值对
        var countData = [
            <c:forEach items="${areas}" var="area" varStatus="stat">
            <c:choose>
            <c:when test="${!stat.last}">
            {name: '${area.area}', value: ${area.count}},
            </c:when>
                <c:otherwise>{
                name: '${area.area}', value: ${area.count}
            }
            </c:otherwise>
            </c:choose>
            </c:forEach>
        ]

        //区域名:平均薪水 键值对
        var salaryData = [
            <c:forEach items="${areas}" var="area" varStatus="stat">
            <c:choose>
            <c:when test="${!stat.last}">
            {name: '${area.area}', value: ${area.avgSalary}},
            </c:when>
                <c:otherwise>{
                name: '${area.area}', value: ${area.avgSalary}
            }
            </c:otherwise>
            </c:choose>
            </c:forEach>
        ]


        var convertData = function (data) {
            var res = [];
            for (var i = 0; i < data.length; i++) {
                var geoCoord = geoCoordMap[data[i].name];
                if (geoCoord) {
                    res.push({
                        name: data[i].name,
                        value: geoCoord.concat(data[i].value)
                    });
                }
            }
            return res;
        };

        countOption = {
            title: {
                text: '全国就业需求分布散点图',
                subtext: 'data from Job Sniffer',
                sublink: '${pageContext.request.contextPath}',
                left: 'center',
                top: '7%',
                textStyle: {
                    fontSize: '20',
                    color: '#fff',
                    fontFamily: 'monospace'
                }
            },
            tooltip: {
                trigger: 'item',
                formatter: function (params, ticket, callback) {
                    var str = params.value.toString().split(',');
                    return params.seriesName + "<br/> " + params.name + ": " + str[2] + "份";
                }
            },
            legend: {
                orient: 'vertical',
                y: '90%',
                x: '77%',
                data: ['需求量'],
                textStyle: {
                    color: '#fff'
                }
            },
            geo: {
                map: 'china',
            },
            series: [
                {
                    name: '需求量',
                    type: 'scatter',
                    coordinateSystem: 'geo',
                    data: convertData(countData),
                    symbolSize: function (val) {
                        return val[2] < 4500 ? 5 : val[2] / 900;
                    },
                    label: {
                        normal: {
                            formatter: '{b}',
                            position: 'right',
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#ddb926'
                        }
                    }
                },
                {
                    name: 'Top 5',
                    type: 'effectScatter',
                    coordinateSystem: 'geo',
                    data: convertData(countData.sort(function (a, b) {
                        return b.value - a.value;
                    }).slice(0, 5)),
                    symbolSize: function (val) {
                        return val[2] / 800;
                    },
                    showEffectOn: 'render',
                    rippleEffect: {
                        brushType: 'stroke'
                    },
                    hoverAnimation: true,
                    label: {
                        normal: {
                            formatter: '{b}',
                            position: 'right',
                            show: true
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#f4e925',
                            shadowBlur: 10,
                            shadowColor: '#333'
                        }
                    },
                    zlevel: 1
                }
            ]
        };


        salaryOption = {
            title: {
                text: '全国平均薪水分布',
                subtext: 'data from Job Sniffer',
                sublink: '${pageContext.request.contextPath}',
                left: 'center',
                top: '7%',
                textStyle: {
                    fontSize: '20',
                    color: '#fff',
                    fontFamily: 'monospace'
                }
            },
            tooltip: {
                trigger: 'item',
                formatter: function (params) {
                    return params.name + ' : ' + params.value[2] + " k/月";
                }
            },
            legend: {
                orient: 'vertical',
                y: '90%',
                x: '77%',
                data: ['平均薪水'],
                textStyle: {
                    color: '#fff'
                }
            },
            dataRange: {
                y: '64%',
                x: '17%',
                min: 0,
                max: 20,
                calculable: true,
                color: ['#d94e5d', '#eac736', '#50a3ba'],
                textStyle: {
                    color: '#fff'
                }
            },
            geo: {
                map: 'china',
            },
            series: [
                {
                    name: '平均薪水',
                    type: 'scatter',
                    coordinateSystem: 'geo',
                    data: convertData(salaryData),
                    symbolSize: 12,
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: false
                        }
                    },
                    itemStyle: {
                        emphasis: {
                            borderColor: '#fff',
                            borderWidth: 1
                        }
                    }
                }
            ]
        }

        countChart = undefined;
        salaryChart = undefined;
        jobTypeCountChart = undefined;
        jobTypeSalaryChart = undefined;
        financeChart = undefined;
        detailJobChart = undefined;
        detailJobCountChart = undefined;
        detailJobSalaryChart = undefined;

// 基于准备好的dom，初始化echarts实例
        $.get('${pageContext.request.contextPath}/js/china.json', function (chinaJson) {
            echarts.registerMap('china', chinaJson);
            countChart = echarts.init(document.getElementById('area-count'), 'dark');
            countChart.setOption(countOption);
            salaryChart = echarts.init(document.getElementById('area-salary'), 'dark');
            salaryChart.setOption(salaryOption);

            /**
             * 初始化.必须进行resize否则echarts会出现大小问题
             */
            $("#myCarousel").carousel('pause');
            $("#myCarousel").carousel('next');
            salaryChart.resize();
            $("#myCarousel").carousel('next');
        });


        /**
         *   具体城市查询
         *   1.工作类别的需求（饼图）
         */

        jobTypeCountOption = {
            backgroundColor: '#333333',
            title: {
                text: '工作类型需求分布',
                left: 'left',
                top: 20,
                textStyle: {
                    color: '#ccc'
                }
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            visualMap: {
                show: false,
                min: 80,
                max: 600,
                inRange: {
                    colorLightness: [0, 1]
                }
            },
            series: [
                {
                    name: '需求数量',
                    type: 'pie',
                    radius: '55%',
                    center: ['50%', '50%'],
                    data: [],
                    roseType: 'angle',
                    label: {
                        normal: {
                            textStyle: {
                                color: 'rgba(255, 255, 255, 0.3)'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            lineStyle: {
                                color: 'rgba(255, 255, 255, 0.3)'
                            },
                            smooth: 0.2,
                            length: 10,
                            length2: 20
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#c23531',
                            shadowBlur: 200,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };


        jobTypeSalaryOption = {
            backgroundColor: '#F3F3F3',
            title: {
                text: '工作类型薪水分布',
                left: 'left',
                top: 20,
                textStyle: {
                    color: '#ccc'
                }
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data: ['平均薪水(单位:k/月)']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    data: []
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: '平均薪水(单位:k/月)',
                    type: 'bar',
                    data: []
                }
            ]
        };

        financeOption = {
            title: {
                text: '公司规模分布',
                left: 'center',
                textStyle: {
                    color: '#FFFFFF'
                }
            },
            tooltip: {
                show: true,
                trigger: 'axis',
                formatter: function (params) {
                    return "公司数: " + params[0].data;
                }
            },
            polar: [{    //极坐标
                indicator: [{'max': 1096, 'text': "成熟型"}, {'max': 1096, 'text': "成熟型"}, {
                    'max': 1096,
                    'text': "成熟型"
                }, {'max': 1096, 'text': "成熟型"}],
                radius: 100,
                startAngle: 120,
                axisLine: {            // 坐标轴线
                    show: true,        // 默认显示，属性show控制显示与否
                    lineStyle: {       // 属性lineStyle控制线条样式
                        color: '#3F3F3F',
                        width: 1,
                        type: 'dash'
                    }
                }
            }],

            series: [{         // 驱动图表生成的数据内容数组，数组中每一项为一个系列的选项及数据
                name: '投资轮',
                type: 'radar',
                data: [{
                    value: [294, 729, 1096, 224],
                    name: '公司数'
                }],
                itemStyle: {//图形样式，可设置图表内图形的默认样式和强调样式（悬浮时样式）：
                    normal: {
                        areaStyle: {
                            type: 'default'
                        }
                    }
                }
            }]
        };

        industryOption = {
            title: {
                text: '公司类型分布',
                left: 'center',
                textStyle: {
                    color: '#FFFFFF'
                }
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b}: {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                x: 'left',
                data: []
            },
            series: [
                {
                    name: '公司类型',
                    type: 'pie',
                    radius: ['50%', '70%'],
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            show: true,
                            textStyle: {
                                fontSize: '30',
                                fontWeight: 'bold'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    data: []
                }
            ]
        };

        detailJobOption = {
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b}: {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                x: 'left',
                data: []
            },
            series: [
                {
                    name: '岗位数量',
                    type: 'pie',
                    radius: ['56%', '70%'],
                    center: ['70%', '40%'],
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            show: true,
                            textStyle: {
                                fontSize: '30',
                                fontWeight: 'bold'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    data: []
                }
            ]
        };

        detailJobCountOption = {
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b}: {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                x: 'left',
                data:[]
            },
            series: [
                {
                    name:'工作数量',
                    type:'pie',
                    selectedMode: 'single',
                    radius: [0, '30%'],

                    label: {
                        normal: {
                            position: 'inner'
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    data:[]
                },
                {
                    name:'工作数量',
                    type:'pie',
                    radius: ['40%', '55%'],

                    data:[]
                }
            ]
        };


        detailJobSalaryOption = {
            title : {
                text: '某地区蒸发量和降水量',
            },
            tooltip : {
                trigger: 'axis'
            },
            toolbox: {
                show : true,
                feature : {
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    data : []
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'平均工资',
                    type:'bar',
                    data:[],
                    markPoint : {
                        data : []
                    },
                    markLine : {
                        data : [
                            {type : 'average', name : '平均值'}
                        ]
                    }
                }
            ]
        };



        $('#search').click(function () {
            $.get("${pageContext.request.contextPath}/area/detail/" + $('#detail-city').val(), function (data, status) {
                if (status) {
                    if (data) {
                        jobTypeCountChart = echarts.init(document.getElementById('job-type-count'), 'dark');
                        jobTypeSalaryChart = echarts.init(document.getElementById('job-type-salary'), 'dark');
                        //preview
                        $('#preview').html('<table class="table table-bordered"><thead><tr><th style="color: #FFFFFF;text-align: center">城市</th> <th style="color: #FFFFFF;text-align: center">需求数量(份)</th> <th style="color: #FFFFFF;text-align: center">平均薪水(k/月)</th> </tr> </thead> <tbody id="preview-body"> </tbody> </table>');
                        $('#preview-body').html('<tr><td class="text-center" style="background: rgb(111,30,27);color: #FFFFFF;">' + data.area + '</td><td class="text-center" style="background: rgb(174,47,43);color: #FFFFFF;">' + data.count + '</td><td class="text-center" style="background: #DD6B66;color: #FFFFFF;">' + data.avgSalary + '</td></tr>');
                        //标题渲染
                        $('#job-type-preview').html('<h1 class="analysis-h1">工作类型分析 <small>JOB TYPE ANALYSIS</small></h1> <hr/>');
                        //工作类型数量分布
                        var datas = new Array();
                        $.each(JSON.parse(data.jobTypeCount), function (name, val) {
                            datas.push({'value': val, 'name': name});
                        });
                        jobTypeCountOption.series[0].data = datas.sort(function (a, b) {
                            return a.value - b.value;
                        });
                        //工作类型薪水对比
                        $.each(JSON.parse(data.jobTypeSalary), function (name, val) {
                            jobTypeSalaryOption.xAxis[0].data.push(name);
                            jobTypeSalaryOption.series[0].data.push(val);
                        });
                        jobTypeCountChart.setOption(jobTypeCountOption);
                        jobTypeSalaryChart.setOption(jobTypeSalaryOption);

                        //标题渲染
                        $('#city-analysis-preview').html('<h1 class="analysis-h1">城市产业分析 <small>CITY ANALYSIS</small></h1> <hr/>');

                        //公司投资轮分布
                        financeChart = echarts.init(document.getElementById('finance'), 'dark');
                        var names = new Array();
                        datas = new Array();
                        var max = -1;
                        $.each(JSON.parse(data.financeStage), function (name, val) {
                            names.push(name);
                            datas.push(val);
                            if (max < val) {
                                max = val;
                            }
                        });
                        var indicators = new Array();
                        for (var i = 0; i < names.length; i++) {
                            indicators.push({'text': names[i], 'max': max});
                        }
                        financeOption.polar[0].indicator = indicators;
                        financeOption.series[0].data[0].value = datas;
                        financeChart.setOption(financeOption, true);

                        //公司类型分布
                        industryChart = echarts.init(document.getElementById('industry'), 'dark');
                        industryOption.legend.data = [];
                        industryOption.series[0].data = [];
                        $.each(JSON.parse(data.industryField), function (name, val) {
                            industryOption.legend.data.push(name);
                            industryOption.series[0].data.push({'value': val, 'name': name});
                        });
                        industryChart.setOption(industryOption);

                        //标题渲染
                        $('#detail-job-preview').html('<h1 class="analysis-h1">指定岗位分析 <small>DETAIL JOB ANALYSIS</small></h1> <hr/>');

                        //所有工作预览
                        detailJobChart = echarts.init(document.getElementById('detail-job'), 'dark');
                        $.each(JSON.parse(data.jobDetailCount), function (name, val) {
                            $.each(val, function (name, val) {
                                detailJobOption.legend.data.push(name);
                                detailJobOption.series[0].data.push({'value': val, 'name': name});
                            });
                        });
                        $('#tip').html('<p class="text-center" style="color: #ffffff;">tip:&nbsp;点击图中岗位可以查看具体岗位信息</p>');
                        detailJobChart.setOption(detailJobOption);
                        detailJobChart.on('click', function (params) {
                            detailJobCountChart = echarts.init(document.getElementById('detail-job-count'),'dark');
                            detailJobSalaryChart = echarts.init(document.getElementById('detail-job-salary'),'dark');

                            //工作数目
                            $.each(JSON.parse(data.jobDetailCount), function (name, val) {
                                var flag = false;
                                var data = undefined;
                                $.each(val, function (n1, v1) {
                                    data = new Array();
                                    $.each(val, function (n2, v2) {
                                        data.push({'value':v2,'name':n2});
                                        if (n2 == params.name)
                                            flag = true;
                                    });
                                    if (flag)
                                        return false;
                                });
                                detailJobCountOption.legend.data = [];
                                detailJobCountOption.series[0].data = [];
                                detailJobCountOption.series[1].data = [];
                                if(flag){
                                    var count = 0;
                                    for(var i in data){
                                        detailJobCountOption.legend.data.push(data[i].name);
                                        if(data[i].name == params.name){
                                            detailJobCountOption.series[1].data.push({'value':data[i].value,'name':data[i].name,'selected':true});
                                            detailJobCountOption.series[0].data.push(data[i]);
                                        }else{
                                            detailJobCountOption.series[1].data.push(data[i]);
                                            count += data[i].value;
                                        }
                                    }
                                    detailJobCountOption.series[0].data.push({'name':'其他','value':count});
                                    return false;
                                }
                            });

                            //平均薪水
                            $.each(JSON.parse(data.jobDetailSalary), function (name, val) {
                                var flag = false;
                                var data = undefined;
                                $.each(val, function (n1, v1) {
                                    data = new Array();
                                    $.each(val, function (n2, v2) {
                                        data.push({'value':v2,'name':n2});
                                        if (n2 == params.name)
                                            flag = true;
                                    });
                                    if (flag)
                                        return false;
                                });
                                detailJobSalaryOption.xAxis[0].data = [];
                                detailJobSalaryOption.series[0].data = [];
                                detailJobSalaryOption.series[0].markPoint.data = [];
                                if(flag){
                                    var max = data[0];
                                    var min = data[0];
                                    var maxIndex = 0;
                                    var minIndex = 0;
                                    for(var i in data){
                                        if(max < data[i].value){
                                            max = data[i].value;
                                            maxIndex = i;
                                        }
                                        if(min > data[i].value){
                                            min = data[i].value;
                                            minIndex = i;
                                        }
                                        detailJobSalaryOption.xAxis[0].data.push(data[i].name);
                                        detailJobSalaryOption.series[0].data.push(data[i].value);
                                    }
                                    detailJobSalaryOption.series[0].markPoint.data.push({'name' : '最高', value : max, xAxis: maxIndex, yAxis: Math.round(max)});
                                    detailJobSalaryOption.series[0].markPoint.data.push({'name' : '最低', value : min, xAxis: minIndex, yAxis: Math.round(min)});
                                    return false;
                                }
                            });
                            detailJobSalaryChart.setOption(detailJobSalaryOption);
                            detailJobCountChart.setOption(detailJobCountOption);
                        });
                    }
                } else {
                    alert('网络加载失败');
                }
            });
        });


    });
</script>
</body>
</html>
