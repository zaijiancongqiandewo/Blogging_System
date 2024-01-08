package com.lsr.blogging.controller;

import com.lsr.blogging.common.Result;
import com.lsr.blogging.common.ResultCodeEnum;
import com.lsr.blogging.filters.ArticleFilter;
import com.lsr.blogging.pojo.Article;
import com.lsr.blogging.pojo.ArticleCategory;
import com.lsr.blogging.pojo.BloggingUser;
import com.lsr.blogging.pojo.user.userEdit;
import com.lsr.blogging.service.adminService;
import com.lsr.blogging.service.articleService;
import com.lsr.blogging.service.impl.ArticleServiceImpl;
import com.lsr.blogging.service.impl.UserServiceImpl;
import com.lsr.blogging.service.impl.adminServiceImpl;
import com.lsr.blogging.service.userService;
import com.lsr.blogging.util.WebUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

import java.io.InputStream;
import java.lang.invoke.VarHandle;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;


@WebServlet("/user/*")
@MultipartConfig
public class BloggingUserController extends BaseController{

    userService userService=new UserServiceImpl();
    articleService articleService=new ArticleServiceImpl();
    adminService adminService=new adminServiceImpl();
    ArticleFilter articleFilter= new ArticleFilter();
    /**
     * 注册时校验用户名是否被占用
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void checkUserName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 获取账号
        String username = req.getParameter("username");

        // 根据用户名查询用户信息  找到了 返回505  找不到 200
        BloggingUser newsUser = userService.findByUserName(username);
        Result result =Result.ok(null);
        if(null != newsUser){
            result=Result.build(null,ResultCodeEnum.USERNAME_USED);
        }

        WebUtil.writeJson(resp,result);
    }

    /**
     * 用于将用户数据保存到数据库中
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void SignUp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BloggingUser bloggingUser= null;
        bloggingUser = WebUtil.readJson(req, BloggingUser.class);
        if(userService.addBloggingUser(bloggingUser)){
           Result result=Result.build(null,200,"注册成功");
           WebUtil.writeJson(resp,result.ok(result));
        }
    }
    protected void Login(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
        BloggingUser bloggingUser=null;
        bloggingUser=WebUtil.readJson(request,BloggingUser.class);
        //System.out.println(bloggingUser.toString());
        BloggingUser databaseUser=userService.findByUserName(bloggingUser.getUserName());
        //System.out.println(databaseUser);
        if(databaseUser!=null)
        {
            if(bloggingUser.getPassword().equals(databaseUser.getPassword()))
            {
                databaseUser.setPassword("");
                WebUtil.writeJson(response,Result.ok(databaseUser));
            }
            else{
                WebUtil.writeJson(response,Result.build(null,ResultCodeEnum.PASSWORD_ERROR));
            }
        }
        else{
            WebUtil.writeJson(response,Result.build(null,ResultCodeEnum.USERNAME_USED));

        }
    }
    protected  void submitArticle(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
        Article article=null;
        article = WebUtil.readJson(request,Article.class);
      //  System.out.println(article);
        String s = articleFilter.filterSensitiveWords(article.getArticleContent());
        article.setArticleContent(s);
    //    System.out.println(s);
        if(articleService.addArticle(article)){
            Result result=Result.build(null,200,"文章添加成功");
            WebUtil.writeJson(response,result);
        }
    }
    protected  void showArticle(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
        ArticleCategory articleCategory = WebUtil.readJson(request, ArticleCategory.class);
        if(articleCategory!=null){
            if(articleCategory.getCategoryName().equals("全部"))
            {
                List<Article> allArticle = articleService.getAllArticle();
                WebUtil.writeJson(response,Result.build(allArticle,ResultCodeEnum.SUCCESS));
            }
            else if(articleCategory.getCategoryName().equals("技术分享")||articleCategory.getCategoryName().equals("随笔杂谈")||articleCategory.getCategoryName().equals("生活感悟"))
            {
                List<Article> articles=articleService.getArticlesByCategoryname(articleCategory.getCategoryName());
                WebUtil.writeJson(response,Result.build(articles,ResultCodeEnum.SUCCESS));
            }else{
                WebUtil.writeJson(response,Result.build(null,404,"文章获取失败"));
            }
        }
        else{
            WebUtil.writeJson(response,Result.build(null,404,"文章获取失败"));
        }

    }
    protected  void passwordEdit(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
        userEdit userEdit = WebUtil.readJson(request, userEdit.class);
        BloggingUser byUserName = userService.findByUserName(userEdit.getUserName());
        if(byUserName.getUserName()!=null)
        {
            if(userEdit.getOldPassword().equals(byUserName.getPassword()))
            {
                if(userService.userPasswordEdit(userEdit.getUserName(),userEdit.getNewPassword()))
                {
                    WebUtil.writeJson(response,Result.build(null,ResultCodeEnum.SUCCESS));
                }
                else
                {
                    WebUtil.writeJson(response,Result.build(null,500,"密码修改失败"));
                }
            }
            else {
                WebUtil.writeJson(response,Result.build(null,503,"旧密码错误"));
            }

        }

    }
    protected void getArticleById  (HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
        Article article = WebUtil.readJson(request, Article.class);
        Article currentArticle=articleService.getArticleById(article.getArticleID());

        if(currentArticle!=null){
            if(articleService.updateArticleViews(currentArticle.getArticleID())){
                adminService.updateUserIdentify(currentArticle.getArticleID());
                WebUtil.writeJson(response,Result.build(currentArticle,ResultCodeEnum.SUCCESS));
            }

            else{
                WebUtil.writeJson(response,Result.build(null,503,"文章修改浏览次数出错"));
            }
        }
        else{
            WebUtil.writeJson(response,Result.build(null,500,"没有该文章"));
        }
    }
    protected void getArticleByUserName(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
        Article article = WebUtil.readJson(request, Article.class);
        if(article!=null){
            List<Article> articlesByUserName = articleService.getArticleByUserName(article.getUserName());
            WebUtil.writeJson(response,Result.build(articlesByUserName,ResultCodeEnum.SUCCESS));
        }else {
            WebUtil.writeJson(response,Result.build(null,503,"获取用户名失败"));
        }

    }
    protected void updateArticle(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
        Article article = WebUtil.readJson(request, Article.class);
        Article articleById = articleService.getArticleById(article.getArticleID());
        String pageImageUrl=articleById.getPageImageUrl();
        if(article.getPageImageUrl()!=null&&article.getPageImageUrl()!=""){
            pageImageUrl=article.getPageImageUrl();
        }
        String s = articleFilter.filterSensitiveWords(article.getArticleContent());
        article.setArticleContent(s);
        if(articleService.updateArticle(article.getArticleID(),article.getArticleName(),article.getCategoryName(),article.getArticleContent(),article.getUpdateTime(),pageImageUrl)){
            WebUtil.writeJson(response,Result.build(null,200,"文章修改成功"));
        }
        else{
            WebUtil.writeJson(response,Result.build(null,503,"文章修改失败"));
        }
    }

    /**
     * 保存用户的头像，并将url链接返回给前端
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void headImage(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
        Part file = request.getPart("file");
        InputStream inputStream = file.getInputStream();
        String uploadDirectory="E:\\JavaWeb\\课程设计\\web\\images\\user\\";
        String submittedFileName = file.getSubmittedFileName();
        Path of = Path.of(uploadDirectory + submittedFileName);
        Files.copy(inputStream,of, StandardCopyOption.REPLACE_EXISTING);//如果目标文件已经存在，则用新的源文件覆盖现有目标文件
        inputStream.close();
        String baseUrl = "http://localhost:8081/lsr/blogging";  // 基础 URL 地址
        // 假设你已经从用户请求中获取了用户信息
        String headImageUrl =baseUrl+ "/images/user/" + submittedFileName; // 计算头像的 URL 地址headImageUrl
        WebUtil.writeJson(response,Result.build(null,200,headImageUrl));

    }
    protected void articleImage(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
        Part file = request.getPart("file");
        InputStream inputStream = file.getInputStream();
        String uploadDirectory="E:\\JavaWeb\\课程设计\\web\\images\\article\\";
        String submittedFileName = file.getSubmittedFileName();
        Path of = Path.of(uploadDirectory + submittedFileName);
        Files.copy(inputStream,of, StandardCopyOption.REPLACE_EXISTING);
        inputStream.close();
        String baseUrl = "http://localhost:8081/lsr/blogging";  // 基础 URL 地址
        // 假设你已经从用户请求中获取了用户信息
        String pageImageUrl =baseUrl+ "/images/article/" + submittedFileName; // 计算头像的 URL 地址headImageUrl
        WebUtil.writeJson(response,Result.build(null,200,pageImageUrl));
    }
    protected void deleteArticle(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
        int articleID = WebUtil.readJson(request, Article.class).getArticleID();
        if(articleID>0) {
            if (articleService.deleteArticleById(articleID)) {
                WebUtil.writeJson(response, Result.build(null, 200, "该文章删除成功"));
            } else {
                WebUtil.writeJson(response, Result.build(null, 503, "删除该文章时出错"));
            }
        }else{
            WebUtil.writeJson(response,Result.build(null,503,"不是有效的文章ID"));
        }
    }
    protected void updateHeadImage(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
        BloggingUser bloggingUser = WebUtil.readJson(request, BloggingUser.class);
        if(bloggingUser.getUserName()!=null){
            if(userService.updateHeadImage(bloggingUser.getUserName(),bloggingUser.getHeadImageUrl())){

                WebUtil.writeJson(response,Result.build(null,ResultCodeEnum.SUCCESS));
            }
            else {
                WebUtil.writeJson(response,Result.build(null,503,"图片修改失败"));
            }
        }
        else{
            WebUtil.writeJson(response,Result.build(null,503,"用户名不能为空"));
        }


    }
}
