package com.nogemasa.management.mapper.goods;

import com.nogemasa.management.pojo.PricePojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <br/>create at 15-8-23
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface PriceMapper {
    List<PricePojo> getPriceListByGoodsSid(String goodsSid);

    PricePojo getGoodsPrice(@Param("goodsSn") String goodsSn, @Param("storeSid") String storeSid);

    int addPricePojo(PricePojo price);

    int updatePricePojo(@Param("storeSid") String storeSid, @Param("goodsSid") String goodsSid,
            @Param("price") double price);
}
