package com.nogemasa.management.mapper.order;

import com.nogemasa.management.pojo.GodownOrderListPojo;

import java.util.List;

/**
 * 入库单列表操作
 * <br/>create at 15-8-18
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface GodownOrderListMapper {
    List<GodownOrderListPojo> getAllGodownOrderList(String orderGodownSid);

    GodownOrderListPojo getGodownOrderList(GodownOrderListPojo pojo);

    void addGodownOrderList(GodownOrderListPojo pojo);

    void updateGodownOrderList(GodownOrderListPojo pojo);
}
