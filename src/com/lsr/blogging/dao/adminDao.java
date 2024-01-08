package com.lsr.blogging.dao;

import com.lsr.blogging.pojo.BloggingUser;

public interface adminDao {
    boolean userUpdate(BloggingUser bloggingUser);

    boolean deleteUserByUserName(String userName);

    void updateUserIdentify(String userName, Integer totalViews);
}
