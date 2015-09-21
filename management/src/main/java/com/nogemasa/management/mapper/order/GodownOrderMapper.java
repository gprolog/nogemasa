package com.nogemasa.management.mapper.order;

import com.nogemasa.management.pojo.GodownOrderPojo;

import java.util.List;

/**
 * 入库单操作
 * <br/>create at 15-8-18
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface GodownOrderMapper {
    GodownOrderPojo getGodownOrderBySid(String sid);

    List<GodownOrderPojo> getGodownOrderList();

    void addGodownOrder(GodownOrderPojo godownOrderPojo);

    void updateGodownOrder(GodownOrderPojo godownOrderPojo);

    void deleteGodownOrder(String sid);
}
