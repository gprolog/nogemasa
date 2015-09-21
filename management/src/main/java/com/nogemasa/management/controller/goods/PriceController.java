package com.nogemasa.management.controller.goods;

import com.google.common.collect.Maps;
import com.nogemasa.management.pojo.GoodsPojo;
import com.nogemasa.management.pojo.PricePojo;
import com.nogemasa.management.service.goods.IPriceService;
import com.nogemasa.util.UnicodeStringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * <br/>create at 15-8-29
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/management/price")
public class PriceController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IPriceService priceService;

    @RequestMapping("/read/{goodsSid}")
    @ResponseBody
    public Map<String, Object> goodsList(@PathVariable("goodsSid") String goodsSid) {
        Map<String, Object> map = Maps.newHashMap();
        List<PricePojo> list = priceService.getPriceListByGoodsSid(goodsSid);
        map.put("success", true);
        map.put("total", list.size());
        map.put("list", list);
        return map;
    }

    @RequestMapping("/create/{goodsSid}")
    @ResponseBody
    public JSONObject createPrice(String data, @PathVariable("goodsSid") String goodsSid) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的数据，请检查！");
            return json;
        }
        try {
            JSONArray array = JSONArray.fromObject(UnicodeStringUtil.fromUnicodeString(data));
            json = array.getJSONObject(0);
            PricePojo pojo = (PricePojo) JSONObject.toBean(json, PricePojo.class);
            GoodsPojo goods = new GoodsPojo();
            goods.setSid(goodsSid);
            pojo.setGoods(goods);
            // FIXME 需要修改门店id获取方式
            pojo.getStore().setSid(pojo.getStore().getName());
            priceService.addPricePojo(pojo);
            logger.debug("插入后的id=" + pojo.getSid());
            json.put("sid", pojo.getSid());
            json.put("success", true);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "该价格已经存在，请检查！");
            logger.error("创建价格出错 PriceController.createPrice", e);
        }
        return json;
    }

    @RequestMapping("/update/{goodsSid}")
    @ResponseBody
    public JSONObject updatePrice(String data, @PathVariable("goodsSid") String goodsSid) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的数据，请检查！");
            return json;
        }
        try {
            JSONArray array = JSONArray.fromObject(UnicodeStringUtil.fromUnicodeString(data));
            json = array.getJSONObject(0);
            PricePojo pojo = (PricePojo) JSONObject.toBean(json, PricePojo.class);
            priceService.updatePricePojo(pojo.getStore().getSid(), goodsSid, pojo.getPrice());
            json.put("success", true);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "修改价格出错，请稍后重试！");
            logger.error("修改价格出错 PriceController.updatePrice", e);
        }
        return json;
    }
}
