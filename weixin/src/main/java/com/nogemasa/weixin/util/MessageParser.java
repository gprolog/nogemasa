package com.nogemasa.weixin.util;

import net.sf.json.JSONObject;

/**
 * 参数解析工具
 * <br/>create at 15-8-4
 *
 * @author liuxh
 * @since 1.0.0
 */
public abstract class MessageParser {
    /**
     * 通过传入的参数放回JSONObject格式的参数对象
     *
     * @param message    POST请求的参数
     * @param messageGet GET请求的参数
     * @return JSONObject格式的参数对象
     */
    public static JSONObject getParams(String message, String messageGet) {
        if (message == null || message.trim().isEmpty()) {
            message = messageGet;
        }
        JSONObject params = JSONObject.fromObject(message);
        return params.getJSONObject("messageBody");
    }
}
