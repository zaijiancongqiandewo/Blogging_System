package com.lsr.blogging.dao.impl;

import com.lsr.blogging.dao.ArticleDao;
import com.lsr.blogging.pojo.Article;
import com.lsr.blogging.pojo.BloggingUser;
import com.lsr.blogging.util.JDBCUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleDaoImpl implements ArticleDao {
    QueryRunner queryRunner=new QueryRunner(JDBCUtil.getDataSource());

    /**
     * 添加文章
     * @param article
     * @return
     */
    @Override
    public boolean addArticle(Article article) {
        int update=0;
        String sql="insert into article values (default ,?,?,?,?,?,?,?,?)";
        try {
            update = queryRunner.update(sql,
                    article.getArticleName(),
                    article.getUserName(),
                    article.getCategoryName(),
                    article.getArticleContent(),
                    article.getCreateTime(),
                    article.getUpdateTime(),
                    article.getArticleViews(),
                    article.getPageImageUrl());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return update>0;
    }

    @Override
    public List<Article> getAllArticle() {
        List<Article> query=null;
        String sql="select * from article";
        try {
            query=queryRunner.query(sql, new BeanListHandler<>(Article.class));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return query;
    }

    /**
     * 按照不同的类型名称查询文章
     * @param categoryName
     * @return
     */
    @Override
    public List<Article> getArticlesByCategoryname(String categoryName) {
        List<Article> query=null;
        String sql="select * from article where categoryName=?";
        try {
            query = queryRunner.query(sql, new BeanListHandler<>(Article.class), categoryName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return query;
    }

    @Override
    public Article getArticleById(int articleID) {
        Article query=null;
        String sql="select *from article where articleID=?";
        try {
            query = queryRunner.query(sql, new BeanHandler<>(Article.class), articleID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return query;
    }

    @Override
    public boolean updateArticleViews(int articleId) {
        int query=0;
        String sql="update article set articleViews=articleViews+1 where articleID=?";
        try {
            query=queryRunner.update(sql,articleId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return query>0;
    }

    @Override
    public List<Article> getArticleByUserName(String userName) {
        List<Article> articles=null;
        String sql="select *from article where userName=?";
        try {
            articles=queryRunner.query(sql,new BeanListHandler<>(Article.class),userName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return articles;
    }

    @Override
    public List<Article> getArticleByArticleNameAndByCategory(String articleName, String categoryName) {
        return null;
    }

    @Override
    public boolean updateArticle(int articleID, String articleName, String categoryName, String articleContent, String updateTime,String pageImageUrl) {
        int update=0;
        String sql="update article set articleName=?,categoryName=?,articleContent=?,updateTime=?,pageImageUrl=? where articleID=?";
        try {
            update=queryRunner.update(sql,articleName,categoryName,articleContent,updateTime,pageImageUrl,articleID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return update>0;
    }

    @Override
    public boolean deleteArticleById(int articleID) {
        int query=0;
        String sql="delete from article where articleID=?";
        try {
            query=queryRunner.update(sql,articleID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return query>0;
    }

    @Override
    public Integer getAllArticleViewsByUserName(String userName) {
        Article query=null;
        String sql="select userName, sum(articleViews) as articleViews from article group by userName having userName=?";
        try {
            query = queryRunner.query(sql, new BeanHandler<>(Article.class), userName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return query.getArticleViews();
    }
}
