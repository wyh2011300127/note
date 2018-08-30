package com.yuheng.wang.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuheng.wang.common.util.StringUtil;
import com.yuheng.wang.model.User;
import com.yuheng.wang.service.UserService;

@Controller
public class TestController {
    private static Logger log = LoggerFactory.getLogger(TestController.class);
    @Resource(name = "userServiceImp")
    private UserService userService;

    /**
     * 自定义一个AOP被切入的类，被切入的类要交给spring管理，并不能new一个实例对象，需要由spring注入
     */
    @Resource(name = "stringUtil")
    private StringUtil strUtil;

    /**
     * 测试注解方式开发AOP切面功能
     * 
     * @return
     */
    @RequestMapping("/aopAnno.do")
    public String testAopAnnotation() {
        log.info(">>>Enter TestController method hello");
        User user = userService.getById(1);
        log.info("User:" + (user != null ? user.toString() : null));
        return "hello";
    }

    /**
     * 测试XML配置文件开发AOP切面功能
     * 
     * @param request
     * @return
     */
    @RequestMapping("/aopXml.do")
    public String testAopXml(HttpServletRequest request) {
        log.info(">>>Enter method testXmlInterceptor");
        String param = request.getParameter("param");
        try {
            String param2 = strUtil.getParam(param);
            log.info("param2:" + param2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(">>>Exit method testXmlInterceptor");

        return "hello";
    }

}
