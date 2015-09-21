package com.nogemasa.management.service.auth;

import com.nogemasa.management.pojo.UserPojo;

import java.util.List;

/**
 * <br/>create at 15-7-8
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface IUserService {
    /**
     * 查询用户列表
     *
     * @return 用户列表
     */
    List<UserPojo> userList();

    /**
     * 创建用户信息
     *
     * @param user 用户信息
     * @return 包含主键用户信息。如果用户名已经存在，返回新对象;如果用户名不存在，返回原对象。
     */
    UserPojo createUser(UserPojo user);

    /**
     * 删除用户信息
     *
     * @param user 用户信息
     * @return 是否删除成功
     */
    boolean deleteUser(UserPojo user);

    /**
     * 使用户重新生效
     *
     * @param user 待生效用户信息
     */
    void enabledUser(UserPojo user);
}
