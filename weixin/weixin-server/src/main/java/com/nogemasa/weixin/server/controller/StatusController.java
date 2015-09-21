package com.nogemasa.weixin.server.controller;

import com.nogemasa.util.system.SystemInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * /status.json resolver
 * <br/>create at 15-6-1
 *
 * @author liufl
 * @since 1.0.0
 */
@Controller
public class StatusController {
    @RequestMapping("/status")
    public String status(Model model) {
        model.addAttribute("system", SystemInfo.getSystemInfo());
        model.addAttribute("jvm", SystemInfo.getJvmInfo());
        return "jsonView"; // no view's name should match this.
    }
}
