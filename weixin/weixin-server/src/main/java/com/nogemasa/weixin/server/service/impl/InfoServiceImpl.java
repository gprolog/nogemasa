package com.nogemasa.weixin.server.service.impl;

import com.nogemasa.common.pojo.MemberInfoPojo;
import com.nogemasa.weixin.common.component.AccessTokenKeeper;
import com.nogemasa.weixin.common.util.WxUtils;
import com.nogemasa.weixin.server.service.IInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * <br/>create at 15-7-26
 *
 * @author liuxh
 * @since 1.0.0
 */
@Service("infoService")
public class InfoServiceImpl implements IInfoService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AccessTokenKeeper accessTokenKeeper;

    @Override
    public String getAccessToken() {
        return accessTokenKeeper.getAccessToken();
    }

    @Override
    public Set<String> getUserOpenIDs() {
        return WxUtils.getUserOpenIDs(accessTokenKeeper.getAccessToken());
    }

    @Override
    public MemberInfoPojo getUserInfo(String openId) {
        try {
            return WxUtils.getUserInfo(accessTokenKeeper.getAccessToken(), openId);
        } catch (Exception e) {
            logger.error("根据openId获取用户信息出错", e);
            return null;
        }
    }

    @Override
    public List<MemberInfoPojo> getUsers() {
        return WxUtils.getUserInfoBatch(accessTokenKeeper.getAccessToken(), this.getUserOpenIDs());
    }

    @Override
    public Set<String> getCallBackIps() {
        return WxUtils.getCallBackIps(accessTokenKeeper.getAccessToken());
    }
}
