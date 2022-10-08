/**
 * @Title DoconvertVOImpl.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月11日下午3:33:50
 */
package com.sx.oesb.serviceImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sx.oesb.entity.Answer;
import com.sx.oesb.entity.Article;
import com.sx.oesb.entity.Comment;
import com.sx.oesb.entity.Question;
import com.sx.oesb.entity.User;
import com.sx.oesb.mapper.UserMapper;
import com.sx.oesb.service.DoConvertVO;
import com.sx.oesb.vo.AnswerVO;
import com.sx.oesb.vo.ArticleVO;
import com.sx.oesb.vo.CommentVO;
import com.sx.oesb.vo.QuestionVO;

/** 
* @ClassName DoconvertVOImpl 
* @Description 实现转换的服务
* @author 皮龙霖
* @date 2022年7月11日 下午3:33:50 
*  
*/
@Component
public class DoconvertVOImpl implements DoConvertVO {
	
	private final UserMapper userMapper;
	private User emptyUser;
	
	@Autowired
	public DoconvertVOImpl(UserMapper userMapper) {
		super();
		this.userMapper = userMapper;
		this.emptyUser = new User();
	}

	/**
	 * @Title fillCommentVO
	   * @author 皮龙霖
	   * @description 取用户信息，放入返回，其他复制
	   * @createdate 2022年7月11日 下午3:33:50
	   * @param c
	   * @return 
	 * @see com.sx.oesb.service.DoConvertVO#fillCommentVO(com.sx.oesb.entity.Comment)
	   **/

	@Override
	public CommentVO fillCommentVO(Comment c) {
		if(c == null)
			return null;
		CommentVO cVo = new CommentVO();
		BeanUtils.copyProperties(c, cVo);
		User user = loadUser(c.getUserId());
		cVo.setUserName(user.getName());
		cVo.setUserPhotoUrl(user.getPhotoUrl());
		return cVo;
	}

	/**
	 * @Title fillAnswerVO
	   * @author 皮龙霖
	   * @description  取用户信息，放入返回，其他复制
	   * @createdate 2022年7月11日 下午3:33:50
	   * @param a
	   * @return 
	 * @see com.sx.oesb.service.DoConvertVO#fillAnswerVO(com.sx.oesb.entity.Answer)
	   **/

	@Override
	public AnswerVO fillAnswerVO(Answer a) {
		if(a == null)
			return null;
		AnswerVO aVo = new AnswerVO();
		BeanUtils.copyProperties(a, aVo);
		User user = loadUser(a.getUserId());
		aVo.setUserName(user.getName());
		aVo.setUserPhotoUrl(user.getPhotoUrl());
		return aVo;
	}

	/**
	 * @Title fillArticleVO
	   * @author 皮龙霖
	   * @description 取用户信息，放入返回，其他复制
	   * @createdate 2022年7月11日 下午3:33:50
	   * @param a
	   * @return 
	 * @see com.sx.oesb.service.DoConvertVO#fillArticleVO(com.sx.oesb.entity.Article)
	   **/

	@Override
	public ArticleVO fillArticleVO(Article a) {
		if(a == null)
			return null;
		ArticleVO aVo = new ArticleVO();
		BeanUtils.copyProperties(a, aVo);
		User user = loadUser(a.getUserId());
		aVo.setUserName(user.getName());
		aVo.setUserPhotoUrl(user.getPhotoUrl());
		return aVo;
	}

	/**
	 * @Title fillQuestionVO
	   * @author 皮龙霖
	   * @description 取用户信息，放入返回，其他复制
	   * @createdate 2022年7月11日 下午3:33:50
	   * @param q
	   * @return 
	 * @see com.sx.oesb.service.DoConvertVO#fillQuestionVO(com.sx.oesb.entity.Question)
	   **/

	@Override
	public QuestionVO fillQuestionVO(Question q) {
		if(q == null)
			return null;
		QuestionVO qVo = new QuestionVO();
		BeanUtils.copyProperties(q, qVo);
		User user = loadUser(q.getUserId());
		qVo.setUserName(user.getName());
		qVo.setUserPhotoUrl(user.getPhotoUrl());
		return qVo;
	}

	/**
	 * @Title loadUser
	   * @author 皮龙霖 
	   * @description userMapper.selectById并保证user不是空指针
	   * @createdate 2022年7月11日 下午3:33:50
	   * @param uId
	   * @return 
	 * @see com.sx.oesb.service.DoConvertVO#loadUser(int)
	   **/

	@Override
	public User loadUser(int uId) {
		User user = userMapper.selectById(uId);
		if(user == null)
			user = emptyUser;
		return user;
	}

}
