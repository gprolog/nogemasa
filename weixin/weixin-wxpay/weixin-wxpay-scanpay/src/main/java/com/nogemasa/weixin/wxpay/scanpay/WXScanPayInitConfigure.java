package com.nogemasa.weixin.wxpay.scanpay;

import com.tencent.WXPay;
import com.tencent.common.Configure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 微信刷卡支付初始化配置器
 * <br/>create at 15-9-25
 *
 * @author liuxh
 * @since 1.0.0
 */
public final class WXScanPayInitConfigure {
    private static final Logger logger = LoggerFactory.getLogger(WXScanPayInitConfigure.class);

    /**
     * 初始化微信刷卡支付SDK依赖的几个关键配置
     *
     * @param key           签名算法需要用到的秘钥
     * @param appID         公众账号ID
     * @param mchID         商户ID
     * @param sdbMchID      子商户ID，受理模式必填
     * @param certLocalPath HTTP证书在服务器中的路径，用来加载证书用
     * @param certPassword  HTTP证书的密码，默认等于MCHID
     */
    public WXScanPayInitConfigure(String key, String appID, String mchID, String sdbMchID, String certLocalPath,
            String certPassword) {
        WXPay.initSDKConfiguration(key, appID, mchID, sdbMchID, certLocalPath, certPassword);
        if (!mchID.equals(certPassword)) {
            logger.warn("********************************************************************************************");
            logger.warn("*检测到HTTP证书的密码与商户ID不相同（默认相同），请检查！输入的商户ID为{}，输入的HTTP证书的密码为{}",
                    mchID, certPassword);
            logger.warn("********************************************************************************************");
        }
    }

    /**
     * 签名算法需要用到的秘钥
     *
     * @return 签名算法需要用到的秘钥
     */
    public static String getKey() {
        return Configure.getKey();
    }

    /**
     * 公众账号ID
     *
     * @return 公众账号ID
     */
    public static String getAppid() {
        return Configure.getAppid();
    }

    /**
     * 商户ID
     *
     * @return 商户ID
     */
    public static String getMchid() {
        return Configure.getMchid();
    }

    /**
     * 子商户ID
     *
     * @return 子商户ID
     */
    public static String getSubMchid() {
        return Configure.getSubMchid();
    }

    /**
     * HTTP证书在服务器中的路径
     *
     * @return HTTP证书在服务器中的路径
     */
    public static String getCertLocalPath() {
        return Configure.getCertLocalPath();
    }

    /**
     * HTTP证书的密码
     *
     * @return HTTP证书的密码
     */
    public static String getCertPassword() {
        return Configure.getCertPassword();
    }
}
