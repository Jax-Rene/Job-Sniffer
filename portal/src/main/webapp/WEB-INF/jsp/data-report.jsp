<%--
  Created by IntelliJ IDEA.
  User: johnny
  Date: 16/5/11
  Time: 下午8:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>数据报表</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet"/>


</head>
<body>
<jsp:include page="head.jsp"></jsp:include>

<div class="container">
    <div class="row">
        <div class="divider"></div>
    </div>

    <div class="row text-center">
        <h1 class="analysis-h1" style="color: #000000;font-size: 5rem;">数/据/报/表</h1>
    </div>

    <div class="row">
        <div class="divider"></div>
    </div>

    <div class="row">
        <iframe src="${pageContext.request.contextPath}/data-report-iframe" frameborder="0" width="100%" height="100%"></iframe>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
</body>
</html>
