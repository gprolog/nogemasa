package com.nogemasa.weixin.wxpay.scanpay.controller;

import com.nogemasa.signature.agent.annotation.SignatureVerifyService;
import com.nogemasa.signature.util.json.MessageParser;
import com.nogemasa.weixin.wxpay.scanpay.bridge.BridgeForScanPayBusiness;
import com.nogemasa.weixin.wxpay.scanpay.service.IScanPayService;
import com.tencent.protocol.pay_protocol.ScanPayResData;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <br/>create at 15-9-25
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/wxpay/scanpay")
public class WXPayScanPayController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IScanPayService scanPayService;

    @RequestMapping("/pay")
    @SignatureVerifyService
    @ResponseBody
    public JSONObject scanPay(@RequestBody(required = false) String message,
            @RequestParam(value = "message", required = false) String messageGet) {
        logger.debug("接收到RequestBody数据：{}", message);
        logger.debug("接收到RequestParam数据：{}", messageGet);
        JSONObject json = new JSONObject();
        try {
            JSONObject params = MessageParser.getParams(message, messageGet);
            String authCode = params.getString("authCode");
            String body = params.getString("body");
            String attach = params.getString("attach");
            String outTradeNo = params.getString("outTradeNo");
            int totalFee = params.getInt("totalFee");
            String deviceInfo = params.getString("deviceInfo");
            String spBillCreateIP = params.getString("spBillCreateIP");
            String goodsTag = params.getString("goodsTag");
            BridgeForScanPayBusiness bridge = new BridgeForScanPayBusiness();
            bridge.setAuthCode(authCode).setBody(body).setAttach(attach).setOutTradeNo(outTradeNo).setTotalFee(totalFee)
                    .setDeviceInfo(deviceInfo).setSpBillCreateIP(spBillCreateIP)
                    .setGoodsTag(goodsTag);
            ScanPayResData scanPayResData = scanPayService.scanPay(bridge);
            json.put("success", true);
            json.put("scanPayResData", scanPayResData);
        } catch (IllegalArgumentException e) {
            logger.error("输入参数有误，请检查！", e);
            json.put("success", false);
            json.put("err_type", "illegal_argument");
        } catch (Exception e) {
            logger.error("微信支付错误", e);
            json.put("success", false);
        }
        return json;
    }
}
