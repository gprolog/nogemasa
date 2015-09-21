package com.nogemasa.management.service.auth;

import com.nogemasa.management.pojo.AuthorityPojo;

import java.util.List;

/**
 * <br/>create at 15-7-8
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface IAuthorityService {
    /**
     * 查询权限列表
     *
     * @return 权限列表
     */
    List<AuthorityPojo> authorityList();

    /**
     * 增加权限数据
     *
     * @param authority 权限数据
     */
    void addAuthotiry(AuthorityPojo authority);

    /**
     * 修改权限数据
     *
     * @param authority 权限数据
     */
    void updateAuthority(AuthorityPojo authority);

    /**
     * 删除权限数据
     *
     * @param authority 权限数据
     */
    void deleteAuthority(AuthorityPojo authority);
}
