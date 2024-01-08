package com.lsr.blogging.service.impl;

import com.lsr.blogging.dao.UserDao;
import com.lsr.blogging.dao.impl.UserDaoImpl;
import com.lsr.blogging.pojo.BloggingUser;
import com.lsr.blogging.service.userService;

import java.util.List;

public class UserServiceImpl implements userService {
    private UserDao UserDao =new UserDaoImpl();
    @Override
    public BloggingUser findByUserName(String username) {
        return UserDao.findByUserName(username);
    }

    @Override
    public boolean addBloggingUser(BloggingUser bloggingUser) {
        return UserDao.addBloggingUser(bloggingUser);
    }

    @Override
    public boolean userPasswordEdit(String userName,String newPassword) {
        return UserDao.userPasswordEdit(userName, newPassword);
    }

    @Override
    public List<BloggingUser> getAllUsers() {
        return UserDao.getAllUsers();
    }

    @Override
    public boolean updateHeadImage(String userName, String headImageUrl) {
        return UserDao.updateHeadImage(userName,headImageUrl);
    }
}
