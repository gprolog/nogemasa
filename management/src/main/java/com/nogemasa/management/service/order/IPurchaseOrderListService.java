package com.nogemasa.management.service.order;

import com.nogemasa.management.pojo.PurchaseOrderListPojo;

import java.util.List;

/**
 * <br/>create at 15-8-7
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface IPurchaseOrderListService {
    List<PurchaseOrderListPojo> getAllPurchaseOrderList(String orderPurchaseSid);

    void addPurchaseOrderList(PurchaseOrderListPojo pojo);
}
