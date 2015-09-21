<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    request.setAttribute("ctx", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>诺格曼莎销售系统</title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" media="all"/>
    <link href='http://fonts.useso.com/css?family=Roboto:500,900italic,900,400italic,100,700italic,300,700,500italic,100italic,300italic,400'
          rel='stylesheet' type='text/css'>
    <link href='http://fonts.useso.com/css?family=Droid+Serif:400,700,400italic,700italic' rel='stylesheet'
          type='text/css'>
</head>
<body>
<div class="login">
    <h2>NOGEMASA</h2>

    <div class="login-top">
        <h1>登录表单</h1>

        <form action="${ctx}/login_check.html" method="post" id="login">
            <c:if test="${error}">
                <div class="tips"><span>登录失败！请重新登录！</span></div>
            </c:if>
            <input name="username" id="username" type="text" value="用户帐号" onfocus="this.value = '';"
                   onblur="if (this.value == '') {this.value = '用户帐号';}">
            <input name="password" type="password" value="password" onfocus="this.value = '';"
                   onblur="if (this.value == '') {this.value = 'password';}">

            <div class="forgot">
                <a href="#">忘记密码</a>
                <input type="submit" value="登录">
            </div>
        </form>
    </div>
    <div class="login-bottom">
        <h3>新用户 &nbsp;<a href="#">注册</a>&nbsp 这里</h3>
    </div>
    <c:if test="${deny}">
        <p>
            <font color="red">您没有权限访问！</font>
        </p>
    </c:if>
</div>
</body>
</html>
