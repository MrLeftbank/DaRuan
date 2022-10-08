package com.sx.oesb.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sx.oesb.entity.Answer;
import com.sx.oesb.entity.Comment;
import com.sx.oesb.mapper.AnswerMapper;
import com.sx.oesb.mapper.CommentMapper;
import com.sx.oesb.service.AnswerService;
import com.sx.oesb.service.DoConvertVO;
import com.sx.oesb.util.CommentEnum;
import com.sx.oesb.vo.CommentVO;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AnswerServiceImpl implements AnswerService {
	private final DoConvertVO doConvertVO;
    private final AnswerMapper answerMapper;

    private final CommentMapper commentMapper;

    
    public AnswerServiceImpl(DoConvertVO doConvertVO, AnswerMapper answerMapper, CommentMapper commentMapper) {
		super();
		this.doConvertVO = doConvertVO;
		this.answerMapper = answerMapper;
		this.commentMapper = commentMapper;
	}
    
    @Override
    public IPage<CommentVO> checkCommentOnAnswer(int id, int pageNum, int pageSize) {
        Answer answer = answerMapper.selectById(id);
        if (answer == null)
            throw new RuntimeException();
        return commentMapper.selectPage(new Page<>(pageNum, pageSize),
                new QueryWrapper<Comment>()
                        .eq("parent_id", id)
                        .eq("type", CommentEnum.ANSWER.ordinal())
                        .orderByDesc("time")
        ).convert(c->{ return doConvertVO.fillCommentVO(c);}
        );
    }

    @Override
    public void postCommentOnAnswer(Comment comment) {
        Answer answer = answerMapper.selectById(comment.getParentId());
        if (answer == null)
            throw new RuntimeException();
        comment.setTime(LocalDateTime.now());
        commentMapper.insert(comment);
    }

	@Override
	public boolean deleteAnswer(int id) {
		return answerMapper.deleteById(id) > 0;
	}
}
