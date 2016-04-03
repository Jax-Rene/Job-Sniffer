<%--
  Created by IntelliJ IDEA.
  User: johnny
  Date: 16/3/28
  Time: 下午7:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>具体工作维度分析</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/jquery-ui.min.css" rel="stylesheet">
</head>
<body style="background-color: #333333">
<div>
    <jsp:include page="head.jsp"></jsp:include>
</div>

<div class="container">
    <div class="row">
        <div class="divider"></div>
        <div class="divider"></div>
    </div>
    <div class="row">
        <div class="col-md-offset-3 col-md-5">
            <input type="text" id="job-name" class="form-control" placeholder="请输入要查询的职位"/>
        </div>
        <div class="col-md-1">
            <input type="button" id="search" class="btn analysis-btn" value="查询"/>
        </div>
    </div>
    <div class="row">
        <div class="divider"></div>
        <div class="col-md-offset-2 col-md-8">
            <div id="preview" class="analysis"></div>
        </div>
    </div>
    <div class="row">
        <div class="divider"></div>
        <div id="type-compare-preview"></div>
    </div>


    <div class="row">
        <div class="divider"></div>
        <div class="col-md-6">
            <div id="job-type-count" style="width: 100%;height: 100%;"></div>
        </div>
        <div class="col-md-6">
            <div id="job-type-salary" style="width: 100%;height: 100%;"></div>
        </div>
    </div>

    <div class="row">
        <div class="divider"></div>
        <div id="job-require-preview"></div>
    </div>

    <div class="row">
        <div class="divider"></div>
        <div class="col-md-6">
            <div id="education" style="width: 100%;height: 100%;"></div>
        </div>
        <div class="col-md-6">
            <div id="work-year" style="width: 100%;height: 100%;"></div>
        </div>
    </div>

    <div class="row">
        <div class="divider"></div>
        <div id="company-analysis-preview"></div>
    </div>

    <div class="row">
        <div class="divider"></div>
        <div class="col-md-6">
            <div id="industry" style="width: 100%;height: 100%;"></div>
        </div>
        <div class="col-md-6">
            <div id="finance" style="width: 100%;height: 100%;"></div>
        </div>
    </div>


    <div class="row">
        <div class="divider"></div>
        <div id="area-analysis-preview"></div>
    </div>

    <div class="row">
        <div class="divider"></div>
        <div class="col-md-12">
            <div id="area-count-salary" style="width: 100%;height: 100%;"></div>
        </div>
    </div>

    <div class="modal fade" id="myModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close"
                            data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        警告
                    </h4>
                </div>
                <div class="modal-body">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>


</div>
</div>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/echarts.js"></script>
<script src="${pageContext.request.contextPath}/js/dark.js"></script>
<script src="${pageContext.request.contextPath}/js/base.js"></script>
<script>
    $(document).ready(function () {
        var jobTypeCountChart = undefined;
        var jobTypeSalaryChart = undefined;
        var educationChart = undefined;
        var workYearChart = undefined;
        var areaCount = undefined;
        var areaSalary = undefined;
        var countSalaryChart = undefined;

        var jobTypeCountOption = {
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
                    name: '工作数量',
                    type: 'pie',
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
                    data: []
                },
                {
                    name: '工作数量',
                    type: 'pie',
                    radius: ['40%', '55%'],

                    data: []
                }
            ]
        };


        var jobTypeSalaryOption = {
            tooltip: {
                trigger: 'axis'
            },
            toolbox: {
                show: true,
                feature: {
                    dataView: {show: true, readOnly: false},
                    magicType: {show: true, type: ['line', 'bar']},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
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
                    name: '平均薪水',
                    type: 'bar',
                    data: [],
                    markPoint: {
                        data: []
                    },
                    markLine: {
                        data: [
                            {type: 'average', name: '平均值'}
                        ]
                    }
                }
            ]
        };

        educationOption = {
            title: {
                text: '学历分布',
                x: 'center'
            },
            legend: {
                orient: 'vertical',
                x: 'left',
                data: []
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            toolbox: {
                show: true,
                feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    magicType: {
                        show: true,
                        type: ['pie', 'funnel']
                    },
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            series: [
                {
                    name: '公司数目',
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
                    center: ['center', 300],
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    data: []
                }
            ]
        };

        workYearOption = {
            title: {
                text: '平均要求工龄分布',
                x: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: []
            },
            series: [
                {
                    name: '平均工龄对应数目',
                    type: 'pie',
                    radius: '55%',
                    center: ['center', 300],
                    data: [],
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'HSL(0, 0%, 20%)'
                        }
                    }
                }
            ]
        };

        industryOption = {
            title: {
                text: '工作产业分析',
                x: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: []
            },
            series: [
                {
                    name: '产业数量',
                    type: 'pie',
                    radius: '55%',
                    center: ['center', 300],
                    data: [],
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };

        financeOption = {
            title: {
                text: '公司投资轮分布',
                x: 'center'
            },
            legend: {
                orient: 'vertical',
                x: 'left',
                data: []
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            toolbox: {
                show: true,
                feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    magicType: {
                        show: true,
                        type: ['pie', 'funnel']
                    },
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            series: [
                {
                    name: '公司数目',
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
                    center: ['center', 300],
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    data: []
                }
            ]
        };


        //需求薪水比例城市分布
        var countSalaryDatas = [
            [1, 55, 1, "良"],
            [2, 25, 1, "优"]
        ];

        var schema = [
            {name: 'salary', index: 0, text: '平均薪水'},
            {name: 'count', index: 1, text: '需求量'},
        ];


        var itemStyle = {
            normal: {
                opacity: 0.8,
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowOffsetY: 0,
                shadowColor: '#EEE8AA'
            }
        };

        var countSalaryOption = {
            color: [
                '#dd4444', '#fec42c', '#80F1BE'
            ],
            legend: {
                y: 'top',
                data: ['城市'],
                textStyle: {
                    color: '#fff',
                    fontSize: 16
                }
            },
            grid: {
                x: '10%',
                x2: 150,
                y: '18%',
                y2: '10%'
            },
            tooltip: {
                padding: 10,
                backgroundColor: '#222',
                borderColor: '#777',
                borderWidth: 1,
                formatter: function (obj) {
                    var value = obj.value;
                    return '<div style="border-bottom: 1px solid rgba(255,255,255,.3); font-size: 18px;padding-bottom: 7px;margin-bottom: 7px">'
                            + '城市：' + value[3] + '<br>'
                            + '</div>'
                            + schema[0].text + '：' + value[0] + '<br>'
                            + schema[1].text + '：' + value[1] + '<br>'
                }
            },
            xAxis: {
                type: 'value',
                name: '需求量',
                nameGap: 16,
                nameTextStyle: {
                    color: '#fff',
                    fontSize: 14
                },
                max: 3000,
                splitLine: {
                    show: false
                },
                axisLine: {
                    lineStyle: {
                        color: '#777'
                    }
                },
                axisTick: {
                    lineStyle: {
                        color: '#777'
                    }
                },
                axisLabel: {
                    formatter: '{value}',
                    textStyle: {
                        color: '#fff'
                    }
                }
            },
            yAxis: {
                type: 'value',
                name: '平均薪水',
                nameLocation: 'end',
                nameGap: 20,
                max:30,
                nameTextStyle: {
                    color: '#fff',
                    fontSize: 16
                },
                axisLine: {
                    lineStyle: {
                        color: '#777'
                    }
                },
                axisTick: {
                    lineStyle: {
                        color: '#777'
                    }
                },
                splitLine: {
                    show: false
                },
                axisLabel: {
                    textStyle: {
                        color: '#fff'
                    }
                }
            },
            visualMap: [
                {
                    left: 'right',
                    top: '10%',
                    dimension: 2,
                    min: 0,
                    max: 250,
                    itemWidth: 30,
                    itemHeight: 120,
                    calculable: true,
                    precision: 0.1,
                    text: ['圆形大小：需求量与薪水比例'],
                    textGap: 30,
                    textStyle: {
                        color: '#fff'
                    },
                    inRange: {
                        symbolSize: [10, 70]
                    },
                    outOfRange: {
                        symbolSize: [10, 70],
                        color: ['rgba(255,255,255,.2)']
                    },
                    controller: {
                        inRange: {
                            color: ['#c23531']
                        },
                        outOfRange: {
                            color: ['#444']
                        }
                    }
                }
            ],
            series: [
                {
                    name: '城市',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: countSalaryDatas
                }
            ]
        };


        var availableTags = ["Java", "Python", "PHP", ".NET", "C/C++/C#", "VB", "Delphi", "Perl", "Ruby", "Hadoop", "Node.js", "数据挖掘", "自然语言处理", "搜索算法", "精准推荐", "全栈工程师", "Go", "ASP", "Shell", "后端开发其他", "Android", "IOS", "WP", "移动开发其他", "web前端", "Flash", "html5", "JavaScript", "U3D", "COCOS2D-X", "前端开发其他", "测试工程师", "自动化测试", "功能测试", "性能测试", "测试开发", "游戏测试", "白盒测试", "灰盒测试", "黑盒测试", "手机测试", "硬件测试", "测试经理", "测试其他", "运维工程师", "运维开发工程师", "网络工程师", "系统工程师", "IT支持", "IDC", "CDN", "F5", "病毒分析", "WEB安全", "网络安全", "系统安全", "运维经理", "运维其他", "MySQL", "SQLServer", "Oracle", "DB2", "MongoDB", "ETL", "Hive", "数据仓库", "DBA其他", "项目经理", "项目助理", "项目类其他", "嵌入式", "自动化", "单片机", "电路设计", "驱动开发", "系统集成", "FPGA开发", "DSP开发", "ARM开发", "PCB工艺", "模具设计", "热传导", "材料工程师", "精益工程师", "射频工程师", "硬件开发其他", "实施工程师", "售前工程师", "售后工程师", "BI工程师", "QA", "企业类其他"];
        $("#job-name").autocomplete({
            source: availableTags
        });

        $('#search').click(function () {
            $.post("${pageContext.request.contextPath}/job/detail", {
                job: $('#job-name').val()
            }, function (data, status) {
                if (status) {
                    debugger;
                    if (data) {
                        jobTypeCountChart = echarts.init(document.getElementById('job-type-count'), 'dark');
                        jobTypeSalaryChart = echarts.init(document.getElementById('job-type-salary'), 'dark');
                        educationChart = echarts.init(document.getElementById('education'), 'dark');
                        workYearChart = echarts.init(document.getElementById('work-year'), 'dark');
                        industryChart = echarts.init(document.getElementById('industry'), 'dark');
                        financeChart = echarts.init(document.getElementById('finance'), 'dark');
                        areaCountSalaryChart = echarts.init(document.getElementById('area-count-salary'), 'dark');

                        //previewa
                        $('#preview').html('<table class="table table-bordered"><thead><tr><th style="color: #FFFFFF;text-align: center">工作名</th> <th style="color: #FFFFFF;text-align: center">总数量(份)</th> <th style="color: #FFFFFF;text-align: center">平均薪水(k/月)</th> </tr> </thead> <tbody id="preview-body"> </tbody> </table>');
                        $('#preview-body').html('<tr><td class="text-center" style="background: rgb(111,30,27);color: #FFFFFF;">' + data.job.jobName + '</td><td class="text-center" style="background: rgb(174,47,43);color: #FFFFFF;">' + data.job.count + '</td><td class="text-center" style="background: #DD6B66;color: #FFFFFF;">' + data.job.avgSalary + '</td></tr>');

                        //同种工作类型对比
                        $('#type-compare-preview').html('<h1 class="analysis-h1">同种类型对比 <small>SAME TYPE COMPARE</small></h1> <hr/>')
                        //具体工作数目
                        var datas = datasPushMapArray(data.typeCount, 'name', 'value');
                        jobTypeCountOption.legend.data = [];
                        jobTypeCountOption.series[0].data = [];
                        jobTypeCountOption.series[1].data = [];
                        var count = 0;
                        for (var i = 0; i < datas.length; i++) {
                            jobTypeCountOption.legend.data.push(datas[i].name);
                            if (datas[i].name == data.job.jobName) {
                                jobTypeCountOption.series[1].data.push({
                                    'value': datas[i].value,
                                    'name': datas[i].name,
                                    'selected': true
                                });
                                jobTypeCountOption.series[0].data.push(datas[i]);
                            } else {
                                jobTypeCountOption.series[1].data.push(datas[i]);
                                count += datas[i].value;
                            }
                        }
                        jobTypeCountOption.series[0].data.push({'name': '其他', 'value': count});

                        //平均薪水
                        datas = datasPushMapArray(data.typeSalary, 'name', 'value');
                        jobTypeSalaryOption.xAxis[0].data = [];
                        jobTypeSalaryOption.series[0].data = [];
                        jobTypeSalaryOption.series[0].markPoint.data = [];
                        var names = getProperty(datas, 'name');
                        var values = getProperty(datas, 'value');
                        var maxAndIndex = findMax(values);
                        var minAndIndex = findMin(values);
                        jobTypeSalaryOption.xAxis[0].data = names;
                        jobTypeSalaryOption.series[0].data = values;
                        jobTypeSalaryOption.series[0].markPoint.data.push({
                            'name': '最高',
                            value: maxAndIndex[1],
                            xAxis: maxAndIndex[0],
                            yAxis: Math.round(maxAndIndex[1])
                        });
                        jobTypeSalaryOption.series[0].markPoint.data.push({
                            'name': '最低',
                            value: minAndIndex[1],
                            xAxis: minAndIndex[0],
                            yAxis: Math.round(minAndIndex[1])
                        });

                        //工作要求
                        $('#job-require-preview').html('<h1 class="analysis-h1">工作要求 <small>JOB REQUIRE</small></h1> <hr/>')
                        educationOption.series[0].data = datasPushMapArray(JSON.parse(data.job.education), 'name', 'value');
                        educationOption.legend.data = datasPushArray(JSON.parse(data.job.education), 0);
                        workYearOption.series[0].data = datasPushMapArray(JSON.parse(data.job.workYear), 'name', 'value');
                        workYearOption.legend.data = datasPushArray(JSON.parse(data.job.workYear), 0);

                        //公司分布
                        $('#company-analysis-preview').html('<h1 class="analysis-h1">公司分布 <small>COMPANY DISTRIBUTION</small></h1> <hr/>');
                        industryOption.series[0].data = datasPushMapArray(JSON.parse(data.job.industryField));
                        financeOption.legend.data = datasPushArray(JSON.parse(data.job.financeStage), 0);
                        financeOption.series[0].data = datasPushMapArray(JSON.parse(data.job.financeStage), 'name', 'value');

                        //特定工作的地区分布
                        $('#area-analysis-preview').html('<h1 class="analysis-h1">地区分布 <small>AREA DISTRIBUTION</small></h1> <hr/>');
                        datas = [];
                        $.each(JSON.parse(data.job.areaCount), function (k, v) {
                            var arr = new Array();
                            arr.push(v);
                            datas.push(arr);
                        });
                        var i = 0;
                        $.each(JSON.parse(data.job.areaSalary), function (k, v) {
                            datas[i].push(v, datas[i++][0] / v , k);
                        });
                        countSalaryOption.series[0].data = datas;
                        financeChart.setOption(financeOption);
                        industryChart.setOption(industryOption);
                        educationChart.setOption(educationOption);
                        workYearChart.setOption(workYearOption);
                        jobTypeSalaryChart.setOption(jobTypeSalaryOption);
                        jobTypeCountChart.setOption(jobTypeCountOption);
                        debugger;
                        areaCountSalaryChart.setOption(countSalaryOption);
                    }else{
                        $('.modal-body').html('<h4>很抱歉,目前尚没有该工作的分析数据!</h4>');
                        $('#myModal').modal({
                            keyboard: true
                        });
                    }
                }else{
                    $('.modal-body').html('<h4>网络加载失败!</h4>');
                    $('#myModal').modal({
                        keyboard: true
                    });
                }
            });
        });

        $(document).keydown(function (event) {
            if (event.keyCode == 13) {
                $('#search').click();
            }
        });

    });
</script>
</body>
</html>
