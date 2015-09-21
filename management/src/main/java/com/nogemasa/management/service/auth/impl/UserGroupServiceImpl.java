package com.nogemasa.management.service.auth.impl;

import com.nogemasa.management.mapper.auth.GroupMapper;
import com.nogemasa.management.mapper.auth.UserGroupMapper;
import com.nogemasa.management.mapper.auth.UserMapper;
import com.nogemasa.management.pojo.GroupPojo;
import com.nogemasa.management.pojo.UserGroup;
import com.nogemasa.management.pojo.UserPojo;
import com.nogemasa.management.service.auth.IUserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <br/>create at 15-7-8
 *
 * @author liuxh
 * @since 1.0.0
 */
@Service("userGroupService")
public class UserGroupServiceImpl implements IUserGroupService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserGroupMapper userGroupMapper;
    @Autowired
    private GroupMapper groupMapper;

    @Override
    public List<UserGroup> getGroupsByUserSid(String userSid) {
        UserPojo user = userMapper.loadUserBySid(userSid);
        if(user == null) {
            return Collections.emptyList();
        }
        List<UserGroup> userGroups = userGroupMapper.findGroupsOfUser(user);
        List<GroupPojo> groups = groupMapper.getAllGroups(null);
        List<UserGroup> list = new ArrayList<>(groups.size());
        // 1、遍历用户-组列表，放入map中
        Map<String, UserGroup> map = new HashMap<>(userGroups.size());
        for (UserGroup userGroup : userGroups) {
            map.put(userGroup.getGroupSid(), userGroup);
        }
        // 2、遍历全用户组列表，将已经存在于map中的，enabled字段设为true
        for (GroupPojo group : groups) {
            UserGroup ug = new UserGroup();
            ug.setUserSid(user.getSid());
            ug.setGroupSid(group.getSid());
            ug.setGroupName(group.getGroupName());
            ug.setGroupDesc(group.getGroupDesc());
            UserGroup userGroup = map.get(group.getSid());
            if(userGroup != null) {
                ug.setSid(userGroup.getSid());
                ug.setEnabled(true);
            }
            list.add(ug);
        }
        return list;
    }

    @Override
    public void insertGroupOfUser(UserGroup userGroup) {
        if (userGroup == null || userGroup.getUserSid() == null || userGroup.getUserSid()
                .isEmpty() || userGroup.getGroupSid() == null || userGroup.getGroupSid().isEmpty()) {
            return;
        }
        userGroupMapper.insertGroupOfUser(userGroup);
    }

    @Override
    public void deleteGroupOfUser(UserGroup userGroup) {
        if (userGroup == null || userGroup.getSid() == null || userGroup.getSid().isEmpty()) {
            return;
        }
        userGroupMapper.deleteGroupOfUser(userGroup);
    }
}
