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
        <div class="col-md-offset-3 col-md-6">
            <div id="preview" class="analysis">

            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
<script>
    $(function () {
        var availableTags = ["Java", "Python", "PHP", ".NET", "C/C++/C#", "VB", "Delphi", "Perl", "Ruby", "Hadoop", "Node.js", "数据挖掘", "自然语言处理", "搜索算法", "精准推荐", "全栈工程师", "Go", "ASP", "Shell", "后端开发其他", "Android", "IOS", "WP", "移动开发其他", "web前端", "Flash", "html5", "JavaScript", "U3D", "COCOS2D-X", "前端开发其他", "测试工程师", "自动化测试", "功能测试", "性能测试", "测试开发", "游戏测试", "白盒测试", "灰盒测试", "黑盒测试", "手机测试", "硬件测试", "测试经理", "测试其他", "运维工程师", "运维开发工程师", "网络工程师", "系统工程师", "IT支持", "IDC", "CDN", "F5", "病毒分析", "WEB安全", "网络安全", "系统安全", "运维经理", "运维其他", "MySQL", "SQLServer", "Oracle", "DB2", "MongoDB", "ETL", "Hive", "数据仓库", "DBA其他", "项目经理", "项目助理", "项目类其他", "嵌入式", "自动化", "单片机", "电路设计", "驱动开发", "系统集成", "FPGA开发", "DSP开发", "ARM开发", "PCB工艺", "模具设计", "热传导", "材料工程师", "精益工程师", "射频工程师", "硬件开发其他", "实施工程师", "售前工程师", "售后工程师", "BI工程师", "QA", "企业类其他"];
        $("#job-name").autocomplete({
            source: availableTags
        });


        $('#search').click(function () {
            $.get("${pageContext.request.contextPath}/area/detail/" + $('#detail-city').val(), function (data, status) {
                if (status) {
                    if (data) {
                        //preview
                        $('#preview').html('<table class="table table-bordered"><thead><tr><th style="color: #FFFFFF;text-align: center">城市</th> <th style="color: #FFFFFF;text-align: center">需求数量(份)</th> <th style="color: #FFFFFF;text-align: center">平均薪水(k/月)</th> </tr> </thead> <tbody id="preview-body"> </tbody> </table>');
                        $('#preview-body').html('<tr><td class="text-center" style="background: rgb(111,30,27);color: #FFFFFF;">' + data.area + '</td><td class="text-center" style="background: rgb(174,47,43);color: #FFFFFF;">' + data.count + '</td><td class="text-center" style="background: #DD6B66;color: #FFFFFF;">' + data.avgSalary + '</td></tr>');
                    }
                }
            });
        });

    });
</script>
</body>
</html>
