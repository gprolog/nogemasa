package com.nogemasa.management.service.auth.impl;

import com.nogemasa.management.mapper.auth.GroupAuthorityMapper;
import com.nogemasa.management.mapper.auth.GroupMapper;
import com.nogemasa.management.mapper.auth.UserGroupMapper;
import com.nogemasa.management.pojo.GroupAuthority;
import com.nogemasa.management.pojo.GroupPojo;
import com.nogemasa.management.pojo.UserGroup;
import com.nogemasa.management.service.auth.IGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * <br/>create at 15-7-8
 *
 * @author liuxh
 * @since 1.0.0
 */
@Service("groupService")
public class GroupServiceImpl implements IGroupService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private GroupAuthorityMapper groupAuthorityMapper;
    @Autowired
    private UserGroupMapper userGroupMapper;

    @Override
    public List<GroupPojo> groupList() {
        return groupMapper.getAllGroups(null);
    }

    @Override
    public void addGroup(GroupPojo group) {
        group.setSid(null);
        List<GroupPojo> groups = groupMapper.getAllGroups(group);
        if (groups.size() == 0) {
            groupMapper.addGroup(group);
            log.debug("group插入后的id" + group.getSid());
        }
    }

    @Override
    public void updateGroup(GroupPojo group) {
        if (group == null
                || group.getSid() == null || group.getSid().isEmpty()
                || group.getGroupName() == null || group.getGroupName().isEmpty()) {
            return;
        }
        groupMapper.updateGroup(group);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteGroup(GroupPojo group) {
        if(group == null || group.getSid() == null || group.getSid().isEmpty()) {
            return false;
        } else {
            // 1、删除用户组-权限关系表
            List<GroupPojo> groups = Collections.emptyList();
            groups.add(group);
            List<GroupAuthority> groupAuthorities = groupAuthorityMapper.findAuthorityOfGroups(groups);
            groupAuthorities.forEach(groupAuthorityMapper::deleteAuthorityOfGroup);
            // 2、删除用户-用户组关系表
            List<UserGroup> userGroups = userGroupMapper.findGroupsOfUserByGroup(group);
            userGroups.forEach(userGroupMapper::deleteGroupOfUser);
            // 3、删除用户组数据
            groupMapper.deleteGroup(group.getSid());
            return true;
        }
    }
}
