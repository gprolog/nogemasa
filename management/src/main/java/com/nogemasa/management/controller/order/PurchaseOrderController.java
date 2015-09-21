package com.nogemasa.management.controller.order;

import com.google.common.collect.Maps;
import com.nogemasa.management.pojo.PurchaseOrderPojo;
import com.nogemasa.management.pojo.UserPojo;
import com.nogemasa.management.service.order.IPurchaseOrderService;
import com.nogemasa.management.service.store.IStoreService;
import com.nogemasa.util.UnicodeStringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <br/>create at 15-8-3
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/order/purchase")
public class PurchaseOrderController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IPurchaseOrderService purchaseOrderService;
    @Autowired
    private IStoreService storeService;

    @RequestMapping("read")
    @ResponseBody
    public Map<String, Object> goodsList(HttpServletRequest request, int start, int limit) {
        Map<String, Object> map = Maps.newHashMap();
        List<PurchaseOrderPojo> purchaseOrders = purchaseOrderService.getAllPurchaseOrders();
        if (purchaseOrders.size() == 0) {
            map.put("success", true);
            map.put("total", 0);
            map.put("list", Collections.emptyList());
            return map;
        }
        map.put("success", true);
        map.put("total", purchaseOrders.size());
        map.put("list", purchaseOrders
                .subList(start, purchaseOrders.size() < start + limit ? purchaseOrders.size() : start + limit));
        return map;
    }

    @RequestMapping("create")
    @ResponseBody
    public JSONObject createPurchaseOrder(HttpServletRequest request, String data) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的采购订单数据，请检查！");
            return json;
        }
        try {
            json = JSONObject.fromObject(UnicodeStringUtil.fromUnicodeString(data)); // 解码转换
            PurchaseOrderPojo goodsPojo = (PurchaseOrderPojo) JSONObject.toBean(json, PurchaseOrderPojo.class);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserPojo userPojo = (UserPojo) authentication.getPrincipal();
            goodsPojo.setStore(storeService.getStoreByUserSid(userPojo.getSid()));
            purchaseOrderService.addPurchaseOrder(goodsPojo);
            logger.debug("插入后的id" + goodsPojo.getSid());
            json.put("sid", goodsPojo.getSid());
            json.put("success", true);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "该采购订单已经存在，请检查！");
            logger.error("创建采购订单出错 PurchaseOrderController.createPurchaseOrder", e);
            throw new RuntimeException("创建采购订单出错 PurchaseOrderController.createPurchaseOrder", e);
        }
        return json;
    }

    @RequestMapping("update")
    @ResponseBody
    public JSONObject updatePurchaseOrder(HttpServletRequest request, String data) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的采购订单数据，请检查！");
            return json;
        }
        try {
            json = JSONObject.fromObject(UnicodeStringUtil.fromUnicodeString(data));
            PurchaseOrderPojo purchaseOrderPojo = (PurchaseOrderPojo) JSONObject.toBean(json, PurchaseOrderPojo.class);
            purchaseOrderService.updatePurchaseOrder(purchaseOrderPojo);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "采购订单数据错误，请检查后再试！");
            logger.error("修改采购订单出错 PurchaseOrderController.updatePurchaseOrder", e);
            throw new RuntimeException("修改采购订单出错 PurchaseOrderController.updatePurchaseOrder", e);
        }
        return json;
    }

    @RequestMapping("destroy")
    @ResponseBody
    public JSONObject deletePurchaseOrder(HttpServletRequest request, String data) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的采购订单数据，请检查！");
            return json;
        }
        try {
            json = JSONObject.fromObject(UnicodeStringUtil.fromUnicodeString(data));
            PurchaseOrderPojo purchaseOrderPojo = (PurchaseOrderPojo) JSONObject.toBean(json, PurchaseOrderPojo.class);
            purchaseOrderService.deletePurchaseOrder(purchaseOrderPojo);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "采购订单数据错误，请检查后再试！");
            logger.error("删除采购订单出错 PurchaseOrderController.deletePurchaseOrder", e);
            throw new RuntimeException("删除采购订单出错 PurchaseOrderController.deletePurchaseOrder", e);
        }
        return json;
    }
}
