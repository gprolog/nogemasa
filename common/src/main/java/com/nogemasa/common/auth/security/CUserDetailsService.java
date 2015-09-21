package com.nogemasa.common.auth.security;

import com.nogemasa.common.auth.mapper.AuthorityMapper;
import com.nogemasa.common.auth.mapper.GroupMapper;
import com.nogemasa.common.auth.mapper.UserMapper;
import com.nogemasa.common.auth.pojo.AuthorityPojo;
import com.nogemasa.common.auth.pojo.GroupPojo;
import com.nogemasa.common.auth.pojo.UserPojo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * 扩展Spring Security中关于用户权限的控制
 * <br/>create at 15-7-3
 *
 * @author liuxh
 * @since 1.0.0
 */
public class CUserDetailsService implements UserDetailsService {
    private final Log logger = LogFactory.getLog(this.getClass());
    private UserMapper userMapper;
    private GroupMapper groupMapper;
    private AuthorityMapper authorityMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPojo userFound = userMapper.loadUserByUsername(username);
        if (userFound == null) {
            logger.debug("Query returned no results for user \'" + username + "\'");
            throw new UsernameNotFoundException("Username " + username + " not found");
        } else {
            List<GroupPojo> groups = this.groupMapper.loadUserGroups(userFound);
            for (GroupPojo group : groups) {
                userFound.getAuthorities().add(new SimpleGrantedAuthority(group.getGroupName()));
            }
            if (groups.size() > 0) {
                List<AuthorityPojo> authorities = this.authorityMapper.findAuthorityOfGroups(groups);
                for (AuthorityPojo authority : authorities) {
                    userFound.getAuthorities().add(new SimpleGrantedAuthority(authority.getAuthority()));
                }
            }
            return userFound;
        }
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void setGroupMapper(GroupMapper groupMapper) {
        this.groupMapper = groupMapper;
    }

    public void setAuthorityMapper(AuthorityMapper authorityMapper) {
        this.authorityMapper = authorityMapper;
    }
}
