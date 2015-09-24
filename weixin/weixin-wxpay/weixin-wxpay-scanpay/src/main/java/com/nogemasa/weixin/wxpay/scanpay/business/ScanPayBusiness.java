package com.nogemasa.weixin.wxpay.scanpay.business;

import com.nogemasa.weixin.wxpay.scanpay.bridge.BridgeForScanPayBusiness;
import com.nogemasa.weixin.wxpay.scanpay.listener.ScanPayBusinessResultListener;
import com.tencent.WXPay;
import com.tencent.protocol.pay_protocol.ScanPayReqData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 支付业务逻辑
 * <br/>create at 15-9-24
 *
 * @author liuxh
 * @since 1.0.0
 */
public class ScanPayBusiness {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static void run() throws Exception {
        //第一步：初始化SDK（只需全局初始化一次即可）
        WXPay.initSDKConfiguration(
                //签名算法需要用到的秘钥
                "40a8f8aa8ebe45a40bdc4e0f7307bc66",
                //公众账号ID，成功申请公众账号后获得
                "wxf5b5e87a6a0fde94",
                //商户ID，成功申请微信支付功能之后通过官方发出的邮件获得
                "10000097",
                //子商户ID，受理模式下必填；
                "",
                //HTTP证书在服务器中的路径，用来加载证书用
                "/home/liuxh/work/projects/nogemasa/weixin/weixin-wxpay/weixin-wxpay-scanpay/docs/apiclient_cert.p12",
                //HTTP证书的密码，默认等于MCHID
                "10000097"
        );

        //第二步：准备好提交给API的数据(scanPayReqData)
        //1）创建一个可以用来生成数据的bridge，这里用的是一个专门用来测试用的Bridge，商户需要自己实现
        BridgeForScanPayBusiness bridge = new BridgeForScanPayBusiness();
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

        //第三步：准备好一个用来处理各种结果分支的监听器(resultListener)
        ScanPayBusinessResultListener resultListener = new ScanPayBusinessResultListener();

        //第四步：执行业务逻辑
        WXPay.doScanPayBusiness(scanPayReqData,resultListener);
    }
}
