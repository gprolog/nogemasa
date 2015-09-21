package com.nogemasa.weixin.front.operation.impl;

import com.google.common.collect.Lists;
import com.nogemasa.common.message.mapper.ReplyNewsMessageMapper;
import com.nogemasa.common.message.pojo.ReplyNewsMessagePojo;
import com.nogemasa.weixin.front.operation.IMsgOperation;
import com.nogemasa.weixin.front.pojo.ItemMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * <br/>create at 15-8-28
 *
 * @author liuxh
 * @since 1.0.0
 */
@Component("msgOperation")
public class MsgOperationImpl implements IMsgOperation {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${message.template.zindp}")
    private String zindpMsg;
    @Value("${message.template.zxsc}")
    private String zxscMsg;
    @Value("${message.template.rlfb.Title}")
    private String title;
    @Value("${message.template.rlfb.Description}")
    private String description;
    @Value("${message.template.rlfb.PicUrl}")
    private String picUrl;
    @Value("${message.template.rlfb.Url}")
    private String url;
    @Autowired
    private ReplyNewsMessageMapper replyNewsMessageMapper;

    @Override
    public String zindpOperation() {
        return zindpMsg;
    }

    @Override
    public List<ItemMessage> rlfbOperation() {
        List<ReplyNewsMessagePojo> newsList = Collections.emptyList();
        try {
            newsList = replyNewsMessageMapper.getNewsMessages();
        } catch (Exception e) {
            logger.error("查询最新活动图文信息失败", e);
        }
        List<ItemMessage> items = Lists.newArrayList();
        if (newsList.size() == 0) {
            ItemMessage message = new ItemMessage();
            message.setTitle(title);
            message.setDescription(description);
            message.setPicUrl(picUrl);
            message.setUrl(url);
            items.add(message);
        } else {
            newsList.forEach(news -> {
                ItemMessage message = new ItemMessage();
                message.setTitle(news.getTitle());
                message.setDescription(news.getDescription());
                message.setPicUrl(news.getPicUrl());
                message.setUrl(news.getUrl());
                items.add(message);
            });
        }
        return items;
    }

    @Override
    public String zxscOperation() {
        return zxscMsg;
    }
}
