package com.yuheng.wang.service;

import com.yuheng.wang.model.User;

/**
 * UserService接口
 * 
 * @author wangyuheng
 * @date 2018/08/28
 */
public interface UserService {
    public User getById(int id);

    public void deleteUser();
}
