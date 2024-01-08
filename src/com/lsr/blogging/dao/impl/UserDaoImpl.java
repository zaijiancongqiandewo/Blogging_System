package com.lsr.blogging.dao.impl;

import com.lsr.blogging.dao.BaseDao;
import com.lsr.blogging.dao.UserDao;
import com.lsr.blogging.pojo.BloggingUser;
import com.lsr.blogging.util.JDBCUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl  implements UserDao {
    QueryRunner queryRunner=new QueryRunner(JDBCUtil.getDataSource());
    @Override
    public BloggingUser findByUserName(String username) {

        String sql ="select userName,adminName,password,identify,age,headImageUrl from users where userName = ?";
        BloggingUser query = null;
        try {
            query = queryRunner.query(sql, new BeanHandler<>(BloggingUser.class), username);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return query;
    }

    /**
     * 添加用户
     * @param bloggingUser
     * @return
     */
    @Override
    public boolean addBloggingUser(BloggingUser bloggingUser) {
        int update=0;
        String sql="insert into users values (?,default,?,default,?,?)";
        try {
            update = queryRunner.update(sql, bloggingUser.getUserName(), bloggingUser.getPassword(), bloggingUser.getAge(),bloggingUser.getHeadImageUrl());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return update>0;
    }

    @Override
    public boolean userPasswordEdit(String userName,String newPassword) {
        int update=0;
        String sql="update users set password = ? where userName = ?";
        try {
            update = queryRunner.update(sql, newPassword, userName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return update>0;
    }

    @Override
    public List<BloggingUser> getAllUsers() {
        List<BloggingUser> query=null;
        String sql="select * from users";
        try {
            query = queryRunner.query(sql, new BeanListHandler<>(BloggingUser.class));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return query;
    }

    @Override
    public boolean updateHeadImage(String userName, String headImageUrl) {
        int update=0;
        String sql="update users set headImageUrl=? where userName=?";
        try {
            update=queryRunner.update(sql,headImageUrl,userName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return update>0;
    }


}
