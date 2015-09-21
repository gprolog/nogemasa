package com.nogemasa.management.mapper.auth;

import com.nogemasa.management.pojo.GroupPojo;
import com.nogemasa.management.pojo.UserGroup;
import com.nogemasa.management.pojo.UserPojo;

import java.util.List;

/**
 * <br/>create at 15-7-8
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface UserGroupMapper {
    /**
     * 根据用户数据查询用户-用户组对于关系列表
     *
     * @param user 用户数据
     * @return 用户-用户组列表
     */
    List<UserGroup> findGroupsOfUser(UserPojo user);

    /**
     * 根据用户组数据查询用户-用户组对于关系列表
     *
     * @param group 用户组数据
     * @return 用户-用户组列表
     */
    List<UserGroup> findGroupsOfUserByGroup(GroupPojo group);

    /**
     * 向用户-用户组关系表中插入数据
     *
     * @param userGroup 用户-用户组关系数据
     */
    void insertGroupOfUser(UserGroup userGroup);

    /**
     * 删除用户-用户组关系表中数据
     *
     * @param userGroup 用户-用户组关系数据
     */
    void deleteGroupOfUser(UserGroup userGroup);
}
