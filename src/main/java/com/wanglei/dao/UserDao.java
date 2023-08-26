package com.wanglei.dao;

import com.wanglei.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {

    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

}
