package com.nogemasa.management.pojo;

/**
 * 入库单详细
 * <br/>create at 15-8-18
 *
 * @author liuxh
 * @since 1.0.0
 */
public class GodownOrderListPojo {
    private String sid;
    private GodownOrderPojo godownOrder;
    private GoodsPojo goods;
    private int count;
    private double costPrice;
    private double price;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public GodownOrderPojo getGodownOrder() {
        return godownOrder;
    }

    public void setGodownOrder(GodownOrderPojo godownOrder) {
        this.godownOrder = godownOrder;
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

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
