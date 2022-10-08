/**
 * @Title DoConvertVO.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月11日下午3:05:10
 */
package com.sx.oesb.service;

import com.sx.oesb.entity.Answer;
import com.sx.oesb.entity.Article;
import com.sx.oesb.entity.Comment;
import com.sx.oesb.entity.Question;
import com.sx.oesb.entity.User;
import com.sx.oesb.vo.AnswerVO;
import com.sx.oesb.vo.ArticleVO;
import com.sx.oesb.vo.CommentVO;
import com.sx.oesb.vo.QuestionVO;

/** 
* @ClassName DoConvertVO 
* @Description 将DO实体转换为VO实体
* @author 皮龙霖
* @date 2022年7月11日 下午3:05:10 
*  
*/
public interface DoConvertVO {
	
	  /**
		 * @Title fillCommentVO
	     * @author 皮龙霖
	     * @description 为Comment填充作者消息
	     * @createdate 2022年7月11日 下午3:22:36
	     * @param c
	     * @return CommentVO
	     **/
	public CommentVO fillCommentVO(Comment c);
	

	  /**
		 * @Title fillAnswerVO
	     * @author 皮龙霖
	     * @description 为Answer填充作者消息
	     * @createdate 2022年7月11日 下午3:22:47
	     * @param a
	     * @return AnswerVO
	     **/
	public AnswerVO fillAnswerVO(Answer a);
	

	 
	  /**
		 * @Title fillArticleVO
	     * @author 皮龙霖
	     * @description 为Article填充作者消息
	     * @createdate 2022年7月11日 下午3:25:51
	     * @param a
	     * @return ArticleVO
	     **/
	public ArticleVO fillArticleVO(Article a);
	
	  /**
		 * @Title fillQuestionVO
	     * @author 皮龙霖
	     * @description 为Question填充作者消息
	     * @createdate 2022年7月11日 下午3:29:53
	     * @param q
	     * @return QuestionVO
	     **/
	public QuestionVO fillQuestionVO(Question q);
	
	  /**
		 * @Title loadUser
	     * @author 皮龙霖
	     * @description 加载id用户，为上面几个方法服务
	     * @createdate 2022年7月11日 下午3:09:35
	     * @param uId
	     * @return User
	     **/
	public User loadUser(int uId);
}
