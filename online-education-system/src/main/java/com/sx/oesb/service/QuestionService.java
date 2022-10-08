package com.sx.oesb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sx.oesb.entity.Answer;
import com.sx.oesb.entity.Question;
import com.sx.oesb.vo.AnswerVO;
import com.sx.oesb.vo.QuestionVO;

public interface QuestionService {
    public IPage<QuestionVO> checkALLQuestion(int pageNum, int pageSize);

    public IPage<QuestionVO> searchQuestion(String description, int pageNum, int pageSize);

    public void postQuestion(Question question);

    public IPage<AnswerVO> checkAnswer(int id, int pageNum, int pageSize);

    public void postAnswer(Answer answer);

    QuestionVO getQuestion(int id);
    
    public boolean deleteQuestion(int id);
}
