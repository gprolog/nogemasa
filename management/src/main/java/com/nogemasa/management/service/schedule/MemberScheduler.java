package com.nogemasa.management.service.schedule;

import com.google.common.collect.Maps;
import com.nogemasa.common.mapper.MemberInfoMapper;
import com.nogemasa.common.mapper.MemberMapper;
import com.nogemasa.common.pojo.MemberInfoPojo;
import com.nogemasa.common.pojo.MemberPojo;
import com.nogemasa.management.common.WxUrls;
import com.nogemasa.util.httpclient.HttpRequester;
import com.nogemasa.signature.util.handler.PrivateSignatureHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <br/>create at 15-8-15
 *
 * @author liuxh
 * @since 1.0.0
 */
@Component("memberScheduler")
public class MemberScheduler {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MemberInfoMapper memberInfoMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Value("${private.key.path}")
    private String privateKeyFilePath;

    public synchronized void refreshMemberList() {
        Map<String, MemberInfoPojo> addMap = Maps.newHashMap();
        Map<String, MemberInfoPojo> updateMap = Maps.newHashMap();
        Map<String, MemberInfoPojo> removeMap = Maps.newHashMap();
        Map<String, MemberInfoPojo> currentMap = Maps.newHashMap();
        try {
            PrivateSignatureHandler handler = new PrivateSignatureHandler();
            handler.setCaller("management");
            handler.setUsername("scheduler");
            handler.setKeyFilePath(privateKeyFilePath);
            String signatureJson = handler.sign(new JSONObject());
            logger.debug("签名结果：{}", signatureJson);
            JSONObject json = JSONObject.fromObject(HttpRequester.httpPostString(WxUrls.getUserInfoListUrl(),
                    signatureJson));
            JSONArray array = json.getJSONArray("user_info_list");
            for (int i = 0; i < array.size(); i++) {
                MemberInfoPojo memberInfo = (MemberInfoPojo) JSONObject
                        .toBean(array.getJSONObject(i), MemberInfoPojo.class);
                if (memberInfo != null) {
                    addMap.put(memberInfo.getOpenid(), memberInfo);
                }
            }
        } catch (Exception e) {
            logger.error("从weixin服务获取数据异常", e);
            return;
        }

        List<MemberInfoPojo> list = memberInfoMapper.listMemberByParam(null);
        list.forEach(m -> currentMap.put(m.getOpenid(), m));

        // 1、list中有，addMap有，且两者相同，不变
        // 2、list中有，addMap有，两者不同，修改
        // 3、list中有，addMap没有，删除
        // 4、list中没有，addMap有，新增
        for (MemberInfoPojo m : list) {
            if (addMap.containsKey(m.getOpenid())) {
                if (!addMap.get(m.getOpenid()).equals(m)) {
                    updateMap.put(m.getOpenid(), addMap.get(m.getOpenid()));
                }
                addMap.remove(m.getOpenid());
            } else {
                removeMap.put(m.getOpenid(), m);
                addMap.remove(m.getOpenid());
            }
        }

        logger.debug("新增{}条：{}", addMap.size(), addMap.toString());
        logger.debug("删除{}条：{}", removeMap.size(), removeMap.toString());
        logger.debug("修改{}条：{}", updateMap.size(), updateMap.toString());

        try {
            List<MemberInfoPojo> addList = new ArrayList<>(addMap.values());
            addList.forEach(memberInfo -> {
                try {
                    MemberPojo member = new MemberPojo();
                    member.setPoints(0);
                    member.setCard_no(memberInfo.getSubscribe_time() + "");
                    member.setMemberInfo(memberInfo);
                    memberMapper.addMember(member);
                    memberInfoMapper.addMemberInfo(memberInfo);
                    logger.debug("插入{}成功", member);
                } catch (Exception e) {
                    logger.error("新增数据异常，openid={}", memberInfo.getOpenid(), e);
                }
            });
        } catch (Exception e) {
            logger.error("新增数据异常", e);
        }
        try {
            List<MemberInfoPojo> currentList = new ArrayList<>(currentMap.values());
            currentList.forEach(memberInfo -> {
                try {
                    MemberPojo member = memberMapper.getMemberByOpenId(memberInfo.getOpenid());
                    if (member == null) {
                        member = new MemberPojo();
                        member.setPoints(0);
                        member.setCard_no(memberInfo.getSubscribe_time() + "");
                        member.setMemberInfo(memberInfo);
                        memberMapper.addMember(member);
                        logger.debug("插入{}成功", member);
                    } else {
                        logger.debug("从数据库查出的数据为：{}", member);
                    }
                } catch (Exception e) {
                    logger.error("插入{}失败", memberInfo, e);
                }
            });
        } catch (Exception e) {
            logger.error("检查会员表异常", e);
        }
        try {
            List<MemberInfoPojo> updateList = new ArrayList<>(updateMap.values());
            updateList.forEach(memberInfo -> {
                try {
                    memberInfoMapper.updateMemberInfo(memberInfo);
                    logger.debug("修改{}成功", memberInfo);
                } catch (Exception e) {
                    logger.error("修改数据异常，openid={}", memberInfo.getOpenid(), e);
                }
            });
        } catch (Exception e) {
            logger.error("修改会员表异常", e);
        }
        try {
            List<MemberInfoPojo> removeList = new ArrayList<>(removeMap.values());
            removeList.forEach(memberInfo -> {
                try {
                    memberInfoMapper.disabledMemberInfo(memberInfo);
                    logger.debug("删除{}成功", memberInfo);
                } catch (Exception e) {
                    logger.error("删除数据异常，openid={}", memberInfo.getOpenid(), e);
                }
            });
        } catch (Exception e) {
            logger.error("删除会员表异常", e);
        }
    }
}
