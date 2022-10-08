package com.sx.oesb.controllerM;

import com.sx.oesb.mapper.ArticleMapper;
import com.sx.oesb.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "文章管理")
@RequestMapping("/admin")
public class ArticleManageController {
    private final ArticleMapper articleMapper;

    public ArticleManageController(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @DeleteMapping("/article/{id}")
    @ApiOperation("删除文章")
    public R deleteArticle(@PathVariable int id) {
        articleMapper.deleteById(id);
        return new R().success();
    }
}
