package com.nogemasa.management.service.goods;

import com.nogemasa.management.pojo.GoodsPojo;

import java.util.List;

/**
 * 商品服务接口
 * <br/>create at 15-8-1
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface IGoodsService {
    /**
     * 获取所有商品列表
     *
     * @return 商品列表
     */
    List<GoodsPojo> getAllGoods();

    List<GoodsPojo> listPutInGoods();

    /**
     * 根据分类获取商品列表
     *
     * @param categorySid 分类编号
     * @return 商品列表
     */
    List<GoodsPojo> getGoodsListByCategory(String categorySid);

    /**
     * 根据条形码获取商品信息
     *
     * @param sn 条形码
     * @return 商品信息
     */
    GoodsPojo getGoods(String sn);

    void addGoods(GoodsPojo goodsPojo);

    void updateGoods(GoodsPojo goodsPojo);

    void deleteGoods(GoodsPojo goodsPojo);

    int updateGoodsPutInStatus(GoodsPojo goodsPojo);

    int resetAllPutInStatus();
}
