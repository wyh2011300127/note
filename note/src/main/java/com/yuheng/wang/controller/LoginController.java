package com.yuheng.wang.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yuheng.wang.common.util.ValidateCode;
import com.yuheng.wang.common.util.VerifyCode;

/**
 * 用于登录的controller
 * 
 * @author wangyuheng
 * @date 2018/08/30
 */
@Controller
public class LoginController {
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/login.do")
    public String login(HttpServletRequest request) {
        return "login";
    }

    @RequestMapping(value = "/verifyCode.do")
    public String checkVerifyCode(HttpServletRequest request, HttpServletResponse response) {
        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        try {
            ValidateCode vCode = new ValidateCode(130, 30, 6, 100);
            BufferedImage buffImg = vCode.getBuffImg();
            String code = vCode.getCode();

            HttpSession session = request.getSession();
            session.setAttribute("yzm", code);

            ImageIO.write(buffImg, "jpeg", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("获取验证码信息异常", e);
        }
        return null;
    }
}
