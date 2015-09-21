<%@ page pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>qrcode</title>
    <script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="js/jquery.qrcode.min.js"></script>
</head>
<body>
<img src="image/qrcode_for_gh_590f61c1733c_344.jpg">
<%--<div id="cnCode"></div>--%>
<%--<script>--%>
<%--function toUtf8(str) {--%>
<%--var out, i, len, c;--%>
<%--out = "";--%>
<%--len = str.length;--%>
<%--for(i = 0; i < len; i++) {--%>
<%--c = str.charCodeAt(i);--%>
<%--if ((c >= 0x0001) && (c <= 0x007F)) {--%>
<%--out += str.charAt(i);--%>
<%--} else if (c > 0x07FF) {--%>
<%--out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));--%>
<%--out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));--%>
<%--out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));--%>
<%--} else {--%>
<%--out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));--%>
<%--out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));--%>
<%--}--%>
<%--}--%>
<%--return out;--%>
<%--}--%>
<%--$("#cnCode").qrcode({--%>
<%--render: "table", //table方式--%>
<%--width: 200, //宽度--%>
<%--height:200, //高度--%>
<%--text: toUtf8("诺格曼莎") //任意内容--%>
<%--});--%>
<%--</script>--%>
</body>
</html>
