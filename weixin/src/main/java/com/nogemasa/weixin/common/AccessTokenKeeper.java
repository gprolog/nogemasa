package com.nogemasa.weixin.common;

import com.nogemasa.weixin.pojo.AccessToken;
import com.nogemasa.weixin.util.WxUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * <br/>create at 15-8-10
 *
 * @author liuxh
 * @since 1.0.0
 */
@Component("accessTokenKeeper")
public class AccessTokenKeeper {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final static String CACHE_KEY_ACCESS_TOKEN = "CACHE_KEY_ACCESS_TOKEN";
    private ValueOperations<String, String> stringValueCache;

    /**
     * 获取微信的access_token
     *
     * @return 微信的access_token
     */
    public String getAccessToken() {
        if (this.stringValueCache != null) {
            String accessToken = this.stringValueCache.get(CACHE_KEY_ACCESS_TOKEN);
            if (accessToken != null) {
                return accessToken;
            }
        }
        AccessToken accessToken = WxUtils.getAccessToken(WxConstant.appid, WxConstant.appSecret);
        this.stringValueCache.set(CACHE_KEY_ACCESS_TOKEN, accessToken.getToken(), accessToken.getExpiresIn(),
                TimeUnit.SECONDS);
        logger.info("从微信获取的令牌为：{}，有效时间为：{}。", accessToken.getToken(), accessToken.getExpiresIn());
        return accessToken.getToken();
    }

    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        if (redisTemplate != null) {
            this.stringValueCache = redisTemplate.opsForValue();
        }
    }
}
