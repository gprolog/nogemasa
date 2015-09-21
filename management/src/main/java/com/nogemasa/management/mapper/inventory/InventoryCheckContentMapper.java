package com.nogemasa.management.mapper.inventory;

import com.nogemasa.management.pojo.InventoryCheckContentPojo;

import java.util.List;

/**
 * <br/>create at 15-9-13
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface InventoryCheckContentMapper {
    InventoryCheckContentPojo getInventoryCheckContentBySid(String sid);

    List<InventoryCheckContentPojo> getInventoryCheckContentList();

    void addInventoryCheckContent(InventoryCheckContentPojo godownOrderPojo);

    void updateInventoryCheckContent(InventoryCheckContentPojo godownOrderPojo);

    void deleteInventoryCheckContent(String sid);
}
