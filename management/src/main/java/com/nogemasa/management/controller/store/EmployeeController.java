package com.nogemasa.management.controller.store;

import com.google.common.collect.Maps;
import com.nogemasa.management.pojo.EmployeePojo;
import com.nogemasa.management.service.store.IEmployeeService;
import com.nogemasa.util.UnicodeStringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <br/>create at 15-8-13
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/management/employee")
public class EmployeeController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IEmployeeService employeeService;

    @RequestMapping("/list/{storeSid}")
    @ResponseBody
    public Map<String, Object> listEmployeeByStoreSid(@PathVariable("storeSid") String storeSid) {
        Map<String, Object> map = Maps.newHashMap();
        List<EmployeePojo> list = employeeService.listEmployeeByStoreSid(storeSid);
        map.put("success", true);
        map.put("total", list.size());
        map.put("list", list);
        logger.debug("返回json数据为：{}", map.toString());
        return map;
    }

    @RequestMapping("/create")
    @ResponseBody
    public JSONObject addEmployee(HttpServletRequest request, String data) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的数据，请检查！");
            return json;
        }
        try {
            JSONArray array = JSONArray.fromObject(UnicodeStringUtil.fromUnicodeString(data));
            json = array.getJSONObject(0);
            EmployeePojo pojo = (EmployeePojo) JSONObject.toBean(json, EmployeePojo.class);
            employeeService.addEmployee(pojo);
            logger.debug("插入后的id=" + pojo.getSid());
            json.put("sid", pojo.getSid());
            json.put("success", true);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "输入员工数据有误，请检查！");
            logger.error("创建员工出错 EmployeeController.addEmployee", e);
            throw new RuntimeException("创建员工出错 EmployeeController.addEmployee", e);
        }
        return json;
    }

    @RequestMapping("/update")
    @ResponseBody
    public JSONObject updateEmployee(HttpServletRequest request, String data) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的数据，请检查！");
            return json;
        }
        try {
            JSONArray array = JSONArray.fromObject(UnicodeStringUtil.fromUnicodeString(data));
            json = array.getJSONObject(0);
            EmployeePojo pojo = (EmployeePojo) JSONObject.toBean(json, EmployeePojo.class);
            employeeService.updateEmployee(pojo);
            json.put("success", true);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "修改员工数据有误，请检查！");
            logger.error("修改员工出错 EmployeeController.updateEmployee", e);
            throw new RuntimeException("创建员工出错 EmployeeController.updateEmployee", e);
        }
        return json;
    }
}
