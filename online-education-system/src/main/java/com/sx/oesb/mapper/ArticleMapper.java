package com.sx.oesb.mapper;

import com.sx.oesb.entity.Article;
import com.sx.oesb.vo.ArticleDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 自动生成
 * @since 2022-07-01
 */
public interface ArticleMapper extends BaseMapper<Article> {

    /*
    SELECT
    article.id AS id,
    article.user_id AS userId,
    article.title AS title,
    article.content AS content,
    article.time AS time,
    user.username AS name,
    COUNT(*) AS commentCount
    FROM
    article, user, comment
    WHERE
    article.user_id=user.id
    AND comment.type=0
    AND comment.parent_id=article.id
    GROUP BY article.id
    ORDER BY commentCount DESC;
     */
    @Select("SELECT \n" +
            "article.id AS id, \n" +
            "article.user_id AS userId, \n" +
            "article.title AS title,\n" +
            "article.content AS content,\n" +
            "article.time AS time,\n" +
            "user.username AS name,\n" +
            "COUNT(comment.parent_id) AS commentCount\n" +
            "FROM\n" +
            "article \n" +
            "LEFT JOIN user ON article.user_id = user.id\n" +
            "LEFT JOIN comment ON comment.type=0 AND comment.parent_id=article.id\n" +
            "GROUP BY article.id\n" +
            "ORDER BY commentCount DESC\n" +
            "LIMIT 20")
    List<ArticleDetail> findBestArticleDetail();
}
