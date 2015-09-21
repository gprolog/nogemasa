package com.nogemasa.management.service.inventory.impl;

import com.nogemasa.management.mapper.goods.InventoryMapper;
import com.nogemasa.management.mapper.inventory.InventoryCheckContentMapper;
import com.nogemasa.management.mapper.inventory.InventoryCheckDetailMapper;
import com.nogemasa.management.pojo.InventoryCheckContentPojo;
import com.nogemasa.management.pojo.InventoryCheckDetailPojo;
import com.nogemasa.management.pojo.InventoryPojo;
import com.nogemasa.management.pojo.UserPojo;
import com.nogemasa.management.service.inventory.IInventoryCheckContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <br/>create at 15-9-13
 *
 * @author liuxh
 * @since 1.0.0
 */
@Service("inventoryCheckContentService")
public class InventoryCheckContentServiceImpl implements IInventoryCheckContentService {
    @Autowired
    private InventoryCheckContentMapper inventoryCheckContentMapper;
    @Autowired
    private InventoryCheckDetailMapper inventoryCheckDetailMapper;
    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    public InventoryCheckContentPojo getInventoryCheckContentBySid(String contentSid) {
        if (contentSid == null || contentSid.isEmpty()) {
            return null;
        }
        return inventoryCheckContentMapper.getInventoryCheckContentBySid(contentSid);
    }

    @Override
    public List<InventoryCheckContentPojo> getAllInventoryCheckContents() {
        return inventoryCheckContentMapper.getInventoryCheckContentList();
    }

    @Override
    public void addInventoryCheckContent(InventoryCheckContentPojo checkContent) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPojo userPojo = (UserPojo) authentication.getPrincipal();
        checkContent.setSid(null);
        checkContent.setCreateUser(userPojo);
        inventoryCheckContentMapper.addInventoryCheckContent(checkContent);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized void commitInventoryCheckContent(InventoryCheckContentPojo checkContent) {
        if (checkContent == null || checkContent.getSid() == null || checkContent.getSid().isEmpty()) {
            return;
        }
        InventoryCheckContentPojo content = inventoryCheckContentMapper
                .getInventoryCheckContentBySid(checkContent.getSid());
        if (content == null || "2".equals(content.getStatus())) {
            return;
        }
        List<InventoryCheckDetailPojo> list = inventoryCheckDetailMapper
                .getAllInventoryCheckDetail(checkContent.getSid());
        for (InventoryCheckDetailPojo pojo : list) {
            InventoryPojo inventory = inventoryMapper.getInventory(checkContent.getStore().getSid(),
                    pojo.getGoods().getSid());
            if (inventory != null) {
                inventoryMapper.updateInventoryCount(checkContent.getStore().getSid(), pojo.getGoods().getSid(),
                        pojo.getDeltaCount());
            }
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPojo userPojo = (UserPojo) authentication.getPrincipal();
        checkContent.setCommitUser(userPojo);
        inventoryCheckContentMapper.updateInventoryCheckContent(checkContent);
    }

    @Override
    public void deleteInventoryCheckContent(InventoryCheckContentPojo checkContent) {
        if (checkContent == null || checkContent.getSid() == null || checkContent.getSid().isEmpty()) {
            return;
        }
        List<InventoryCheckDetailPojo> list = inventoryCheckDetailMapper
                .getAllInventoryCheckDetail(checkContent.getSid());
        if (list.size() != 0) {
            return;
        }
        InventoryCheckContentPojo order = inventoryCheckContentMapper.getInventoryCheckContentBySid(
                checkContent.getSid());
        if (order.getEndTime() != null) {
            return;
        }
        inventoryCheckContentMapper.deleteInventoryCheckContent(checkContent.getSid());
    }
}
