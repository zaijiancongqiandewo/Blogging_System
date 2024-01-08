package com.lsr.blogging.service.impl;

import com.lsr.blogging.dao.ArticleDao;
import com.lsr.blogging.dao.impl.ArticleDaoImpl;
import com.lsr.blogging.pojo.Article;
import com.lsr.blogging.service.articleService;

import java.util.ArrayList;
import java.util.List;

public class ArticleServiceImpl implements articleService {
   ArticleDao articleDao=new ArticleDaoImpl();

    /**
     * 添加文章
     * @param article
     * @return
     */
    @Override
    public boolean addArticle(Article article) {
        return articleDao.addArticle(article);
    }

    @Override
    public List<Article> getAllArticle() {
        return articleDao.getAllArticle();
    }

    @Override
    public List<Article> getArticlesByCategoryname(String categoryName) {
        return articleDao.getArticlesByCategoryname(categoryName);
    }

    /**
     * 通过id获取文章
     * @param articleID
     * @return
     */
    @Override
    public Article getArticleById(int articleID) {
        return articleDao.getArticleById(articleID);
    }

    /**
     * 修改文章浏览量
     * @return
     */
    @Override
    public boolean updateArticleViews(int articleId) {
        return articleDao.updateArticleViews(articleId);
    }

    @Override
    public List<Article> getArticleByUserName(String userName) {
        return articleDao.getArticleByUserName(userName);
    }

    @Override
    public boolean updateArticle(int articleID, String articleName, String categoryName, String articleContent, String updateTime,String pageImageUrl) {
        return articleDao.updateArticle(articleID,articleName,categoryName,articleContent,updateTime,pageImageUrl);
    }

    @Override
    public boolean deleteArticleById(int articleID) {
        return articleDao.deleteArticleById(articleID);
    }
}
