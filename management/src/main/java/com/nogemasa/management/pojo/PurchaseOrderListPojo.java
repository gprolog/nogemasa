package com.nogemasa.management.pojo;

/**
 * 采购订单详细
 * <br/>create at 15-8-5
 *
 * @author liuxh
 * @since 1.0.0
 */
public class PurchaseOrderListPojo {
    private String sid;
    private PurchaseOrderPojo purchaseOrder;
    private GoodsPojo goods;
    private int count;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public PurchaseOrderPojo getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrderPojo purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
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
