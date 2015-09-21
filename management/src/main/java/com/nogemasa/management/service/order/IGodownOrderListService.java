package com.nogemasa.management.service.order;

import com.nogemasa.management.pojo.GodownOrderListPojo;

import java.util.List;

/**
 * <br/>create at 15-8-19
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface IGodownOrderListService {
    List<GodownOrderListPojo> getAllGodownOrderList(String godownSid);

    void addGodownOrderList(GodownOrderListPojo order);
}
