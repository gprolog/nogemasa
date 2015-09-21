package com.nogemasa.management.controller.member;

import com.nogemasa.common.pojo.MemberPojo;
import com.nogemasa.management.service.member.IMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <br/>create at 15-8-15
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/management/member")
public class MemberController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IMemberService memberService;

    @RequestMapping("/read")
    public String listMemberAll(Model model, int page, int start, int limit,
            @RequestParam(required = false) String query) {
        // String queryStr;
        // try {
        //     queryStr = URLDecoder.decode(query, "UTF-8");
        // } catch (Exception e) {
        //     queryStr = null;
        // }
        // List<MemberInfoPojo> list = memberService.listMemberByGroup(null, queryStr);
        List<MemberPojo> list = memberService.getMemberList();
        model.addAttribute("total", list.size());
        model.addAttribute("list", list.subList(start, list.size() < start + limit ? list.size() : start + limit));
        return "jsonView";
    }
}
