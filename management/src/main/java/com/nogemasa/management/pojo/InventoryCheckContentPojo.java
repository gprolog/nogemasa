package com.nogemasa.management.pojo;

import java.util.Date;

/**
 * <br/>create at 15-9-13
 *
 * @author liuxh
 * @since 1.0.0
 */
public class InventoryCheckContentPojo {
    private String sid;
    private StorePojo store;
    private String status;
    private Date beginTime;
    private Date endTime;
    private UserPojo createUser;
    private UserPojo commitUser;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public UserPojo getCreateUser() {
        return createUser;
    }

    public void setCreateUser(UserPojo createUser) {
        this.createUser = createUser;
    }

    public UserPojo getCommitUser() {
        return commitUser;
    }

    public void setCommitUser(UserPojo commitUser) {
        this.commitUser = commitUser;
    }
}
