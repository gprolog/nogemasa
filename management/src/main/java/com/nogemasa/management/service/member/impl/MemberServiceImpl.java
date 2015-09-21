package com.nogemasa.management.service.member.impl;

import com.google.common.collect.Maps;
import com.nogemasa.common.mapper.MemberInfoMapper;
import com.nogemasa.common.mapper.MemberMapper;
import com.nogemasa.common.pojo.MemberInfoPojo;
import com.nogemasa.common.pojo.MemberPojo;
import com.nogemasa.management.common.WxUrls;
import com.nogemasa.management.service.member.IMemberService;
import com.nogemasa.util.httpclient.HttpRequester;
import com.nogemasa.signature.util.handler.PrivateSignatureHandler;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <br/>create at 15-8-15
 *
 * @author liuxh
 * @since 1.0.0
 */
@Service("memberService")
public class MemberServiceImpl implements IMemberService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MemberInfoMapper memberInfoMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Value("${public.key.path}")
    private String privateKeyFilePath;

    @Override
    public List<MemberPojo> getMemberList() {
        return memberMapper.getMemberList();
    }

    @Override
    public List<MemberInfoPojo> listMemberByGroup(String groupId, String queryStr) {
        Map<String, String> params = Maps.newHashMap();
        params.put("groupId", groupId);
        params.put("queryStr", queryStr);
        return memberInfoMapper.listMemberByParam(params);
    }

    @Override
    public int addPointsForMember(String cardNo, int points) {
        if (cardNo == null || points == 0) {
            return -1;
        }
        return memberMapper.addPointsForMember(cardNo, points);
    }

    @Override
    public MemberPojo getMemberPojo(String cardNo) {
        if (cardNo == null || cardNo.trim().isEmpty()) {
            return null;
        }
        MemberPojo member = memberMapper.getMemberByCardNo(cardNo);
        if (member == null) {
            return null;
        }
        if (member.getMemberInfo() == null) {
            member = memberMapper.getMemberByCardNoSimple(cardNo);
            try {
                PrivateSignatureHandler handler = new PrivateSignatureHandler();
                handler.setCaller("management");
                handler.setUsername("scheduler");
                handler.setKeyFilePath(privateKeyFilePath);
                JSONObject params = new JSONObject();
                params.put("openId", member.getOpenid());
                String signatureJson = handler.sign(params);
                logger.debug("签名结果：{}", signatureJson);
                JSONObject json = JSONObject.fromObject(HttpRequester.httpPostString(WxUrls.getUserInfoUrl(),
                        signatureJson));
                MemberInfoPojo memberInfo = (MemberInfoPojo) JSONObject
                        .toBean(json.getJSONObject("user_info"), MemberInfoPojo.class);
                member.setMemberInfo(memberInfo);
            } catch (Exception e) {
                logger.error("从weixin服务获取数据异常", e);
            }
        }
        return member;
    }
}
