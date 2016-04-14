<%--
  Created by IntelliJ IDEA.
  User: johnny
  Date: 16/4/12
  Time: 下午8:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>管理员后台</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet"/>
</head>
<body>
<jsp:include page="head.jsp"></jsp:include>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="divider"></div>
            <div class="divider"></div>
        </div>
    </div>
    <div class="row text-center">
        <span class="admin-h1">Visualization Setting</span>
    </div>
    <hr/>
    <div class="col-md-offset-1 col-md-10">
        <h1 class="admin-title">城市配置
            <small>City Setting</small>
        </h1>
    </div>
    <div class="col-md-offset-1 col-md-10">
        <textarea class="input-lg admin-text" id="area"></textarea>
        <input type="button" id="area-btn" class="btn admin-btn" value="保存"/>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="divider"></div>
        </div>
    </div>

    <div class="col-md-offset-1 col-md-10">
        <h1 class="admin-title">公司类型配置
            <small>Company Type Setting</small>
        </h1>
    </div>
    <div class="col-md-offset-1 col-md-10">
        <textarea class="input-lg admin-text" id="company-type"></textarea>
        <input type="button" id="company-type-btn" class="btn admin-btn" value="保存"/>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="divider"></div>
        </div>
    </div>

    <div class="col-md-offset-1 col-md-10">
        <h1 class="admin-title">公司规模配置
            <small>Finance Stage Setting</small>
        </h1>
    </div>
    <div class="col-md-offset-1 col-md-10">
        <textarea class="input-lg admin-text" id="finance-stage"></textarea>
        <input type="button" id="finance-stage-btn" class="btn admin-btn" value="保存"/>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="divider"></div>
        </div>
    </div>

    <div class="col-md-offset-1 col-md-10">
        <h1 class="admin-title">学历配置
            <small>Education Setting</small>
        </h1>
    </div>
    <div class="col-md-offset-1 col-md-10">
        <textarea class="input-lg admin-text" id="education"></textarea>
        <input type="button" id="education-btn" class="btn admin-btn" value="保存"/>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="divider"></div>
        </div>
    </div>

    <div class="col-md-offset-1 col-md-10">
        <h1 class="admin-title">执行周期
            <small>Execution Times</small>
        </h1>
    </div>
    <div class="col-md-offset-1 col-md-10">
        <textarea class="input-lg admin-text" id="execution-times"></textarea>
        <input type="button" id="execution-times-btn" class="btn admin-btn" value="保存"/>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="divider"></div>
        </div>
    </div>

    <div class="col-md-offset-1 col-md-10">
        <h1 class="admin-title">执行
            <small>Execution</small>
        </h1>
    </div>
    <div class="col-md-offset-1 col-md-10">
        <div class="btn-group" data-toggle="buttons" style="width: 100%;">
            <label class="btn btn-success" style="width: 70%;">
                <input type="radio" name="options" id="grep">数据爬取
            </label>
            <label class="btn btn-danger" style="width: 30%;">
                <input type="radio" name="options" id="analysis">数据分析
            </label>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="divider"></div>
        </div>
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

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script>
    $(function () {
        $.get('${pageContext.request.contextPath}/load-properties', function (data, status) {
            if (status) {
                if (data) {
                    debugger;
                    $('#area').text(data.area);
                } else {
                    $('.modal-body').html('<h4>加载配置数据失败!</h4>');
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
    });
</script>
</body>
</html>
