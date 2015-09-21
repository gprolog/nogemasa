package com.nogemasa.weixin.pojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <br/>create at 15-8-28
 *
 * @author liuxh
 * @since 1.0.0
 */
@XStreamAlias("item")
public class ItemMessage {
    @XStreamAlias("Title")
    private String title;// 图文消息标题
    @XStreamAlias("Description")
    private String description;// 图文消息描述
    @XStreamAlias("PicUrl")
    private String picUrl;// 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
    @XStreamAlias("Url")
    private String url;// 点击图文消息跳转链接

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

    @Override
    public String toString() {
        return "ItemMessage{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}