package com.lsr.blogging.controller;

import com.lsr.blogging.common.Result;
import com.lsr.blogging.common.ResultCodeEnum;
import com.lsr.blogging.pojo.BloggingUser;
import com.lsr.blogging.service.adminService;
import com.lsr.blogging.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lsr.blogging.service.impl.adminServiceImpl;
import com.lsr.blogging.service.userService;
import com.lsr.blogging.util.WebUtil;

import java.io.IOException;
import java.util.List;

@WebServlet("/root/*")
public class bloggingRootController extends BaseController{

    userService userservice=new UserServiceImpl();
    adminService adminService = new adminServiceImpl();
    protected void userMessage(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        List<BloggingUser> users= userservice.getAllUsers();
        if(users!=null&&users.size()>0){

            WebUtil.writeJson(response, Result.build(users,200,"获取成功"));
        }
        else{
            WebUtil.writeJson(response,Result.build(null,503,"获取用户失败"));
        }
    }
    protected void userUpdate(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
        BloggingUser bloggingUser = WebUtil.readJson(request, BloggingUser.class);
      //  System.out.println(bloggingUser);
        boolean update=adminService.userUpdate(bloggingUser) ;
        if(update){
            WebUtil.writeJson(response,Result.build(null, ResultCodeEnum.SUCCESS));
        }
        else{
            WebUtil.writeJson(response,Result.build(null,503,"修改失败"));
        }

    }
    protected void deleteUser(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
        BloggingUser bloggingUser = WebUtil.readJson(request, BloggingUser.class);
        if(bloggingUser.getUserName()!=null)
        {
            if(adminService.deleteUserByUserName(bloggingUser.getUserName()))
            {
                WebUtil.writeJson(response,Result.build(null,ResultCodeEnum.SUCCESS));
            }
            else{
                WebUtil.writeJson(response,Result.build(null,503,"没有该用户"));
            }
        }

    }

}
