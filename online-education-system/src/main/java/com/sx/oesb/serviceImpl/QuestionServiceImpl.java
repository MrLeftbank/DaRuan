package com.sx.oesb.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sx.oesb.entity.Answer;
import com.sx.oesb.entity.Question;
import com.sx.oesb.mapper.AnswerMapper;
import com.sx.oesb.mapper.QuestionMapper;
import com.sx.oesb.service.DoConvertVO;
import com.sx.oesb.service.QuestionService;
import com.sx.oesb.vo.AnswerVO;
import com.sx.oesb.vo.QuestionVO;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class QuestionServiceImpl implements QuestionService {
	private final  DoConvertVO doConvertVO;
	
    private final QuestionMapper questionMapper;

    private final AnswerMapper answerMapper;

   
    public QuestionServiceImpl(DoConvertVO doConvertVO, QuestionMapper questionMapper, AnswerMapper answerMapper) {
		super();
		this.doConvertVO = doConvertVO;
		this.questionMapper = questionMapper;
		this.answerMapper = answerMapper;
	}

	@Override
    public IPage<QuestionVO> checkALLQuestion(int pageNum, int pageSize) {
        return questionMapper.selectPage(new Page<>(pageNum, pageSize),
                new QueryWrapper<Question>().orderByDesc("time")).convert(a->{ return doConvertVO.fillQuestionVO(a);}
                        );
    }

    @Override
    public IPage<QuestionVO> searchQuestion(String keyword, int pageNum, int pageSize) {
        if (Objects.equals(keyword, ""))
            return checkALLQuestion(pageNum, pageSize);
        String[] keys = keyword.split(" ");
        QueryWrapper<Question> queryWrapper = new QueryWrapper<Question>();
        for (String key : keys)
            queryWrapper.and(wrapper ->
                    wrapper.like("title", key).or().like("content", key));
        return questionMapper.selectPage (new Page<>(pageNum, pageSize), queryWrapper).convert(a->{ return doConvertVO.fillQuestionVO(a);}
                );
    }

    @Override
    public void postQuestion(Question question) {
        question.setTime(LocalDateTime.now());
        questionMapper.insert(question);
    }

    @Override
    public IPage<AnswerVO> checkAnswer(int id, int pageNum, int pageSize) {
        getQuestion(id);
        return answerMapper.selectPage(new Page<>(pageNum, pageSize),
                new QueryWrapper<Answer>().eq("question_id", id).orderByDesc("time")).convert(a->{ return doConvertVO.fillAnswerVO(a);}
                        );
    }

    @Override
    public void postAnswer(Answer answer) {
        answer.setTime(LocalDateTime.now());
        answerMapper.insert(answer);
    }

    @Override
    public QuestionVO getQuestion(int id) {
        QuestionVO res = doConvertVO.fillQuestionVO(questionMapper.selectById(id));
        if (res == null)
            throw new RuntimeException();
        else
            return res;
    }

	@Override
	public boolean deleteQuestion(int id) {
		return questionMapper.deleteById(id) > 0;
	}
    
    
}
