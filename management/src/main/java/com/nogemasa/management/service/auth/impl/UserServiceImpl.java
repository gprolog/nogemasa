package com.nogemasa.management.service.auth.impl;

import com.nogemasa.management.mapper.auth.UserGroupMapper;
import com.nogemasa.management.mapper.auth.UserMapper;
import com.nogemasa.management.pojo.UserGroup;
import com.nogemasa.management.pojo.UserPojo;
import com.nogemasa.management.service.auth.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <br/>create at 15-7-8
 *
 * @author liuxh
 * @since 1.0.0
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserGroupMapper userGroupMapper;

    @Override
    public List<UserPojo> userList() {
        return userMapper.getAllUsers();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public UserPojo createUser(UserPojo user) {
        if (user == null) {
            return null;
        }
        user.setSid(null);
        UserPojo u = userMapper.loadUserByUsername(user.getUsername());
        if (u == null) {
            userMapper.addUser(user);
            log.debug("user插入后的id" + user.getSid());
            return user;
        } else {
            return u;
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean deleteUser(UserPojo user) {
        if (user == null || user.getSid() == null || user.getSid().isEmpty()) {
            return false;
        }
        List<UserGroup> userGroups = userGroupMapper.findGroupsOfUser(user);
        userGroups.forEach(userGroupMapper::deleteGroupOfUser);
        user.setEnabled(!user.isEnabled());
        userMapper.updateUser(user);
        return true;
    }

    @Override
    public void enabledUser(UserPojo user) {
        if(user == null || user.getSid() == null || user.getSid().isEmpty()) {
            return;
        }
        UserPojo u = userMapper.loadUserBySid(user.getSid());
        if(u.isEnabled() == user.isEnabled()) {
            user.setEnabled(!user.isEnabled());
            userMapper.updateUser(user);
        }
    }
}
