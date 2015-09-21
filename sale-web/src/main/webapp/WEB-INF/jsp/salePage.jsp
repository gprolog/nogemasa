<%--
  Created by IntelliJ IDEA.
  User: liuxh
  Date: 15-8-23
  Time: 下午12:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>诺格曼莎结账页面</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap/bootstrap-combined.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/nogemasa.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/nogemasa.js"></script>
    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/printSaleList.js"></script>--%>
    <script type="text/javascript">
        var _appctx = '${pageContext.request.contextPath}';
    </script>
</head>
<body youdao="bind">
<div class="container">
    <h1><a href="#" onclick="logout()">诺格曼莎</a></h1>
    <div id="content" class="row-fluid">
        <div class="span9 main">
            <div class="form-horizontal">
                <fieldset>
                    <div id="legend" class="">
                        <legend class="">结账</legend>
                    </div>
                    <div class="control-group">
                        <label class="control-label">导购</label>

                        <div class="controls">
                            <select id="employeeSelect" class="input-xlarge">
                            </select>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">会员号</label>

                        <div class="controls">
                            <input id="cardNo" type="text" placeholder="会员号" class="input-xlarge" onblur="getMemberInfo()">
                            <input id="memberName" type="text" placeholder="会员名称" class="input-xlarge">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">条形码</label>

                        <div class="controls">
                            <input id="sn" type="text" placeholder="条形码" class="input-xlarge">
                            <button class="btn btn-success" onclick="getGoodsInfo()">查询</button>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">购物</label>

                        <div class="controls">
                            <div class="textarea">
                                <textarea id="goodsList" style="margin: 0; width: 90%; height: 146px;"></textarea>
                                <%--<button class="btn btn-success" onclick="printGoodsList()">打印小票</button>--%>
                            </div>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">原价</label>

                        <div class="controls">
                            <input id="totalPrice" type="text" placeholder="0.00" class="input-xlarge">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">活动</label>

                        <div class="controls">
                            <select id="promotionSelect" class="input-xlarge" onchange="changeCost()">
                            </select>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">应收</label>

                        <div class="controls">
                            <input id="totalCost" type="text" placeholder="0.00" class="input-xlarge">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">收银方式</label>

                        <div class="controls">
                            <select id="costTypeSelect" class="input-xlarge">
                                <option value="1">现金</option>
                                <option value="2">刷卡</option>
                            </select>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">确认订单</label>

                        <div class="controls">
                            <button class="btn btn-success" onclick="saveSaleRecord();">保存</button>
                        </div>
                    </div>
                </fieldset>
            </div>
        </div>
    </div>
</div>
</body>
</html>