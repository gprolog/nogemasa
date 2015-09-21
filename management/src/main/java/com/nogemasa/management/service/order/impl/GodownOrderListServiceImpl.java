package com.nogemasa.management.service.order.impl;

import com.google.common.collect.Lists;
import com.nogemasa.management.mapper.order.GodownOrderListMapper;
import com.nogemasa.management.mapper.order.GodownOrderMapper;
import com.nogemasa.management.pojo.GodownOrderListPojo;
import com.nogemasa.management.pojo.GodownOrderPojo;
import com.nogemasa.management.service.order.IGodownOrderListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <br/>create at 15-8-19
 *
 * @author liuxh
 * @since 1.0.0
 */
@Service("godownOrderListService")
public class GodownOrderListServiceImpl implements IGodownOrderListService {
    @Autowired
    private GodownOrderListMapper godownOrderListMapper;
    @Autowired
    private GodownOrderMapper godownOrderMapper;

    @Override
    public List<GodownOrderListPojo> getAllGodownOrderList(String orderGodownSid) {
        if (orderGodownSid == null || orderGodownSid.trim().isEmpty()) {
            return Lists.newArrayList();
        }
        return godownOrderListMapper.getAllGodownOrderList(orderGodownSid);
    }

    @Override
    public void addGodownOrderList(GodownOrderListPojo pojo) {
        if (pojo == null || pojo.getGodownOrder() == null || pojo.getGodownOrder().getSid() == null
                || pojo.getGoods() == null || pojo.getGoods().getSid() == null) {
            return;
        }
        GodownOrderPojo order = godownOrderMapper.getGodownOrderBySid(pojo.getGodownOrder().getSid());
        if("2".equals(order.getStatus())) {
            return;
        }
        GodownOrderListPojo p = godownOrderListMapper.getGodownOrderList(pojo);
        if (p == null) {
            godownOrderListMapper.addGodownOrderList(pojo);
        } else {
            p.setCount(p.getCount() + pojo.getCount());
            godownOrderListMapper.updateGodownOrderList(p);
        }
    }
}
