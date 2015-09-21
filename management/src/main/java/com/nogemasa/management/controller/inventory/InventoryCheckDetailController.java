package com.nogemasa.management.controller.inventory;

import com.google.common.collect.Maps;
import com.nogemasa.management.pojo.InventoryCheckContentPojo;
import com.nogemasa.management.pojo.InventoryCheckDetailPojo;
import com.nogemasa.management.service.inventory.IInventoryCheckDetailService;
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
 * <br/>create at 15-9-13
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/inventory/check/detail")
public class InventoryCheckDetailController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IInventoryCheckDetailService inventoryCheckDetailService;

    @RequestMapping("/read/{contentSid}")
    @ResponseBody
    public Map<String, Object> checkDetails(HttpServletRequest request, @PathVariable("contentSid") String contentSid) {
        Map<String, Object> map = Maps.newHashMap();
        List<InventoryCheckDetailPojo> list = inventoryCheckDetailService.getAllInventoryCheckDetail(contentSid);
        map.put("success", true);
        map.put("total", list.size());
        map.put("list", list);
        return map;
    }

    @RequestMapping("create")
    @ResponseBody
    public JSONObject createCheckDetail(HttpServletRequest request, String data, String contentSid) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的盘点单数据，请检查！");
            return json;
        }
        try {
            JSONArray array = JSONArray.fromObject(UnicodeStringUtil.fromUnicodeString(data));
            json = array.getJSONObject(0);
            InventoryCheckDetailPojo pojo = (InventoryCheckDetailPojo) JSONObject.toBean(json,
                    InventoryCheckDetailPojo.class);
            if (pojo.getCheckContent() == null) {
                pojo.setCheckContent(new InventoryCheckContentPojo());
            }
            pojo.getCheckContent().setSid(contentSid);
            inventoryCheckDetailService.addInventoryCheckDetail(pojo);
            logger.debug("插入后的id=" + pojo.getSid());
            json.put("sid", pojo.getSid());
            json.put("success", true);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "该盘点单详细已经存在，请检查！");
            logger.error("创建盘点单详细出错 InventoryCheckDetailController.createCheckDetail", e);
        }
        return json;
    }

    @RequestMapping("delete")
    @ResponseBody
    public JSONObject deleteCheckDetail(HttpServletRequest request, String sid) {
        JSONObject json = new JSONObject();
        if (sid == null || sid.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的盘点单数据，请检查！");
            return json;
        }
        try {
            InventoryCheckDetailPojo pojo = new InventoryCheckDetailPojo();
            pojo.setSid(sid);
            inventoryCheckDetailService.deleteInventoryCheckDetail(pojo);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "盘点单数据错误，请检查后再试！");
            logger.error("删除盘点单出错 InventoryCheckDetailController.deleteCheckDetail", e);
        }
        return json;
    }
}
