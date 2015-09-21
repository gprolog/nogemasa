package com.nogemasa.management.service.auth.impl;

import com.nogemasa.management.mapper.auth.AuthorityMapper;
import com.nogemasa.management.mapper.auth.GroupAuthorityMapper;
import com.nogemasa.management.pojo.AuthorityPojo;
import com.nogemasa.management.pojo.GroupAuthority;
import com.nogemasa.management.service.auth.IAuthorityService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
@Service("authorityService")
public class AuthorityServiceImpl implements IAuthorityService {
    private final Log log = LogFactory.getLog(getClass());
    @Autowired
    private AuthorityMapper authorityMapper;
    @Autowired
    private GroupAuthorityMapper groupAuthorityMapper;

    @Override
    public List<AuthorityPojo> authorityList() {
        return authorityMapper.getAllAuthorities(null);
    }

    @Override
    public void addAuthotiry(AuthorityPojo authority) {
        authority.setSid(null);
        List<AuthorityPojo> authorities = authorityMapper.getAllAuthorities(authority);
        if (authorities.size() == 0) {
            authorityMapper.addAuthotiry(authority);
            log.debug("authority插入后的id" + authority.getSid());
        }
    }

    @Override
    public void updateAuthority(AuthorityPojo authority) {
        if (authority == null
                || authority.getSid() == null || authority.getSid().isEmpty()
                || authority.getAuthority() == null || authority.getAuthority().isEmpty()) {
            return;
        }
        authorityMapper.updateAuthority(authority);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAuthority(AuthorityPojo authority) {
        if (authority == null || authority.getSid() == null || authority.getSid().isEmpty()) {
            return;
        }
        // 1、删除用户组-权限关系数据
        List<GroupAuthority> groupAuthorities = groupAuthorityMapper.findAuthorityOfGroupByAuthority(authority);
        groupAuthorities.forEach(groupAuthorityMapper::deleteAuthorityOfGroup);
        // 2、删除权限数据
        authorityMapper.deleteAuthority(authority.getSid());
    }
}
