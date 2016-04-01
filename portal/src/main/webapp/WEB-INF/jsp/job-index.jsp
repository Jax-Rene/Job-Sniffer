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
        <div class="row" id="type-compare-preview"></div>


        <div class="row">
            <div class="divider"></div>
            <div class="col-md-6">
                <div id="job-type-count" style="width: 100%;height: 100%;"></div>
            </div>
            <div class="col-md-6">
                <div id="job-type-salary" style="width: 100%;height: 100%;"></div>
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
                    name: '平均工资',
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

        var availableTags = ["Java", "Python", "PHP", ".NET", "C/C++/C#", "VB", "Delphi", "Perl", "Ruby", "Hadoop", "Node.js", "数据挖掘", "自然语言处理", "搜索算法", "精准推荐", "全栈工程师", "Go", "ASP", "Shell", "后端开发其他", "Android", "IOS", "WP", "移动开发其他", "web前端", "Flash", "html5", "JavaScript", "U3D", "COCOS2D-X", "前端开发其他", "测试工程师", "自动化测试", "功能测试", "性能测试", "测试开发", "游戏测试", "白盒测试", "灰盒测试", "黑盒测试", "手机测试", "硬件测试", "测试经理", "测试其他", "运维工程师", "运维开发工程师", "网络工程师", "系统工程师", "IT支持", "IDC", "CDN", "F5", "病毒分析", "WEB安全", "网络安全", "系统安全", "运维经理", "运维其他", "MySQL", "SQLServer", "Oracle", "DB2", "MongoDB", "ETL", "Hive", "数据仓库", "DBA其他", "项目经理", "项目助理", "项目类其他", "嵌入式", "自动化", "单片机", "电路设计", "驱动开发", "系统集成", "FPGA开发", "DSP开发", "ARM开发", "PCB工艺", "模具设计", "热传导", "材料工程师", "精益工程师", "射频工程师", "硬件开发其他", "实施工程师", "售前工程师", "售后工程师", "BI工程师", "QA", "企业类其他"];
        $("#job-name").autocomplete({
            source: availableTags
        });

        $('#search').click(function () {
            jobTypeCountChart = echarts.init(document.getElementById('job-type-count'), 'dark');
            jobTypeSalaryChart = echarts.init(document.getElementById('job-type-salary'), 'dark');

            $.post("${pageContext.request.contextPath}/job/detail", {
                job: $('#job-name').val()
            }, function (data, status) {
                if (status) {
                    if (data) {
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
                        jobTypeSalaryChart.setOption(jobTypeSalaryOption);
                        jobTypeCountChart.setOption(jobTypeCountOption);
                    }
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
