package com.nogemasa.management.mapper.goods;

import com.nogemasa.management.pojo.GoodsPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <br/>create at 15-8-1
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface GoodsMapper {
    GoodsPojo getGoodsBySid(String sid);

    GoodsPojo getGoodsBySn(String sn);

    List<GoodsPojo> getGoodsList(Map<String, String> params);

    int addGoods(GoodsPojo goodsPojo);

    void updateGoods(GoodsPojo goodsPojo);

    void deleteGoods(String sid);

    int updateGoodsPutInStatus(@Param("sid") String sid, @Param("putInStatus") String putInStatus);

    int resetAllPutInStatus();
}
