package com.sx.oesb.controller;

import com.sx.oesb.entity.Answer;
import com.sx.oesb.entity.Comment;
import com.sx.oesb.entity.Question;
import com.sx.oesb.entity.User;
import com.sx.oesb.service.AnswerService;
import com.sx.oesb.service.QuestionService;
import com.sx.oesb.util.CommentEnum;
import com.sx.oesb.util.R;
import com.sx.oesb.util.ResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(tags = "提问、回答")
@RestController
public class QuestionController {
    private final QuestionService questionService;

    private final AnswerService answerService;

    public QuestionController(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @ApiOperation("获取问题列表")
    @GetMapping("/question")
    public R listQuestion(@RequestParam("pageNum") int pageNum,
                                        @RequestParam("pageSize") int pageSize) {
        return new R().success().data(questionService.checkALLQuestion(pageNum, pageSize));
    }

    @ApiOperation("添加问题")
    @PostMapping("/question")
    public R createQuestion(@AuthenticationPrincipal User user, @RequestBody Question question) {
//        if (!Objects.equals(question.getUserId(), user.getId()))
//            return new R().error(ResponseEnum.BAD_REQUEST);
        question.setUserId(user.getId());
        questionService.postQuestion(question);
        return new R().success();
    }


    @ApiOperation("检索问题")
    @GetMapping("/question/search")
    public R searchQuestion(@RequestParam("keyword") String keyword,
                            @RequestParam("pageNum") int pageNum,
                            @RequestParam("pageSize") int pageSize) {
        return new R().success().data(questionService.searchQuestion(keyword, pageNum, pageSize));
    }

    @ApiOperation("按照 id 查找问题")
    @GetMapping("/question/{id}")
    public R getQuestion(@PathVariable("id") int id) {
        R response = new R();
        try {
            response.success().data(questionService.getQuestion(id));
        } catch (Exception e) {
            response.error(ResponseEnum.NOT_FOUND);
        }

        return response;
    }

    @ApiOperation("创建回答")
    @PostMapping("/question/{qId}/answer")
    public R createQuestionAnswer(@AuthenticationPrincipal User user,
                                  @PathVariable("qId") int id,
                                  @RequestBody Answer answer) {
//        if (!answer.getUserId().equals(user.getId()))
//            return new R().error(ResponseEnum.USER_OTHER);
//        if (id != answer.getQuestionId())
//            return new R().error(ResponseEnum.BAD_REQUEST);
        answer.setUserId(user.getId());
        answer.setQuestionId(id);
        questionService.postAnswer(answer);
        return new R().success();
    }

    @ApiOperation("查找问题中的回答")
    @GetMapping("/question/{qId}/answer")
    public R listQuestionAnswer(@PathVariable("qId") int id,
                                @RequestParam("pageNum") int pageNum,
                                @RequestParam("pageSize") int pageSize) {
        R response = new R();
        try {
            response.success().data(questionService.checkAnswer(id, pageNum, pageSize));
            return response;
        } catch (Exception e) {
            response.error(ResponseEnum.NOT_FOUND);
            return response;
        }
    }

    @ApiOperation("查找回答下的评论")
    @GetMapping("/answer/{aId}/comment")
    public R listAnswerComment(@PathVariable("aId") int id,
                               @RequestParam("pageNum") int pageNum,
                               @RequestParam("pageSize") int pageSize) {
        R response = new R();
        try {
            response.success().data(answerService.checkCommentOnAnswer(id, pageNum, pageSize));
        } catch (Exception e) {
            response.error(ResponseEnum.NOT_FOUND);
        }
        return response;
    }

    @ApiOperation("对回答创建评论")
    @PostMapping("/answer/{aId}/comment")
    public R createAnswerComment(@PathVariable("aId") int id,
                                 @AuthenticationPrincipal User user,
                                 @RequestBody Comment comment) {
        R response = new R();
        if (comment.getParentId() != id)
            return response.error(ResponseEnum.BAD_REQUEST);
//        if (!Objects.equals(comment.getUserId(), user.getId()))
//            return response.error(ResponseEnum.USER_OTHER);
        comment.setType(CommentEnum.ANSWER.ordinal());
        comment.setUserId(user.getId());
        try {
            answerService.postCommentOnAnswer(comment);
        } catch (Exception e) {
            response.error(ResponseEnum.NOT_FOUND);
        }
        return response.success();
    }
}
