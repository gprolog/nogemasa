package com.nogemasa.management.service.order.impl;

import com.google.common.collect.Lists;
import com.nogemasa.management.mapper.order.PurchaseOrderListMapper;
import com.nogemasa.management.pojo.PurchaseOrderListPojo;
import com.nogemasa.management.service.order.IPurchaseOrderListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <br/>create at 15-8-7
 *
 * @author liuxh
 * @since 1.0.0
 */
@Service("purchaseOrderListService")
public class PurchaseOrderListServiceImpl implements IPurchaseOrderListService {
    @Autowired
    private PurchaseOrderListMapper purchaseOrderListMapper;

    @Override
    public List<PurchaseOrderListPojo> getAllPurchaseOrderList(String orderPurchaseSid) {
        if (orderPurchaseSid == null || orderPurchaseSid.trim().isEmpty()) {
            return Lists.newArrayList();
        }
        return purchaseOrderListMapper.getAllPurchaseOrderList(orderPurchaseSid);
    }

    @Override
    public void addPurchaseOrderList(PurchaseOrderListPojo pojo) {
        if (pojo == null || pojo.getPurchaseOrder() == null || pojo.getPurchaseOrder().getSid() == null
                || pojo.getGoods() == null || pojo.getGoods().getSid() == null) {
            return;
        }
        PurchaseOrderListPojo p = purchaseOrderListMapper.getPurchaseOrderList(pojo);
        if (p == null) {
            purchaseOrderListMapper.addPurchaseOrderList(pojo);
        } else {
            p.setCount(p.getCount() + pojo.getCount());
            purchaseOrderListMapper.updatePurchaseOrderList(p);
        }
    }
}
