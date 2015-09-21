package com.nogemasa.management.mapper.auth;

import com.nogemasa.management.pojo.UserPojo;

import java.util.List;

/**
 * <br/>create at 15-7-3
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface UserMapper {
    /**
     * 根据用户编号查询用户信息
     *
     * @param sid 用户编号
     * @return 用户信息
     */
    UserPojo loadUserBySid(String sid);

    /**
     * 根据用户名查询用户列表
     *
     * @param username 用户名
     * @return 查询到的用户，没有匹配返回 {@code null}
     */
    UserPojo loadUserByUsername(String username);

    /**
     * 查询用户列表
     *
     * @return 用户列表
     */
    List<UserPojo> getAllUsers();

    /**
     * 插入用户数据
     *
     * @param user 用户数据
     */
    void addUser(UserPojo user);

    /**
     * 删除用户数据
     *
     * @param sid 用户ID
     */
    void deleteUser(String sid);

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     */
    void updateUser(UserPojo user);
}
