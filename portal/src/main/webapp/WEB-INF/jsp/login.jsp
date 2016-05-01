<%--
  Created by IntelliJ IDEA.
  User: johnny
  Date: 16/3/4
  Time: 上午9:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>登录管理员后台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/form-elements.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login-style.css">
</head>
<body>

<div class="top-content">

    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <h1><strong>Admin</strong> Login</h1>
                    <div class="description">
                        <p>
                            In the age of big data,everyone can be god.
                        </p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="form-top">
                        <div class="form-top-left">
                            <h3>Login to be Administrator</h3>
                            <p>Enter your username and password to log on:</p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-lock"></i>
                        </div>
                    </div>
                    <div class="form-bottom">
                        <form role="form" action="${pageContext.request.contextPath}/spring_security_login"
                              method="post"
                              class="login-form" id="form">
                            <div class="form-group">
                                <label class="sr-only" for="form-username">Username</label>
                                <input type="text" placeholder="Username..."
                                       class="form-username form-control" id="form-username">
                                <input type="hidden" id="user-name" name="j_username"/>
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-password">Password</label>
                                <input type="password" placeholder="Password..."
                                       class="form-password form-control" id="form-password">
                                <input type="hidden" id="pass-word" name="j_password"/>
                            </div>
                            <button type="submit" class="btn" style="background-color: #DD6B66">Sign in
                            </button>
                            <div style="text-align: right;">
                                <input id="remember_me" name="_spring_security_remember_me" type="checkbox"/>
                                <label for="remember_me" style="display: inline;">Remember</label>
                            </div>
                            <p class="text-center" style="color: red;">${loginError}</p>
                        </form>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 social-login">
                    <h4>if you are not Administrator,please not try to login!</h4>
                </div>
            </div>
        </div>
    </div>

</div>

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.backstretch.js"></script>
<script src="${pageContext.request.contextPath}/js/md5.min.js"></script>

<script>
    $(function () {
        $.backstretch(["${pageContext.request.contextPath}/img/login-back/1.jpg",
            "${pageContext.request.contextPath}/img/login-back/2.jpg"], {duration: 4000, fade: 1000});
        $('#form').submit(function () {
                    $('#user-name').val(md5($('#form-username').val()));
                    $('#pass-word').val(md5($('#form-password').val()));
                }
        );
    });

</script>


</body>
</html>
