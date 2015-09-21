package com.nogemasa.management.controller.auth;

import com.nogemasa.management.pojo.GroupAuthority;
import com.nogemasa.management.service.auth.IGroupAuthorityService;
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
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <br/>create at 15-7-7
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/admin/auth/groupAuth")
public class GroupAuthorityController {
    private final Log log = LogFactory.getLog(getClass());
    @Autowired
    private IGroupAuthorityService groupAuthorityService;

    @RequestMapping("/list/{groupSid}")
    @ResponseBody
    public JSONObject getAuthorities(HttpServletRequest request, @PathVariable("groupSid") String groupSid)
            throws UnsupportedEncodingException {
        JSONObject json = new JSONObject();
        List<GroupAuthority> authorities = groupAuthorityService.getAuthoritiesByGroupSid(groupSid);
        json.put("total", authorities.size());
        json.put("list", authorities);
        json.put("success", true);
        return json;
    }

    @RequestMapping("update")
    @ResponseBody
    public JSONObject updateGroupAuthority(HttpServletRequest request, String data) {
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
                GroupAuthority groupAuthority = (GroupAuthority) JSONObject.toBean(jsonObject, GroupAuthority.class);
                if (groupAuthority.getSid() == null || groupAuthority.getSid()
                        .isEmpty()) {// 如果sid为空，说明用户组-权限关系表中没有该数据，可以插入
                    if (groupAuthority.isEnabled()) {
                        // 向用户组-权限关系表中插入数据
                        groupAuthorityService.insertAuthorityOfGroup(groupAuthority);
                    }
                } else {
                    if (!groupAuthority.isEnabled()) {
                        // 从用户组-权限关系表中删除数据
                        groupAuthorityService.deleteAuthorityOfGroup(groupAuthority);
                    }
                }
            }
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "用户组-权限数据错误，请检查后再试！");
            log.error("修改用户组权限出错 GroupAuthorityController.updateGroupAuthority", e);
            throw new RuntimeException("修改用户组权限出错 GroupAuthorityController.updateGroupAuthority", e);
        }
        return json;
    }
}
