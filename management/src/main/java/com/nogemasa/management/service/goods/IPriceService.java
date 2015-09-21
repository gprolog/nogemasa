package com.nogemasa.management.service.goods;

import com.nogemasa.management.pojo.PricePojo;

import java.util.List;

/**
 * <br/>create at 15-8-23
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface IPriceService {
    List<PricePojo> getPriceListByGoodsSid(String goodsSid);

    PricePojo getPriceInfo(String goodsSn, String storeSid);

    int addPricePojo(PricePojo price);

    int updatePricePojo(String storeSid, String goodsSid, double price);
}
