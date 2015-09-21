package com.nogemasa.weixin.controller;

import com.nogemasa.weixin.common.WxConstant;
import com.nogemasa.weixin.common.WxEventConstant;
import com.nogemasa.weixin.pojo.EventPojo;
import com.nogemasa.weixin.pojo.TextMessage;
import com.nogemasa.weixin.service.IEventService;
import com.nogemasa.weixin.util.XStreamHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.nogemasa.util.signature.SignatureUtils.checkSignature;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * <br/>create at 15-7-26
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/central")
public class CentralController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IEventService eventService;

    @RequestMapping(value = "", method = GET)
    @ResponseBody
    public String signature(@RequestParam(value = "signature", required = true) String signature,
            @RequestParam(value = "timestamp", required = true) String timestamp,
            @RequestParam(value = "nonce", required = true) String nonce,
            @RequestParam(value = "echostr", required = true) String echostr) {
        logger.info(signature, timestamp, nonce, echostr);
        return checkSignature(signature, WxConstant.token, timestamp, nonce) ? echostr : "Check fail";
    }

    @RequestMapping(value = "", method = POST)
    public void eventHandle(@RequestBody(required = true) String eventXmlMsg, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        logger.info("事件消息为：{}", eventXmlMsg);
        String rtnMsg = "";
        EventPojo event = XStreamHandle.toBean(eventXmlMsg, EventPojo.class);
        if (WxEventConstant.REQ_MESSAGE_TYPE_EVENT.equals(event.getMsgType())) {
            if (WxEventConstant.EVENT_TYPE_SUBSCRIBE.equals(event.getEvent())) {
                eventService.subscribeEvent(event);// 订阅
            } else if (WxEventConstant.EVENT_TYPE_UNSUBSCRIBE.equals(event.getEvent())) {
                eventService.unSubscribeEvent(event);// 取消订阅
            } else if (WxEventConstant.EVENT_TYPE_CLICK.equals(event.getEvent())) {
                /*自定义菜单点击事件*/
                if (WxEventConstant.CLICK_EVENT_KEY_HYZJ.equals(event.getEventKey())) {
                    // 会员之家菜单
                    rtnMsg = XStreamHandle.toXml(eventService.hyzjEvent(event));
                } else if (WxEventConstant.CLICK_EVENT_KEY_ZINDP.equals(event.getEventKey())) {
                    // 最in搭配菜单(于2015年9月12号删除该菜单，生效时间2015年9月13日)
                    rtnMsg = XStreamHandle.toXml(eventService.zindpEvent(event));
                } else if (WxEventConstant.CLICK_EVENT_KEY_ZXHD.equals(event.getEventKey())
                        || WxEventConstant.CLICK_EVENT_KEY_RLFB.equals(event.getEventKey())) {
                    // 最新活动
                    // 热力发布(于2015年9月12号删除该菜单，生效时间2015年9月13日)
                    rtnMsg = XStreamHandle.toXml(eventService.zxhdEvent(event));
                } else if (WxEventConstant.CLICK_EVENT_KEY_ZXSC.equals(event.getEventKey())) {
                    // 在线商城
                    rtnMsg = XStreamHandle.toXml(eventService.zxscEvent(event));
                }
            }
//        } else if(WxEventConstant.REQ_MESSAGE_TYPE_TEXT.equals(event.getMsgType())) {
        } else {
            TextMessage message = new TextMessage();
            message.setToUserName(event.getFromUserName());
            message.setFromUserName(event.getToUserName());
            message.setCreateTime(System.currentTimeMillis());
            message.setContent("马上就来，敬请期待...");
            rtnMsg = XStreamHandle.toXml(message);
        }
        logger.info("请求返回结果为：{}", rtnMsg);
        // 响应消息
        PrintWriter out = response.getWriter();
        out.print(rtnMsg);
        out.flush();
        out.close();
    }
}
