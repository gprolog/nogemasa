package com.nogemasa.management.controller.sale;

import com.google.common.collect.Maps;
import com.nogemasa.management.pojo.SaleRecordContentPojo;
import com.nogemasa.management.service.sale.ISaleRecordContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <br/>create at 15-9-3
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/management/sale/content")
public class SaleRecordContentController {
    @Autowired
    private ISaleRecordContentService saleRecordContentService;

    @RequestMapping("read")
    @ResponseBody
    public Map<String, Object> getContentList(HttpServletRequest request, int start, int limit) {
        Map<String, Object> map = Maps.newHashMap();
        List<SaleRecordContentPojo> list = saleRecordContentService.getSaleRecordContentList();
        if (list.size() == 0) {
            map.put("success", true);
            map.put("total", 0);
            map.put("list", Collections.emptyList());
            return map;
        }
        map.put("success", true);
        map.put("total", list.size());
        map.put("list", list.subList(start, list.size() < start + limit ? list.size() : start + limit));
        return map;
    }
}
