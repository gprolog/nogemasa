package com.nogemasa.weixin.pojo;

import com.google.common.collect.Lists;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

/**
 * <br/>create at 15-8-28
 *
 * @author liuxh
 * @since 1.0.0
 */
@XStreamAlias("xml")
public class NewsMessage implements IMessage {
    @XStreamAlias("ToUserName")
    private String toUserName;// 接收方帐号（收到的OpenID）
    @XStreamAlias("FromUserName")
    private String fromUserName;// 开发者微信号
    @XStreamAlias("CreateTime")
    private Long createTime;// 消息创建时间 （整型）
    @XStreamAlias("MsgType")
    private final String msgType = "news";
    @XStreamAlias("ArticleCount")
    private int articleCount;// 图文消息个数，限制为10条以内
    @XStreamAlias("Articles")
    private List<ItemMessage> articles = Lists.newArrayList();// 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应

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

    public int getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(int articleCount) {
        this.articleCount = articleCount;
    }

    public List<ItemMessage> getArticles() {
        return articles;
    }

    public void setArticles(List<ItemMessage> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "NewsMessage{" +
                "toUserName='" + toUserName + '\'' +
                ", fromUserName='" + fromUserName + '\'' +
                ", createTime=" + createTime +
                ", msgType='" + msgType + '\'' +
                ", articleCount=" + articleCount +
                ", articles=" + articles +
                '}';
    }

}
