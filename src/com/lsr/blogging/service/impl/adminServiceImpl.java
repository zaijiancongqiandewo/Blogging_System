package com.lsr.blogging.service.impl;

import com.lsr.blogging.dao.ArticleDao;
import com.lsr.blogging.dao.adminDao;
import com.lsr.blogging.dao.impl.ArticleDaoImpl;
import com.lsr.blogging.dao.impl.adminDaoImpl;
import com.lsr.blogging.pojo.Article;
import com.lsr.blogging.pojo.BloggingUser;
import com.lsr.blogging.service.adminService;

public class adminServiceImpl implements adminService {
    adminDao admindao=new adminDaoImpl();
    ArticleDao articleDao=new ArticleDaoImpl();
    @Override
    public boolean userUpdate(BloggingUser bloggingUser) {
        return admindao.userUpdate(bloggingUser);
    }

    @Override
    public boolean deleteUserByUserName(String userName) {
        return admindao.deleteUserByUserName(userName);
    }

    @Override
    public void updateUserIdentify(int articleId) {
        Article articleById = articleDao.getArticleById(articleId);
        Integer totalViews=articleDao.getAllArticleViewsByUserName(articleById.getUserName());
        admindao.updateUserIdentify(articleById.getUserName(),totalViews);
    }
}
