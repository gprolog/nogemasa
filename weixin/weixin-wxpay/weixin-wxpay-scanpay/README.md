# 微信支付-刷卡支付笔记
__微信SDK提供的快速上手指南，部分地方有出入。__
## 快速上手，使用SDK只需三步即可接入“被扫支付”
### 第一步，初始化SDK
```java
WXPay.initSDKConfiguration(
        //签名算法需要用到的秘钥
        "myrjDLLY4OJrsevfGJwXeu8qXWji8MHR",
        //公众账号ID，成功申请公众账号后获得
        "wx07606b9311cae097",
        //商户ID，成功申请微信支付功能之后通过官方发出的邮件获得
        "1259994001",
        //子商户ID，受理模式下必填；
        "",
        //HTTP证书在服务器中的路径，用来加载证书用
        "/home/liuxh/temp/apiclient_cert.p12",
        //HTTP证书的密码，默认等于MCHID
        "1259994001"
);
```

### 第二步，准备好提交给API的数据(scanPayReqData)
```java
//1）创建一个可以用来生成数据的bridge，这里用的是一个专门用来测试用的Bridge，商户需要自己实现
IBridge bridge = new BridgeForScanPayBusiness();
//2）从bridge里面拿到数据，构建提交被扫支付API需要的数据对象
ScanPayReqData scanPayReqData = new ScanPayReqData(
        bridge.getAuthCode(),// 这个是扫码终端设备从用户手机上扫取到的支付授权号，有效期是1分钟
        bridge.getBody(),// 要支付的商品的描述信息，用户会在支付成功页面里看到这个信息
        bridge.getAttach(),// 支付订单里面可以填的附加数据，API会将提交的这个附加数据原样返回
        bridge.getOutTradeNo(),// 商户系统内部的订单号,32个字符内可包含字母, 确保在商户系统唯一
        bridge.getTotalFee(),// 订单总金额，单位为“分”，只能整数
        bridge.getDeviceInfo(),// 商户自己定义的扫码支付终端设备号，方便追溯这笔交易发生在哪台终端设备上
        bridge.getSpBillCreateIP(),// 订单生成的机器IP
        bridge.getTimeStart(),// 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010
        bridge.getTimeExpire(),// 订单失效时间，格式同上
        bridge.getGoodsTag()// 商品标记，微信平台配置的商品标记，用于优惠券或者满减使用
);
```

### 第三步，准备好一个用来处理各种结果分支的监听器(resultListener)
```java
//这个是Demo里面写的一个默认行为，需要根据自身需求来进行完善
ScanPayBusiness.ResultListener resultListener = new ScanPayBusinessResultListener();
```

### 第四步，执行业务逻辑
```java
WXPay.doScanPayBusiness(scanPayReqData,resultListener);
```

# 刷卡支付输入输出参数
## 输入参数
<table class="table">
    <thead>
        <tr>
            <th class="name">名称 </th>
            <th class="var">变量名</th>
            <th class="type">类型</th>
            <th class="require">必填</th>
            <th class="example">示例值</th>
            <th class="description">描述</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>公众账号ID</td>
            <td>appid</td>
            <td>String(32)</td>
            <td>是</td>
            <td>wx8888888888888888</td>
            <td>微信分配的公众账号ID</td>
        </tr>
        <tr>
            <td>子商户公众账号ID</td>
            <td>sub_appid</td>
            <td>String(32)</td>
            <td>否</td>
            <td>1900000109</td>
            <td>微信分配的子商户公众账号ID</td>
        </tr>
        <tr>
            <td>商户号</td>
            <td>mch_id</td>
            <td>String(32)</td>
            <td>是</td>
            <td>1900000109</td>
            <td>微信支付分配的商户号</td>
        </tr>
        <tr>
            <td>子商户号</td>
            <td>sub_mch_id</td>
            <td>String(32)</td>
            <td>是</td>
            <td>1900000109</td>
            <td>微信支付分配的子商户号，开发者模式下必填</td>
        </tr>
        <tr>
            <td>设备号</td>
            <td>device_info</td>
            <td>String(32)</td>
            <td>否</td>
            <td>013467007045764</td>
            <td>终端设备号(商户自定义，如门店编号)</td>
        </tr>
        <tr>
            <td>随机字符串</td>
            <td>nonce_str</td>
            <td>String(32)</td>
            <td>是</td>
            <td>5K8264ILTKCH16CQ2502SI8ZNMTM67VS</td>
            <td>随机字符串，不长于32位。推荐<a target="_blank" href="?chapter=4_3">随机数生成算法</a></td>
        </tr>
        <tr>
            <td>签名</td>
            <td>sign</td>
            <td>String(32)</td>
            <td>是</td>
            <td>C380BEC2BFD727A4B6845133519F3AD6</td>
            <td>签名，<a target="_blank" href="?chapter=4_3">详见签名生成算法</a></td>
        </tr>
        <tr>
            <td>商品描述</td>
            <td>body</td>
            <td>String(32)</td>
            <td>是</td>
            <td>Ipad&nbsp;mini&nbsp;&nbsp;16G&nbsp;&nbsp;白色</td>
            <td>商品或支付单简要描述</td>
        </tr>
        <tr>
            <td>商品详情</td>
            <td>detail</td>
            <td>String(8192)</td>
            <td>否</td>
            <td>Ipad&nbsp;mini&nbsp;&nbsp;16G&nbsp;&nbsp;白色</td>
            <td>商品名称明细列表</td>
        </tr>
        <tr>
            <td>附加数据</td>
            <td>attach</td>
            <td>String(127)</td>
            <td>否</td>
            <td>说明</td>
            <td>附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据</td>
        </tr>
        <tr>
            <td>商户订单号</td>
            <td>out_trade_no</td>
            <td>String(32)</td>
            <td>是</td>
            <td>1217752501201407033233368018</td>
            <td>商户系统内部的订单号,32个字符内、可包含字母,&nbsp;其他说明见<a target="_blank" href="?chapter=4_2">商户订单号</a></td>
        </tr>
        <tr>
            <td>总金额</td>
            <td>total_fee</td>
            <td>Int</td>
            <td>是</td>
            <td>888</td>
            <td>订单总金额，单位为分，只能为整数，详见<a target="_blank" href="?chapter=4_2">支付金额</a></td>
        </tr>
        <tr>
            <td>货币类型</td>
            <td>fee_type</td>
            <td>String(16)</td>
            <td>否</td>
            <td>CNY</td>
            <td>符合ISO&nbsp;4217标准的三位字母代码，默认人民币：CNY，其他值列表详见<a target="_blank" href="?chapter=4_3">货币类型</a></td>
        </tr>
        <tr>
            <td>终端IP</td>
            <td>spbill_create_ip</td>
            <td>String(16)</td>
            <td>是</td>
            <td>8.8.8.8</td>
            <td>调用微信支付API的机器IP&nbsp;</td>
        </tr>
        <tr>
            <td>商品标记</td>
            <td>goods_tag</td>
            <td>String(32)</td>
            <td>否</td>
            <td>&nbsp;</td>
            <td>商品标记，代金券或立减优惠功能的参数，说明详见<a target="_blank" href="?chapter=12_1">代金券或立减优惠</a></td>
        </tr>
        <tr>
            <td>指定支付方式</td>
            <td>limit_pay</td>
            <td>String(32)</td>
            <td>否</td>
            <td>no_credit</td>
            <td>no_credit--指定不能使用信用卡支付</td>
        </tr>
        <tr>
            <td>授权码</td>
            <td>auth_code</td>
            <td>String(128)</td>
            <td>是</td>
            <td>120061098828009406</td>
            <td>扫码支付授权码，设备读取用户微信中的条码或者二维码信息</td>
        </tr>
    </tbody>
</table>
## 返回结果
<table class="table">
    <thead>
    <tr>
        <th class="name">名称 </th>
        <th class="var">变量名</th>
        <th class="type">类型</th>
        <th class="require">必填</th>
        <th class="example">示例值</th>
        <th class="description">描述</th>
    </tr>
    </thead>
    <tbody>
        <tr>
        <td>返回状态码</td>
        <td>return_code</td>
        <td>是</td>
        <td>String(16)</td>
        <td>SUCCESS</td>
        <td>SUCCESS/FAIL <br> 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断</td>
        </tr>
        <tr>
        <td>返回信息</td>
        <td>return_msg</td>
        <td>否</td>
        <td>String(128)</td>
        <td>签名失败</td>
        <td>返回信息，如非空，为错误原因 <br>签名失败 <br>参数格式校验错误</td>
        </tr>
    </tbody>
</table>
## 当return_code为SUCCESS的时候，还会包括以下字段：
<table class="table">
    <thead>
        <tr>
            <th class="name">名称 </th>
            <th class="var">变量名</th>
            <th class="type">类型</th>
            <th class="require">必填</th>
            <th class="example">示例值</th>
            <th class="description">描述</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>公众账号ID</td>
            <td>appid</td>
            <td>是</td>
            <td>String(32)</td>
            <td>wx8888888888888888</td>
            <td>调用接口提交的公众账号ID</td>
        </tr>
        <tr>
            <td>子商户公众账号ID</td>
            <td>sub_appid</td>
            <td>是</td>
            <td>String(32)</td>
            <td>wx8888888888888888</td>
            <td>调用接口提交的子商户公众账号ID</td>
        </tr>
        <tr>
            <td>商户号</td>
            <td>mch_id</td>
            <td>是</td>
            <td>String(32)</td>
            <td>1900000109</td>
            <td>调用接口提交的商户号</td>
        </tr>
        <tr>
            <td>子商户号</td>
            <td>sub_mch_id</td>
            <td>是</td>
            <td>String(32)</td>
            <td>1900000109</td>
            <td>调用接口提交的子商户号</td>
        </tr>
        <tr>
            <td>设备号</td>
            <td>device_info</td>
            <td>否</td>
            <td>String(32)</td>
            <td>013467007045764</td>
            <td>调用接口提交的终端设备号，</td>
        </tr>
        <tr>
            <td>随机字符串</td>
            <td>nonce_str</td>
            <td>是</td>
            <td>String(32)</td>
            <td>5K8264ILTKCH16CQ2502SI8ZNMTM67VS</td>
            <td>微信返回的随机字符串</td>
        </tr>
        <tr>
            <td>签名</td>
            <td>sign</td>
            <td>是</td>
            <td>String(32)</td>
            <td>C380BEC2BFD727A4B6845133519F3AD6</td>
            <td>微信返回的签名，详见<a target="_blank" href="?chapter=4_3">签名生成算法</a></td>
        </tr>
        <tr>
            <td>业务结果</td>
            <td>result_code</td>
            <td>是</td>
            <td>String(16)</td>
            <td>SUCCESS</td>
            <td>SUCCESS/FAIL</td>
        </tr>
        <tr>
            <td>错误代码</td>
            <td>err_code</td>
            <td>否</td>
            <td>String(32)</td>
            <td>SYSTEMERROR</td>
            <td>详细参见错误列表</td>
        </tr>
        <tr>
            <td>错误代码描述</td>
            <td>err_code_des</td>
            <td>否</td>
            <td>String(128)</td>
            <td>系统错误</td>
            <td>错误返回的信息描述</td>
        </tr>
    </tbody>
</table>
## 当return_code 和result_code都为SUCCESS的时，还会包括以下字段：
<table class="table">
    <thead>
        <tr>
        <th class="name">名称 </th>
        <th class="var">变量名</th>
        <th class="type">类型</th>
        <th class="require">必填</th>
        <th class="example">示例值</th>
        <th class="description">描述</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>用户标识</td>
            <td>openid</td>
            <td>String(128)</td>
            <td>是</td>
            <td>Y</td>
            <td>用户在商户appid 下的唯一标识</td>
        </tr>
        <tr>
            <td>是否关注公众账号</td>
            <td>is_subscribe</td>
            <td>String(1)</td>
            <td>是</td>
            <td>Y</td>
            <td>用户是否关注公众账号，仅在公众账号类型支付有效，取值范围：Y或N;Y-关注;N-未关注</td>
        </tr>
        <tr>
            <td>用户子标识</td>
            <td>sub_openid</td>
            <td>String(128)</td>
            <td>否</td>
            <td>Y</td>
            <td>子商户appid下用户唯一标识，如需返回则请求时需要传sub_appid</td>
        </tr>
        <tr>
            <td>是否关注子公众账号</td>
            <td>sub_is_subscribe</td>
            <td>String(1)</td>
            <td>是</td>
            <td>Y</td>
            <td>用户是否关注子公众账号，仅在公众账号类型支付有效，取值范围：Y或N;Y-关注;N-未关注</td>
        </tr>
        <tr>
            <td>交易类型</td>
            <td>trade_type</td>
            <td>String(16)</td>
            <td>是</td>
            <td>MICROPAY</td>
            <td>支付类型为MICROPAY(即扫码支付)</td>
        </tr>
        <tr>
            <td>付款银行</td>
            <td>bank_type</td>
            <td>String(16)</td>
            <td>是</td>
            <td>CMC</td>
            <td>银行类型，采用字符串类型的银行标识，值列表详见<a target="_blank" href="?chapter=4_2">银行类型</a></td>
        </tr>
        <tr>
            <td>货币类型</td>
            <td>fee_type</td>
            <td>String(16)</td>
            <td>否</td>
            <td>CNY</td>
            <td>符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见<a target="_blank" href="?chapter=4_2">货币类型</a></td>
        </tr>
        <tr>
            <td>总金额</td>
            <td>total_fee</td>
            <td>Int</td>
            <td>是</td>
            <td>888</td>
            <td>订单总金额，单位为分，只能为整数，详见<a target="_blank" href="?chapter=4_2">支付金额</a></td>
        </tr>
        <tr>
            <td>现金支付货币类型</td>
            <td>cash_fee_type</td>
            <td>String(16)</td>
            <td>否</td>
            <td>CNY</td>
            <td>符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见<a target="_blank" href="?chapter=4_2">货币类型</a></td>
        </tr>
        <tr>
            <td>现金支付金额</td>
            <td>cash_fee</td>
            <td>Int</td>
            <td>是</td>
            <td>100</td>
            <td>订单现金支付金额，详见<a target="_blank" href="?chapter=4_2">支付金额</a></td>
        </tr>
        <tr>
            <td>代金券或立减优惠金额</td>
            <td>coupon_fee</td>
            <td>Int</td>
            <td>否</td>
            <td>100</td>
            <td>代金券或立减优惠金额&lt;=订单总金额，订单总金额-代金券或立减优惠金额=现金支付金额</td>
        </tr>
        <tr>
            <td>微信支付订单号</td>
            <td>transaction_id</td>
            <td>String(32)</td>
            <td>是</td>
            <td>1217752501201407033233368018</td>
            <td>微信支付订单号</td>
        </tr>
        <tr>
            <td>商户订单号</td>
            <td>out_trade_no</td>
            <td>String(32)</td>
            <td>是</td>
            <td>1217752501201407033233368018</td>
            <td>商户系统的订单号，与请求一致。</td>
        </tr>
        <tr>
            <td>商家数据包</td>
            <td>attach</td>
            <td>String(128)</td>
            <td>否</td>
            <td>123456</td>
            <td>商家数据包，原样返回</td>
        </tr>
        <tr>
            <td>支付完成时间</td>
            <td>time_end</td>
            <td>String(14)</td>
            <td>是</td>
            <td>20141030133525</td>
            <td>订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。详见<a target="_blank" href="?chapter=4_2">时间规则</a></td>
        </tr>
    </tbody>
</table>
## 返回错误码
<table class="table">
    <thead>
        <tr>
        <th width="100">名称</th>
        <th>描述</th>
        <th width="100">支付状态</th>
        <th>原因</th>
        <th>解决方案</th>
    </tr>
    </thead>
    <tbody>
        <tr>
            <td>SYSTEMERROR</td>
            <td>接口返回错误</td>
            <td>支付结果未知</td>
            <td>系统超时</td>
            <td>请立即调用被扫订单结果查询API，查询当前订单状态，并根据订单的状态决定下一步的操作。</td>
        </tr>
        <tr>
            <td>PARAM_ERROR</td>
            <td>参数错误</td>
            <td>支付确认失败</td>
            <td>请求参数未按指引进行填写</td>
            <td>请根据接口返回的详细信息检查您的程序</td>
        </tr>
        <tr>
            <td>ORDERPAID</td>
            <td>订单已支付</td>
            <td>支付确认失败</td>
            <td>订单号重复</td>
            <td>请确认该订单号是否重复支付，如果是新单，请使用新订单号提交</td>
        </tr>
        <tr>
            <td>NOAUTH</td>
            <td>商户无权限</td>
            <td>支付确认失败</td>
            <td>商户没有开通被扫支付权限</td>
            <td>请开通商户号权限。请联系产品或商务申请</td>
        </tr>
        <tr>
            <td>AUTHCODEEXPIRE</td>
            <td>二维码已过期，请用户在微信上刷新后再试</td>
            <td>支付确认失败</td>
            <td>用户的条码已经过期</td>
            <td>请收银员提示用户，请用户在微信上刷新条码，然后请收银员重新扫码。
            直接将错误展示给收银员</td>
        </tr>
        <tr>
            <td>NOTENOUGH</td>
            <td>余额不足</td>
            <td>支付确认失败</td>
            <td>用户的零钱余额不足</td>
            <td>请收银员提示用户更换当前支付的卡，然后请收银员重新扫码。建议：商户系统返回给收银台的提示为“用户余额不足.提示用户换卡支付”</td>
        </tr>
        <tr>
            <td>NOTSUPORTCARD</td>
            <td>不支持卡类型</td>
            <td>支付确认失败</td>
            <td>用户使用卡种不支持当前支付形式</td>
            <td>请用户重新选择卡种
            建议：商户系统返回给收银台的提示为“该卡不支持当前支付，提示用户换卡支付或绑新卡支付”</td>
        </tr>
        <tr>
            <td>ORDERCLOSED</td>
            <td>订单已关闭</td>
            <td>支付确认失败</td>
            <td>该订单已关</td>
            <td>商户订单号异常，请重新下单支付</td>
        </tr>
        <tr>
            <td>ORDERREVERSED</td>
            <td>订单已撤销</td>
            <td>支付确认失败</td>
            <td>当前订单已经被撤销</td>
            <td>当前订单状态为“订单已撤销”，请提示用户重新支付</td>
        </tr>
        <tr>
            <td>BANKERROR</td>
            <td>银行系统异常</td>
            <td>支付结果未知</td>
            <td>银行端超时</td>
            <td>请立即调用被扫订单结果查询API，查询当前订单的不同状态，决定下一步的操作。</td>
        </tr>
        <tr>
            <td>USERPAYING</td>
            <td>用户支付中，需要输入密码</td>
            <td>支付结果未知</td>
            <td>该笔交易因为业务规则要求，需要用户输入支付密码。</td>
            <td>等待5秒，然后调用被扫订单结果查询API，查询当前订单的不同状态，决定下一步的操作。</td>
        </tr>
        <tr>
            <td>AUTH_CODE_ERROR</td>
            <td>授权码参数错误</td>
            <td>支付确认失败</td>
            <td>请求参数未按指引进行填写</td>
            <td>每个二维码仅限使用一次，请刷新再试</td>
        </tr>
        <tr>
            <td>AUTH_CODE_INVALID</td>
            <td>授权码检验错误</td>
            <td>支付确认失败</td>
            <td>收银员扫描的不是微信支付的条码</td>
            <td>请扫描微信支付被扫条码/二维码</td>
        </tr>
        <tr>
            <td>XML_FORMAT_ERROR</td>
            <td>XML格式错误</td>
            <td>支付确认失败</td>
            <td>XML格式错误</td>
            <td>请检查XML参数格式是否正确</td>
        </tr>

        <tr>
            <td>REQUIRE_POST_METHOD</td>
            <td>请使用post方法</td>
            <td>支付确认失败</td>
            <td>未使用post传递参数</td>
            <td>请检查请求参数是否通过post方法提交</td>
        </tr>
        <tr>
            <td>SIGNERROR</td>
            <td>签名错误</td>
            <td>支付确认失败</td>
            <td>参数签名结果不正确</td>
            <td>请检查签名参数和方法是否都符合签名算法要求</td>
        </tr>
        <tr>
            <td>LACK_PARAMS</td>
            <td>缺少参数</td>
            <td>支付确认失败</td>
            <td>缺少必要的请求参数</td>
            <td>请检查参数是否齐全</td>
        </tr>
        <tr>
            <td>NOT_UTF8</td>
            <td>编码格式错误</td>
            <td>支付确认失败</td>
            <td>未使用指定编码格式</td>
            <td>请使用UTF-8编码格式</td>
        </tr>
        <tr>
            <td>BUYER_MISMATCH</td>
            <td>支付帐号错误</td>
            <td>支付确认失败</td>
            <td>暂不支持同一笔订单更换支付方</td>
            <td>请确认支付方是否相同</td>
        </tr>
        <tr>
            <td>APPID_NOT_EXIST</td>
            <td>APPID不存在</td>
            <td>支付确认失败</td>
            <td>参数中缺少APPID</td>
            <td>请检查APPID是否正确</td>
        </tr>
        <tr>
            <td>MCHID_NOT_EXIST</td>
            <td>MCHID不存在</td>
            <td>支付确认失败</td>
            <td>参数中缺少MCHID</td>
            <td>请检查MCHID是否正确</td>
        </tr>
        <tr>
            <td>OUT_TRADE_NO_USED</td>
            <td>商户订单号重复</td>
            <td>支付确认失败</td>
            <td>同一笔交易不能多次提交</td>
            <td>请核实商户订单号是否重复提交</td>
        </tr>
        <tr>
            <td>APPID_MCHID_NOT_MATCH</td>
            <td>appid和mch_id不匹配</td>
            <td>支付确认失败</td>
            <td>appid和mch_id不匹配</td>
            <td>请确认appid和mch_id是否匹配</td>
        </tr>
    </tbody>
</table>

## 银行类型
<table class="table">
    <tr><th>字符型银行编码</th><th>银行名称</th></tr>
    <tr><td>ICBC_DEBIT</td><td>工商银行（借记卡）</td></tr>
    <tr><td>ICBC_CREDIT</td><td>工商银行（信用卡）</td></tr>
    <tr><td>ABC_DEBIT</td><td>农业银行（借记卡）</td></tr>
    <tr><td>ABC_CREDIT</td><td>农业银行（信用卡）</td></tr>
    <tr><td>PSBC_DEBIT</td><td>邮政储蓄（借记卡）</td></tr>
    <tr><td>PSBC_CREDIT</td><td>邮政储蓄（信用卡）</td></tr>
    <tr><td>CCB_DEBIT</td><td>建设银行（借记卡）</td></tr>
    <tr><td>CCB_CREDIT</td><td>建设银行（信用卡）</td></tr>
    <tr><td>CMB_DEBIT</td><td>招商银行（借记卡）</td></tr>
    <tr><td>CMB_CREDIT</td><td>招商银行（信用卡）</td></tr>
    <tr><td>COMM_DEBIT</td><td>交通银行（借记卡）</td></tr>
    <tr><td>BOC_CREDIT</td><td>中国银行（信用卡）</td></tr>
    <tr><td>SPDB_DEBIT</td><td>浦发银行（借记卡）</td></tr>
    <tr><td>SPDB_CREDIT</td><td>浦发银行（信用卡）</td></tr>
    <tr><td>GDB_DEBIT</td><td>广发银行（借记卡）</td></tr>
    <tr><td>GDB_CREDIT</td><td>广发银行（信用卡）</td></tr>
    <tr><td>CMBC_DEBIT</td><td>民生银行（借记卡）</td></tr>
    <tr><td>CMBC_CREDIT</td><td>民生银行（信用卡）</td></tr>
    <tr><td>PAB_DEBIT</td><td>平安银行（借记卡）</td></tr>
    <tr><td>PAB_CREDIT</td><td>平安银行（信用卡）</td></tr>
    <tr><td>CEB_DEBIT</td><td>光大银行（借记卡）</td></tr>
    <tr><td>CEB_CREDIT</td><td>光大银行（信用卡）</td></tr>
    <tr><td>CIB_DEBIT</td><td>兴业银行（借记卡）</td></tr>
    <tr><td>CIB_CREDIT</td><td>兴业银行（信用卡）</td></tr>
    <tr><td>CITIC_DEBIT</td><td>中信银行（借记卡）</td></tr>
    <tr><td>CITIC_CREDIT</td><td>中信银行（信用卡）</td></tr>
    <tr><td>SDB_CREDIT</td><td>深发银行（信用卡）</td></tr>
    <tr><td>BOSH_DEBIT</td><td>上海银行（借记卡）</td></tr>
    <tr><td>BOSH_CREDIT</td><td>上海银行（信用卡）</td></tr>
    <tr><td>CRB_DEBIT</td><td>华润银行（借记卡）</td></tr>
    <tr><td>HZB_DEBIT</td><td>杭州银行（借记卡）</td></tr>
    <tr><td>HZB_CREDIT</td><td>杭州银行（信用卡）</td></tr>
    <tr><td>BSB_DEBIT</td><td>包商银行（借记卡）</td></tr>
    <tr><td>BSB_CREDIT</td><td>包商银行（信用卡）</td></tr>
    <tr><td>CQB_DEBIT</td><td>重庆银行（借记卡）</td></tr>
    <tr><td>SDEB_DEBIT</td><td>顺德农商行（借记卡）</td></tr>
    <tr><td>SZRCB_DEBIT</td><td>深圳农商银行（借记卡）</td></tr>
    <tr><td>HRBB_DEBIT</td><td>哈尔滨银行（借记卡）</td></tr>
    <tr><td>BOCD_DEBIT</td><td>成都银行（借记卡）</td></tr>
    <tr><td>GDNYB_DEBIT</td><td>南粤银行（借记卡）</td></tr>
    <tr><td>GDNYB_CREDIT</td><td>南粤银行（信用卡）</td></tr>
    <tr><td>GZCB_CREDIT</td><td>广州银行（信用卡）</td></tr>
    <tr><td>JSB_DEBIT</td><td>江苏银行（借记卡）</td></tr>
    <tr><td>JSB_CREDIT</td><td>江苏银行（信用卡）</td></tr>
    <tr><td>NBCB_DEBIT</td><td>宁波银行（借记卡）</td></tr>
    <tr><td>NBCB_CREDIT</td><td>宁波银行（信用卡）</td></tr>
    <tr><td>NJCB_DEBIT</td><td>南京银行（借记卡）</td></tr>
    <tr><td>QDCCB_DEBIT</td><td>青岛银行（借记卡）</td></tr>
    <tr><td>ZJTLCB_DEBIT</td><td>浙江泰隆银行（借记卡）</td></tr>
    <tr><td>XAB_DEBIT</td><td>西安银行（借记卡）</td></tr>
    <tr><td>CSRCB_DEBIT</td><td>常熟农商银行（借记卡）</td></tr>
    <tr><td>QLB_DEBIT</td><td>齐鲁银行（借记卡）</td></tr>
    <tr><td>LJB_DEBIT</td><td>龙江银行（借记卡）</td></tr>
    <tr><td>HXB_DEBIT</td><td>华夏银行（借记卡）</td></tr>
    <tr><td>CS_DEBIT</td><td>测试银行借记卡快捷支付（借记卡）</td></tr>
    <tr><td>AE_CREDIT</td><td>AE（信用卡）</td></tr>
    <tr><td>JCB_CREDIT</td><td>JCB（信用卡）</td></tr>
    <tr><td>MASTERCARD_CREDIT</td><td>MASTERCARD（信用卡）</td></tr>
    <tr><td>VISA_CREDIT</td><td>VISA（信用卡）</td></tr>
</table>