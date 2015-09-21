<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>我的信息</title>
    <link href="${pageContext.request.contextPath}/css/sale_record.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript">
        var _appctx = '${pageContext.request.contextPath}';
        $(function () {
            $("#barCodeImg").attr('src',
                    _appctx + "/service/bar-code/image/" + '<%=request.getAttribute("cardNo")%>' + ".png");
        });
    </script>
</head>
<body>
<div style="text-align:center; width:100%; height:30%;">
    <img id="barCodeImg" src="" style="vertical-align:middle;width: 90%;" />
</div>
</body>
</html>