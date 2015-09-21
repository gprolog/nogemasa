package com.nogemasa.management.pojo;

/**
 * 门店信息
 * <br/>create at 15-8-1
 *
 * @author liuxh
 * @since 1.0.0
 */
public class StorePojo {
    private String sid;// 编号
    private String name;// 店名
    private String address;// 地址
    private String phone;// 电话
    private String businessModel;// 经营模式：分店、加盟
    private String shopowner;// 店长
    private boolean enabled;// 是否有效

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBusinessModel() {
        return businessModel;
    }

    public void setBusinessModel(String businessModel) {
        this.businessModel = businessModel;
    }

    public String getShopowner() {
        return shopowner;
    }

    public void setShopowner(String shopowner) {
        this.shopowner = shopowner;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
