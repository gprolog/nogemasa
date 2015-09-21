package com.nogemasa.weixin.pojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <br/>create at 15-8-28
 *
 * @author liuxh
 * @since 1.0.0
 */
@XStreamAlias("xml")
public class ImageMessage implements IMessage {
    @XStreamAlias("ToUserName")
    private String toUserName;// 接收方帐号（收到的OpenID）
    @XStreamAlias("FromUserName")
    private String fromUserName;// 开发者微信号
    @XStreamAlias("CreateTime")
    private Long createTime;// 消息创建时间 （整型）
    @XStreamAlias("MsgType")
    private final String msgType = "image";
    @XStreamAlias("MediaId")
    private String MediaId;// 通过素材管理接口上传多媒体文件，得到的id。

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

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
}
