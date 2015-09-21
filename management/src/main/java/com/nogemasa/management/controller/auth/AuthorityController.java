package com.nogemasa.management.controller.auth;

import com.nogemasa.management.pojo.AuthorityPojo;
import com.nogemasa.management.service.auth.IAuthorityService;
import com.nogemasa.util.UnicodeStringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * 权限管理
 * <br/>create at 15-7-6
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/admin/auth/authority")
public class AuthorityController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IAuthorityService authorityService;

    @RequestMapping("read")
    @ResponseBody
    public JSONObject authorityList(HttpServletRequest request, int start, int limit) {
        JSONObject json = new JSONObject();
        List<AuthorityPojo> authorities = authorityService.authorityList();
        if(authorities.size() == 0) {
            json.put("success", true);
            json.put("total", 0);
            json.put("list", Collections.emptyList());
            return json;
        }
        json.put("success", true);
        json.put("total", authorities.size());
        json.put("list", authorities.subList(start, authorities.size() < start + limit ? authorities.size() : start + limit));
        return json;
    }

    @RequestMapping("create")
    @ResponseBody
    public JSONObject createAuthority(HttpServletRequest request, String data) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的权限数据，请检查！");
            return json;
        }
        try {
            json = JSONObject.fromObject(UnicodeStringUtil.fromUnicodeString(data)); // 解码转换
            AuthorityPojo authority = (AuthorityPojo) JSONObject.toBean(json, AuthorityPojo.class);
            authorityService.addAuthotiry(authority);
            json.put("sid", authority.getSid());
            json.put("success", true);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "该权限已经存在，请检查！");
            log.error("创建权限出错 AuthorityController.createAuthority", e);
            throw new RuntimeException("创建权限出错 AuthorityController.createAuthority", e);
        }
        return json;
    }

    @RequestMapping("update")
    @ResponseBody
    public JSONObject updateAuthority(HttpServletRequest request, String data) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的权限数据，请检查！");
            return json;
        }
        try {
            json = JSONObject.fromObject(UnicodeStringUtil.fromUnicodeString(data));
            AuthorityPojo authority = (AuthorityPojo) JSONObject.toBean(json, AuthorityPojo.class);
            authorityService.updateAuthority(authority);
            json.put("success", true);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "权限数据错误，请检查后再试！");
            log.error("修改权限出错 AuthorityController.updateAuthority", e);
            throw new RuntimeException("修改权限出错 AuthorityController.updateAuthority", e);
        }
        return json;
    }

    @RequestMapping("destroy")
    @ResponseBody
    public JSONObject deleteAuthority(HttpServletRequest request, String data) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的权限数据，请检查！");
            return json;
        }
        try {
            json = JSONObject.fromObject(UnicodeStringUtil.fromUnicodeString(data));
            AuthorityPojo authority = (AuthorityPojo) JSONObject.toBean(json, AuthorityPojo.class);
            authorityService.deleteAuthority(authority);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "权限数据错误，请检查后再试！");
            log.error("删除权限出错 AuthorityController.deleteAuthority", e);
            throw new RuntimeException("删除权限出错 AuthorityController.deleteAuthority", e);
        }
        return json;
    }
}
