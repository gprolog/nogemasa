package com.nogemasa.weixin.common.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * <br/>create at 15-8-27
 *
 * @author liuxh
 * @since 1.0.0
 */
public final class XStreamHandle {
    private XStreamHandle() {
        throw new AssertionError("No com.nogemasa.weixin.common.util.XStreamHandle instances for you!");
    }

    public static String toXml(Object obj) {
        XStream xstream = new XStream(new DomDriver("utf8"));
        xstream.processAnnotations(obj.getClass());
        /*
         // 以压缩的方式输出XML
         xstream.marshal(obj, new CompactWriter(new StringWriter()));
         return sw.toString();
         */
        // 以格式化的方式输出XML
        return xstream.toXML(obj);
    }

    @SuppressWarnings("unchecked")
    public static <T> T toBean(String xmlStr, Class<T> cls) {
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        return (T) xstream.fromXML(xmlStr);
    }
}
