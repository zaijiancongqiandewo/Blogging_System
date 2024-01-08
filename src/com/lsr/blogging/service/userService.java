package com.lsr.blogging.service;

import com.lsr.blogging.pojo.BloggingUser;

import java.util.List;

public interface userService {
    /**
    * 根据用户名,获得查询用户的方法
    * @param username 要查询的用户名
    * @return 如果找到返回NewsUser对象,找不到返回null
    */
    BloggingUser findByUserName(String username);

    /**
     * 添加用户
     * @param bloggingUser
     * @return
     */
    boolean addBloggingUser(BloggingUser bloggingUser);

    /**
     * 用户修改密码
     * @param userName
     */
    boolean userPasswordEdit(String userName,String newPassword);

    /**
     * 获取所有的用户信息（管理员操作）
     * @return
     */
    List<BloggingUser> getAllUsers();

    /**
     * 通过用户名修改头像
     * @param userName
     * @param headImageUrl
     * @return
     */
    boolean updateHeadImage(String userName, String headImageUrl);
}
