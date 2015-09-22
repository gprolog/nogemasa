package com.nogemasa.weixin.common.constant;

/**
 * <br/>create at 15-8-12
 *
 * @author liuxh
 * @since 1.0.0
 */
public final class WxUrls {
    private WxUrls() {
        throw new AssertionError("No com.nogemasa.weixin.common.constant.WxUrls instances for you!");
    }

    /**
     * 获取 access_token 接口
     */
    public static final String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    /**
     * 获取用户列表接口，参数 access_token
     */
    public static final String ursers_url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN";
    /**
     * 分页获取用户列表接口，参数 access_token
     */
    public static final String ursers_url_paged = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
    /**
     * 获取用户基本信息接口，需要access_token和openid;
     */
    public static final String user_info_url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    /**
     * 批量获取用户信息
     */
    public static final String user_info_batch_url = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN";
    /**
     * 获取微信服务器IP地址
     */
    public static final String call_back_ip_url = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN";
}
