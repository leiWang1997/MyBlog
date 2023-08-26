package com.wanglei.service.impl;

import com.wanglei.dao.UserDao;
import com.wanglei.pojo.User;
import com.wanglei.service.UserService;
import com.wanglei.util.MD5utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User checkUser(String username, String password) {
        User user = userDao.findByUsernameAndPassword(username, MD5utils.code(password));
        return user;
    }
}
