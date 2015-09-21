package com.nogemasa.management.controller.goods;

import com.nogemasa.management.pojo.PricePojo;
import com.nogemasa.management.service.goods.IPriceService;
import com.nogemasa.signature.util.json.MessageParser;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <br/>create at 15-8-23
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/service/goods")
public class GoodsServiceController {
    @Autowired
    private IPriceService priceService;

    @RequestMapping("/info")
    public JSONObject getGoodsInfo(@RequestBody(required = false) String message,
            @RequestParam(value = "message", required = false) String messageGet) {
        JSONObject params = MessageParser.getParams(message, messageGet);
        JSONObject json = new JSONObject();
        if (!params.containsKey("goodsSn")) {
            json.put("success", false);
            json.put("errorCode", 403);
            json.put("message", "未输入正确条形码！");
            return json;
        }
        String goodsSn = params.getString("goodsSn");
        String storeSid = params.getString("storeSid");
        try {
            PricePojo price = priceService.getPriceInfo(goodsSn, storeSid);
            if (price == null) {
                json.put("success", false);
                json.put("errorCode", 403);
                json.put("message", "未输入正确条形码！");
            } else {
                json.put("success", true);
                json.put("price", price);
            }
        } catch (Exception e) {
            json.put("success", false);
            json.put("errorCode", 500);
            json.put("message", "服务器错误，请稍后再试！");
        }
        return json;
    }
}
