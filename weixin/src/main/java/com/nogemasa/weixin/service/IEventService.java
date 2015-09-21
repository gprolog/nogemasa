package com.nogemasa.weixin.service;

import com.nogemasa.weixin.pojo.EventPojo;
import com.nogemasa.weixin.pojo.IMessage;

/**
 * <br/>create at 15-8-27
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface IEventService {
    /**
     * 订阅订阅事件处理
     *
     * @param event 订阅订阅事件
     * @return 响应结果
     */
    IMessage subscribeEvent(EventPojo event);

    /**
     * 取消订阅事件处理
     *
     * @param event 取消订阅事件
     * @return 响应结果
     */
    IMessage unSubscribeEvent(EventPojo event);

    /**
     * 会员之家事件处理
     *
     * @param event 会员之家事件
     * @return 响应结果
     */
    IMessage hyzjEvent(EventPojo event);

    /**
     * 最in搭配事件处理
     *
     * @param event 最in搭配事件
     * @return 响应结果
     */
    IMessage zindpEvent(EventPojo event);

    /**
     * 最新活动事件处理
     *
     * @param event 最新活动事件
     * @return 响应结果
     */
    IMessage zxhdEvent(EventPojo event);

    /**
     * 在线商城事件处理
     *
     * @param event 在线商城事件
     * @return 响应结果
     */
    IMessage zxscEvent(EventPojo event);
}
