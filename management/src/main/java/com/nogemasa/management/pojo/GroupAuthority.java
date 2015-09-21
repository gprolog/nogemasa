package com.nogemasa.management.pojo;

/**
 * <br/>create at 15-7-7
 *
 * @author liuxh
 * @since 1.0.0
 */
public class GroupAuthority {
    private String sid;// 主键ID
    private String groupSid;// 用户组ID
    private String authoritySid;// 权限ID
    private String authority;// 权限
    private String authorityDesc;// 权限描述
    private boolean enabled;// 用户组是否选中该权限

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getGroupSid() {
        return groupSid;
    }

    public void setGroupSid(String groupSid) {
        this.groupSid = groupSid;
    }

    public String getAuthoritySid() {
        return authoritySid;
    }

    public void setAuthoritySid(String authoritySid) {
        this.authoritySid = authoritySid;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getAuthorityDesc() {
        return authorityDesc;
    }

    public void setAuthorityDesc(String authorityDesc) {
        this.authorityDesc = authorityDesc;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
