package com.nogemasa.management.service.inventory;

import com.nogemasa.management.pojo.InventoryCheckDetailPojo;

import java.util.List;

/**
 * <br/>create at 15-9-13
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface IInventoryCheckDetailService {
    List<InventoryCheckDetailPojo> getAllInventoryCheckDetail(String checkContentSid);

    int addInventoryCheckDetail(InventoryCheckDetailPojo checkDetail);

    int deleteInventoryCheckDetail(InventoryCheckDetailPojo pojo);
}
