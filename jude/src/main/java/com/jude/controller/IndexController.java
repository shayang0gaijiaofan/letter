package com.jude.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 首页或者权限url跳转控制器
 * @author jude
 *
 */
@Controller
public class IndexController {

	
    /**
     * 网站根目录请求
     * @return
     */
    @RequestMapping("/")
    public String root() {
    	return "redirect:/login.html";
    }

    /**
     * 函件展示 及 反馈页面
     */
//    @RequestMapping(value = "/letterFeedBack", method = RequestMethod.GET)
//    public String letterFeedBack() {
//        return "redirect:/letterFeedBack.html";
//    }
}
