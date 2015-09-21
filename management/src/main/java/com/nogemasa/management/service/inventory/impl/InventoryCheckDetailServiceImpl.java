package com.nogemasa.management.service.inventory.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nogemasa.management.mapper.goods.InventoryMapper;
import com.nogemasa.management.mapper.inventory.InventoryCheckContentMapper;
import com.nogemasa.management.mapper.inventory.InventoryCheckDetailMapper;
import com.nogemasa.management.pojo.InventoryCheckContentPojo;
import com.nogemasa.management.pojo.InventoryCheckDetailPojo;
import com.nogemasa.management.pojo.InventoryPojo;
import com.nogemasa.management.service.inventory.IInventoryCheckDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <br/>create at 15-9-13
 *
 * @author liuxh
 * @since 1.0.0
 */
@Service("inventoryCheckDetailService")
public class InventoryCheckDetailServiceImpl implements IInventoryCheckDetailService {
    @Autowired
    private InventoryCheckDetailMapper inventoryCheckDetailMapper;
    @Autowired
    private InventoryCheckContentMapper inventoryCheckContentMapper;
    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    public List<InventoryCheckDetailPojo> getAllInventoryCheckDetail(String checkContentSid) {
        if (checkContentSid == null || checkContentSid.trim().isEmpty()) {
            return Collections.emptyList();
        }
        InventoryCheckContentPojo checkContent = inventoryCheckContentMapper
                .getInventoryCheckContentBySid(checkContentSid);
        if (checkContent == null) {
            return Collections.emptyList();
        }
        List<InventoryPojo> inventoryPojoList = inventoryMapper.getInventoryList();
        List<InventoryCheckDetailPojo> detailPojoList = inventoryCheckDetailMapper
                .getAllInventoryCheckDetail(checkContentSid);
        Map<String, InventoryCheckDetailPojo> checkMap = Maps.newHashMap();
        detailPojoList.forEach(pojo -> checkMap
                .put(pojo.getCheckContent().getStore().getSid() + "-" + pojo.getGoods().getSid(), pojo));
        List<InventoryCheckDetailPojo> list = Lists.newArrayList(detailPojoList);
        for (InventoryPojo inventoryPojo : inventoryPojoList) {
            if (checkMap.containsKey(inventoryPojo.getStore().getSid() + "-" + inventoryPojo.getGoods().getSid())) {
                continue;
            }
            InventoryCheckDetailPojo pojo = new InventoryCheckDetailPojo();
            pojo.setCheckContent(checkContent);
            pojo.setGoods(inventoryPojo.getGoods());
            pojo.setPreCount(inventoryPojo.getCount());
            list.add(pojo);
        }
        return list;
    }

    @Override
    public int addInventoryCheckDetail(InventoryCheckDetailPojo checkDetail) {
        if (checkDetail == null || checkDetail.getCheckContent() == null || checkDetail.getCheckContent()
                .getSid() == null
                || checkDetail.getGoods() == null || checkDetail.getGoods().getSid() == null) {
            return -1;
        }
        InventoryCheckContentPojo order = inventoryCheckContentMapper.getInventoryCheckContentBySid(
                checkDetail.getCheckContent().getSid());
        if ("2".equals(order.getStatus())) {
            return 0;
        }
        InventoryCheckDetailPojo p = inventoryCheckDetailMapper.getInventoryCheckDetail(checkDetail);
        if (p == null) {
            checkDetail.setDeltaCount(checkDetail.getPostCount() - checkDetail.getPreCount());
            return inventoryCheckDetailMapper.addInventoryCheckDetail(checkDetail);
        } else {
            p.setPostCount(checkDetail.getPostCount());
            p.setDeltaCount(p.getPostCount() - p.getPreCount());
            return inventoryCheckDetailMapper.updateInventoryCheckDetail(p);
        }
    }

    @Override
    public int deleteInventoryCheckDetail(InventoryCheckDetailPojo pojo) {
        if (pojo == null || pojo.getSid() == null || pojo.getSid().isEmpty()) {
            return -1;
        }
        return inventoryCheckDetailMapper.deleteInventoryCheckDetail(pojo.getSid());
    }
}
