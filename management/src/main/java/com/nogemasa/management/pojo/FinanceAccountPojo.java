package com.nogemasa.management.pojo;

/**
 * <br/>create at 15-8-30
 *
 * @author liuxh
 * @since 1.0.0
 */
public class FinanceAccountPojo {
    private String sid;
    private String accountType;
    private Double accountRemainingSun;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Double getAccountRemainingSun() {
        return accountRemainingSun;
    }

    public void setAccountRemainingSun(Double accountRemainingSun) {
        this.accountRemainingSun = accountRemainingSun;
    }
}
