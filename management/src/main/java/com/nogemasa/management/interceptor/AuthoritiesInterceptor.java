package com.nogemasa.management.interceptor;

import com.nogemasa.management.pojo.StorePojo;
import com.nogemasa.management.pojo.UserPojo;
import com.nogemasa.management.service.store.IStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * 权限控制拦截器，与mc_djs.ftl结合控制前台显示用户可以操作的功能。
 */
public class AuthoritiesInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private IStoreService storeService;

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            request.setAttribute("authorities", authorities);
            request.setAttribute("isAdmin", false);
            authorities.stream().filter(authority -> "ADMIN".equals(authority.getAuthority()))
                    .forEach(authority -> request.setAttribute("isAdmin", true));
        }
        UserPojo userPojo = (UserPojo) authentication.getPrincipal();
        StorePojo store = storeService.getStoreByUserSid(userPojo.getSid());
        request.setAttribute("storeSid", store.getSid());
        request.setAttribute("storeName", store.getName());
        return super.preHandle(request, response, handler);
    }
}
