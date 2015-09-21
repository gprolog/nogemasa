package com.nogemasa.management.pojo;

/**
 * <br/>create at 15-8-23
 *
 * @author liuxh
 * @since 1.0.0
 */
public class PricePojo {
    private String sid;// 编号
    private StorePojo store;// 门店
    private GoodsPojo goods;// 商品
    private Double price;// 价格

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
