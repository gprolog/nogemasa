package com.nogemasa.management.service.goods.impl;

import com.nogemasa.management.mapper.goods.InventoryMapper;
import com.nogemasa.management.pojo.InventoryPojo;
import com.nogemasa.management.service.goods.IInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <br/>create at 15-9-1
 *
 * @author liuxh
 * @since 1.0.0
 */
@Service("inventoryService")
public class InventoryServiceImpl implements IInventoryService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    public List<InventoryPojo> getInventoryList() {
        return inventoryMapper.getInventoryList();
    }

    @Override
    public int reduceInventoryCount4Sale(String storeSid, String goodsSid) {
        if (storeSid == null || goodsSid == null) {
            return -1;
        }
        return inventoryMapper.reduceInventoryCount4Sale(storeSid, goodsSid);
    }
}
