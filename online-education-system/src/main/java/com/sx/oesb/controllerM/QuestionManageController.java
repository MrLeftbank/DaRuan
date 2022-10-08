package com.sx.oesb.controllerM;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sx.oesb.entity.Answer;
import com.sx.oesb.mapper.AnswerMapper;
import com.sx.oesb.mapper.QuestionMapper;
import com.sx.oesb.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(tags = "提问和回答管理")
@RestController
@RequestMapping("/admin")
public class QuestionManageController {

    private final AnswerMapper answerMapper;

    private final QuestionMapper questionMapper;

    public QuestionManageController(AnswerMapper answerMapper, QuestionMapper questionMapper) {
        this.answerMapper = answerMapper;
        this.questionMapper = questionMapper;
    }

    @ApiOperation("获取所有回答")
    @GetMapping("/answer")
    public R getAnswer(@RequestParam int pageNum,
                       @RequestParam int pageSize) {
        return new R().success().data(answerMapper.selectPage(new Page<>(pageNum, pageSize),
                new QueryWrapper<Answer>().orderByDesc("time")));
    }

    @ApiOperation("删除问题")
    @DeleteMapping("/question/{id}")
    public R deleteQuestion(@PathVariable("id") int id) {
        questionMapper.deleteById(id);
        answerMapper.delete(new QueryWrapper<Answer>().eq("question_id", id));
        return new R().success();
    }

    @ApiOperation("删除回答")
    @DeleteMapping("/answer/{id}")
    public R deleteAnswer(@PathVariable("id") int id) {
        answerMapper.deleteById(id);
        return new R().success();
    }
}
