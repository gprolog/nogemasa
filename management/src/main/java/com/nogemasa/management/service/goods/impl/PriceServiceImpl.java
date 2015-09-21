package com.nogemasa.management.service.goods.impl;

import com.nogemasa.management.mapper.goods.PriceMapper;
import com.nogemasa.management.pojo.PricePojo;
import com.nogemasa.management.service.goods.IPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <br/>create at 15-8-23
 *
 * @author liuxh
 * @since 1.0.0
 */
@Service("priceService")
public class PriceServiceImpl implements IPriceService {
    @Autowired
    private PriceMapper priceMapper;

    @Override
    public List<PricePojo> getPriceListByGoodsSid(String goodsSid) {
        if (goodsSid == null || goodsSid.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return priceMapper.getPriceListByGoodsSid(goodsSid);
    }

    @Override
    public PricePojo getPriceInfo(String goodsSn, String storeSid) {
        if (goodsSn == null || storeSid == null) {
            return null;
        }
        return priceMapper.getGoodsPrice(goodsSn, storeSid);
    }

    @Override
    public int addPricePojo(PricePojo price) {
        if (price == null || price.getStore() == null || price.getGoods() == null) {
            return -1;
        }
        return priceMapper.addPricePojo(price);
    }

    @Override
    public int updatePricePojo(String storeSid, String goodsSid, double price) {
        if (storeSid == null || goodsSid == null) {
            return -1;
        }
        return priceMapper.updatePricePojo(storeSid, goodsSid, price);
    }
}
