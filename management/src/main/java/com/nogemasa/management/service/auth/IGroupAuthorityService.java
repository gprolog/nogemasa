package com.nogemasa.management.service.auth;

import com.nogemasa.management.pojo.GroupAuthority;

import java.util.List;

/**
 * <br/>create at 15-7-7
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface IGroupAuthorityService {
    /**
     * 根据用户组名查询权限列表。如果该用户组选中某权限，GroupAuthority.enabled为true，否则为false。
     *
     * @param groupSid 用户组编号
     * @return 经过格式化后的权限列表
     */
    List<GroupAuthority> getAuthoritiesByGroupSid(String groupSid);

    /**
     * 向用户组-权限关系表中插入数据
     *
     * @param groupAuthority 用户组-权限数据
     */
    void insertAuthorityOfGroup(GroupAuthority groupAuthority);

    /**
     * 从用户组-权限关系表中删除数据
     * @param groupAuthority 用户组-权限数据
     */
    void deleteAuthorityOfGroup(GroupAuthority groupAuthority);
}
