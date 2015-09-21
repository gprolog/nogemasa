package com.nogemasa.management.pojo;

import java.util.Date;

/**
 * <br/>create at 15-8-13
 *
 * @author liuxh
 * @since 1.0.0
 */
public class EmployeePojo {
    private String sid;// 员工编号
    private String name;// 员工姓名
    private Date entryTime;// 入职时间
    private Date firedTime;// 离职时间
    private String identityNo;//身份证号
    private Date birthday;// 生日
    private String phone;// 电话
    private String qq;// QQ
    private String status;// 状态：1在职、-1离职
    private StorePojo store;// 所在门店
    private UserPojo user;// 员工

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

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public Date getFiredTime() {
        return firedTime;
    }

    public void setFiredTime(Date firedTime) {
        this.firedTime = firedTime;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public StorePojo getStore() {
        return store;
    }

    public void setStore(StorePojo store) {
        this.store = store;
    }

    public UserPojo getUser() {
        return user;
    }

    public void setUser(UserPojo user) {
        this.user = user;
    }
}
