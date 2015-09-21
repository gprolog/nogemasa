package com.nogemasa.management.controller.inventory;

import com.google.common.collect.Maps;
import com.nogemasa.management.pojo.InventoryCheckContentPojo;
import com.nogemasa.management.pojo.StorePojo;
import com.nogemasa.management.pojo.UserPojo;
import com.nogemasa.management.service.inventory.IInventoryCheckContentService;
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
@RequestMapping("/inventory/check/content")
public class InventoryCheckContentController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IInventoryCheckContentService inventoryCheckContentService;
    @Autowired
    private IStoreService storeService;

    @RequestMapping("read")
    @ResponseBody
    public Map<String, Object> checkContentList(HttpServletRequest request, int start, int limit) {
        Map<String, Object> map = Maps.newHashMap();
        List<InventoryCheckContentPojo> checkContentPojoList = inventoryCheckContentService
                .getAllInventoryCheckContents();
        if (checkContentPojoList.size() == 0) {
            map.put("success", true);
            map.put("total", 0);
            map.put("list", Collections.emptyList());
            return map;
        }
        map.put("success", true);
        map.put("total", checkContentPojoList.size());
        map.put("list", checkContentPojoList.subList(start,
                checkContentPojoList.size() < start + limit ? checkContentPojoList.size() : start + limit));
        return map;
    }

    @RequestMapping("create")
    @ResponseBody
    public JSONObject createCheckContent(HttpServletRequest request, String data) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的盘点单数据，请检查！");
            return json;
        }
        try {
            json = JSONObject.fromObject(UnicodeStringUtil.fromUnicodeString(data));
            InventoryCheckContentPojo checkContent = (InventoryCheckContentPojo) JSONObject.toBean(json,
                    InventoryCheckContentPojo.class);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserPojo userPojo = (UserPojo) authentication.getPrincipal();
            checkContent.setStore(storeService.getStoreByUserSid(userPojo.getSid()));
            inventoryCheckContentService.addInventoryCheckContent(checkContent);
            logger.debug("插入后的id" + checkContent.getSid());
            json.put("sid", checkContent.getSid());
            json.put("success", true);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "该盘点单已经存在，请检查！");
            logger.error("创建盘点单出错 InventoryCheckContentController.createCheckContent", e);
        }
        return json;
    }

    @RequestMapping("commit")
    @ResponseBody
    public JSONObject commitCheckContent(HttpServletRequest request, String sid, String storeSid) {
        JSONObject json = new JSONObject();
        if (sid == null || sid.isEmpty() || storeSid == null || storeSid.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的盘点单数据，请检查！");
            return json;
        }
        try {
            InventoryCheckContentPojo checkContent = new InventoryCheckContentPojo();
            checkContent.setSid(sid);
            StorePojo store = new StorePojo();
            store.setSid(storeSid);
            checkContent.setStore(store);
            inventoryCheckContentService.commitInventoryCheckContent(checkContent);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "盘点单数据错误，请检查后再试！");
            logger.error("修改盘点单出错 InventoryCheckContentController.commitCheckContent", e);
        }
        return json;
    }

    @RequestMapping("destroy")
    @ResponseBody
    public JSONObject deleteCheckContent(HttpServletRequest request, String data) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的盘点单数据，请检查！");
            return json;
        }
        try {
            json = JSONObject.fromObject(UnicodeStringUtil.fromUnicodeString(data));
            InventoryCheckContentPojo checkContent = (InventoryCheckContentPojo) JSONObject.toBean(json,
                    InventoryCheckContentPojo.class);
            inventoryCheckContentService.deleteInventoryCheckContent(checkContent);
            json.clear();
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "盘点单数据错误，请检查后再试！");
            logger.error("删除盘点单出错 InventoryCheckContentController.deleteCheckContent", e);
        }
        return json;
    }
}
