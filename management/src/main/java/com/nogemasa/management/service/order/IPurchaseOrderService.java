package com.nogemasa.management.service.order;

import com.nogemasa.management.pojo.PurchaseOrderPojo;

import java.util.List;

/**
 * <br/>create at 15-8-3
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface IPurchaseOrderService {
    List<PurchaseOrderPojo> getAllPurchaseOrders();

    void addPurchaseOrder(PurchaseOrderPojo purchaseOrderPojo);

    void updatePurchaseOrder(PurchaseOrderPojo purchaseOrderPojo);

    void deletePurchaseOrder(PurchaseOrderPojo purchaseOrderPojo);
}
