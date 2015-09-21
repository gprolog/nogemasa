<%--
  Created by IntelliJ IDEA.
  User: liuxh
  Date: 15-8-24
  Time: 下午5:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>打印测完</title>
    <meta name="description" content="">
    <meta name="keywords" content="">
    <link href="" rel="stylesheet">
    <style>
        #oDiv2 div{width: 100px;height: 100px;border:1px solid #c50000;}
    </style>
</head>
<body>
<div>aaa</div>
<div id='oDiv2'><div>bbb</div></div>
<div>ccc</div>
<input type="button" value="打印" id="js_print" />
<script>
    var oPrintBtn = document.getElementById("js_print");
    var oDiv2 = document.getElementById("oDiv2");
    oPrintBtn.onclick=function(){
        var oPop = window.open('','oPop');
        var str = '<!DOCTYPE html>';
        str += '<html>';
        str += '<head>';
        str += '<meta charset="utf-8">';
        str += '<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">';
        str+='<style>';
        str+='#oDiv2 div{width: 100px;height: 100px;border:1px solid #c50000;}';
        str+='</style>';
        str += '</head>';
        str += '<body>';
        str +="<div id='oDiv2'><div>bbb</div></div>";
        str += '</body>';
        str += '</html>';
        oPop.document.write(str);
        oPop.print();
        oPop.close();
    }
</script>
</body>
</html>
