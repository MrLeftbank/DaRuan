/**
 * @Title UerIndexController.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月4日上午9:28:23
 */
package com.sx.oesb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sx.oesb.entity.User;
import com.sx.oesb.service.AnswerService;
import com.sx.oesb.service.ArticleService;
import com.sx.oesb.service.QuestionService;
import com.sx.oesb.service.UserService;
import com.sx.oesb.util.R;
import com.sx.oesb.util.ResponseEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @ClassName UerIndexController 
* @Description 个人中心的controller
* @author 皮龙霖
* @date 2022年7月4日 上午9:28:23 
*  
*/
@Api(tags="个人中心")
@RestController
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	private final QuestionService questionService;
	private final AnswerService answerService;
	private final ArticleService articleService;

	@Autowired
	public UserController(UserService userService, QuestionService questionService, AnswerService answerService,
			ArticleService articleService) {
		super();
		this.userService = userService;
		this.questionService = questionService;
		this.answerService = answerService;
		this.articleService = articleService;
	}

	  /**
		 * @Title getUser
	     * @author 皮龙霖
	     * @description 用户查看个人信息，登录验证时已经查了一次表
	     * @createdate 2022年7月4日 上午9:59:11
	     * @param user
	     * @return R
	     **/
	@ApiOperation("用户查看自己")
	@GetMapping("")
	public R getUserSelf(@AuthenticationPrincipal User user){
		R responseBody = new R();
		return responseBody.success().data(userService.searchUserByID(user.getId()));    
	}
	

	/**
		 * @Title updateUser
	     * @author 皮龙霖
	     * @description 用户改自己，用户登录验证时已经确认存在
	     * @createdate 2022年7月4日 上午9:59:09
	     * @param user
	     * @param newUser
	     * @return R
	     **/
	@ApiOperation("用户改自己")
	@PutMapping("")
	public R updateUser(@AuthenticationPrincipal User user,@RequestBody(required = true) User newUser,@RequestParam(name = "password",required = false)String password){
		R responseBody = new R();
		
		//保证用户在改自己
		newUser.setId(user.getId());
		//如果改密码
		if(password != null)
			newUser.setPassword(password);
		
		boolean modify = false;
		try {
			modify = userService.modifyUser(newUser);
		} catch (Exception exception) {
            exception.printStackTrace();
            return responseBody.error(ResponseEnum.USER_PASSWORD_FAIL); 
		}
		
		if(modify)
			return responseBody.success();
		else {
			return responseBody.error(ResponseEnum.USER_UPDATE_ERROR);
		}
	}
	
	  /**
		 * @Title getUserArticle
	     * @author 皮龙霖
	     * @description 获取用户文章，页面大小与页码是可选参数
	     * @createdate 2022年7月4日 上午10:07:58
	     * @param user
	     * @param pageNum
	     * @param pageSize
	     * @return R
	     **/
	@ApiOperation("获取用户文章")
	@GetMapping("/article")
	public R getUserArticle(@AuthenticationPrincipal User user,@RequestParam(defaultValue = "0")int pageNum,@RequestParam(defaultValue = "8")int pageSize){
		R responseBody = new R();
		return responseBody.success().data(userService.checkArticleOfUser(user.getId(), pageSize, pageNum));
	}
	
	  /**
		 * @Title getUserQuestion
	     * @author 皮龙霖
	     * @description 获取用户问题，页面大小与页码是可选参数
	     * @createdate 2022年7月4日 上午10:10:09
	     * @param user
	     * @param pageNum
	     * @param pageSize
	     * @return R
	     **/
	@ApiOperation("获取用户问题")
	@GetMapping("/question")
	public R getUserQuestion(@AuthenticationPrincipal User user,@RequestParam(defaultValue = "0")int pageNum,@RequestParam(defaultValue = "8")int pageSize){
		R responseBody = new R();
		return responseBody.success().data(userService.checkQuestionOfUser(user.getId(), pageSize, pageNum));
	}

	  /**
		 * @Title getUserAnswer
	     * @author 皮龙霖
	     * @description 获取用户回答，页面大小与页码是可选参数
	     * @createdate 2022年7月4日 上午10:12:17
	     * @param user
	     * @param pageNum
	     * @param pageSize
	     * @return R
	     **/
	@ApiOperation("获取用户回答")
	@GetMapping("/answer")
	public R getUserAnswer(@AuthenticationPrincipal User user,@RequestParam(defaultValue = "0")int pageNum,@RequestParam(defaultValue = "8")int pageSize){
		R responseBody = new R();
		return responseBody.success().data(userService.checkAnswerOfUser(user.getId(), pageSize, pageNum));
	}
	
	  /**
		 * @Title getUserComment
	     * @author 皮龙霖
	     * @description 获取用户评论，页面大小与页码是可选参数
	     * @createdate 2022年7月4日 上午10:13:04
	     * @param user
	     * @param pageNum
	     * @param pageSize
	     * @return R
	     **/
	@ApiOperation("获取用户评论")
	@GetMapping("/comment")
	public R getUserComment(@AuthenticationPrincipal User user,@RequestParam(defaultValue = "0")int pageNum,@RequestParam(defaultValue = "8")int pageSize){
		R responseBody = new R();
		return responseBody.success().data(userService.checkCommentOfUser(user.getId(), pageSize, pageNum));
	}
	
	  /**
		 * @Title getUserPurchaseCourse
	     * @author 皮龙霖
	     * @description 获取用户购买课程，页面大小与页码是可选参数
	     * @createdate 2022年7月4日 上午10:26:39
	     * @param user
	     * @param pageNum
	     * @param pageSize
	     * @return R
	     **/
	@ApiOperation("获取用户购买课程")
	@GetMapping("/order")
	public R getUserPurchaseCourse(@AuthenticationPrincipal User user,@RequestParam(defaultValue = "0")int pageNum,@RequestParam(defaultValue = "8")int pageSize){
		R responseBody = new R();
		return responseBody.success().data(userService.checkPurchaseCourse(user.getId(), pageSize, pageNum));
	}
	
	
	  /**
		 * @Title getUserStarCourse
	     * @author 皮龙霖
	     * @description 获取用户收藏课程，页面大小与页码是可选参数
	     * @createdate 2022年7月4日 上午10:26:41
	     * @param user
	     * @param pageNum
	     * @param pageSize
	     * @return R
	     **/
	@ApiOperation("获取用户收藏课程")
	@GetMapping("/star")
	public R getUserStarCourse(@AuthenticationPrincipal User user,@RequestParam(defaultValue = "0")int pageNum,@RequestParam(defaultValue = "8")int pageSize){
		R responseBody = new R();
		return responseBody.success().data(userService.checkStarCourse(user.getId(), pageSize, pageNum));
	}
	
	
	  /**
		 * @Title getUserQuestion
	     * @author 皮龙霖
	     * @description 检验本人问题后直接删除
	     * @createdate 2022年7月9日 上午11:13:59
	     * @param user
	     * @param qId
	     * @return R
	     **/
	@ApiOperation("删除用户问题")
	@DeleteMapping("/question/{qId}")
	public R deleteUserQuestion(@AuthenticationPrincipal User user,@PathVariable(required = true) int qId){
		R responseBody = new R();
		
		if(!userService.isQuestionOfUser(user.getId(), qId))
			return responseBody.error(ResponseEnum.USER_OTHER);
		
		if(questionService.deleteQuestion(qId))
			return responseBody.success();
		else 
			return responseBody.error(ResponseEnum.SERVICE_FAIL);
	}
	
	
	  /**
		 * @Title getUserAnswer
	     * @author 皮龙霖
	     * @description 检验本人回答后直接删除
	     * @createdate 2022年7月9日 上午11:17:43
	     * @param user
	     * @param aId
	     * @return R
	     **/
	@ApiOperation("删除用户回答")
	@DeleteMapping("/answer/{aId}")
	public R deleteUserAnswer(@AuthenticationPrincipal User user,@PathVariable(required = true) int aId){
		R responseBody = new R();
		
		if(!userService.isAnswerOfUser(user.getId(), aId))
			return responseBody.error(ResponseEnum.USER_OTHER);
		
		if(answerService.deleteAnswer(aId))
			return responseBody.success();
		else 
			return responseBody.error(ResponseEnum.SERVICE_FAIL);
	}
	
	  /**
		 * @Title getUserArticle
	     * @author 皮龙霖
	     * @description 检验本人文章后直接删除
	     * @createdate 2022年7月9日 上午11:17:53
	     * @param user
	     * @param qId
	     * @return R
	     **/
	@ApiOperation("删除用户文章")
	@DeleteMapping("/article/{qId}")
	public R deleteUserArticle(@AuthenticationPrincipal User user,@PathVariable(required = true) int qId){
		R responseBody = new R();
		
		if(!userService.isArticleOfUser(user.getId(), qId))
			return responseBody.error(ResponseEnum.USER_OTHER);
		
		if(articleService.deleteArticle(qId))
			return responseBody.success();
		else 
			return responseBody.error(ResponseEnum.SERVICE_FAIL);
	}
	
	  /**
		 * @Title deleteUserStar
	     * @author 皮龙霖
	     * @description 直接删除收藏，不验证有没有收藏过
	     * @createdate 2022年7月12日 上午10:34:46
	     * @param user
	     * @param courseId
	     * @return R
	     **/
	@ApiOperation("删除用户课程收藏")
	@DeleteMapping("/star/{courseId}")
	public R deleteUserStar(@AuthenticationPrincipal User user,@PathVariable(required = true) int courseId){
		R responseBody = new R();
		
		if(userService.deleteUserStar(user.getId(), courseId))
			return responseBody.success();
		else 
			return responseBody.error(ResponseEnum.SERVICE_FAIL);
	}
}
