package com.nogemasa.weixin.common.constant;

/**
 * <br/>create at 15-8-28
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface WxEventConstant {
    /**
     * 返回消息类型：文本
     */
    String RESP_MESSAGE_TYPE_TEXT = "text";

    /**
     * 返回消息类型：音乐
     */
    String RESP_MESSAGE_TYPE_MUSIC = "music";

    /**
     * 返回消息类型：图文
     */
    String RESP_MESSAGE_TYPE_NEWS = "news";

    /**
     * 请求消息类型：文本
     */
    String REQ_MESSAGE_TYPE_TEXT = "text";

    /**
     * 请求消息类型：图片
     */
    String REQ_MESSAGE_TYPE_IMAGE = "image";

    /**
     * 请求消息类型：链接
     */
    String REQ_MESSAGE_TYPE_LINK = "link";

    /**
     * 请求消息类型：地理位置
     */
    String REQ_MESSAGE_TYPE_LOCATION = "location";

    /**
     * 请求消息类型：音频
     */
    String REQ_MESSAGE_TYPE_VOICE = "voice";

    /**
     * 请求消息类型：推送
     */
    String REQ_MESSAGE_TYPE_EVENT = "event";

    /**
     * 事件类型：subscribe(订阅)
     */
    String EVENT_TYPE_SUBSCRIBE = "subscribe";

    /**
     * 事件类型：unsubscribe(取消订阅)
     */
    String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    /**
     * 事件类型：CLICK(自定义菜单点击事件)
     */
    String EVENT_TYPE_CLICK = "CLICK";

    /**
     * 事件KEY值：hyzj（自定义菜单-会员之家）
     */
    String CLICK_EVENT_KEY_HYZJ = "hyzj";

    /**
     * 事件KEY值：zindp（自定义菜单-最新活动）
     */
    String CLICK_EVENT_KEY_ZXHD = "zxhd";

    /**
     * 事件KEY值：zindp（自定义菜单-在线商城）
     */
    String CLICK_EVENT_KEY_ZXSC = "zxsc";

    /**
     * 事件KEY值：zindp（自定义菜单-最in搭配）
     */
    String CLICK_EVENT_KEY_ZINDP = "zindp";

    /**
     * 事件KEY值：rlfb（自定义菜单-热力发布）
     */
    String CLICK_EVENT_KEY_RLFB = "rlfb";
}
