package com.itlike.dao;

import com.itlike.domain.User;

public interface UserDao {
    //查询用户是否存在
    public User getUser(String username, String password);
}
