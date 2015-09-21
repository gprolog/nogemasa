package com.nogemasa.management.service.inventory;

import com.nogemasa.management.pojo.InventoryCheckContentPojo;

import java.util.List;

/**
 * <br/>create at 15-9-13
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface IInventoryCheckContentService {
    InventoryCheckContentPojo getInventoryCheckContentBySid(String contentSid);

    List<InventoryCheckContentPojo> getAllInventoryCheckContents();

    void addInventoryCheckContent(InventoryCheckContentPojo checkContent);

    void commitInventoryCheckContent(InventoryCheckContentPojo checkContent);

    void deleteInventoryCheckContent(InventoryCheckContentPojo checkContent);
}
