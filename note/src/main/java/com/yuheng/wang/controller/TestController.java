package com.yuheng.wang.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuheng.wang.model.User;
import com.yuheng.wang.service.UserService;

@Controller
public class TestController {
    private static Logger log = LoggerFactory.getLogger(TestController.class);
    @Resource(name = "userServiceImp")
    private UserService userService;

    @RequestMapping("/hello.do")
    public String test() {
        log.info(">>>Enter TestController method hello");
        // User user = userService.getById(1);
        // log.info("User:" + (user != null ? user.toString() : null));
        userService.deleteUser();
        return "hello";
    }

}
