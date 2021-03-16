package com.gongjintao.blog.mapper;

import com.gongjintao.blog.bean.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper {
    int addNewArticle(Article article);

    int updateArticle(Article article);

    // 根据状态来获取所有当前用户的文章，有分页功能
    List<Article> getArticleByState(@Param("state") Integer state, @Param("start") Integer start, @Param("count") Integer count, @Param("uid") Long uid, @Param("keywords") String keywords);

    // 获取当前状态下文章的总数
    int getArticleCountByState(@Param("state") Integer state, @Param("uid") Long uid, @Param("keywords") String keywords);

    //批量更新文章的状态
    int updateArticleState(@Param("aids") Long aids[], @Param("state") Integer state);


    int updateArticleStateById(@Param("articleId") Integer articleId, @Param("state") Integer state);

    int deleteArticleById(@Param("aids") Long[] aids);

    Article getArticleById(Long aid);

    //新增浏览量
    void pvIncrement(Long aid);

    //插入每天的浏览量统计
    void pvStatisticsPerDay();

    List<String> getCategories(Long uid);

    List<Integer> getDataStatistics(Long uid);
}
