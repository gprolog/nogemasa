package com.nogemasa.weixin.wxpay.scanpay.service;

import com.nogemasa.weixin.wxpay.scanpay.bridge.BridgeForScanPayBusiness;
import com.tencent.protocol.pay_protocol.ScanPayResData;

/**
 * <br/>create at 15-9-25
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface IScanPayService {
    /**
     * 刷卡支付
     *
     * @param bridge 输入数据桥接器
     * @return 刷卡支付返回结果
     */
    ScanPayResData scanPay(BridgeForScanPayBusiness bridge) throws Exception;
}
