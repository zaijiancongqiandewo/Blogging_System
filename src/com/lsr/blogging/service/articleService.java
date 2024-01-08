package com.lsr.blogging.service;

import com.lsr.blogging.pojo.Article;

import java.util.ArrayList;
import java.util.List;

public interface articleService {
    /**
     * 添加文章
     * @param article
     * @return
     */
    boolean addArticle(Article article);

    /**
     * 获取所有文章
     * @return
     */
    List<Article> getAllArticle();

    /**
     * 获取不同类型的文章
     * @param categoryName
     * @return
     */
    List<Article> getArticlesByCategoryname(String categoryName);

    Article getArticleById(int articleID);

    boolean updateArticleViews(int articleId);

    List<Article> getArticleByUserName(String userName);

    boolean updateArticle(int articleID, String articleName, String categoryName, String articleContent, String updateTime,String pageImageUrl);

    boolean deleteArticleById(int articleID);
}
