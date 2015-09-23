package com.nogemasa.weixin.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nogemasa.common.pojo.MemberInfoPojo;
import com.nogemasa.util.http.HttpUtils;
import com.nogemasa.util.httpclient.HttpRequestException;
import com.nogemasa.util.httpclient.HttpRequester;
import com.nogemasa.weixin.common.constant.WxUrls;
import com.nogemasa.weixin.common.pojo.AccessToken;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 微信接口公共类，获取access_token等。
 *
 * @author liuxh
 */
public final class WxUtils {
    private final static Logger logger = LoggerFactory.getLogger(WxUtils.class);

    private WxUtils() {
        throw new AssertionError("No com.nogemasa.weixin.common.util.WxUtils.WxUtils instances for you!");
    }

    /**
     * 获取access_token的接口地址（GET）
     *
     * @param appid     凭证
     * @param appsecret 密钥
     * @return accessToken 授权access_token
     * 董瑞龙
     */
    public static AccessToken getAccessToken(String appid, String appsecret) {
        AccessToken accessToken = null;
        String requestUrl = WxUrls.access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
        JSONObject jsonObject = JSONObject.fromObject(HttpUtils.httpGet(requestUrl));
        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                e.printStackTrace();
                accessToken = null;
                logger.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
                        jsonObject.getString("errmsg"));
            }
        } else {
            logger.error("获取AccessToken失败，请求url为：{}！", requestUrl);
        }
        return accessToken;
    }

    /**
     * 获取关注用户OpenId列表
     *
     * @param accessToken 授权access_token
     * @return 用户OpenId列表
     */
    public static Set<String> getUserOpenIDs(String accessToken) {
        return getUserOpenIDs(accessToken, null);
    }

    /**
     * 获取关注用户OpenId列表
     *
     * @param accessToken 授权access_token
     * @param nextOpenId  第一个用户的OpenId
     * @return 用户OpenId列表
     */
    public static Set<String> getUserOpenIDs(String accessToken, String nextOpenId) {
        Set<String> userOpenIDS = Sets.newHashSet();
        String requestUrl;
        if (nextOpenId == null) {
            requestUrl = WxUrls.ursers_url.replace("ACCESS_TOKEN", accessToken);
        } else {
            requestUrl = WxUrls.ursers_url_paged.replace("ACCESS_TOKEN", accessToken)
                    .replace("NEXT_OPENID", nextOpenId);
        }
        JSONObject jsonObject = JSONObject.fromObject(HttpUtils.httpGet(requestUrl));
        if (null != jsonObject) {
            try {
                logger.debug(jsonObject.toString());
                long total = jsonObject.getLong("total");
                long count = jsonObject.getLong("count");
                if (count > 0) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONArray openIDs = data.getJSONArray("openid");
                    for (int i = 0; i < openIDs.size(); i++) {
                        userOpenIDS.add(openIDs.getString(i));
                    }
                    String next_openid = jsonObject.getString("next_openid");
                    if (total > 1000 && count == 1000) {
                        getUserOpenIDs(accessToken, next_openid);
                    }
                }
            } catch (JSONException e) {
                logger.error("获取用户列表 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
                        jsonObject.getString("errmsg"), e);
            }
        } else {
            logger.error("获取用户列表{}失败！", requestUrl);
        }
        return userOpenIDS;
    }

    /**
     * 根据用户openId获取用户信息
     *
     * @param accessToken 授权access_token
     * @param openId      用户openId
     * @return 用户信息对象
     */
    public static MemberInfoPojo getUserInfo(String accessToken, String openId)
            throws URISyntaxException, IOException, HttpRequestException {
        MemberInfoPojo memberInfo = null;
        String requestUrl = WxUrls.user_info_url.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        logger.debug("请求微信服务器url是{}", requestUrl);
        JSONObject jsonObject = JSONObject.fromObject(HttpRequester.httpGetString(requestUrl));
        if (null != jsonObject) {
            try {
                logger.debug(jsonObject.toString());
                memberInfo = (MemberInfoPojo) JSONObject.toBean(jsonObject, MemberInfoPojo.class);
            } catch (JSONException e) {
                logger.error("获取用户信息失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
                        jsonObject.getString("errmsg"), e);
            }
        } else {
            logger.error("获取用户信息失败，请求url为：{}！", requestUrl);
        }
        return memberInfo;
    }

    /**
     * 批量获取用户信息
     *
     * @param accessToken 授权令牌
     * @param openIds     用户opendId列表
     * @return 用户信息列表
     */
    public static List<MemberInfoPojo> getUserInfoBatch(String accessToken, Set<String> openIds) {
        List<MemberInfoPojo> memberInfoList = Lists.newArrayList();
        /*
        JSONObject json = new JSONObject();
        JSONArray memberList = new JSONArray();
        for (String openId : openIds) {
            JSONObject j = new JSONObject();
            j.put("openid", openId);
            j.put("lang", "zh-CN");
            memberList.add(j);
        }
        json.put("user_list", memberList);
        String requestUrl = WxUrls.user_info_batch_url.replace("ACCESS_TOKEN", accessToken);
        logger.info("批量获取用户信息，请求地址{}，请求参数{}。", requestUrl, json.toString());
        JSONObject result = JSONObject.fromObject(HttpUtils.httpRequest(requestUrl, "POST", json.toString()));
        JSONArray userInfoArray = result.getJSONArray("user_info_list");
        for (int i = 0; i < userInfoArray.size(); i++) {
            JSONObject memberInfo = userInfoArray.getJSONObject(i);
            MemberInfoPojo member = (MemberInfoPojo) JSONObject.toBean(memberInfo, MemberInfoPojo.class);
            memberInfoList.add(member);
        }
        */
        ExecutorService threadPool = Executors.newFixedThreadPool(20 * Runtime.getRuntime().availableProcessors(),
                r -> {
                    Thread thread = new Thread(r);
                    thread.setDaemon(true);
                    return thread;
                });
        CountDownLatch countDownLatch = new CountDownLatch(openIds.size());
        for (String openId : openIds) {
            threadPool.execute(() -> {
                int count = 1;
                do {
                    MemberInfoPojo member = null;
                    try {
                        member = getUserInfo(accessToken, openId);
                        memberInfoList.add(member);
                        break;
                    } catch (HttpRequestException | IOException | URISyntaxException e) {
                        logger.error("openid为{}的即将重试第{}次。", openId, count);
                    }
                    count += 1;
                } while (count <= 3);
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
            threadPool.shutdown();
        } catch (InterruptedException ignored) {
        }
        /*
        for (String openId : openIds) {
            int count = 1;
            do {
                MemberInfoPojo member = null;
                try {
                    member = getUserInfo(accessToken, openId);
                    memberInfoList.add(member);
                    break;
                } catch (HttpRequestException | IOException | URISyntaxException e) {
                    logger.error("openid为{}的即将重试第{}次。", openId, count);
                }
                count += 1;
            } while (count <= 3);
        }*/
        return memberInfoList;
    }

    public static Set<String> getCallBackIps(String accessToken) {
        Set<String> ips = Sets.newHashSet();
        String requestUrl = WxUrls.call_back_ip_url.replace("ACCESS_TOKEN", accessToken);
        logger.debug(requestUrl);
        JSONObject jsonObject = JSONObject.fromObject(HttpUtils.httpGet(requestUrl));
        if (null != jsonObject) {
            try {
                logger.debug(jsonObject.toString());
                JSONArray ipList = jsonObject.getJSONArray("ip_list");
                for (int i = 0; i < ipList.size(); i++) {
                    ips.add(ipList.getString(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                logger.error("获取微信服务器IP地址失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
                        jsonObject.getString("errmsg"));
            }
        } else {
            logger.error(requestUrl);
            logger.error("获取微信服务器IP地址失败！");
        }
        return ips;
    }
}
