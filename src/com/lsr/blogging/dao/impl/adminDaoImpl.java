package com.lsr.blogging.dao.impl;

import com.lsr.blogging.dao.adminDao;
import com.lsr.blogging.pojo.BloggingUser;
import com.lsr.blogging.util.JDBCUtil;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

public class adminDaoImpl implements adminDao {
    QueryRunner queryRunner=new QueryRunner(JDBCUtil.getDataSource());
    @Override
    public boolean userUpdate(BloggingUser bloggingUser) {
        int update=0;
        String sql="update users set password=?,headImageUrl=? where userName=?";
        try {
            update=queryRunner.update(sql,bloggingUser.getPassword(),bloggingUser.getHeadImageUrl(),bloggingUser.getUserName());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return update>0;
    }

    @Override
    public boolean deleteUserByUserName(String userName) {
        int delete=0;
        String sql="delete from users where userName=?";
        try {
            delete= queryRunner.update(sql, userName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return delete>0;
    }

    @Override
    public void updateUserIdentify(String userName, Integer totalViews) {
        String sql=null;
        if(totalViews>200){
            sql="update users set identify = '资深用户' where userName=?";
        }else if(totalViews>100){
            sql="update users set identify = '高级用户' where userName=?";
        }else if(totalViews>50){
            sql="update users set identify = '中级用户' where userName=?";
        }else if(totalViews>10){
            sql="update users set identify = '初级用户' where userName=?";
        }else{
            sql="update users set identify = '萌新用户' where userName=?";
        }
        try {
            int update = queryRunner.update(sql, userName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
