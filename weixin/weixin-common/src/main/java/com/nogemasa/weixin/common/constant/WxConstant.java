package com.nogemasa.weixin.common.constant;

/**
 * <br/>create at 15-7-26
 *
 * @author liuxh
 * @since 1.0.0
 */
public final class WxConstant {
    private static String appid;
    private static String appSecret;
    private static String token;
    private static String encodingAESKey;
    private static String wxWebAddress;

    public WxConstant(String appid, String appSecret, String token, String encodingAESKey, String wxWebAddress) {
        WxConstant.appid = appid;
        WxConstant.appSecret = appSecret;
        WxConstant.token = token;
        WxConstant.encodingAESKey = encodingAESKey;
        WxConstant.wxWebAddress = wxWebAddress;
    }

    public static String getAppid() {
        return appid;
    }

    public static String getAppSecret() {
        return appSecret;
    }

    public static String getToken() {
        return token;
    }

    public static String getEncodingAESKey() {
        return encodingAESKey;
    }

    public static String getWxWebAddress() {
        return wxWebAddress;
    }
}
