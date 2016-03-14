<%--
  Created by IntelliJ IDEA.
  User: johnny
  Date: 16/3/4
  Time: 上午9:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录管理员后台</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login-admin">
    用户名:&nbsp;<input type="text" id="user-name" name="userName"/>
    密码:&nbsp;<input type="password" id="pass-word" name="passWord"/>
    <input type="button" value="登录"/>
</form>
</body>
</html>
