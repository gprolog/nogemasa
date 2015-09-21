package com.nogemasa.management.mapper.goods;

import com.nogemasa.management.pojo.InventoryPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <br/>create at 15-8-23
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface InventoryMapper {
    List<InventoryPojo> getInventoryList();

    InventoryPojo getInventory(@Param("storeSid") String storeSid, @Param("goodsSid") String goodsSid);

    int addInventory(@Param("storeSid") String storeSid, @Param("goodsSid") String goodsSid,
            @Param("count") int count);

    int updateInventoryCount(@Param("storeSid") String storeSid, @Param("goodsSid") String goodsSid,
            @Param("deltaCount") int deltaCount);

    int reduceInventoryCount4Sale(@Param("storeSid") String storeSid, @Param("goodsSid") String goodsSid);
}
