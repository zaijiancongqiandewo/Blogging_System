package com.lsr.blogging.dao;

import com.lsr.blogging.pojo.BloggingUser;

import java.util.List;

public interface UserDao {
    /**
     * 根据用户名查询用户信息
     * @param username 要查询的用户名
     * @return 找到返回NewsUser对象,找不到返回null
     */
    BloggingUser findByUserName(String username);
    boolean addBloggingUser(BloggingUser bloggingUser);

    boolean userPasswordEdit(String userName,String newPassword);

    List<BloggingUser> getAllUsers();

    boolean updateHeadImage(String userName, String headImageUrl);
}
