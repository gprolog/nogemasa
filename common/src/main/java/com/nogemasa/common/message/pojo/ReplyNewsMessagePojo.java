package com.nogemasa.common.message.pojo;

import java.util.Date;

/**
 * <br/>create at 15-9-12
 *
 * @author liuxh
 * @since 1.0.0
 */
public class ReplyNewsMessagePojo {
    private String sid;// 图文消息主键
    private String messageStatus;// 是否最新标志位
    private String title;// 图文消息的标题
    private String description;// 图文消息的描述
    private String picUrl;// 图文消息图片链接
    private String url;// 图文消息跳转链接
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
