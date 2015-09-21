package com.nogemasa.common.auth.mapper;


import com.nogemasa.common.auth.pojo.AuthorityPojo;
import com.nogemasa.common.auth.pojo.GroupPojo;

import java.util.List;

/**
 * <br/>create at 15-7-3
 *
 * @author liuxh
 * @since 1.0
 */
public interface AuthorityMapper {
    /**
     * 根据用户组查询权限
     *
     * @param groups 用户组
     * @return 权限列表
     */
    List<AuthorityPojo> findAuthorityOfGroups(List<GroupPojo> groups);

    /**
     * 根据权限字段authority查询权限列表
     *
     * @param authority 权限数据。通过sid、authority参数查询数据。
     * @return 权限列表
     */
    List<AuthorityPojo> getAllAuthorities(AuthorityPojo authority);

    /**
     * 添加权限数据
     *
     * @param authority 权限数据
     */
    void addAuthority(AuthorityPojo authority);

    /**
     * 修改权限信息
     *
     * @param authority 权限信息
     */
    void updateAuthority(AuthorityPojo authority);

    /**
     * 删除权限信息
     *
     * @param sid 权限主键
     */
    void deleteAuthority(String sid);
}
