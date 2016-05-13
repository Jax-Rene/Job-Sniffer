<%--
  Created by IntelliJ IDEA.
  User: johnny
  Date: 16/5/11
  Time: 下午11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/dpl.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/bui.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
</head>
<body>

<div class="row text-center">
    <div class="row">
        <div class="doc-content">
            <form class="form-panel" action="post" style="background-color: #FFFFFF">
                <ul class="panel-content">
                    <li>
                        <label>工作名: </label><input type="text" class="text" id="job-name" name="jobName"/>&nbsp;&nbsp;
                        <label>工作类型: </label>
                        <div id="job-type" style="display: inline">
                            <input type="hidden" id="job-type-value" name="jobType" value="">
                        </div>&nbsp;&nbsp;
                        <label>城市: </label>
                        <div id="city" style="display: inline">
                            <input type="hidden" id="city-value" name="city" value="">
                        </div>&nbsp;&nbsp;
                        <label>学历: </label>
                        <div id="education" style="display: inline">
                            <input type="hidden" id="education-value" name="education" value="">
                        </div>&nbsp;&nbsp;
                        <label>投资轮: </label>
                        <div id="finance-stage" style="display: inline">
                            <input type="hidden" id="finance-stage-value" name="financeStage" value="">
                        </div>&nbsp;&nbsp;
                    </li>
                    <li>
                        <label>公司类型: </label>
                        <div id="industry-field" style="display: inline">
                            <input type="hidden" id="industry-field-value" name="industryField" value="">
                        </div>&nbsp;&nbsp;
                        <label>工作经验: </label><input type="text" class="text" name="workStart" id="work-start"/>&nbsp;-
                        <input type="text" class="text" name="workEnd" id="work-end"/>&nbsp;&nbsp;
                <label>分析日期：</label><input type="text" class="calendar" id="start-time"
                                           style="width: 130px;"> <label>至</label>
                                           <input type="text" class="calendar" id="end-time"
                                                  style="width: 130px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" id="search" class="button button-success analysis-btn">查询</button>
                    </li>
                </ul>
            </form>
        </div>
    </div>
</div>

<div class="row">
    <div>
        <div id="grid" style="padding-left: 5%;padding-right:5%;" class="message"></div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/sea.js"></script>
<script src="${pageContext.request.contextPath}/js/config.js"></script>
<script src="${pageContext.request.contextPath}/js/bui.js"></script>
<script>
    $(function () {
        var Calendar = BUI.Calendar
        var datepicker = new Calendar.DatePicker({
            trigger: '.calendar',
            autoRender: true
        });

        var Overlay = BUI.Overlay,
                Form = BUI.Form;

        var form = new Form.HForm({
            srcNode: '#form'
        }).render();


        var Select = BUI.Select
        var jobType = [
                    <c:forEach items="${jobTypeMap}" var="type" varStatus="stat">
                    <c:choose>
                    <c:when test="${!stat.last}">
                    {text: '${type.value}', value: '${type.key}'},
                    </c:when>
                    <c:otherwise>
                    {text: '${type.value}', value: '${type.key}'},
                    </c:otherwise>
                    </c:choose>
                    </c:forEach>
                ],
                select = new Select.Select({
                    render: '#job-type',
                    valueField: '#job-type-value',
                    items: jobType
                });
        select.render();
        var city = [
                    <c:forEach items="${city}" var="citys" varStatus="stat">
                    <c:choose>
                    <c:when test="${!stat.last}">
                    {text: '${citys}', value: '${citys}'},
                    </c:when>
                    <c:otherwise>
                    {text: '${citys}', value: '${citys}'},
                    </c:otherwise>
                    </c:choose>
                    </c:forEach>
                ],
                select = new Select.Select({
                    render: '#city',
                    valueField: '#city-value',
                    items: city
                });
        select.render();
        var education = [
                    <c:forEach items="${education}" var="education" varStatus="stat">
                    <c:choose>
                    <c:when test="${!stat.last}">
                    {text: '${education}', value: '${education}'},
                    </c:when>
                    <c:otherwise>
                    {text: '${education}', value: '${education}'},
                    </c:otherwise>
                    </c:choose>
                    </c:forEach>
                ],
                select = new Select.Select({
                    render: '#education',
                    valueField: '#education-value',
                    items: education
                });
        select.render();

        var financeStage = [
                    <c:forEach items="${financeStage}" var="finance" varStatus="stat">
                    <c:choose>
                    <c:when test="${!stat.last}">
                    {text: '${finance}', value: '${finance}'},
                    </c:when>
                    <c:otherwise>
                    {text: '${finance}', value: '${finance}'},
                    </c:otherwise>
                    </c:choose>
                    </c:forEach>
                ],
                select = new Select.Select({
                    render: '#finance-stage',
                    valueField: '#finance-stage-value',
                    items: financeStage
                });
        select.render();

        var  industryField = [
                    <c:forEach items="${industryField}" var="industry" varStatus="stat">
                    <c:choose>
                    <c:when test="${!stat.last}">
                    {text: '${industry}', value: '${industry}'},
                    </c:when>
                    <c:otherwise>
                    {text: '${industry}', value: '${industry}'},
                    </c:otherwise>
                    </c:choose>
                    </c:forEach>
                ],
                select = new Select.Select({
                    render: '#industry-field',
                    valueField: '#industry-field-value',
                    items: industryField
                });
        select.render();


        $('#search').click(function () {
            $('#grid').html('');
            var Grid = BUI.Grid,
                    Data = BUI.Data;
            var Store = Data.Store,
                    columns = [
                        {title: '工作名', dataIndex: 'jobName', width: 150},
                        {title: '工作类型', dataIndex: 'jobTypeName', width: 100},
                        {title: '工作城市', dataIndex: 'companyCity', width: 70},
                        {title: '公司名', dataIndex: 'companyName', width: 100},
                        {title: '工作经验', dataIndex: 'workYear', width: 70},
                        {title: '平均薪水', dataIndex: 'salary', width: 70},
                        {title: '最低学历', dataIndex: 'education', width: 100},
                        {title: '投资轮', dataIndex: 'financeStage', width: 70},
                        {title: '公司类型', dataIndex: 'industryField', width: 70},
                        {title: '来源时间', dataIndex: 'date', width: 70},
                        {title: '数据来源', dataIndex: 'origin', width: 100}
                    ];
            var store = new Store({
                        url: '${pageContext.request.contextPath}/data-report/load',
                        autoLoad: true,
                        pageSize: 10,
                        proxy: {
                            ajaxOptions: { //ajax的配置项，不要覆盖success,和error方法
                                traditional: true,
                                type: 'post'
                            }
                        },
                        params: {
                            jobName: $('#job-name').val(),
                            jobType:$('#job-type-value').val(),
                            city:$('#city-value').val(),
                            education:$('#education-value').val(),
                            workType:$('#work-type-value').val(),
                            financeStage:$('#finance-stage-value').val(),
                            industryField:$('#industry-field-value').val(),
                            workStart:$('#work-start').val(),
                            workEnd:$('#work-end').val(),
                            startTime:$('#start-time').val(),
                            endTime:$('#end-time').val()
                        },
                        root: 'records',
                        totalProperty: 'totalCount'
                    }),
                    grid = new Grid.Grid({
                        render: '#grid',
                        columns: columns,
                        loadMask: true,
                        store: store,
                        bbar: {
                            // pagingBar:表明包含分页栏
                            pagingBar: true
                        },
                        plugins: [Grid.Plugins.ColumnResize, Grid.Plugins.ColumnResize]
                    });
            grid.render();
        });
    });
</script>
</body>
</html>
