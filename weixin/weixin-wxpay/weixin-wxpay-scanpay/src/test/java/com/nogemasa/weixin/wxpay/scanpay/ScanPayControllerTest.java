package com.nogemasa.weixin.wxpay.scanpay;

import com.nogemasa.signature.util.exception.CallerNotFoundException;
import com.nogemasa.signature.util.handler.PrivateSignatureHandler;
import com.nogemasa.util.httpclient.HttpRequestException;
import com.nogemasa.util.httpclient.HttpRequester;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

/**
 * <br/>create at 15-9-25
 *
 * @author liuxh
 * @since 1.0.0
 */
public class ScanPayControllerTest {
    @Test
    public void test() throws CallerNotFoundException, IOException, InvalidKeySpecException, URISyntaxException,
            HttpRequestException {
        String authCode = "130479312513192433";// 这个是扫码终端设备从用户手机上扫取到的支付授权号，这个号是跟用户用来支付的银行卡绑定的，有效期是1分钟
        String body = "nogemasa测试-微信支付开发过程中的测试商品";// 要支付的商品的描述信息，用户会在支付成功页面里看到这个信息
        String attach = "此处可以写系统生成的订单号、会员卡号等信息";// 支付订单里面可以填的附加数据，API会将提交的这个附加数据原样返回
        String outTradeNo = UUID.randomUUID().toString().replace("-", "");// 商户系统内部的订单号,32个字符内可包含字母, 确保在商户系统唯一
        int totalFee = 200;// 订单总金额，单位为“分”，只能整数
        String deviceInfo = "123456";// 商户自己定义的扫码支付终端设备号，方便追溯这笔交易发生在哪台终端设备上
        String spBillCreateIP = "8.8.8.8";// 订单生成的机器IP
        String goodsTag = "";// 商品标记，微信平台配置的商品标记，用于优惠券或者满减使用
        JSONObject json = new JSONObject();
        json.put("authCode", authCode);
        json.put("body", URLEncoder.encode(body, "UTF-8"));
        json.put("attach", URLEncoder.encode(attach, "UTF-8"));
        json.put("outTradeNo", outTradeNo);
        json.put("totalFee", totalFee);
        json.put("deviceInfo", deviceInfo);
        json.put("spBillCreateIP", spBillCreateIP);
        json.put("goodsTag", goodsTag);
        PrivateSignatureHandler handler = new PrivateSignatureHandler();
        handler.setCaller("management");
        handler.setUsername("scheduler");
        handler.setKeyFilePath("/home/liuxh/.ssh/rsaKey");
        String signatureJson = handler.sign(json);
        System.out.println(
                HttpRequester.httpPostString("http://localhost:8680/weixin-wxpay-scanpay/wxpay/scanpay/pay.json",
                        signatureJson));
    }
}
