package com.nogemasa.management.controller.sale;

import com.google.common.collect.Maps;
import com.nogemasa.management.pojo.SaleRecordContentPojo;
import com.nogemasa.management.pojo.SaleRecordDetailPojo;
import com.nogemasa.management.service.sale.ISaleRecordDetailService;
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
 * <br/>create at 15-9-3
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/management/sale/detail")
public class SaleRecordDetailController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ISaleRecordDetailService saleRecordDetailService;

    @RequestMapping("/list/{contentSid}")
    @ResponseBody
    public Map<String, Object> listEmployeeByStoreSid(@PathVariable("contentSid") String contentSid) {
        Map<String, Object> map = Maps.newHashMap();
        SaleRecordContentPojo content = new SaleRecordContentPojo();
        content.setSid(contentSid);
        List<SaleRecordDetailPojo> list = saleRecordDetailService.getSaleRecordDetailList(content);
        map.put("success", true);
        map.put("total", list.size());
        map.put("list", list);
        logger.debug("返回json数据为：{}", map.toString());
        return map;
    }
}
