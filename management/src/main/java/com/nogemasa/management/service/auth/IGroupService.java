package com.nogemasa.management.service.auth;

import com.nogemasa.management.pojo.GroupPojo;

import java.util.List;

/**
 * <br/>create at 15-7-8
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface IGroupService {
    /**
     * 查询所有用户组数据
     *
     * @return 用户组列表
     */
    List<GroupPojo> groupList();

    /**
     * 添加用户组数据
     *
     * @param consoleGroup 用户组数据
     */
    void addGroup(GroupPojo consoleGroup);

    /**
     * 修改用户组数据
     *
     * @param consoleGroup 用户组数据
     */
    void updateGroup(GroupPojo consoleGroup);

    /**
     * 删除用户组数据
     *
     * @param consoleGroup 用户组数据
     * @return 是否删除成功
     */
    boolean deleteGroup(GroupPojo consoleGroup);
}
