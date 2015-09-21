package com.nogemasa.weixin.controller;

import com.nogemasa.signature.agent.annotation.SignatureVerifyService;
import com.nogemasa.weixin.service.IInfoService;
import com.nogemasa.signature.util.json.MessageParser;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <br/>create at 15-8-28
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/info-service")
public class InfoServiceController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IInfoService infoService;

    @RequestMapping("getAccessToken")
    @ResponseBody
    @SignatureVerifyService
    public JSONObject getAccessToken(@RequestBody(required = false) String message,
            @RequestParam(value = "message", required = false) String messageGet) {
        JSONObject json = new JSONObject();
        json.put("access_token", infoService.getAccessToken());
        return json;
    }

    @RequestMapping("getUserOpenIDS")
    @ResponseBody
    @SignatureVerifyService
    public JSONObject getUserOpenIDS(@RequestBody(required = false) String message,
            @RequestParam(value = "message", required = false) String messageGet) {
        JSONObject json = new JSONObject();
        json.put("openIDS", infoService.getUserOpenIDs());
        return json;
    }

    @RequestMapping("getIPS")
    @ResponseBody
    @SignatureVerifyService
    public JSONObject getIPS(@RequestBody(required = false) String message,
            @RequestParam(value = "message", required = false) String messageGet) {
        JSONObject json = new JSONObject();
        json.put("ips", infoService.getCallBackIps());
        return json;
    }

    @RequestMapping("getUserInfoList")
    @ResponseBody
    @SignatureVerifyService
    public JSONObject getUserInfoList(@RequestBody(required = false) String message,
            @RequestParam(value = "message", required = false) String messageGet) {
        JSONObject json = new JSONObject();
        json.put("user_info_list", infoService.getUsers());
        return json;
    }

    @RequestMapping("getUserInfo")
    @ResponseBody
    @SignatureVerifyService
    public JSONObject getUserInfo(@RequestBody(required = false) String message,
            @RequestParam(value = "message", required = false) String messageGet) {
        JSONObject params = MessageParser.getParams(message, messageGet);
        String openId = null;
        if (params.containsKey("openId")) {
            openId = params.getString("openId");
        }
        JSONObject json = new JSONObject();
        json.put("openId", openId);
        json.put("user_info", infoService.getUserInfo(openId));
        return json;
    }
}
