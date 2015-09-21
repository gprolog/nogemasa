package com.nogemasa.management.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <br/>create at 15-8-15
 *
 * @author liuxh
 * @since 1.0.0
 */
@Component
public final class WxUrls {
    @Value("${wx.service.protocol}")
    private String protocol;
    @Value("${wx.service.host}")
    private String host;
    @Value("${wx.service.port}")
    private String port;
    @Value("${wx.service.application}")
    private String application;
    @Value("${wx.service.getAccessToken}")
    private String accessTokenUri;
    @Value("${wx.service.getUserOpenIDS}")
    private String userOpenIDSUri;
    @Value("${wx.service.getUserInfoList}")
    private String userInfoListUri;
    @Value("${wx.service.getUserInfo}")
    private String userInfoUri;
    @Value("${wx.service.getIPS}")
    private String iPSUri;

    private static String accessTokenUrl;
    private static String userOpenIDSUrl;
    private static String userInfoListUrl;
    private static String userInfoUrl;
    private static String iPSUrl;

    @PostConstruct
    public void initUrls() {
        String preUrl = protocol + "://" + host + ":" + port + "/" + application;
        accessTokenUrl = preUrl + accessTokenUri;
        userOpenIDSUrl = preUrl + userOpenIDSUri;
        userInfoListUrl = preUrl + userInfoListUri;
        userInfoUrl = preUrl + userInfoUri;
        iPSUrl = preUrl + iPSUri;
    }

    public static String getAccessTokenUrl() {
        return accessTokenUrl;
    }

    public static String getUserOpenIDSUrl() {
        return userOpenIDSUrl;
    }

    public static String getUserInfoListUrl() {
        return userInfoListUrl;
    }

    public static String getUserInfoUrl() {
        return userInfoUrl;
    }

    public static String getiPSUrl() {
        return iPSUrl;
    }
}
