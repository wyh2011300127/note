package com.yuheng.wang.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用于登录的controller
 * 
 * @author wangyuheng
 * @date 2018/08/30
 */
@Controller
public class LoginController {
    @RequestMapping(method = RequestMethod.GET, value = "/login.do")
    public String login(HttpServletRequest request) {
        return "login";
    }
}
