package com.nogemasa.common.auth.pojo;

import org.springframework.security.core.GrantedAuthority;

/**
 * 控制台权限
 * <br/>create at 15-7-3
 *
 * @author liuxh
 * @since 1.0
 */
public class AuthorityPojo implements GrantedAuthority {
    private String sid;// 权限ID
    private String authority;// 权限
    private String authorityDesc;// 权限描述

    /**
     * 权限ID
     *
     * @return 权限ID
     */
    public String getSid() {
        return sid;
    }

    /**
     * 权限ID
     *
     * @param sid 权限ID
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /**
     * 权限
     *
     * @return 权限
     */
    public String getAuthority() {
        return authority;
    }

    /**
     * 权限
     *
     * @param authority 权限
     */
    public void setAuthority(String authority) {
        this.authority = authority;
    }

    /**
     * 权限描述
     *
     * @return 权限描述
     */
    public String getAuthorityDesc() {
        return authorityDesc;
    }

    /**
     * 权限描述
     *
     * @param authorityDesc 权限描述
     */
    public void setAuthorityDesc(String authorityDesc) {
        this.authorityDesc = authorityDesc;
    }
}
