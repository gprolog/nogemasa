package com.nogemasa.weixin.wxpay.scanpay.bridge;

import com.tencent.bridge.IBridge;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 刷卡支付桥接器
 * <br/>create at 15-9-24
 *
 * @author liuxh
 * @since 1.0.0
 */
public class BridgeForScanPayBusiness implements IBridge {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String authCode;
    private String body;
    private String attach;
    private String outTradeNo;
    private int totalFee;
    private String deviceInfo;
    private String spBillCreateIP;
    private String goodsTag;

    @Override
    public String getAuthCode() {
        //由于这个authCode有效期只有1分钟，所以实际测试SDK的时候也可以手动将微信刷卡界面一维码下的那串数字输入进来
        // TODO 授权码
        return authCode;
    }

    @Override
    public synchronized String getOutTradeNo() {
        //建议测试的时候自己手动写一个，多次测试可以慢慢累加
        // TODO 订单号
        return outTradeNo;
    }

    @Override
    public String getBody() {
        // TODO 商品详情
        return body;
    }

    @Override
    public String getAttach() {
        // TODO 附加数据，可以写订单号、用户卡号等信息
        return attach;
    }

    @Override
    public int getTotalFee() {
        //测试的时候就用1分钱吧，这可是真金白银啊。。。
        // TODO 付款金额
        return totalFee;
    }

    @Override
    public String getDeviceInfo() {
        //建议商户自己尽量实现这个获取具体支付终端设备号的功能
        // TODO 终端设备号
        return deviceInfo;
    }

    @Override
    public String getUserIp() {
        // TODO 终端设备的IP地址
        return "8.8.8.8";
    }

    @Override
    public String getSpBillCreateIP() {
        //这个就是生成订单机器的IP地址了，请商户尽量获取到传过来，作用跟上面的device_info类似，在后续定位问题的时候可以派上用场
        // TODO 订单生成的机器IP
        return spBillCreateIP;
    }

    @Override
    public String getTimeStart() {
        //订单生成时间自然就是当前服务器系统时间咯
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(new Date());
    }

    @Override
    public String getTimeExpire() {
        //订单失效时间，这个每个商户有自己的过期原则，我这里设置的是10天后过期
        // TODO 确定订单失效时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, 10);
        return simpleDateFormat.format(c.getTime());
    }

    @Override
    public String getGoodsTag() {
        // 这个功能要参考“优惠券”功能才知道怎么弄，当然有文档了
        // TODO 参考优惠券
        return goodsTag;
    }

    @Override
    public String getTransactionID() {
        return null;
    }

    @Override
    public String getOutRefundNo() {
        return null;
    }

    @Override
    public int getRefundFee() {
        return 0;
    }

    @Override
    public String getRefundID() {
        return null;
    }

    @Override
    public String getBillDate() {
        return null;
    }

    @Override
    public String getBillType() {
        return null;
    }

    @Override
    public String getOpUserID() {
        return null;
    }

    @Override
    public String getRefundFeeType() {
        return null;
    }

    public BridgeForScanPayBusiness setAuthCode(String authCode) {
        if (StringUtils.isBlank(authCode)) {
            logger.error("授权码为空，请检查！");
            throw new IllegalArgumentException("授权码为空，请检查！");
        }
        this.authCode = authCode;
        return this;
    }

    public BridgeForScanPayBusiness setBody(String body) {
        if (StringUtils.isBlank(body)) {
            body = "诺格曼莎购买服装";
            logger.warn("输入的商品信息为空，请检查！");
        }
        this.body = body;
        return this;
    }

    public BridgeForScanPayBusiness setAttach(String attach) {
        if (attach == null) {
            attach = "";
            logger.warn("输入的附加数据为空，请检查！");
        }
        this.attach = attach;
        return this;
    }

    public BridgeForScanPayBusiness setOutTradeNo(String outTradeNo) {
        if (StringUtils.isBlank(outTradeNo)) {
            logger.error("商户订单号为空，请检查！");
            throw new IllegalArgumentException("商户订单号为空，请检查！");
        }
        this.outTradeNo = outTradeNo;
        return this;
    }

    public BridgeForScanPayBusiness setTotalFee(int totalFee) {
        if (totalFee == 0) {
            logger.error("总金额为0分，请检查！");
            throw new IllegalArgumentException("总金额为0分总金额为0分，请检查！");
        }
        this.totalFee = totalFee;
        return this;
    }

    public BridgeForScanPayBusiness setDeviceInfo(String deviceInfo) {
        if (deviceInfo == null) {
            deviceInfo = "";
        }
        this.deviceInfo = deviceInfo;
        return this;
    }

    public BridgeForScanPayBusiness setSpBillCreateIP(String spBillCreateIP) {
        if (StringUtils.isBlank(spBillCreateIP)) {
            spBillCreateIP = "127.0.0.1";
        }
        this.spBillCreateIP = spBillCreateIP;
        return this;
    }

    public BridgeForScanPayBusiness setGoodsTag(String goodsTag) {
        if (goodsTag == null) {
            goodsTag = "";
        }
        this.goodsTag = goodsTag;
        return this;
    }
}
