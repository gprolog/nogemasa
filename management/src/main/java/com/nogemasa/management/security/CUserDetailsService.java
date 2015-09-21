package com.nogemasa.management.security;

import com.nogemasa.management.mapper.auth.AuthorityMapper;
import com.nogemasa.management.mapper.auth.GroupMapper;
import com.nogemasa.management.mapper.auth.UserMapper;
import com.nogemasa.management.pojo.AuthorityPojo;
import com.nogemasa.management.pojo.GroupPojo;
import com.nogemasa.management.pojo.UserPojo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
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
}
