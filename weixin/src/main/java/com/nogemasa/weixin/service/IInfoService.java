package com.nogemasa.weixin.service;

import com.nogemasa.common.pojo.MemberInfoPojo;

import java.util.List;
import java.util.Set;

/**
 * <br/>create at 15-7-26
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface IInfoService {
    String getAccessToken();

    Set<String> getUserOpenIDs();

    MemberInfoPojo getUserInfo(String openId);

    List<MemberInfoPojo> getUsers();

    Set<String> getCallBackIps();
}
