<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    request.setAttribute("ctx", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>诺格曼莎管理台</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/css/login.css" />
</head>
<body>
<div class="admlogbox">
    <div class="systitle">管理台</div>
    <div class="admlogmain">
        <div class="linklogo"></div>
        <form action="${ctx}/login_check.html" method="post" id="login">
            <ul>
                <c:if test="${error}">
                    <li class="tips"><span>登录失败！请重新登录！</span></li>
                </c:if>
                <li><label>用户名：</label>

                    <div class="coll">
                        <input type="text" name="username" id="username">
                    </div>
                </li>
                <li><label>密&nbsp;&nbsp;码：</label>

                    <div class="coll">
                        <input type="password" name="password">
                    </div>
                </li>
                <li><label></label>

                    <div class="coll">
                        <input class="admlog_btn" type="submit" value="登&nbsp;录">
                    </div>
                </li>
            </ul>
        </form>
        <p>版权所有</p>
        <c:if test="${deny}">
            <p>
                <font color="red">您没有权限访问！</font>
            </p>
        </c:if>
    </div>
</div>
</body>
</html>
