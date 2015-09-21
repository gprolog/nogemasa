package com.nogemasa.weixin.front.service.impl;

import com.google.common.collect.Lists;
import com.nogemasa.common.mapper.MemberInfoMapper;
import com.nogemasa.common.mapper.MemberMapper;
import com.nogemasa.common.pojo.MemberInfoPojo;
import com.nogemasa.common.pojo.MemberPojo;
import com.nogemasa.weixin.common.component.AccessTokenKeeper;
import com.nogemasa.weixin.common.util.WxUtils;
import com.nogemasa.weixin.front.operation.IMsgOperation;
import com.nogemasa.weixin.front.pojo.*;
import com.nogemasa.weixin.front.service.IEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <br/>create at 15-8-27
 *
 * @author liuxh
 * @since 1.0.0
 */
@Service("eventService")
public class EventServiceImpl implements IEventService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AccessTokenKeeper accessTokenKeeper;
    @Autowired
    private MemberInfoMapper memberInfoMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private IMsgOperation msgOperation;
    @Value("${weixin.address}")
    private String weixinAddress;

    @Override
    public IMessage subscribeEvent(EventPojo event) {
        logger.debug("接受到的时间数据：{}", event);
        String openId = event.getFromUserName();
        MemberInfoPojo memberInfo = null;
        try {
            memberInfo = WxUtils.getUserInfo(accessTokenKeeper.getAccessToken(), openId);
        } catch (Exception e) {
            logger.error("根据openId获取用户信息出错", e);
        }
        logger.debug("从微信服务器获取数据为：{}" + memberInfo);
        TextMessage message = new TextMessage();
        message.setToUserName(event.getFromUserName());
        message.setFromUserName(event.getToUserName());
        message.setCreateTime(System.currentTimeMillis());
        String content = "欢迎来到诺格曼莎，我们将已最热忱的服务为您提供服务。";
        if (memberInfo != null && memberInfo.getSubscribe() != 0) {
            if (memberInfoMapper.getMemberByOpenId(memberInfo.getOpenid()) == null) {
                memberInfoMapper.addMemberInfo(memberInfo);
                MemberPojo member = new MemberPojo();
                member.setCard_no(memberInfo.getSubscribe_time() + "");
                member.setMemberInfo(memberInfo);
                member.setPoints(0);
                memberMapper.addMember(member);
            } else {
                memberInfoMapper.enabledMemberInfo(memberInfo.getOpenid());
                content = "欢迎回来，我们将为您提供更好的服务。";
            }
        }
        message.setContent(content);
        return message;
    }

    @Override
    public IMessage unSubscribeEvent(EventPojo event) {
        logger.debug("接受到的时间数据：{}", event);
        MemberInfoPojo memberInfo = new MemberInfoPojo();
        memberInfo.setOpenid(event.getFromUserName());
        memberInfoMapper.disabledMemberInfo(memberInfo);
        TextMessage message = new TextMessage();
        message.setToUserName(event.getFromUserName());
        message.setFromUserName(event.getToUserName());
        message.setCreateTime(System.currentTimeMillis());
        message.setContent("亲爱的，不要离开我，我舍不得你。。。");
        return message;
    }

    @Override
    public IMessage hyzjEvent(EventPojo event) {
        String openId = event.getFromUserName();
        MemberPojo member = null;
        MemberInfoPojo memberInfo = null;
        try {
            member = memberMapper.getMemberByOpenId(openId);
            if (member == null) {
                memberInfo = memberInfoMapper.getMemberByOpenId(openId);
            } else {
                memberInfo = member.getMemberInfo();
            }
        } catch (Exception e) {
            logger.error("查询服务器异常", e);
        }
        if (member != null) {
            NewsMessage message = new NewsMessage();
            message.setToUserName(event.getFromUserName());
            message.setFromUserName(event.getToUserName());
            message.setCreateTime(System.currentTimeMillis());
            ItemMessage item = new ItemMessage();
            item.setTitle("会员信息");
            item.setDescription("会员卡号：" + member.getCard_no() + "\n会员积分：" + member.getPoints() + "\n点击查看会员卡");
            item.setPicUrl(weixinAddress + "/service/bar-code/image/" + member.getCard_no() + ".png");
            item.setUrl(weixinAddress + "/service/bar-code.html?cardNo=" + member.getCard_no());
            List<ItemMessage> articles = Lists.newArrayList(item);
            message.setArticleCount(articles.size());
            message.setArticles(articles);
            return message;
        } else {
            TextMessage message = new TextMessage();
            message.setToUserName(event.getFromUserName());
            message.setFromUserName(event.getToUserName());
            message.setCreateTime(System.currentTimeMillis());
            message.setContent("服务器异常，请稍后再试。");
            final MemberInfoPojo finalMemberInfo = memberInfo;
            Thread thread = new Thread(() -> {
                if (finalMemberInfo == null) {
                    MemberInfoPojo mi = null;
                    try {
                        mi = WxUtils.getUserInfo(accessTokenKeeper.getAccessToken(), openId);
                    } catch (Exception e) {
                        logger.error("通过微信接口根据openId获取用户信息出错", e);
                    }
                    if (mi != null) {
                        try {
                            memberInfoMapper.addMemberInfo(mi);
                            MemberPojo m = new MemberPojo();
                            m.setCard_no(mi.getSubscribe_time() + "");
                            m.setMemberInfo(mi);
                            m.setPoints(0);
                            memberMapper.addMember(m);
                        } catch (Exception e) {
                            logger.error("写入服务器异常", e);
                        }
                    }
                } else {
                    MemberPojo m = new MemberPojo();
                    m.setCard_no(finalMemberInfo.getSubscribe_time() + "");
                    m.setMemberInfo(finalMemberInfo);
                    m.setPoints(0);
                    memberMapper.addMember(m);
                }
            });
            thread.start();
            return message;
        }
    }

    @Override
    public IMessage zindpEvent(EventPojo event) {
        TextMessage message = new TextMessage();
        message.setToUserName(event.getFromUserName());
        message.setFromUserName(event.getToUserName());
        message.setCreateTime(System.currentTimeMillis());
        message.setContent(msgOperation.zindpOperation());
        return message;
    }

    @Override
    public IMessage zxhdEvent(EventPojo event) {
        NewsMessage message = new NewsMessage();
        message.setToUserName(event.getFromUserName());
        message.setFromUserName(event.getToUserName());
        message.setCreateTime(System.currentTimeMillis());
        List<ItemMessage> articles = msgOperation.rlfbOperation();
        message.setArticleCount(articles.size());
        message.setArticles(articles);
        return message;
    }

    @Override
    public IMessage zxscEvent(EventPojo event) {
        TextMessage message = new TextMessage();
        message.setToUserName(event.getFromUserName());
        message.setFromUserName(event.getToUserName());
        message.setCreateTime(System.currentTimeMillis());
        message.setContent(msgOperation.zxscOperation());
        return message;
    }
}
