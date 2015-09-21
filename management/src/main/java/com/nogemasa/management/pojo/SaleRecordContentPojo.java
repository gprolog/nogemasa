package com.nogemasa.management.pojo;

import com.nogemasa.common.pojo.MemberPojo;

import java.util.Date;

/**
 * <br/>create at 15-9-3
 *
 * @author liuxh
 * @since 1.0.0
 */
public class SaleRecordContentPojo {
    private String sid;// 编号
    private StorePojo store;// 门店
    private EmployeePojo employee;// 员工
    private MemberPojo member;// 会员
    private PromotionPojo promotion;// 参加活动
    private Date saleTime;// 销售时间
    private String costType;// 收银方式
    private double totalPrice;// 商品总原价
    private double totalCost;// 商品总售价

    public SaleRecordContentPojo() {
    }

    public SaleRecordContentPojo(String storeSid, String employeeSid, String memberCardNo,
            String promotionSid, String costType, double totalPrice, double totalCost) {
        this.store = new StorePojo();
        this.store.setSid(storeSid);
        this.employee = new EmployeePojo();
        this.employee.setSid(employeeSid);
        this.member = new MemberPojo();
        this.member.setCard_no(memberCardNo);
        this.promotion = new PromotionPojo();
        this.promotion.setSid(promotionSid);
        this.costType = costType;
        this.totalPrice = totalPrice;
        this.totalCost = totalCost;
    }

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

    public Date getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(Date saleTime) {
        this.saleTime = saleTime;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
