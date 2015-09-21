package com.nogemasa.management.controller.core;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录页面
 * 
 * @author liufl / 2015年1月14日
 */
@Controller
public class LoginPageController {
    @RequestMapping("login")
    public String login(Model model, Boolean error) {
        model.addAttribute("error", error);
        return "login";
    }
}
