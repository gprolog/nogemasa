package com.nogemasa.management.controller.goods;

import com.nogemasa.management.pojo.GoodsPojo;
import com.nogemasa.management.service.goods.IGoodsService;
import com.nogemasa.util.UnicodeStringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * <br/>create at 15-8-1
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/management/goods")
public class GoodsController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IGoodsService goodsService;

    @RequestMapping("listAll")
    @ResponseBody
    public JSONObject listAll(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        List<GoodsPojo> goodsList = goodsService.getAllGoods();
        json.put("success", true);
        json.put("total", goodsList.size());
        json.put("list", goodsList);
        return json;
    }

    @RequestMapping("listPutInGoods")
    @ResponseBody
    public JSONObject listPutInGoods(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        List<GoodsPojo> goodsList = goodsService.listPutInGoods();
        json.put("success", true);
        json.put("total", goodsList.size());
        json.put("list", goodsList);
        return json;
    }

    @RequestMapping("read")
    @ResponseBody
    public JSONObject goodsList(HttpServletRequest request, int start, int limit) {
        JSONObject json = new JSONObject();
        List<GoodsPojo> goodsList = goodsService.getAllGoods();
        if(goodsList.size() == 0) {
            json.put("success", true);
            json.put("total", 0);
            json.put("list", Collections.emptyList());
            return json;
        }
        json.put("success", true);
        json.put("total", goodsList.size());
        json.put("list", goodsList.subList(start, goodsList.size() < start + limit ? goodsList.size() : start + limit));
        return json;
    }

    @RequestMapping("create")
    @ResponseBody
    public JSONObject createGoods(HttpServletRequest request, String data) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的商品数据，请检查！");
            return json;
        }
        try {
            json = JSONObject.fromObject(UnicodeStringUtil.fromUnicodeString(data)); // 解码转换
            GoodsPojo goodsPojo = (GoodsPojo) JSONObject.toBean(json, GoodsPojo.class);
            goodsService.addGoods(goodsPojo);
            logger.debug("插入后的id" + goodsPojo.getSid());
            json.put("sid", goodsPojo.getSid());
            json.put("success", true);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "该商品已经存在，请检查！");
            logger.error("创建商品出错 GoodsController.createGoods", e);
            throw new RuntimeException("创建商品出错 GoodsController.createGoods", e);
        }
        return json;
    }

    @RequestMapping("update")
    @ResponseBody
    public JSONObject updateGoods(HttpServletRequest request, String data) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的商品数据，请检查！");
            return json;
        }
        try {
            json = JSONObject.fromObject(UnicodeStringUtil.fromUnicodeString(data));
            GoodsPojo goodsPojo = (GoodsPojo) JSONObject.toBean(json, GoodsPojo.class);
            goodsService.updateGoods(goodsPojo);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "商品数据错误，请检查后再试！");
            logger.error("修改商品出错 GoodsController.updateGoods", e);
            throw new RuntimeException("修改商品出错 GoodsController.updateGoods", e);
        }
        return json;
    }

    @RequestMapping("updateGoodsPutInStatus")
    @ResponseBody
    public JSONObject updateGoodsPutInStatus(HttpServletRequest request, String sid, String putInStatus) {
        JSONObject json = new JSONObject();
        if (sid == null || sid.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的商品数据，请检查！");
            return json;
        }
        try {
            GoodsPojo goodsPojo = new GoodsPojo();
            goodsPojo.setSid(sid);
            goodsPojo.setPutInStatus(putInStatus);
            goodsService.updateGoodsPutInStatus(goodsPojo);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "商品数据错误，请检查后再试！");
            logger.error("修改商品导入状态出错 GoodsController.updateGoodsPutInStatus", e);
            throw new RuntimeException("修改商品导入状态出错 GoodsController.updateGoodsPutInStatus", e);
        }
        return json;
    }

    @RequestMapping("resetAllPutInStatus")
    @ResponseBody
    public JSONObject resetAllPutInStatus() {
        JSONObject json = new JSONObject();
        try {
            goodsService.resetAllPutInStatus();
            json.put("success", true);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "商品数据错误，请检查后再试！");
            logger.error("修改商品导入状态出错 GoodsController.resetAllPutInStatus", e);
            throw new RuntimeException("修改商品导入状态出错 GoodsController.resetAllPutInStatus", e);
        }
        return json;
    }
}
