package com.nogemasa.management.mapper.order;

import com.nogemasa.management.pojo.PurchaseOrderPojo;

import java.util.List;

/**
 * 采购订单操作
 * <br/>create at 15-8-3
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface PurchaseOrderMapper {
    PurchaseOrderPojo getPurchaseOrderBySid(String sid);

    List<PurchaseOrderPojo> getPurchaseOrderList();

    void addPurchaseOrder(PurchaseOrderPojo purchaseOrderPojo);

    void updatePurchaseOrder(PurchaseOrderPojo purchaseOrderPojo);

    void deletePurchaseOrder(String sid);
}
