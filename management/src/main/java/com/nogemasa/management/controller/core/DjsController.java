package com.nogemasa.management.controller.core;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <br/>create at 15-7-13
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
public class DjsController {
    @RequestMapping("/mc")
    public String mcDjs() {
        return "mc_djs";
    }
}
