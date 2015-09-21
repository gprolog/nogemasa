package com.nogemasa.management.controller.sale;

import com.nogemasa.management.service.sale.IPromotionService;
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
 * <br/>create at 15-8-24
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/service/sale")
public class PromotionServiceController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IPromotionService promotionService;

    @RequestMapping("/getPromotionList")
    @ResponseBody
    public JSONObject getPromotionList(@RequestBody(required = false) String message,
            @RequestParam(value = "message", required = false) String messageGet) {
        JSONObject json = new JSONObject();
        try {
            json.put("promotions", promotionService.listPromotion());
            json.put("success", true);
        } catch (Exception e) {
            json.put("success", false);
            json.put("errorCode", 500);
            json.put("message", "服务器异常，请稍后再试！");
        }
        return json;
    }
}
