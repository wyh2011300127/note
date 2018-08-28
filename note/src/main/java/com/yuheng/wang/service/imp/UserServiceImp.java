package com.yuheng.wang.service.imp;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yuheng.wang.dao.UserDao;
import com.yuheng.wang.model.User;
import com.yuheng.wang.service.UserService;

@Service(value = "userServiceImp")
public class UserServiceImp implements UserService {
    private static Logger log = LoggerFactory.getLogger(UserServiceImp.class);
    @Resource
    private UserDao userDao;

    public User getById(int id) {
        User user;
        try {
            user = userDao.getById(1);
            log.info(user.toString());
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteUser() {
        log.info(">>>>>>>>>>>>UserServiceImp deleteUser method ");
        throw new RuntimeException("抛出一个异常！");
    }

}
