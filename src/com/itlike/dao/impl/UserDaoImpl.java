package com.itlike.dao.impl;

import com.itlike.dao.UserDao;
import com.itlike.domain.User;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

public class UserDaoImpl extends HibernateDaoSupport implements UserDao {
    @Override
    public User getUser(String username, String password) {
        System.out.println("user:"+username+password+"-->go to userdao ");
        //到数据库中进行查询
        //设置到哪个表中去查
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class);
        //设置条件
        detachedCriteria.add(Restrictions.eq("username",username));
        detachedCriteria.add(Restrictions.eq("password",password));
        List<User> list = (List<User>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
        System.out.println("database query-->"+list);
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
