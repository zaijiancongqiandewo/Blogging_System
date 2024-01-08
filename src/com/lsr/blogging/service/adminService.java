package com.lsr.blogging.service;

import com.lsr.blogging.pojo.BloggingUser;

public interface adminService {
    /**
     * 实现用户数据的更新
     * @param bloggingUser
     * @return
     */
    boolean userUpdate(BloggingUser bloggingUser);

    /**
     * 实现用户的删除
     * @param userName
     * @return
     */
    boolean deleteUserByUserName(String userName);

    void updateUserIdentify(int articleId);
}
