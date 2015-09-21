package com.nogemasa.sale.web.controller;

import com.nogemasa.util.httpclient.HttpRequestException;
import com.nogemasa.util.httpclient.HttpRequester;
import com.nogemasa.signature.util.exception.CallerNotFoundException;
import com.nogemasa.signature.util.handler.PrivateSignatureHandler;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.spec.InvalidKeySpecException;

/**
 * <br/>create at 15-8-19
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/web/sale")
public class SaleWebController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${manage.service.host}")
    private String host;
    @Value("${manage.service.getGoodsInfo}")
    private String getGoodsInfo;
    @Value("${manage.service.getMemberInfo}")
    private String getMemberInfo;
    @Value("${manage.service.getPromotionList}")
    private String getPromotionList;
    @Value("${manage.service.getEmployeeList}")
    private String getEmployeeList;
    @Value("${manage.service.addSaleRecord}")
    private String addSaleRecord;
    @Value("${manage.private.key.path}")
    private String privateKeyPath;

    @RequestMapping("/record/save")
    public JSONObject saveSaleRecord(String employeeSid, String memberCardNo, String goodsSns, String totalPrice,
            String promotionSid, String totalCost, String costType) {
        JSONObject json = new JSONObject();
        try {
            PrivateSignatureHandler handler = new PrivateSignatureHandler();
            handler.setCaller("sale-web");
            handler.setKeyFilePath(privateKeyPath);
            JSONObject params = new JSONObject();
            params.put("storeSid", "1");
            params.put("employeeSid", employeeSid);
            params.put("memberCardNo", memberCardNo);
            params.put("goodsSns", goodsSns);
            params.put("totalPrice", totalPrice);
            params.put("promotionSid", promotionSid);
            params.put("totalCost", totalCost);
            params.put("costType", costType);
            if (goodsSns == null || goodsSns.trim().isEmpty()) {
                json.put("success", false);
                json.put("errorCode", 403);
                json.put("message", "输入数据有误，请检查后再试。");
            } else {
                String result = HttpRequester.httpPostString(host + "/" + addSaleRecord, handler.sign(params));
                json.put("success", true);
                json.put("message", result);
            }
        } catch (Exception e) {
            json.put("success", false);
            json.put("errorCode", 500);
            json.put("message", "服务器异常，请稍后再试。");
        }
        return json;
    }

    @RequestMapping("/goods/info/{goodsSn}")
    @ResponseBody
    public JSONObject getGoodsInfo(@PathVariable("goodsSn") String goodsSn) {
        JSONObject json = new JSONObject();
        json.put("goodsSn", goodsSn);
        json.put("storeSid", 1);
        try {
            PrivateSignatureHandler handler = new PrivateSignatureHandler();
            handler.setCaller("sale-web");
            handler.setKeyFilePath(privateKeyPath);
            String goodsInfo = HttpRequester.httpPostString(host + "/" + getGoodsInfo, handler.sign(json));
            JSONObject goods = JSONObject.fromObject(goodsInfo);
            Boolean success = goods.getBoolean("success");
            if (success) {
                json.put("success", true);
                json.put("price", goods.getString("price"));
            } else {
                json.put("success", false);
                json.put("errorCode", goods.getString("errorCode"));
                json.put("message", goods.getString("message"));
            }
        } catch (IOException | InvalidKeySpecException | CallerNotFoundException | HttpRequestException | URISyntaxException e) {
            json.put("success", false);
            json.put("errorCode", 500);
            json.put("message", "服务器调用异常，请稍后再试！");
            logger.error("服务器异常！", e);
        }
        return json;
    }

    @RequestMapping("/member/info/{cardNo}")
    @ResponseBody
    public JSONObject getMemberInfo(@PathVariable("cardNo") String cardNo) {
        JSONObject json = new JSONObject();
        json.put("cardNo", cardNo);
        try {
            PrivateSignatureHandler handler = new PrivateSignatureHandler();
            handler.setCaller("sale-web");
            handler.setKeyFilePath(privateKeyPath);
            String goodsInfo = HttpRequester.httpPostString(host + "/" + getMemberInfo, handler.sign(json));
            JSONObject info = JSONObject.fromObject(goodsInfo);
            Boolean success = info.getBoolean("success");
            if (success) {
                json.put("success", true);
                json.put("member", info.getString("member"));
            } else {
                json.put("success", false);
                json.put("errorCode", info.getString("errorCode"));
                json.put("message", info.getString("message"));
            }
        } catch (IOException | InvalidKeySpecException | CallerNotFoundException | HttpRequestException | URISyntaxException e) {
            json.put("success", false);
            json.put("errorCode", 500);
            json.put("message", "服务器调用异常，请稍后再试！");
            logger.error("服务器异常！", e);
        }
        logger.debug("返回的数据：{}", json.toString());
        return json;
    }

    @RequestMapping("/promotion/list")
    @ResponseBody
    public JSONObject getPromotion() {
        JSONObject json = new JSONObject();
        try {
            PrivateSignatureHandler handler = new PrivateSignatureHandler();
            handler.setCaller("sale-web");
            handler.setKeyFilePath(privateKeyPath);
            JSONObject result = JSONObject
                    .fromObject(HttpRequester.httpPostString(host + "/" + getPromotionList, handler.sign(json)));
            Boolean success = result.getBoolean("success");
            if (success) {
                json.put("success", true);
                json.put("promotions", result.get("promotions"));
            } else {
                json.put("success", false);
                json.put("errorCode", result.getString("errorCode"));
                json.put("message", result.getString("message"));
            }
        } catch (IOException | InvalidKeySpecException | CallerNotFoundException | HttpRequestException | URISyntaxException e) {
            json.put("success", false);
            json.put("errorCode", 500);
            json.put("message", "服务器调用异常，请稍后再试！");
            logger.error("服务器异常！", e);
        }
        return json;
    }

    @RequestMapping("/employee/list")
    @ResponseBody
    public String getEmployee() {
        JSONObject json = new JSONObject();
        try {
            PrivateSignatureHandler handler = new PrivateSignatureHandler();
            handler.setCaller("sale-web");
            handler.setKeyFilePath(privateKeyPath);
            JSONObject result = JSONObject
                    .fromObject(
                            HttpRequester.httpPostString(host + "/" + getEmployeeList, handler.sign(new JSONObject())));
            Boolean success = result.getBoolean("success");
            if (success) {
                json.put("success", true);
                json.put("employees", result.get("employees"));
            } else {
                json.put("success", false);
                json.put("errorCode", result.getString("errorCode"));
                json.put("message", result.getString("message"));
            }
        } catch (IOException | InvalidKeySpecException | CallerNotFoundException | HttpRequestException | URISyntaxException e) {
            json.put("success", false);
            json.put("errorCode", 500);
            json.put("message", "服务器调用异常，请稍后再试！");
            logger.error("服务器异常！", e);
        }
        logger.debug("返回数据为：{}", json);
        return json.toString();
    }
}
