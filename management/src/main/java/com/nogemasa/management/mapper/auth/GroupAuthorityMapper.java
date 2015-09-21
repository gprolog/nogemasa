package com.nogemasa.management.mapper.auth;

import com.nogemasa.management.pojo.AuthorityPojo;
import com.nogemasa.management.pojo.GroupAuthority;
import com.nogemasa.management.pojo.GroupPojo;

import java.util.List;

/**
 * <br/>create at 15-7-8
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface GroupAuthorityMapper {
    /**
     * 根据用户组列表查询用户组-权限对于关系列表
     *
     * @param groups 用户组列表
     * @return 用户组-权限列表
     */
    List<GroupAuthority> findAuthorityOfGroups(List<GroupPojo> groups);

    /**
     * 根据权限数据查询查询用户组-权限对于关系列表
     *
     * @param authority 权限数据
     * @return 用户组-权限列表
     */
    List<GroupAuthority> findAuthorityOfGroupByAuthority(AuthorityPojo authority);

    /**
     * 向用户组-权限关系表中插入数据
     *
     * @param groupAuthority 用户组-权限数据
     */
    void insertAuthorityOfGroup(GroupAuthority groupAuthority);

    /**
     * 从用户组-权限关系表中删除数据
     *
     * @param groupAuthority 用户组-权限数据
     */
    void deleteAuthorityOfGroup(GroupAuthority groupAuthority);
}
