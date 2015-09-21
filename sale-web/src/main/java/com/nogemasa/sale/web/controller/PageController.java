package com.nogemasa.sale.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <br/>create at 15-8-23
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
public class PageController {
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login(Model model, Boolean error) {
        model.addAttribute("error", error);
        return "login";
    }

    @RequestMapping("/salePage")
    public String salePage() {
        return "salePage";
    }

    @RequestMapping("/print")
    public String print() {
        return "print";
    }
}
