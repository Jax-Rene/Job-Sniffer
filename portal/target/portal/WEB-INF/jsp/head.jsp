<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: johnny
  Date: 16/3/14
  Time: 上午10:49
  To change this template use File | Settings | File Templates.
--%>
<nav class="navbar navbar-custom-second navbar-inverse navbar-fixed-top" role="navigation">
    <div class="collapse navbar-collapse">
        <ul class="nav navbar-nav">
            <li><a href="${pageContext.request.contextPath}">HOME</a></li>
            <li class="dropdown active">
                <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
                    FUNCTION <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="${pageContext.request.contextPath}/area-index">地区纬度分析</a></li>
                    <li><a href="${pageContext.request.contextPath}/job-index">具体工作纬度分析</a></li>
                    <li><a href="${pageContext.request.contextPath}/relation-index">关系分析</a></li>
                    <li><a href="${pageContext.request.contextPath}/trend-index">趋势分析</a></li>
                </ul>
            </li>
            <li><a href="${pageContext.request.contextPath}#section3">SETTING</a></li>
            <li><a href="${pageContext.request.contextPath}#section4">CONTACT</a></li>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <li><a href="${pageContext.request.contextPath}/j_spring_security_logout_">LOGIN OUT</a></li>
            </sec:authorize>
        </ul>
    </div>
</nav>