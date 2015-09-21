package com.nogemasa.weixin.front.util;

import com.nogemasa.weixin.common.util.XStreamHandle;
import com.nogemasa.weixin.front.pojo.EventPojo;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class XStreamHandleTest {
    @Test
    public void testToBean() throws Exception {
        String xml = "<xml>\n" +
                "<ToUserName><![CDATA[123]]></ToUserName>\n" +
                "<FromUserName><![CDATA[456]]></FromUserName>\n" +
                "<CreateTime>123456789</CreateTime>\n" +
                "<MsgType><![CDATA[789]]></MsgType>\n" +
                "<Event><![CDATA[subscribe]]></Event>\n" +
                "</xml>";
        assertEquals(XStreamHandle.toBean(xml, EventPojo.class).getToUserName(), "123");

        String xml2 = "<xml>\n" +
                "<ToUserName><![CDATA[toUser]]></ToUserName>\n" +
                "<FromUserName><![CDATA[FromUser]]></FromUserName>\n" +
                "<CreateTime>123456789</CreateTime>\n" +
                "<MsgType><![CDATA[event]]></MsgType>\n" +
                "<Event><![CDATA[CLICK]]></Event>\n" +
                "<EventKey><![CDATA[EVENTKEY]]></EventKey>\n" +
                "</xml>";
        assertEquals(XStreamHandle.toBean(xml2, EventPojo.class).getEventKey(), "EVENTKEY");
    }
}