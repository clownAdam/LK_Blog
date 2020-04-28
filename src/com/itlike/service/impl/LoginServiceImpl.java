package com.itlike.service.impl;

import com.itlike.dao.UserDao;
import com.itlike.domain.User;
import com.itlike.service.LoginService;

/**
 * @author clown
 */
public class LoginServiceImpl implements LoginService {
    //注入dao层
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User login(User user) {
        System.out.println("用户："+user+"-->来到user login service ");
        //调用dao,查询用户
       return userDao.getUser(user.getUsername(),user.getPassword());
    }
}
