package com.nogemasa.management.pojo;

import java.util.List;

/**
 * 组实体
 * <br/>create at 15-7-3
 *
 * @author liuxh
 * @since 1.0
 */
public class GroupPojo {
    private String sid;// 用户组ID
    private String groupName;// 用户组名称
    private String groupDesc;// 用户组描述
    private List<AuthorityPojo> authorities;// 权限

    /**
     * 用户组ID
     *
     * @return 用户组ID
     */
    public String getSid() {
        return sid;
    }

    /**
     * 用户组ID
     *
     * @param sid 用户组ID
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /**
     * 用户组名称
     *
     * @return 用户组名称
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * 用户组名称
     *
     * @param groupName 用户组名称
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * 用户组描述
     *
     * @return 用户组描述
     */
    public String getGroupDesc() {
        return groupDesc;
    }

    /**
     * 用户组描述
     *
     * @param groupDesc 用户组描述
     */
    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    /**
     * 权限
     *
     * @return 权限
     */
    public List<AuthorityPojo> getAuthorities() {
        return authorities;
    }

    /**
     * 权限
     *
     * @param authorities 权限
     */
    public void setAuthorities(List<AuthorityPojo> authorities) {
        this.authorities = authorities;
    }
}
