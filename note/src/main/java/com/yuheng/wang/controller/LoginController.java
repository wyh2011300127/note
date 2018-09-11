package com.yuheng.wang.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuheng.wang.common.consts.PublicConsts;
import com.yuheng.wang.common.util.SessionUtils;
import com.yuheng.wang.common.util.StringUtils;
import com.yuheng.wang.common.util.ValidateCode;
import com.yuheng.wang.controller.base.BaseController;

/**
 * 用于登录的controller
 * 
 * @author wangyuheng
 * @date 2018/08/30
 */
@Controller
public class LoginController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    /**
     * 跳转到登录页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/toLogin.do")
    public String toLogin(HttpServletRequest request) {
        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    public Map<String, String> login(HttpServletRequest request, HttpServletResponse response) {
        // 返回结果Map
        Map<String, String> resultMap = new HashMap<String, String>();
        // 用户名
        String userName = super.getParameter("userName", request);
        // 密码
        String pwd = super.getParameter("pwd", request);
        // 验证码
        String yzm = super.getParameter("yzm", request);
        if (StringUtils.isBlank(yzm)) {
            resultMap.put("result", PublicConsts.FAIL);
            resultMap.put("msg", "验证码为空");
            return resultMap;
        }
        String yzmCode = (String)SessionUtils.getSessionAttribute(PublicConsts.RAND_YZM_IMAGE);
        // 移除session中的验证码信息
        SessionUtils.removeSession(PublicConsts.RAND_YZM_IMAGE);
        if (StringUtils.isBlank(yzmCode) || !yzm.equals(yzmCode)) {
            resultMap.put("result", PublicConsts.FAIL);
            resultMap.put("msg", "验证码输入错误");
            return resultMap;
        }

        if (StringUtils.isBlank(userName)) {
            resultMap.put("result", PublicConsts.FAIL);
            resultMap.put("msg", "用户名为空");
            return resultMap;
        }
        if (StringUtils.isBlank(pwd)) {
            resultMap.put("result", PublicConsts.FAIL);
            resultMap.put("msg", "密码为空");
            return resultMap;
        }
        
        
        
        return resultMap;
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
            ValidateCode vCode = new ValidateCode(120, 30, 6, 100);
            BufferedImage buffImg = vCode.getBuffImg();
            String yzmCode = vCode.getCode();
            log.info("获取验证码信息：" + yzmCode);

            SessionUtils.putSessionAttribute(PublicConsts.RAND_YZM_IMAGE, yzmCode);
            SessionUtils.putSessionAttribute(PublicConsts.RAND_YZM_START_TIME, System.currentTimeMillis());

            ImageIO.write(buffImg, "jpeg", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("获取验证码信息异常", e);
        }
        return null;
    }
}
