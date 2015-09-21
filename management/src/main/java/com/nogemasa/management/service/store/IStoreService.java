package com.nogemasa.management.service.store;

import com.nogemasa.management.pojo.StorePojo;

import java.util.List;

/**
 * <br/>create at 15-8-2
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface IStoreService {
    StorePojo getStoreByUserSid(String userSid);

    List<StorePojo> getAllStore();

    void addStore(StorePojo storePojo);

    void enabledStore(StorePojo storePojo);

    boolean deleteStore(StorePojo storePojo);

    void updateStore(StorePojo storePojo);
}
