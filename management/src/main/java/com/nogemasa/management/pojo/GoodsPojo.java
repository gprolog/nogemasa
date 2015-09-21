package com.nogemasa.management.pojo;

/**
 * 商品实体
 * <br/>create at 15-8-1
 *
 * @author liuxh
 * @since 1.0.0
 */
public class GoodsPojo {
    private String sid;// 编号
    private String name;// 名称
    private String goodsSize;// 大小
    private String color;// 颜色
    private String fabrics;// 材质
    private String style;// 款式
    private String sn;// 条形码
    private String putInStatus;
//    private CategoryPojo category;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoodsSize() {
        return goodsSize;
    }

    public void setGoodsSize(String goodsSize) {
        this.goodsSize = goodsSize;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFabrics() {
        return fabrics;
    }

    public void setFabrics(String fabrics) {
        this.fabrics = fabrics;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getPutInStatus() {
        return putInStatus;
    }

    public void setPutInStatus(String putInStatus) {
        this.putInStatus = putInStatus;
    }
}
