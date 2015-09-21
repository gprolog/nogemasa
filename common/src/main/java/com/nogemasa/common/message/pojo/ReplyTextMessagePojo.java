package com.nogemasa.common.message.pojo;

import java.util.Date;

/**
 * <br/>create at 15-9-12
 *
 * @author liuxh
 * @since 1.0.0
 */
public class ReplyTextMessagePojo {
    private String sid;// 消息主键
    private String messageStatus;// 是否最新标志位
    private String content;// 文本消息的内容
    private Date createTime;// 创建时间
    private Date effectTime;// 生效时间
    private Date invalidTime;// 失效时间

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(Date effectTime) {
        this.effectTime = effectTime;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }
}
