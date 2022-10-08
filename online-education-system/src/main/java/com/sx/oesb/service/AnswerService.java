package com.sx.oesb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sx.oesb.entity.Comment;
import com.sx.oesb.vo.CommentVO;

public interface AnswerService {
    IPage<CommentVO> checkCommentOnAnswer(int id, int pageNum, int pageSize);

    void postCommentOnAnswer(Comment comment);

    public boolean deleteAnswer(int id);
}
