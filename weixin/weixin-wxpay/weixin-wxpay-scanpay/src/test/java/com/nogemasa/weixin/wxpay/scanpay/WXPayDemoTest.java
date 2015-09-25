package com.nogemasa.weixin.wxpay.scanpay;

import com.nogemasa.weixin.wxpay.scanpay.listener.ScanPayBusinessResultListener;
import com.tencent.WXPay;
import com.tencent.protocol.pay_protocol.ScanPayReqData;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * <br/>create at 15-9-25
 *
 * @author liuxh
 * @since 1.0.0
 */
public class WXPayDemoTest {
    @Test
    public void test() throws Exception {
        String authCode = "130479312513192433";// 这个是扫码终端设备从用户手机上扫取到的支付授权号，这个号是跟用户用来支付的银行卡绑定的，有效期是1分钟
        String body = "nogemasa测试-微信支付开发过程中的测试商品";// 要支付的商品的描述信息，用户会在支付成功页面里看到这个信息
        String attach = "此处可以写系统生成的订单号、会员卡号等信息";// 支付订单里面可以填的附加数据，API会将提交的这个附加数据原样返回
        String outTradeNo = "5841408d8e9047dbb255575605db6ae5";// 商户系统内部的订单号,32个字符内可包含字母, 确保在商户系统唯一
        int totalFee = 200;// 订单总金额，单位为“分”，只能整数
        String deviceInfo = "123456";// 商户自己定义的扫码支付终端设备号，方便追溯这笔交易发生在哪台终端设备上
        String spBillCreateIP = "8.8.8.8";// 订单生成的机器IP
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeStart = "20150925175818";//simpleDateFormat.format(new Date());// 订单生成时间， 格式为yyyyMMddHHmmss。
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, 10);
        String timeExpire = "20151005175818";//simpleDateFormat.format(c.getTime());// 订单失效时间，格式同上
        String goodsTag = "";// 商品标记，微信平台配置的商品标记，用于优惠券或者满减使用
        ScanPayReqData scanPayReqData = new ScanPayReqData(authCode, body, attach, outTradeNo, totalFee, deviceInfo,
                spBillCreateIP, timeStart, timeExpire, goodsTag);
        ScanPayBusinessResultListener resultListener = new ScanPayBusinessResultListener();
        WXPay.doScanPayBusiness(scanPayReqData, resultListener);
    }
}