package com.nogemasa.management.controller.order;

import com.google.common.collect.Maps;
import com.nogemasa.management.pojo.GodownOrderListPojo;
import com.nogemasa.management.pojo.GodownOrderPojo;
import com.nogemasa.management.service.order.IGodownOrderListService;
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
@RequestMapping("/order/godown/list")
public class GodownOrderListController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IGodownOrderListService godownOrderListService;

    @RequestMapping("/read/{orderGodownSid}")
    @ResponseBody
    public Map<String, Object> goodsList(HttpServletRequest request,
            @PathVariable("orderGodownSid") String orderGodownSid) {
        Map<String, Object> map = Maps.newHashMap();
        List<GodownOrderListPojo> list = godownOrderListService.getAllGodownOrderList(orderGodownSid);
        map.put("success", true);
        map.put("total", list.size());
        map.put("list", list);
        return map;
    }

    @RequestMapping("create")
    @ResponseBody
    public JSONObject createGodownOrderList(HttpServletRequest request, String data, String godownOrderSid) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的入库单数据，请检查！");
            return json;
        }
        try {
            JSONArray array = JSONArray.fromObject(UnicodeStringUtil.fromUnicodeString(data));
            json = array.getJSONObject(0);
            GodownOrderListPojo pojo = (GodownOrderListPojo) JSONObject.toBean(json, GodownOrderListPojo.class);
            if (pojo.getGodownOrder() == null) {
                pojo.setGodownOrder(new GodownOrderPojo());
            }
            pojo.getGodownOrder().setSid(godownOrderSid);
            // FIXME 需要修改商品id获取方式
            pojo.getGoods().setSid(pojo.getGoods().getName());
            godownOrderListService.addGodownOrderList(pojo);
            logger.debug("插入后的id=" + pojo.getSid());
            json.put("sid", pojo.getSid());
            json.put("success", true);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "该入库单详细已经存在，请检查！");
            logger.error("创建入库单详细出错 GodownOrderListController.createGodownOrderList", e);
        }
        return json;
    }
}
