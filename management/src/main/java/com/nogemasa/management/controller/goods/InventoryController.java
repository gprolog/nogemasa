package com.nogemasa.management.controller.goods;

import com.nogemasa.management.pojo.InventoryPojo;
import com.nogemasa.management.service.goods.IInventoryService;
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
 * <br/>create at 15-9-1
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/management/inventory")
public class InventoryController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IInventoryService inventoryService;

    @RequestMapping("read")
    @ResponseBody
    public JSONObject goodsList(HttpServletRequest request, int start, int limit) {
        JSONObject json = new JSONObject();
        List<InventoryPojo> list = inventoryService.getInventoryList();
        if (list.size() == 0) {
            json.put("success", true);
            json.put("total", 0);
            json.put("list", Collections.emptyList());
            return json;
        }
        json.put("success", true);
        json.put("total", list.size());
        json.put("list", list.subList(start, list.size() < start + limit ? list.size() : start + limit));
        return json;
    }
}
