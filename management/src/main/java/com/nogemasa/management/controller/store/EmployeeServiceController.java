package com.nogemasa.management.controller.store;

import com.nogemasa.management.service.store.IEmployeeService;
import com.nogemasa.signature.agent.annotation.SignatureVerifyService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <br/>create at 15-8-29
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/service/employee")
public class EmployeeServiceController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IEmployeeService employeeService;

    @RequestMapping("/getEmployeeList")
    @SignatureVerifyService
    @ResponseBody
    public String getEmployeeList(@RequestBody(required = false) String message,
            @RequestParam(value = "message", required = false) String messageGet) {
        JSONObject json = new JSONObject();
        try {
            json.put("employees", employeeService.listEmployeeByStoreSid(null));
            json.put("success", true);
        } catch (Exception e) {
            json.put("success", false);
            json.put("errorCode", 500);
            json.put("message", "服务器异常，请稍后再试！");
        }
        logger.debug("返回数据为：{}", json);
        return json.toString();
    }
}
