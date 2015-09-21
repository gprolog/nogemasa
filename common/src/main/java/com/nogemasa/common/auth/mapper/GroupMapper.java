package com.nogemasa.common.auth.mapper;


import com.nogemasa.common.auth.pojo.GroupPojo;
import com.nogemasa.common.auth.pojo.UserPojo;

import java.util.List;

/**
 * GroupMapper
 * <br/>create at 15-7-6
 *
 * @author liufl
 * @since 1.0.0
 */
public interface GroupMapper {
    /**
     * 根据用户编号查询用户组列表
     *
     * @param user 用户信息
     * @return 用户组列表
     */
    List<GroupPojo> loadUserGroups(UserPojo user);

    /**
     * 根据用户组信息查询用户组列表
     *
     * @param group 用户组列表，如果参数为空，返回所有用户组信息
     * @return 用户组信息列表
     */
    List<GroupPojo> getAllGroups(GroupPojo group);

    /**
     * 添加用户组数据
     *
     * @param group 权限数据
     */
    void addGroup(GroupPojo group);

    /**
     * 修改用户组数据
     *
     * @param group 用户组数据
     */
    void updateGroup(GroupPojo group);

    /**
     * 删除用户组数据
     *
     * @param sid 用户组ID
     */
    void deleteGroup(String sid);
}
