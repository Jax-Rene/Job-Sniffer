<%--
  Created by IntelliJ IDEA.
  User: johnny
  Date: 16/4/5
  Time: 下午12:11
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>关系分析</title>
    <meta name="generator" content="Bootply"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet"/>
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
            <input type="text" id="search-number" class="form-control popover-show" placeholder="请输入要加载的数据量" title="提示"
                   data-container="body" data-toggle="popover" data-placement="top" data-content="总数据量:${count}条"/>
        </div>
        <div class="col-md-1">
            <input type="button" id="load" class="btn analysis-btn" value="加载"/>
        </div>
    </div>

    <div class="row">
        <div class="divider"></div>
        <div class="divider"></div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div id="whole-relation" style="width: 100%;height: 100%;"></div>
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
    </div
</div>

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/echarts.js"></script>
<script src="${pageContext.request.contextPath}/js/dark.js"></script>
<script src="${pageContext.request.contextPath}/js/base.js"></script>
<script>
    $(document).ready(function () {
        $('.popover-show').bind({
            click: function () {
                $('.popover-show').popover('show');

            },
            blur: function () {
                $('.popover-show').popover('destroy');
            }
        });


        var wholeRelationChart = undefined;
        var data = [];
        var schema = [
            {name: 'salary', index: 0, text: '薪水'},
            {name: 'education', index: 1, text: '学历'},
            {name: 'finance', index: 2, text: '公司规模'},
            {name: 'workYear', index: 3, text: '工作经验'},
        ];
        var lineStyle = {
            normal: {
                width: 1,
                opacity: 0.5
            }
        };
        wholeRelationOption = {
            title: {
                text: '总体关系平行图',
                x: 'center'
            },
            backgroundColor: '#333',
            parallelAxis: [
                {dim: 0, name: schema[0].text},
                {
                    dim: 1, name: schema[1].text,
                    type: 'category', data: ['大专', '本科', '硕士', '博士', '学历不限']
                },
                {
                    dim: 2,
                    name: schema[2].text,
                    type: 'category',
                    data: ['成长型(A轮)', '成熟型(不需要融资)', '初创型(未融资)', '上市公司', '成熟型(C轮)', '成熟型(D轮及以上)', '初创型(天使轮)', '成长型(B轮)', '初创型(不需要融资)', '成长型(不需要融资)']
                },
                {dim: 3, name: schema[3].text}
            ],
            visualMap: {
                show: false,
                min: 0,
                max: 50,
                dimension: 0,
                inRange: {
                    color: ['#d94e5d', '#eac736', '#50a3ba'].reverse(),
                    // colorAlpha: [0, 1]
                }
            },
            parallel: {
                left: '1%',
                right: '3%',
                bottom: 100,
                parallelAxisDefault: {
                    type: 'value',
                    name: 'AQI指数',
                    nameLocation: 'end',
                    nameGap: 20,
                    nameTextStyle: {
                        color: '#fff',
                        fontSize: 12
                    },
                    axisLine: {
                        lineStyle: {
                            color: '#aaa'
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
                }
            },
            series: [
                {
                    name: '总体分析',
                    type: 'parallel',
                    lineStyle: lineStyle,
                    data: data
                }
            ]
        };


        $('#load').click(function () {
            if ($('#search-number').val() > ${count}) {
                $('.modal-body').html('<h4>加载条数不能大于总条数!</h4>');
                $('#myModal').modal({
                    keyboard: true
                });
            } else {
                $.get('${pageContext.request.contextPath}/relation/whole/' + $('#search-number').val(), function (val, status) {
                    if (status) {
                        if (val) {
                            wholeRelationOption.series[0].data = datasPushObjsArray(val,['salary','education','finance','workYear']);
                            wholeRelationChart = echarts.init(document.getElementById('whole-relation'), 'dark');
                            wholeRelationChart.setOption(wholeRelationOption);
                        } else {
                            $('.modal-body').html('<h4>很抱歉,当前没有加载数据请稍后重试</h4>');
                            $('#myModal').modal({
                                keyboard: true
                            });
                        }
                    } else {
                        $('.modal-body').html('<h4>网络加载失败!</h4>');
                        $('#myModal').modal({
                            keyboard: true
                        });
                    }
                });
            }

        });


    });

</script>
</body>
</html>
