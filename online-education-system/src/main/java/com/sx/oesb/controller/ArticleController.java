package com.sx.oesb.controller;

import com.sx.oesb.entity.Article;
import com.sx.oesb.entity.Comment;
import com.sx.oesb.entity.User;
import com.sx.oesb.service.ArticleService;
import com.sx.oesb.util.R;
import com.sx.oesb.util.ResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Api(tags = "文章")
@RestController
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @ApiOperation("请求文章列表")
    @GetMapping("/article")
    public R getArticleList(@RequestParam("pageNum") int pageNum,
                            @RequestParam("pageSize") int pageSize) {
        return new R().success().data(articleService.checkAllArticle(pageNum, pageSize));
    }

    @ApiOperation("发表文章")
    @PostMapping("/article")
    public R createArticle(@AuthenticationPrincipal User user,
                           @RequestBody Article article) {
        R response = new R();
//        if (!article.getUserId().equals(user.getId()))
//            return response.error(ResponseEnum.BAD_REQUEST);
//        else {
        article.setUserId(user.getId());
        articleService.postArticle(article);
        return response.success();
//        }
    }

    @ApiOperation("搜索文章")
    @GetMapping("/article/search")
    public R searchArticle(@RequestParam("keyword") String keyword,
                           @RequestParam("pageNum") int pageNum,
                           @RequestParam("pageSize") int pageSize) {
        return new R().success().data(articleService.searchArticle(keyword, pageNum, pageSize));
    }

    @ApiOperation("获取文章")
    @GetMapping("/article/{aId}")
    public R getArticle(@PathVariable("aId") int id) {
        try {
            return new R().success().data(articleService.checkArticle(id));
        } catch (Exception e) {
            return new R().error(ResponseEnum.NOT_FOUND);
        }
    }

    @ApiOperation("获取文章评论")
    @GetMapping("/article/{aId}/comment")
    public R getArticleComment(@PathVariable("aId") int id,
                               @RequestParam("pageNum") int pageNum,
                               @RequestParam("pageSize") int pageSize) {
        try {
            return new R().success().data(
                    articleService.checkCommentOnArticle(id, pageNum, pageSize));
        } catch (Exception e) {
            return new R().error(ResponseEnum.NOT_FOUND);
        }
    }

    @ApiOperation("评论文章")
    @PostMapping("/article/{aId}/comment")
    public R createArticleComment(@AuthenticationPrincipal User user,
                                  @PathVariable("aId") int id,
                                  @RequestBody Comment comment) {
        if (!comment.getParentId().equals(id))
            return new R().error(ResponseEnum.BAD_REQUEST);
        try {
            comment.setUserId(user.getId());
            comment.setTime(LocalDateTime.now());
            articleService.postCommentOnArticle(comment);
            return new R().success();
        } catch (Exception e) {
            return new R().error(ResponseEnum.NOT_FOUND);
        }
    }
}
