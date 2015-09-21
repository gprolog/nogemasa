package com.nogemasa.management.pojo;

/**
 * <br/>create at 15-9-13
 *
 * @author liuxh
 * @since 1.0.0
 */
public class InventoryCheckDetailPojo {
    private String sid;
    private InventoryCheckContentPojo checkContent;
    private GoodsPojo goods;
    private int preCount;
    private int postCount;
    private int deltaCount;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public InventoryCheckContentPojo getCheckContent() {
        return checkContent;
    }

    public void setCheckContent(InventoryCheckContentPojo checkContent) {
        this.checkContent = checkContent;
    }

    public GoodsPojo getGoods() {
        return goods;
    }

    public void setGoods(GoodsPojo goods) {
        this.goods = goods;
    }

    public int getPreCount() {
        return preCount;
    }

    public void setPreCount(int preCount) {
        this.preCount = preCount;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public int getDeltaCount() {
        return deltaCount;
    }

    public void setDeltaCount(int deltaCount) {
        this.deltaCount = deltaCount;
    }
}
