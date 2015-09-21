package com.nogemasa.weixin.front.pojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 文本消息
 * <br/>create at 15-8-28
 *
 * @author liuxh
 * @since 1.0.0
 */
@XStreamAlias("xml")
public class TextMessage implements IMessage {
    @XStreamAlias("ToUserName")
    private String toUserName;// 接收方帐号（收到的OpenID）
    @XStreamAlias("FromUserName")
    private String fromUserName;// 开发者微信号
    @XStreamAlias("CreateTime")
    private Long createTime;// 消息创建时间 （整型）
    @XStreamAlias("MsgType")
    private final String msgType = "text";
    @XStreamAlias("Content")
    private String content;// 回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "toUserName='" + toUserName + '\'' +
                ", fromUserName='" + fromUserName + '\'' +
                ", createTime=" + createTime +
                ", msgType='" + msgType + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
