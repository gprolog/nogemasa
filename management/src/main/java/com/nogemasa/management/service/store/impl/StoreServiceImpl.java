package com.nogemasa.management.service.store.impl;

import com.nogemasa.management.mapper.store.StoreMapper;
import com.nogemasa.management.pojo.StorePojo;
import com.nogemasa.management.service.store.IStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <br/>create at 15-8-2
 *
 * @author liuxh
 * @since 1.0.0
 */
@Service("storeService")
public class StoreServiceImpl implements IStoreService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private StoreMapper storeMapper;

    @Override
    public StorePojo getStoreByUserSid(String userSid) {
        if(userSid == null || userSid.trim().isEmpty()) {
            return null;
        }
        return storeMapper.getStoreByUserSid(userSid);
    }

    @Override
    public List<StorePojo> getAllStore() {
        return storeMapper.getStoreList(null);
    }

    @Override
    public void addStore(StorePojo storePojo) {
        storePojo.setSid(null);
        List<StorePojo> storeList = storeMapper.getStoreList(storePojo);
        if (storeList.size() == 0) {
            storeMapper.addStore(storePojo);
            log.debug("group插入后的id" + storePojo.getSid());
        }
    }

    @Override
    public void enabledStore(StorePojo storePojo) {
        if(storePojo == null || storePojo.getSid() == null || storePojo.getSid().isEmpty()) {
            return;
        }
        StorePojo s = storeMapper.loadStoreBySid(storePojo.getSid());
        if(s.isEnabled() == storePojo.isEnabled()) {
            storePojo.setEnabled(!storePojo.isEnabled());
            storeMapper.enabledStore(storePojo);
        }
    }

    @Override
    public boolean deleteStore(StorePojo storePojo) {
        if (storePojo == null || storePojo.getSid() == null || storePojo.getSid().isEmpty()) {
            return false;
        }
        StorePojo s = storeMapper.loadStoreBySid(storePojo.getSid());
        if(s.isEnabled() == storePojo.isEnabled()) {
            storePojo.setEnabled(!storePojo.isEnabled());
            storeMapper.enabledStore(storePojo);
        }
        return true;
    }

    @Override
    public void updateStore(StorePojo storePojo) {
        if (storePojo == null
                || storePojo.getSid() == null || storePojo.getSid().isEmpty()
                || storePojo.getName() == null || storePojo.getName().isEmpty()) {
            return;
        }
        storeMapper.updateStore(storePojo);
    }
}
