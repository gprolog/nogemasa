package com.nogemasa.management.pojo;

import com.nogemasa.common.pojo.MemberPojo;

import java.util.Date;

/**
 * <br/>create at 15-8-24
 *
 * @author liuxh
 * @since 1.0.0
 */
public class SaleRecordPojo {
    private String sid;
    private StorePojo store;
    private EmployeePojo employee;
    private GoodsPojo goods;
    private MemberPojo member;
    private PromotionPojo promotion;
    private double goodsOriginalCost;
    private double goodsPrice;
    private Date saleTime;

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

    public EmployeePojo getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeePojo employee) {
        this.employee = employee;
    }

    public GoodsPojo getGoods() {
        return goods;
    }

    public void setGoods(GoodsPojo goods) {
        this.goods = goods;
    }

    public MemberPojo getMember() {
        return member;
    }

    public void setMember(MemberPojo member) {
        this.member = member;
    }

    public PromotionPojo getPromotion() {
        return promotion;
    }

    public void setPromotion(PromotionPojo promotion) {
        this.promotion = promotion;
    }

    public double getGoodsOriginalCost() {
        return goodsOriginalCost;
    }

    public void setGoodsOriginalCost(double goodsOriginalCost) {
        this.goodsOriginalCost = goodsOriginalCost;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Date getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(Date saleTime) {
        this.saleTime = saleTime;
    }
}
