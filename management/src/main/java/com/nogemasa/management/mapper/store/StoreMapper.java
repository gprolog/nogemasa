package com.nogemasa.management.mapper.store;

import com.nogemasa.management.pojo.StorePojo;

import java.util.List;

/**
 * <br/>create at 15-8-2
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface StoreMapper {
    List<StorePojo> getStoreList(StorePojo storePojo);

    void addStore(StorePojo storePojo);

    StorePojo loadStoreBySid(String sid);

    StorePojo getStoreByUserSid(String sid);

    void updateStore(StorePojo storePojo);

    void enabledStore(StorePojo storePojo);
}
