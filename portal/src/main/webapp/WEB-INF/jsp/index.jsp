<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: johnny
  Date: 16/3/7
  Time: 下午2:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>欢迎来到Job Sniffer</title>
    <meta name="generator" content="Bootply"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet">
</head>
<body>
<div id="wrap">
    <header class="masthead">
        <div id="myCarousel" class="carousel slide">
            <!-- 画廊游标 -->
            <ol class="carousel-indicators">
                <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                <li data-target="#myCarousel" data-slide-to="1"></li>
                <li data-target="#myCarousel" data-slide-to="2"></li>
            </ol>

            <!-- 画廊图像 -->
            <div class="carousel-inner">
                <div class="item active">
                    <img src="${pageContext.request.contextPath}/img/bg_1.jpg">
                    <div class="container">
                        <div class="carousel-caption">
                            <h2>Intelligent Analysis</h2>
                            <p class="title-child">通过各大就业网站的招聘信息实现智能的,可视化的分析</p>
                        </div>
                    </div>
                </div>
                <div class="item">
                    <img src="${pageContext.request.contextPath}/img/bg_2.png">
                    <div class="container">
                        <div class="carousel-caption">
                            <h2>Automated Analysis</h2>
                            <p>自动化更新数据,清晰展现变化趋势以及预测</p>
                        </div>
                    </div>
                </div>
                <div class="item">
                    <img src="${pageContext.request.contextPath}/img/bg_3.jpeg">
                    <div class="container">
                        <div class="carousel-caption">
                            <h2>Correlation Analysis</h2>
                            <p>充分分析各种就业因素之前的关联关系,方便调研</p>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 标题 -->
            <div class="logo">Job Sniffer</div>
            <!-- Controls -->
            <a class="left carousel-control" href="#myCarousel" data-slide="prev">
                <span class="glyphicon glyphicon-chevron-left"></span>
            </a>
            <a class="right carousel-control" href="#myCarousel" data-slide="next">
                <span class="glyphicon glyphicon-chevron-right"></span>
            </a>
        </div>
    </header>

    <div class="navbar navbar-custom navbar-inverse navbar-static-top" id="nav">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav nav-justified">
                    <li>
                        <a href="#section1">Home</a>
                    </li>
                    <li>
                        <a href="#section2">Function</a>
                    </li>
                    <li>
                        <a href="#section3">Setting</a>
                    </li>
                    <li>
                        <a href="#section4">Contact</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>

    <div id="section1"></div>
    <div class="container">
        <div class="col-sm-10 col-sm-offset-1">
            <div class="page-header text-center">
                <h1>Last Data</h1>
                <span style="color:#777">Last Updata Finished At 2015-10-10 10:12:13</span>
            </div>

            <div class="table-responsive">
                <table class="table" style="font-family: monospace;font-size: 18px">
                    <caption>综合统计如下:</caption>
                    <thead>
                    <tr>
                        <th>招聘网站</th>
                        <th>工作数量(单位:份)</th>
                        <th>平均薪水(单位:k)</th>
                        <th>具体岗位信息</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach items="${origins}" var="origin">
                        <tr>
                            <td>${origin.origin}</td>
                            <td>${origin.count}</td>
                            <td>${origin.salary}</td>
                            <td><a href="javascript:void(0)" id="${origin.id}">查看详情</a></td>

                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="divider"></div>

        </div>
    </div>


    <div class="divider"></div>

    <section class="bg-1">
        <div class="col-sm-6 col-sm-offset-3 text-center">
            <h1 style="padding:25px;background-color:rgba(5,5,5,.8)">Detail Analysis</h1>
        </div>
    </section>

    <div class="divider" id="section2"></div>

    <div class="bg-3">
        <div class="container">
            <div class="row" style="font-size:20px">
                <div class="col-sm-4 col-xs-6">

                    <div class="panel panel-default">
                        <div>
                            <a href="${pageContext.request.contextPath}/area-index" title="地区分析">
                                <img src="${pageContext.request.contextPath}/img/chengshi.jpg"
                                     class="img-responsive"/></a>
                        </div>
                        <div class="panel-body">
                            <p class="text-center">地区维度分析</p>
                            <p></p>

                        </div>
                    </div>
                    <!--/panel--> </div>
                <!--/col-->

                <div class="col-sm-4 col-xs-6">

                    <div class="panel panel-default">
                        <div>
                            <a href="${pageContext.request.contextPath}/job-index" title="工作分析">
                                <img src="${pageContext.request.contextPath}/img/gongzuo.jpg"
                                     class="img-responsive"></a>
                        </div>
                        <div class="panel-body">
                            <p class="text-center">具体工作维度分析</p>
                            <p></p>

                        </div>
                    </div>
                    <!--/panel--> </div>
                <!--/col-->

                <div class="col-sm-4 col-xs-6">

                    <div class="panel panel-default">
                        <div>
                            <a href="${pageContext.request.contextPath}/relation-index" title="关系分析">
                                <img src="${pageContext.request.contextPath}/img/guanxi.jpg" class="img-responsive"></a>
                        </div>
                        <div class="panel-body">
                            <p class="text-center">关系分析</p>
                            <p></p>

                        </div>
                    </div>
                    <!--/panel--> </div>
                <!--/col-->

                <div class="col-sm-4 col-xs-6">

                    <div class="panel panel-default">
                        <div>
                            <a href="#" title="趋势分析">
                                <img src="${pageContext.request.contextPath}/img/qushi.jpg" class="img-responsive"></a>
                        </div>
                        <div class="panel-body">
                            <p class="text-center">趋势分析</p>
                            <p></p>

                        </div>
                    </div>
                    <!--/panel--> </div>
                <!--/col-->

                <!--/col--> </div>
            <!--/row--> </div>
        <!--/container--> </div>
    <div class="divider"></div>


    <section class="bg-2">
        <div class="col-sm-6 col-sm-offset-3 text-center">
            <h1 style="padding:25px;background-color:rgba(5,5,5,.8)">System Setting</h1>
        </div>
    </section>


    <div class="divider" id="section3"></div>
    <div class="container">
        <div class="row col-md-12">
            <div class="col-md-4 col-md-offset-1">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">搜索的城市</h3>
                    </div>
                    <div class="panel-body">
                        上海,广州
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h3 class="panel-title">搜索的工作</h3>
                    </div>
                    <div class="panel-body">
                        Java,Ios
                    </div>
                </div>
            </div>

            <div class="col-md-2">
                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h3 class="panel-title">自动化频率</h3>
                    </div>
                    <div class="panel-body">
                        * * * * /10
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="divider"></div>
    <div class="divider" id="section4"></div>

    <div class="container">
        <div class="col-sm-10 col-sm-offset-1">
            <div class="page-header text-center">
                <h1>CONTACT</h1>
            </div>
        </div>

        <div class="col-sm-12">
            <div class="row form-group">
                <div class="col-xs-5 col-md-offset-1"><h2>Email Me</h2></div>
                <div class="col-xs-5 col-md-offset-1"><h2>Subscribe Me</h2></div>
            </div>

            <div class="row form-group">
                <div class="col-md-5 col-md-offset-1">
                    <input type="text" class="form-control" id="subject" placeholder="Your Subject"></div>
                <div class="col-md-5 col-md-offset-1">
                    <input type="email" class="form-control" id="email" placeholder="Your Email"/>
                </div>
            </div>

            <div class="row form-group">
                <div class="col-md-5 col-md-offset-1">
                    <textarea class="form-control" placeholder="Comments" id="contents"
                              style="height: 100px"></textarea>
                </div>

                <div class="col-md-5 col-md-offset-1">
                    <button id="subscribe" class="btn btn-success pull-left">Subscribe</button>
                </div>
            </div>

            <div class="row form-group">
                <div class="col-md-5 col-md-offset-1">
                    <button id="contact" class="btn btn-success text-center">Contact Me</button>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<!--/row-->

<div class="container">
    <div class="col-sm-8 col-sm-offset-2 text-center">

        <ul class="list-inline center-block">
            <li>
                <a href="#">
                    <img src="${pageContext.request.contextPath}/img/icon/linkedin.svg.png" width="25px"></a>
            </li>
            <li>
                <a href="#">
                    <img src="${pageContext.request.contextPath}/img/icon/GitHub.svg.png" width="25px"></a>
            </li>
            <li>
                <a href="#">
                    <img src="${pageContext.request.contextPath}/img/icon/sina weibo.svg.png" width="25px"></a>
            </li>
        </ul>

    </div>
    <!--/col--> </div>
<!--/wrap-->

<div id="footer">
    <div class="container">
        <p class="text-muted">Copyright @2016 Johnny-Zhuang FZU </p>
    </div>
</div>

<ul class="nav pull-right scroll-top">
    <li>
        <a href="#" title="Scroll to top"> <i class="glyphicon glyphicon-chevron-up"></i>
        </a>
    </li>
</ul>


<!-- 查看具体网站统计信息 -->
<div class="modal fade" id="total-detail" tabindex="-1" role="dialog"
     aria-labelledby="totalDetailLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="totalDetailLabel">
                    具体网站统计信息
                </h4>
            </div>
            <div class="panel-group" id="job-count"></div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">关闭
                </button>
            </div>
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
<script src="${pageContext.request.contextPath}/js/scripts.js"></script>


<script type="text/javascript">
    //轮播
    $("#myCarousel").carousel('cycle');

    $('#contact').click(function () {
        parent.location.href = 'Mailto:johnny.zhuang@foxmail.com?Subject=' + $('#subject').val() + "&Body=" + $('#contents').val();
    });

    $('td > a').click(function () {
        $.get("${pageContext.request.contextPath}/load-origin?id=" + this.id, function (data, status) {
            if (status) {
                $('#job-count').html('');
                $.each(data, function (name, value) {
                    var id = guid();
                    $('#job-count').append("<div class='panel-heading'> <h4 class='panel-title'> <a data-toggle='collapse' data-parent='#job-count' href='#" + id + "'>" + name + "<span class='badge'>" + value[0] + "</span></a> </h4> </div> <div id='" + id + "' class='panel-collapse collapse'> <div class='panel-body'></div></div>");

                    $.each(value[1], function (name, value) {
                        $('#' + id + ' > .panel-body').append(name + "&nbsp;<span class='badge'>" + value + "</span><br/><hr/>");
                    });
                });
                $('#total-detail').modal('toggle');
            }else{
                $('.modal-body').html('<h4>网络加载失败!</h4>');
                $('#myModal').modal({
                    keyboard: true
                });
            }
        });
    });


    function guid() {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
            var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
            return v.toString(16);
        });
    }
</script>
</body>
</html>
