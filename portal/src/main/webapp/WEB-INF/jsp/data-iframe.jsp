<%--
  Created by IntelliJ IDEA.
  User: johnny
  Date: 16/5/11
  Time: 下午11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="http://g.alicdn.com/bui/bui/1.1.21/css/bs3/dpl.css" rel="stylesheet">
    <link href="http://g.alicdn.com/bui/bui/1.1.21/css/bs3/bui.css" rel="stylesheet">
</head>
<body>

<div class="row text-center">
    <div class="row">
        <div class="doc-content">
            <form class="form-panel" action="post" style="background-color: #FFFFFF">
                <ul class="panel-content">
                    <li>
                            <span>
                <label>预订日期：</label><input type="text" class="calendar" id="start-time"
                                           style="width: 130px;"> <label>至</label>
                                           <input type="text" class="calendar" id="end-time"
                                                  style="width: 130px;"></span>
                        <input type="text" placeholder="手机号码" id="phone">&nbsp;&nbsp;
                        <button type="button" id="search" class="button button-primary">查询</button>
                        <button type="button" id="today" class="button button-success">今日预订</button>
                        <button type="button" id="add" class="button button-danger">添加订单</button>
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
<script src="http://g.alicdn.com/bui/seajs/2.3.0/sea.js"></script>
<script src="http://g.alicdn.com/bui/bui/1.1.10/config.js"></script>
<script src="http://g.alicdn.com/bui/bui/1.1.21/bui.js"></script>
<script>
    $(function () {
        var Calendar = BUI.Calendar
        var datepicker = new Calendar.DatePicker({
            trigger: '.calendar',
            showTime: true,
            autoRender: true
        });

        var Overlay = BUI.Overlay,
                Form = BUI.Form;

        var form = new Form.HForm({
            srcNode: '#form'
        }).render();

        $('#search').click(function () {
            $('#grid').html('');
            var Grid = BUI.Grid,
                    Data = BUI.Data;
            var Store = Data.Store,
                    columns = [
                        {title: '工作名', dataIndex: 'jobName', width: 100},
                        {title: '工作类型', dataIndex: 'jobType', width: 200},
                        {title: '工作城市', dataIndex: 'companyCity', width: 200},
                        {title: '公司名', dataIndex: 'companyName', width: 100},
                        {title: '工作经验(平均)', dataIndex: 'workYear', width: 200},
                        {title: '平均薪水', dataIndex: 'salary', width: 500},
                        {title: '最低学历', dataIndex: 'education', width: 500},
                        {title: '投资轮', dataIndex: 'financeStage', width: 500},
                        {title: '公司类型', dataIndex: 'industryField', width: 500},
                        {title: '来源时间', dataIndex: 'time', width: 500},
                        {title: '数据来源', dataIndex: 'origin', width: 500}
                    ];
            var store = new Store({
                        url: '${pageContext.request.contextPath}/data-report/load',
                        autoLoad: true,
                        pageSize: 100,
                        proxy: {
                            ajaxOptions: { //ajax的配置项，不要覆盖success,和error方法
                                traditional: true,
                                type: 'post'
                            }
                        },
                        params: {
                            startTime: $('#start-time').val(),
                            endTime: $('#end-time').val(),
                            phone: $('#phone').val()
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
                        plugins: [Grid.Plugins.CheckSelection, Grid.Plugins.ColumnResize]
                    });
            grid.render();
        });
    });
</script>
</body>
</html>
