package com.nogemasa.weixin.wxpay.scanpay.business;

import com.tencent.WXPay;
import com.tencent.bridge.IBridge;
import com.tencent.business.ScanPayBusiness;
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
public class DefaultScanPayBusiness {
    private static final Logger logger = LoggerFactory.getLogger(DefaultScanPayBusiness.class);

    /**
     * 刷卡支付业务逻辑
     * @param bridge 桥接器
     * @param resultListener 处理各种结果分支的监听器
     * @throws Exception
     */
    public static void run(IBridge bridge, ScanPayBusiness.ResultListener resultListener) throws Exception {
        // 从bridge里面拿到数据，构建提交被扫支付API需要的数据对象
        ScanPayReqData scanPayReqData = new ScanPayReqData(
                bridge.getAuthCode(),// 这个是扫码终端设备从用户手机上扫取到的支付授权号，有效期是1分钟
                bridge.getBody(),// 要支付的商品的描述信息，用户会在支付成功页面里看到这个信息
                bridge.getAttach(),// 支付订单里面可以填的附加数据，API会将提交的这个附加数据原样返回
                bridge.getOutTradeNo(),// 商户系统内部的订单号,32个字符内可包含字母, 确保在商户系统唯一
                bridge.getTotalFee(),// 订单总金额，单位为“分”，只能整数
                bridge.getDeviceInfo(),// 商户自己定义的扫码支付终端设备号，方便追溯这笔交易发生在哪台终端设备上
                bridge.getSpBillCreateIP(),// 订单生成的机器IP
                bridge.getTimeStart(),// 订单生成时间，格式为yyyyMMddHHmmss
                bridge.getTimeExpire(),// 订单失效时间，格式同上
                bridge.getGoodsTag()// 商品标记，微信平台配置的商品标记，用于优惠券或者满减使用
        );
        logger.info("刷卡支付请求数据{}", scanPayReqData.toMap().toString());
        // 执行业务逻辑
        WXPay.doScanPayBusiness(scanPayReqData,resultListener);
    }
}
