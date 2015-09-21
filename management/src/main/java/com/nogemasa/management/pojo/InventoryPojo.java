package com.nogemasa.management.pojo;

/**
 * 库存实体
 * <br/>create at 15-8-1
 *
 * @author liuxh
 * @since 1.0.0
 */
public class InventoryPojo {
    private String sid;// 编号
    private StorePojo store;// 门店
    private GoodsPojo goods;// 商品
    private int count;// 库存量

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public StorePojo getStore() {
        return store;
    }

    public void setStore(StorePojo store) {
        this.store = store;
    }

    public GoodsPojo getGoods() {
        return goods;
    }

    public void setGoods(GoodsPojo goods) {
        this.goods = goods;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
