package com.nogemasa.management.service.auth.impl;

import com.nogemasa.management.mapper.auth.AuthorityMapper;
import com.nogemasa.management.mapper.auth.GroupAuthorityMapper;
import com.nogemasa.management.mapper.auth.GroupMapper;
import com.nogemasa.management.pojo.AuthorityPojo;
import com.nogemasa.management.pojo.GroupAuthority;
import com.nogemasa.management.pojo.GroupPojo;
import com.nogemasa.management.service.auth.IGroupAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <br/>create at 15-7-7
 *
 * @author liuxh
 * @since 1.0.0
 */
@Service("groupAuthorityService")
public class GroupAuthorityServiceImpl implements IGroupAuthorityService {
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private AuthorityMapper authorityMapper;
    @Autowired
    private GroupAuthorityMapper groupAuthorityMapper;

    @Override
    public List<GroupAuthority> getAuthoritiesByGroupSid(String groupSid) {
        GroupPojo group = new GroupPojo();
        group.setSid(groupSid);
        List<GroupPojo> groups = groupMapper.getAllGroups(group);
        if (groups.size() == 0) {
            return Collections.emptyList();
        }
        List<GroupAuthority> groupAuthorities = groupAuthorityMapper.findAuthorityOfGroups(groups);// 根据用户组查询已经被授予的权限列表
        List<AuthorityPojo> authorities = authorityMapper.getAllAuthorities(null);// 查询所有的权限列表
        List<GroupAuthority> list = new ArrayList<>(authorities.size());// 构造返回列表
        // 1、遍历用户组权限列表，放入map中
        Map<String, GroupAuthority> map = new HashMap<>(groupAuthorities.size());
        for (GroupAuthority groupAuthority : groupAuthorities) {
            map.put(groupAuthority.getAuthoritySid(), groupAuthority);
        }
        // 2、遍历全权限列表，将已经存在于map中的，enabled字段设为true
        for (AuthorityPojo authority : authorities) {
            GroupAuthority ga = new GroupAuthority();
            group = groups.get(0);
            ga.setGroupSid(group.getSid());
            ga.setAuthoritySid(authority.getSid());
            ga.setAuthority(authority.getAuthority());
            ga.setAuthorityDesc(authority.getAuthorityDesc());
            GroupAuthority groupAuthority = map.get(authority.getSid());
            if (groupAuthority != null) {
                ga.setEnabled(true);
                ga.setSid(groupAuthority.getSid());
            }
            list.add(ga);
        }
        return list;
    }

    @Override
    public void insertAuthorityOfGroup(GroupAuthority groupAuthority) {
        if (groupAuthority == null || groupAuthority.getGroupSid() == null || groupAuthority.getGroupSid()
                .isEmpty() || groupAuthority.getAuthoritySid() == null || groupAuthority.getAuthoritySid().isEmpty()) {
            return;
        }
        groupAuthorityMapper.insertAuthorityOfGroup(groupAuthority);
    }

    @Override
    public void deleteAuthorityOfGroup(GroupAuthority groupAuthority) {
        if (groupAuthority == null || groupAuthority.getSid() == null || groupAuthority.getSid().isEmpty()) {
            return;
        }
        groupAuthorityMapper.deleteAuthorityOfGroup(groupAuthority);
    }
}