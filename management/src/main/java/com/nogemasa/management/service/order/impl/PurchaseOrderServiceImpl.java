package com.nogemasa.management.service.order.impl;

import com.nogemasa.management.mapper.order.PurchaseOrderListMapper;
import com.nogemasa.management.mapper.order.PurchaseOrderMapper;
import com.nogemasa.management.pojo.PurchaseOrderListPojo;
import com.nogemasa.management.pojo.PurchaseOrderPojo;
import com.nogemasa.management.pojo.UserPojo;
import com.nogemasa.management.service.order.IPurchaseOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <br/>create at 15-8-3
 *
 * @author liuxh
 * @since 1.0.0
 */
@Service("purchaseOrderService")
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;
    @Autowired
    private PurchaseOrderListMapper purchaseOrderListMapper;

    @Override
    public List<PurchaseOrderPojo> getAllPurchaseOrders() {
        return purchaseOrderMapper.getPurchaseOrderList();
    }

    @Override
    public void addPurchaseOrder(PurchaseOrderPojo purchaseOrderPojo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPojo userPojo = (UserPojo) authentication.getPrincipal();
        purchaseOrderPojo.setSid(null);
        purchaseOrderPojo.setCreateUser(userPojo);
        purchaseOrderMapper.addPurchaseOrder(purchaseOrderPojo);
        logger.debug("采购订单插入后的id" + purchaseOrderPojo.getSid());
    }

    @Override
    public void updatePurchaseOrder(PurchaseOrderPojo purchaseOrderPojo) {
        if (purchaseOrderPojo == null || purchaseOrderPojo.getSid() == null || purchaseOrderPojo.getSid().isEmpty()) {
            return;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPojo userPojo = (UserPojo) authentication.getPrincipal();
        purchaseOrderPojo.setCommitUser(userPojo);
        purchaseOrderMapper.updatePurchaseOrder(purchaseOrderPojo);
    }

    @Override
    public void deletePurchaseOrder(PurchaseOrderPojo purchaseOrderPojo) {
        if (purchaseOrderPojo == null || purchaseOrderPojo.getSid() == null || purchaseOrderPojo.getSid().isEmpty()) {
            return;
        }
        List<PurchaseOrderListPojo> list = purchaseOrderListMapper.getAllPurchaseOrderList(purchaseOrderPojo.getSid());
        if (list.size() != 0) {
            return;
        }
        PurchaseOrderPojo order = purchaseOrderMapper.getPurchaseOrderBySid(purchaseOrderPojo.getSid());
        if (order.getEndTime() != null) {
            return;
        }
        purchaseOrderMapper.deletePurchaseOrder(purchaseOrderPojo.getSid());
    }
}
