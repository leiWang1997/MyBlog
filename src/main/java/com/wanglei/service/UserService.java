package com.wanglei.service;

import com.wanglei.pojo.User;

public interface UserService {

    User checkUser(String username, String password);
}
