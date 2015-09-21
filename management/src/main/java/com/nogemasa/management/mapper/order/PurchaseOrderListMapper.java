package com.nogemasa.management.mapper.order;

import com.nogemasa.management.pojo.PurchaseOrderListPojo;

import java.util.List;

/**
 * 采购订单列表操作
 * <br/>create at 15-8-7
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface PurchaseOrderListMapper {
    List<PurchaseOrderListPojo> getAllPurchaseOrderList(String orderPurchaseSid);

    PurchaseOrderListPojo getPurchaseOrderList(PurchaseOrderListPojo pojo);

    void addPurchaseOrderList(PurchaseOrderListPojo pojo);

    void updatePurchaseOrderList(PurchaseOrderListPojo pojo);
}
