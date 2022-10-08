package com.sx.oesb.controllerM;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sx.oesb.mapper.CommentMapper;
import com.sx.oesb.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "评论管理")
@RequestMapping("/admin")
public class CommentManageController {

    private final CommentMapper commentMapper;

    public CommentManageController(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @GetMapping("/comment")
    @ApiOperation("获取评论列表")
    public R getComment(@RequestParam("pageNum") int pageNum,
                        @RequestParam("pageSize") int pageSize) {
        return new R().success().data(commentMapper.selectPage(new Page<>(pageNum, pageSize),
                new QueryWrapper<>()));
    }

    @DeleteMapping("/comment/{id}")
    @ApiOperation("删除评论")
    public R deleteComment(@PathVariable int id) {
        commentMapper.deleteById(id);
        return new R().success();
    }
}
