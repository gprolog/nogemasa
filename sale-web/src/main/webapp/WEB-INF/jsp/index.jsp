<%--
  Created by IntelliJ IDEA.
  User: liuxh
  Date: 15-8-23
  Time: 下午12:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Twitter Bootstrap Tutorial - A responsive layout tutorial</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap/bootstrap-combined.min.css" rel="stylesheet">
    <style type="text/css">
        body {
            background-color: #CCC;
        }

        #content {
            background-color: #FFF;
            border-radius: 5px;
        }

        #content .main {
            padding: 20px;
        }

        #content .sidebar {
            padding: 10px;
        }

        #content p {
            line-height: 30px;
        }
    </style>
</head>
<body youdao="bind">
<div class="container">
    <h1>诺格曼莎</h1>

    <div class="navbar navbar-inverse">
        <div class="navbar-inner nav-collapse" style="height: auto;">
            <ul class="nav">
                <li class="active"><a href="${pageContext.request.contextPath}/index.html">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/salePage.html">销售页</a></li>
            </ul>
        </div>
    </div>
    <div id="content" class="row-fluid">
        <div class="span9 main">
            <h2>公司简介</h2>

            <p></p>
        </div>
        <div class="span3 sidebar">
            <h2>Sidebar</h2>
            <ul class="nav nav-tabs nav-stacked">
                <li><a href="${pageContext.request.contextPath}/salePage.html">销售页</a></li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>