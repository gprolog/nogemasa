package com.nogemasa.management.controller.store;

import com.nogemasa.management.pojo.StorePojo;
import com.nogemasa.management.service.store.IStoreService;
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
 * <br/>create at 15-8-2
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/management/store")
public class StoreController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IStoreService storeService;

    @RequestMapping("read")
    @ResponseBody
    public JSONObject goodsList(HttpServletRequest request, int start, int limit) {
        JSONObject json = new JSONObject();
        List<StorePojo> storePojos = storeService.getAllStore();
        if(storePojos.size() == 0) {
            json.put("success", true);
            json.put("total", 0);
            json.put("list", Collections.emptyList());
            return json;
        }
        json.put("success", true);
        json.put("total", storePojos.size());
        json.put("list", storePojos.subList(start, storePojos.size() < start + limit ? storePojos.size() : start + limit));
        return json;
    }

    @RequestMapping("create")
    @ResponseBody
    public JSONObject createStore(HttpServletRequest request, String data) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的门店数据，请检查！");
            return json;
        }
        try {
            json = JSONObject.fromObject(UnicodeStringUtil.fromUnicodeString(data)); // 解码转换
            StorePojo storePojo = (StorePojo) JSONObject.toBean(json, StorePojo.class);
            storeService.addStore(storePojo);
            logger.debug("插入后的id" + storePojo.getSid());
            json.put("sid", storePojo.getSid());
            json.put("success", true);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "该门店已经存在，请检查！");
            logger.error("创建门店出错 StoreController.createStore", e);
            throw new RuntimeException("创建门店出错 StoreController.createStore", e);
        }
        return json;
    }

    @RequestMapping("destroy")
    @ResponseBody
    public JSONObject deleteStore(HttpServletRequest request, String data) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的门店数据，请检查！");
            return json;
        }
        try {
            json = JSONObject.fromObject(UnicodeStringUtil.fromUnicodeString(data));
            StorePojo storePojo = (StorePojo) JSONObject.toBean(json, StorePojo.class);
            if (storeService.deleteStore(storePojo)) {
                json.put("success", true);
            } else {
                json.put("msg", "门店数据错误，请检查后再试！");
                json.put("success", false);
            }
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "门店数据错误，请检查后再试！");
            logger.error("删除门店出错 UserController.deleteStore", e);
            throw new RuntimeException("删除门店出错 StoreController.deleteStore", e);
        }
        return json;
    }

    @RequestMapping("enabled")
    @ResponseBody
    public JSONObject enabledStore(HttpServletRequest request, String sid, boolean enabled) {
        JSONObject json = new JSONObject();
        if (sid == null || sid.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的门店数据，请检查！");
            return json;
        }
        try {
            StorePojo storePojo = new StorePojo();
            storePojo.setSid(sid);
            storePojo.setEnabled(enabled);
            storeService.enabledStore(storePojo);
            json.put("success", true);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "门店数据错误，请检查后再试！");
            logger.error("使门店生效出错 StoreController.enabledStore", e);
            throw new RuntimeException("使门店生效出错 StoreController.enabledStore", e);
        }
        return json;
    }

    @RequestMapping("update")
    @ResponseBody
    public JSONObject updateStore(HttpServletRequest request, String data) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的门店数据，请检查！");
            return json;
        }
        try {
            json = JSONObject.fromObject(UnicodeStringUtil.fromUnicodeString(data));
            StorePojo group = (StorePojo) JSONObject.toBean(json, StorePojo.class);
            storeService.updateStore(group);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "门店数据错误，请检查后再试！");
            logger.error("修改门店出错 StoreController.updateStore", e);
            throw new RuntimeException("修改门店出错 StoreController.updateStore", e);
        }
        return json;
    }
}
