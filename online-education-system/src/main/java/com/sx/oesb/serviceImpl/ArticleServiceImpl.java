package com.sx.oesb.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sx.oesb.entity.Article;
import com.sx.oesb.entity.Comment;
import com.sx.oesb.mapper.ArticleMapper;
import com.sx.oesb.mapper.CommentMapper;
import com.sx.oesb.service.ArticleService;
import com.sx.oesb.service.DoConvertVO;
import com.sx.oesb.util.CommentEnum;
import com.sx.oesb.vo.ArticleVO;
import com.sx.oesb.vo.CommentVO;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleMapper articleMapper;
    
    private final CommentMapper commentMapper;

    private final DoConvertVO doConvertVO;
    

    public ArticleServiceImpl(ArticleMapper articleMapper, CommentMapper commentMapper, DoConvertVO doConvertVO) {
		super();
		this.articleMapper = articleMapper;
		this.commentMapper = commentMapper;
		this.doConvertVO = doConvertVO;
	}

	@Override
    public IPage<ArticleVO> checkAllArticle(int pageNum, int pageSize) {
        return articleMapper.selectPage(new Page<>(pageNum, pageSize),
                new QueryWrapper<Article>().orderByDesc("time")).convert(a->{ return doConvertVO.fillArticleVO(a);}
                        );
    }
    
    @Override
    public ArticleVO checkArticle(int id) {
        ArticleVO res = doConvertVO.fillArticleVO(articleMapper.selectById(id));
        if (res == null)
            throw new RuntimeException();
        else
            return res;
    }

    @Override
    public void postArticle(Article article) {
        article.setTime(LocalDateTime.now());
        articleMapper.insert(article);
    }

    @Override
    public IPage<ArticleVO> searchArticle(String keyword, int pageNum, int pageSize) {
        if (Objects.equals(keyword, ""))
            return checkAllArticle(pageNum, pageSize);
        String[] keys = keyword.split(" ");
        QueryWrapper<Article> queryWrapper = new QueryWrapper<Article>();
        for (String key : keys)
            queryWrapper.and(wrapper ->
                    wrapper.like("title", key).or().like("content", key));
        return articleMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper).convert(a->{ return doConvertVO.fillArticleVO(a);}
                );
    }

    @Override
    public IPage<CommentVO> checkCommentOnArticle(int id, int pageNum, int pageSize) {
        Article article = articleMapper.selectById(id);
        if (article == null)
            throw new RuntimeException();
        return commentMapper.selectPage(new Page<>(pageNum, pageSize),
                new QueryWrapper<Comment>()
                        .eq("parent_id", id)
                        .eq("type", CommentEnum.ARTICLE.ordinal())
                        .orderByDesc("time")
        ).convert(c->{ return doConvertVO.fillCommentVO(c);}
                );
    }

    @Override
    public void postCommentOnArticle(Comment comment) {
        Article article = articleMapper.selectById(comment.getParentId());
        if (article == null)
            throw new RuntimeException();
        commentMapper.insert(comment);
    }

	@Override
	public boolean deleteArticle(int id) {
		return articleMapper.deleteById(id) > 0;
	}
}
