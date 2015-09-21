package com.nogemasa.management.service.order;

import com.nogemasa.management.pojo.GodownOrderPojo;

import java.util.List;

/**
 * <br/>create at 15-8-19
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface IGodownOrderService {
    GodownOrderPojo getGodownOrderBySid(String godownSid);

    List<GodownOrderPojo> getAllGodownOrders();

    void addGodownOrder(GodownOrderPojo order);

    void updateGodownOrder(GodownOrderPojo order);

    void deleteGodownOrder(GodownOrderPojo order);
}
