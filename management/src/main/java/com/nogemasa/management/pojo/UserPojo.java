package com.nogemasa.management.pojo;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * <br/>create at 15-7-3
 *
 * @author liuxh
 * @since 1.0
 */
public class UserPojo implements UserDetails {
    private String sid;
    private String username;
    private String password;
    private boolean enabled;
    private final Set<GrantedAuthority> authorities = Sets.newConcurrentHashSet();

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object rhs) {
        return rhs instanceof UserPojo && username.equals(((UserPojo) rhs).username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
