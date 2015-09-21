package com.nogemasa.management.service.goods;

import com.nogemasa.management.pojo.InventoryPojo;

import java.util.List;

/**
 * <br/>create at 15-9-1
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface IInventoryService {
    List<InventoryPojo> getInventoryList();

    int reduceInventoryCount4Sale(String storeSid, String goodsSid);
}
