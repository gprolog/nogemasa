package com.nogemasa.management.controller.member;

import com.nogemasa.common.pojo.MemberPojo;
import com.nogemasa.management.service.member.IMemberService;
import com.nogemasa.signature.agent.annotation.SignatureVerifyService;
import com.nogemasa.signature.util.json.MessageParser;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <br/>create at 15-8-31
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/service/member")
public class MemberServiceController {
    @Autowired
    private IMemberService memberService;

    @RequestMapping("/info")
    @SignatureVerifyService
    public JSONObject getGoodsInfo(@RequestBody(required = false) String message,
            @RequestParam(value = "message", required = false) String messageGet) {
        JSONObject params = MessageParser.getParams(message, messageGet);
        JSONObject json = new JSONObject();
        if (!params.containsKey("cardNo")) {
            json.put("success", false);
            json.put("errorCode", 403);
            json.put("message", "未输入正确条形码！");
            return json;
        }
        String cardNo = params.getString("cardNo");
        try {
            MemberPojo pojo = memberService.getMemberPojo(cardNo);
            if (pojo == null) {
                json.put("success", false);
                json.put("errorCode", 403);
                json.put("message", "未输入正确会员卡号！");
            } else {
                json.put("success", true);
                json.put("member", pojo);
            }
        } catch (Exception e) {
            json.put("success", false);
            json.put("errorCode", 500);
            json.put("message", "服务器错误，请稍后再试！");
        }
        return json;
    }
}
