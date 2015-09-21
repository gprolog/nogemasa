package com.nogemasa.management.service.order.impl;

import com.nogemasa.management.mapper.goods.InventoryMapper;
import com.nogemasa.management.mapper.order.GodownOrderListMapper;
import com.nogemasa.management.mapper.order.GodownOrderMapper;
import com.nogemasa.management.pojo.GodownOrderListPojo;
import com.nogemasa.management.pojo.GodownOrderPojo;
import com.nogemasa.management.pojo.InventoryPojo;
import com.nogemasa.management.pojo.UserPojo;
import com.nogemasa.management.service.order.IGodownOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <br/>create at 15-8-19
 *
 * @author liuxh
 * @since 1.0.0
 */
@Service("godownOrderService")
public class GodownOrderServiceImpl implements IGodownOrderService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private GodownOrderMapper godownOrderMapper;
    @Autowired
    private GodownOrderListMapper godownOrderListMapper;
    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    public GodownOrderPojo getGodownOrderBySid(String godownSid) {
        if(godownSid == null || godownSid.isEmpty()) {
            return null;
        }
        return godownOrderMapper.getGodownOrderBySid(godownSid);
    }

    @Override
    public List<GodownOrderPojo> getAllGodownOrders() {
        return godownOrderMapper.getGodownOrderList();
    }

    @Override
    public void addGodownOrder(GodownOrderPojo godownOrderPojo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPojo userPojo = (UserPojo) authentication.getPrincipal();
        godownOrderPojo.setSid(null);
        godownOrderPojo.setCreateUser(userPojo);
        godownOrderMapper.addGodownOrder(godownOrderPojo);
        logger.debug("入库单插入后的id" + godownOrderPojo.getSid());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized void updateGodownOrder(GodownOrderPojo godownOrderPojo) {
        if (godownOrderPojo == null || godownOrderPojo.getSid() == null || godownOrderPojo.getSid().isEmpty()) {
            return;
        }
        GodownOrderPojo orderPojo = godownOrderMapper.getGodownOrderBySid(godownOrderPojo.getSid());
        if (orderPojo == null || "2".equals(orderPojo.getStatus())) {
            return;
        }
        List<GodownOrderListPojo> list = godownOrderListMapper.getAllGodownOrderList(godownOrderPojo.getSid());
        for (GodownOrderListPojo l : list) {
            InventoryPojo inventory = inventoryMapper.getInventory(godownOrderPojo.getStore().getSid(),
                    l.getGoods().getSid());
            if (inventory == null) {
                inventoryMapper.addInventory(godownOrderPojo.getStore().getSid(), l.getGoods().getSid(), l.getCount());
            } else {
                inventoryMapper.updateInventoryCount(godownOrderPojo.getStore().getSid(),
                        l.getGoods().getSid(), l.getCount());
            }
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPojo userPojo = (UserPojo) authentication.getPrincipal();
        godownOrderPojo.setCommitUser(userPojo);
        godownOrderMapper.updateGodownOrder(godownOrderPojo);
    }

    @Override
    public void deleteGodownOrder(GodownOrderPojo godownOrderPojo) {
        if (godownOrderPojo == null || godownOrderPojo.getSid() == null || godownOrderPojo.getSid().isEmpty()) {
            return;
        }
        List<GodownOrderListPojo> list = godownOrderListMapper.getAllGodownOrderList(godownOrderPojo.getSid());
        if (list.size() != 0) {
            return;
        }
        GodownOrderPojo order = godownOrderMapper.getGodownOrderBySid(godownOrderPojo.getSid());
        if (order.getEndTime() != null) {
            return;
        }
        godownOrderMapper.deleteGodownOrder(godownOrderPojo.getSid());
    }
}
