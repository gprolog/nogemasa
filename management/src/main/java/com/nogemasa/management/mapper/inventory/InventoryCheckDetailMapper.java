package com.nogemasa.management.mapper.inventory;

import com.nogemasa.management.pojo.InventoryCheckDetailPojo;

import java.util.List;

/**
 * <br/>create at 15-9-13
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface InventoryCheckDetailMapper {
    List<InventoryCheckDetailPojo> getAllInventoryCheckDetail(String checkContentSid);

    InventoryCheckDetailPojo getInventoryCheckDetail(InventoryCheckDetailPojo pojo);

    int addInventoryCheckDetail(InventoryCheckDetailPojo pojo);

    int updateInventoryCheckDetail(InventoryCheckDetailPojo pojo);

    int deleteInventoryCheckDetail(String sid);
}
