package com.nogemasa.management.controller.auth;

import com.nogemasa.management.pojo.UserPojo;
import com.nogemasa.management.service.auth.IUserService;
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
 * <br/>create at 15-7-8
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/admin/auth/user")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private IUserService userService;

    @RequestMapping("read")
    @ResponseBody
    public JSONObject userList(HttpServletRequest request, int start, int limit) {
        JSONObject json = new JSONObject();
        List<UserPojo> users = userService.userList();
        if (users.size() == 0) {
            json.put("success", true);
            json.put("total", 0);
            json.put("list", Collections.emptyList());
            return json;
        }
        json.put("success", true);
        json.put("total", users.size());
        json.put("list", users.subList(start, users.size() < start + limit ? users.size() : start + limit));
        return json;
    }

    @RequestMapping("create")
    @ResponseBody
    public JSONObject createUser(HttpServletRequest request, String data) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的用户数据，请检查！");
            return json;
        }
        try {
            json = JSONObject.fromObject(UnicodeStringUtil.fromUnicodeString(data)); // 解码转换
            UserPojo user = (UserPojo) JSONObject.toBean(json, UserPojo.class);
            UserPojo u = userService.createUser(user);
            if (u == user) {// 如果返回同一个对象，说明是新插入数据。
                log.debug("user插入后的id" + user.getSid());
                json.put("sid", user.getSid());
                json.put("success", true);
            } else {
                json.put("success", false);
                json.put("msg", "该用户已经存在，请检查！");
            }
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "用户数据错误，请检查后再试！");
            log.error("创建用户出错 UserController.createUser", e);
            throw new RuntimeException("创建用户出错 UserController.createUser", e);
        }
        return json;
    }

    @RequestMapping("destroy")
    @ResponseBody
    public JSONObject deleteUser(HttpServletRequest request, String data) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的用户数据，请检查！");
            return json;
        }
        try {
            json = JSONObject.fromObject(UnicodeStringUtil.fromUnicodeString(data));
            UserPojo user = (UserPojo) JSONObject.toBean(json, UserPojo.class);
            if (userService.deleteUser(user)) {
                json.put("success", true);
            } else {
                json.put("msg", "用户数据错误，请检查后再试！");
                json.put("success", false);
            }
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "用户数据错误，请检查后再试！");
            log.error("删除用户出错 UserController.deleteUser", e);
            throw new RuntimeException("删除用户出错 UserController.deleteUser", e);
        }
        return json;
    }

    @RequestMapping("enabled")
    @ResponseBody
    public JSONObject enabledUser(HttpServletRequest request, String sid, boolean enabled) {
        JSONObject json = new JSONObject();
        if (sid == null || sid.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的用户数据，请检查！");
            return json;
        }
        try {
            UserPojo user = new UserPojo();
            user.setSid(sid);
            user.setEnabled(enabled);
            userService.enabledUser(user);
            json.put("success", true);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "用户数据错误，请检查后再试！");
            log.error("使用户生效出错 UserController.enabledUser", e);
            throw new RuntimeException("使用户生效出错 UserController.enabledUser", e);
        }
        return json;
    }
}
