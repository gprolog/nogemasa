package com.nogemasa.management.service.goods.impl;

import com.google.common.collect.Maps;
import com.nogemasa.management.mapper.goods.GoodsMapper;
import com.nogemasa.management.pojo.GoodsPojo;
import com.nogemasa.management.service.goods.IGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <br/>create at 15-8-1
 *
 * @author liuxh
 * @since 1.0.0
 */
@Service("goodsService")
public class GoodsServiceImpl implements IGoodsService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<GoodsPojo> getAllGoods() {
        return this.goodsMapper.getGoodsList(Maps.newHashMap());
    }

    @Override
    public List<GoodsPojo> listPutInGoods() {
        Map<String, String> params = Maps.newHashMap();
        params.put("putInStatus", "2");
        return this.goodsMapper.getGoodsList(params);
    }

    @Override
    public List<GoodsPojo> getGoodsListByCategory(String categorySid) {
        Map<String, String> params = Maps.newHashMap();
        params.put("categorySid", categorySid);
        return this.goodsMapper.getGoodsList(params);
    }

    @Override
    public GoodsPojo getGoods(String sn) {
        Map<String, String> params = Maps.newHashMap();
        params.put("sn", sn);
        List<GoodsPojo> goodsList = goodsMapper.getGoodsList(params);
        if (goodsList.size() == 0) {
            return null;
        }
        return goodsList.get(0);
    }

    @Override
    public void addGoods(GoodsPojo goods) {
        goods.setSid(null);
        Map<String, String> params = Maps.newHashMap();
        params.put("sn", goods.getSn());
        List<GoodsPojo> goodsList = goodsMapper.getGoodsList(params);
        if (goodsList.size() == 0) {
//            String sn;
//            while (true) {
//                sn = UUID.randomUUID().toString();
//                sn = sn.replaceAll("-", "");
//                sn = sn.substring(0, 16);
//                GoodsPojo g = getGoods(sn);
//                if (g == null) {
//                    break;
//                }
//            }
//            goods.setSn(sn);
            goodsMapper.addGoods(goods);
            log.debug("group插入后的id" + goods.getSid());
        }
    }

    @Override
    public void updateGoods(GoodsPojo goods) {
        if (goods == null
                || goods.getSid() == null || goods.getSid().isEmpty()
                || goods.getName() == null || goods.getName().isEmpty()) {
            return;
        }
        goodsMapper.updateGoods(goods);
    }

    @Override
    public void deleteGoods(GoodsPojo goods) {
        if (goods == null || goods.getSid() == null || goods.getSid().isEmpty()) {
            return;
        }
        goodsMapper.deleteGoods(goods.getSid());
    }

    @Override
    public int updateGoodsPutInStatus(GoodsPojo goods) {
        if (goods == null || goods.getSid() == null || goods.getSid().isEmpty()) {
            return -1;
        }
        if ("1".equals(goods.getPutInStatus())) {
            return goodsMapper.updateGoodsPutInStatus(goods.getSid(), "2");
        } else {
            return goodsMapper.updateGoodsPutInStatus(goods.getSid(), "1");
        }
    }

    @Override
    public int resetAllPutInStatus() {
        return goodsMapper.resetAllPutInStatus();
    }
}
