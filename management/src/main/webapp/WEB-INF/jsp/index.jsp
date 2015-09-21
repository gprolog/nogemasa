<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>诺格曼莎管理后台</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/ext/resources/css/ext-all-neptune.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/desktop.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/notification.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/ext/ext-all-debug.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/ext/locale/ext-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/ext/ext-theme-neptune.js"></script>
    <%--<link rel="stylesheet"  href="${pageContext.request.contextPath}/ext/UploadDialog/css/Ext.ux.UploadDialog.css" />--%>
    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/ext/UploadDialog/Ext.ux.UploadDialog.js"></script>--%>
    <script type="text/javascript">
        var _appctx = '${pageContext.request.contextPath}';
        var username = '<sec:authentication property="name"/>';
        var storeSid = '<%=request.getAttribute("storeSid")%>';
        var storeName = '<%=request.getAttribute("storeName")%>';
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/mc.djs"></script>
</head>
<body>
<div id="poweredby">
    <div></div>
</div>
</body>
</html>