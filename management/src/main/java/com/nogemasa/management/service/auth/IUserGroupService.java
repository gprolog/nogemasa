package com.nogemasa.management.service.auth;

import com.nogemasa.management.pojo.UserGroup;

import java.util.List;

/**
 * <br/>create at 15-7-8
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface IUserGroupService {
    /**
     * 根据用户名查询所属用户组
     *
     * @param userSid 用户名编号
     * @return 用户组数据列表
     */
    List<UserGroup> getGroupsByUserSid(String userSid);

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
