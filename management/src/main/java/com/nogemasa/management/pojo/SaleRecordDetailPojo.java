package com.nogemasa.management.pojo;

/**
 * <br/>create at 15-9-3
 *
 * @author liuxh
 * @since 1.0.0
 */
public class SaleRecordDetailPojo {
    private String sid;// 编号
    private SaleRecordContentPojo content;// 销售单
    private GoodsPojo goods;// 商品
    private double goodsPrice;// 商品原价
    private double goodsCost;// 商品售价

    public SaleRecordDetailPojo() {
    }

    public SaleRecordDetailPojo(String contentSid, String goodsSn, double goodsCost, double goodsPrice) {
        this.content = new SaleRecordContentPojo();
        this.content.setSid(contentSid);
        this.goods = new GoodsPojo();
        this.goods.setSn(goodsSn);
        this.goodsCost = goodsCost;
        this.goodsPrice = goodsPrice;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public SaleRecordContentPojo getContent() {
        return content;
    }

    public void setContent(SaleRecordContentPojo content) {
        this.content = content;
    }

    public GoodsPojo getGoods() {
        return goods;
    }

    public void setGoods(GoodsPojo goods) {
        this.goods = goods;
    }

    public double getGoodsCost() {
        return goodsCost;
    }

    public void setGoodsCost(double goodsCost) {
        this.goodsCost = goodsCost;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }
}
