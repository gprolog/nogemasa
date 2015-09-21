package com.nogemasa.management.controller.auth;

import com.nogemasa.management.pojo.UserGroup;
import com.nogemasa.management.service.auth.IUserGroupService;
import com.nogemasa.util.UnicodeStringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <br/>create at 15-7-8
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/admin/auth/userGroup")
public class UserGroupController {
    private final Log log = LogFactory.getLog(getClass());
    @Autowired
    private IUserGroupService userGroupService;

    @RequestMapping("/list/{userSid}")
    @ResponseBody
    public JSONObject getAuthorities(HttpServletRequest request, @PathVariable("userSid") String userSid) {
        JSONObject json = new JSONObject();
        List<UserGroup> userGroups = userGroupService.getGroupsByUserSid(userSid);
        json.put("total", userGroups.size());
        json.put("list", userGroups);
        json.put("success", true);
        return json;
    }

    @RequestMapping("update")
    @ResponseBody
    public JSONObject updateUserGroup(HttpServletRequest request, String data) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的权限数据，请检查！");
            return json;
        }
        try {
            JSONArray array = JSONArray.fromObject(UnicodeStringUtil.fromUnicodeString(data));
            for (int i = 0; i < array.size(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                UserGroup userGroup = (UserGroup) JSONObject.toBean(jsonObject, UserGroup.class);
                if(userGroup.getSid() == null || userGroup.getSid().isEmpty()) {// 如果sid为空，说明用户组-权限关系表中没有该数据，可以插入
                    if(userGroup.isEnabled()) {
                        // 向用户组-权限关系表中插入数据
                        userGroupService.insertGroupOfUser(userGroup);
                    }
                } else {
                    if(!userGroup.isEnabled()) {
                        // 从用户组-权限关系表中删除数据
                        userGroupService.deleteGroupOfUser(userGroup);
                    }
                }
            }
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "用户-用户组数据错误，请检查后再试！");
            log.error("修改用户-用户组出错 UserGroupController.updateUserGroup", e);
            throw new RuntimeException("修改用户-用户组出错 UserGroupController.updateUserGroup", e);
        }
        return json;
    }
}
