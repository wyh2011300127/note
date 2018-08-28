package com.yuheng.wang.dao;

import org.springframework.stereotype.Component;

import com.yuheng.wang.model.User;

@Component
public interface UserDao {
    public User getById(int id) throws Exception;
}
