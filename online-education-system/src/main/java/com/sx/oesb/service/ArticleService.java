package com.sx.oesb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sx.oesb.entity.Article;
import com.sx.oesb.entity.Comment;
import com.sx.oesb.vo.ArticleVO;
import com.sx.oesb.vo.CommentVO;

public interface ArticleService {
    IPage<ArticleVO> checkAllArticle(int pageNum, int pageSize);

    ArticleVO checkArticle(int id);

    void postArticle(Article article);
    IPage<ArticleVO> searchArticle(String keyword, int pageNum, int pageSize);
    IPage<CommentVO> checkCommentOnArticle(int id, int pageNum, int pageSize);
    void postCommentOnArticle(Comment comment);
   
    public boolean deleteArticle(int id);

}
