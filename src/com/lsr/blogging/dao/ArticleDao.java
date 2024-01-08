package com.lsr.blogging.dao;

import com.lsr.blogging.pojo.Article;
import com.lsr.blogging.pojo.BloggingUser;

import java.util.ArrayList;
import java.util.List;

public interface ArticleDao {
    boolean addArticle(Article article);
    List<Article> getAllArticle();

    List<Article> getArticlesByCategoryname(String categoryName);

    Article getArticleById(int articleID);

    boolean updateArticleViews(int articleId);

    /**
     * 通过用户名查询自己所写的文章集合
     * @param userName 用户名
     * @return 返回当前用户自己所写的所有文章
     */
    List<Article> getArticleByUserName(String userName);

    /**
     * 进行顶部搜索框的模糊查询，根据文章名的模糊匹配，返回不同类型的数据
     * @param articleName 文章名（模糊的文章名）
     * @param categoryName 文章所属的类型
     * @return 当前条件下的所有文章
     */
    List<Article> getArticleByArticleNameAndByCategory(String articleName,String categoryName);

    boolean updateArticle(int articleID, String articleName, String categoryName, String articleContent, String updateTime,String pageImageUrl);

    boolean deleteArticleById(int articleID);

    Integer getAllArticleViewsByUserName(String userName);
}
