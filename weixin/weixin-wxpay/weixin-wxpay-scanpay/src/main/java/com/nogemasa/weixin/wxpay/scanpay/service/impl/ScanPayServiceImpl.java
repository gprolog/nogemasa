package com.nogemasa.weixin.wxpay.scanpay.service.impl;

import com.nogemasa.weixin.wxpay.scanpay.bridge.BridgeForScanPayBusiness;
import com.nogemasa.weixin.wxpay.scanpay.business.DefaultScanPayBusiness;
import com.nogemasa.weixin.wxpay.scanpay.listener.ScanPayBusinessResultListener;
import com.nogemasa.weixin.wxpay.scanpay.service.IScanPayService;
import com.tencent.bridge.IBridge;
import com.tencent.protocol.pay_protocol.ScanPayResData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <br/>create at 15-9-25
 *
 * @author liuxh
 * @since 1.0.0
 */
@Service("scanPayService")
public class ScanPayServiceImpl implements IScanPayService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ScanPayResData scanPay(BridgeForScanPayBusiness bridge) throws Exception {
        ScanPayBusinessResultListener resultListener = new ScanPayBusinessResultListener();
        DefaultScanPayBusiness.run(bridge, resultListener);
        ScanPayResData scanPayResData = resultListener.getScanPayResData();
        logger.debug("返回状态码：{}", scanPayResData.getReturn_code());
        return scanPayResData;
    }
}
