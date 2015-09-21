package com.nogemasa.management.controller.order;

import com.google.common.collect.Maps;
import com.nogemasa.management.pojo.PurchaseOrderListPojo;
import com.nogemasa.management.pojo.PurchaseOrderPojo;
import com.nogemasa.management.service.order.IPurchaseOrderListService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <br/>create at 15-8-7
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/order/purchase/list")
public class PurchaseOrderListController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IPurchaseOrderListService purchaseOrderListService;

    @RequestMapping("/read/{orderPurchaseSid}")
    @ResponseBody
    public Map<String, Object> goodsList(HttpServletRequest request,
            @PathVariable("orderPurchaseSid") String orderPurchaseSid) {
        Map<String, Object> map = Maps.newHashMap();
        List<PurchaseOrderListPojo> list = purchaseOrderListService.getAllPurchaseOrderList(orderPurchaseSid);
        map.put("success", true);
        map.put("total", list.size());
        map.put("list", list);
        return map;
    }

    @RequestMapping("create")
    @ResponseBody
    public JSONObject createPurchaseOrderList(HttpServletRequest request, String data, String purchaseOrderSid) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的采购订单数据，请检查！");
            return json;
        }
        try {
            JSONArray array = JSONArray.fromObject(UnicodeStringUtil.fromUnicodeString(data));
            json = array.getJSONObject(0);
            PurchaseOrderListPojo pojo = (PurchaseOrderListPojo) JSONObject.toBean(json, PurchaseOrderListPojo.class);
            if (pojo.getPurchaseOrder() == null) {
                pojo.setPurchaseOrder(new PurchaseOrderPojo());
            }
            pojo.getPurchaseOrder().setSid(purchaseOrderSid);
            pojo.getGoods().setSid(pojo.getGoods().getName());
            purchaseOrderListService.addPurchaseOrderList(pojo);
            logger.debug("插入后的id=" + pojo.getSid());
            json.put("sid", pojo.getSid());
            json.put("success", true);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "该采购订单详细已经存在，请检查！");
            logger.error("创建采购订单详细出错 PurchaseOrderListController.createPurchaseOrderList", e);
            throw new RuntimeException("创建采购订单详细出错 PurchaseOrderListController.createPurchaseOrderList", e);
        }
        return json;
    }
}
