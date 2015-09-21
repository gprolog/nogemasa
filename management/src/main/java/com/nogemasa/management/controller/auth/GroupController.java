package com.nogemasa.management.controller.auth;

import com.nogemasa.management.pojo.GroupPojo;
import com.nogemasa.management.service.auth.IGroupService;
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
 * <br/>create at 15-7-7
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/admin/auth/group")
public class GroupController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IGroupService groupService;

    @RequestMapping("read")
    @ResponseBody
    public JSONObject groupList(HttpServletRequest request, int start, int limit) {
        JSONObject json = new JSONObject();
        List<GroupPojo> groups = groupService.groupList();
        if(groups.size() == 0) {
            json.put("success", true);
            json.put("total", 0);
            json.put("list", Collections.emptyList());
            return json;
        }
        json.put("success", true);
        json.put("total", groups.size());
        json.put("list", groups.subList(start, groups.size() < start + limit ? groups.size() : start + limit));
        return json;
    }

    @RequestMapping("create")
    @ResponseBody
    public JSONObject createGroup(HttpServletRequest request, String data) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的用户组数据，请检查！");
            return json;
        }
        try {
            json = JSONObject.fromObject(UnicodeStringUtil.fromUnicodeString(data)); // 解码转换
            GroupPojo group = (GroupPojo) JSONObject.toBean(json, GroupPojo.class);
            groupService.addGroup(group);
            log.debug("group插入后的id" + group.getSid());
            json.put("sid", group.getSid());
            json.put("success", true);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "该用户组已经存在，请检查！");
            log.error("创建用户组出错 GroupController.createGroup", e);
            throw new RuntimeException("创建用户组出错 GroupController.createGroup", e);
        }
        return json;
    }

    @RequestMapping("update")
    @ResponseBody
    public JSONObject updateGroup(HttpServletRequest request, String data) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的用户组数据，请检查！");
            return json;
        }
        try {
            json = JSONObject.fromObject(UnicodeStringUtil.fromUnicodeString(data));
            GroupPojo group = (GroupPojo) JSONObject.toBean(json, GroupPojo.class);
            groupService.updateGroup(group);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "用户组数据错误，请检查后再试！");
            log.error("修改用户组出错 GroupController.updateGroup", e);
            throw new RuntimeException("修改用户组出错 GroupController.updateGroup", e);
        }
        return json;
    }

    @RequestMapping("destroy")
    @ResponseBody
    public JSONObject deleteGroup(HttpServletRequest request, String data) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的用户组数据，请检查！");
            return json;
        }
        try {
            json = JSONObject.fromObject(UnicodeStringUtil.fromUnicodeString(data));
            GroupPojo group = (GroupPojo) JSONObject.toBean(json, GroupPojo.class);
            groupService.deleteGroup(group);
            json.put("success", true);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "用户组数据错误，请检查后再试！");
            log.error("删除用户组出错 GroupController.deleteGroup", e);
            throw new RuntimeException("删除用户组出错 GroupController.deleteGroup", e);
        }
        return json;
    }
}
