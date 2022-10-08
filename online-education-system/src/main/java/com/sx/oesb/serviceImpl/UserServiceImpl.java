package com.sx.oesb.serviceImpl;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;

import com.sx.oesb.entity.*;
import com.sx.oesb.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sx.oesb.service.UserService;


/** 
* @ClassName UserServiceImpl 
* @Description 个人中心相关服务
* @author 皮龙霖
* @date 2022年7月2日 下午3:37:03 
*  
*/
@Service
public class UserServiceImpl implements UserService {

	
	private final UserMapper userMapper;
	private final ArticleMapper articleMapper;
	private final CommentMapper commentMapper;
	private final QuestionMapper questionMapper;
	private final AnswerMapper answerMapper;
	private final PurchaseMapper purchaseMapper;
	private final StarMapper starMapper;
	private final CourseMapper courseMapper;

	private final UserLogMapper userLogMapper;

	private final PasswordEncoder passwordEncoder;
	
	
	@Autowired
	public UserServiceImpl(UserMapper userMapper, ArticleMapper articleMapper, CommentMapper commentMapper,
						   QuestionMapper questionMapper, AnswerMapper answerMapper, PurchaseMapper purchaseMapper,
						   StarMapper starMapper, CourseMapper courseMapper, UserLogMapper userLogMapper, PasswordEncoder passwordEncoder) {
		super();
		this.userMapper = userMapper;
		this.articleMapper = articleMapper;
		this.commentMapper = commentMapper;
		this.questionMapper = questionMapper;
		this.answerMapper = answerMapper;
		this.purchaseMapper = purchaseMapper;
		this.starMapper = starMapper;
		this.courseMapper = courseMapper;
		this.userLogMapper = userLogMapper;
		this.passwordEncoder = passwordEncoder;
	}


	@Override
	public IPage<User> checkAllUsers(int pageSize, int pageNum) {
		return userMapper.selectPage(new Page<>(pageNum,pageSize), null);
	}

	@Override
	public User searchUserByID(int id) {
		return userMapper.selectById(id);
	}

	@Override
	public boolean deleteUser(int id) {
		return userMapper.deleteById(id) > 0;
	}

	  /**
		 * @Title modifyUser
	     * @author 皮龙霖
	     * @description	直接调用userMapper.updateById,默认在Controller已经完成UserId检验。
	     * @createdate 2022年7月4日 下午3:57:28
	     * @param u
	     * @return 
		 * @see com.sx.oesb.service.UserService#modifyUser(com.sx.oesb.entity.User, int)
	     **/
	@Override
	public boolean modifyUser(User u) {
		//没密码直接修改
		if(u.getPassword() == null )
			return userMapper.updateById(u) > 0;
			
		
		//有新密码就对密码编码
		if(u.getPassword().matches(passwordConstraint))
			u.setPassword(passwordEncoder.encode(u.getPassword()));
		else 
			throw new RuntimeException();
		
		return userMapper.updateById(u) > 0;
	}

	@Override
	public IPage<Article> checkArticleOfUser(int userId, int pageSize, int pageNum) {
		return articleMapper.selectPage(new Page<>(pageNum,pageSize),new QueryWrapper<Article>().eq("user_id",userId));
	}

	@Override
	public IPage<Question> checkQuestionOfUser(int userId, int pageSize, int pageNum) {
		return questionMapper.selectPage(new Page<>(pageNum,pageSize),new QueryWrapper<Question>().eq("user_id",userId));	
	}

	@Override
	public IPage<Answer> checkAnswerOfUser(int userId, int pageSize, int pageNum) {
		return answerMapper.selectPage(new Page<>(pageNum,pageSize),new QueryWrapper<Answer>().eq("user_id",userId));	
	}

	@Override
	public IPage<Comment> checkCommentOfUser(int userId, int pageSize, int pageNum) {
		return commentMapper.selectPage(new Page<>(pageNum,pageSize),new QueryWrapper<Comment>().eq("user_id",userId));	
	}

	
	
	@Override
	public boolean isArticleOfUser(int userId, int aId) {
		Article article =  articleMapper.selectById(aId);
		if(article == null)
			return false;
		return article.getUserId() == userId;
	}


	@Override
	public boolean isQuestionOfUser(int userId, int qId) {
		Question question =  questionMapper.selectById(qId);
		if(question == null)
			return false;
		return question.getUserId() == userId;
	}


	@Override
	public boolean isAnswerOfUser(int userId, int aId) {
		Answer answer =  answerMapper.selectById(aId);
		if(answer == null)
			return false;
		return answer.getUserId() == userId;
	}


	/**
		 * @Title checkPurchaseCourse
	     * @author 皮龙霖
	     * @description	查询用户购买的所有课程，分页。
	     * @createdate 2022年7月2日 下午3:58:34
	     * @param userId
	     * @param pageSize
	     * @param pageNum
	     * @return 
		 * @see com.sx.oesb.service.UserService#checkPurchaseCourse(int, int, int)
	     **/
	@Override
	public IPage<Course> checkPurchaseCourse(int userId, int pageSize, int pageNum) {
//		先查purcahse表获得买的课程的id集合，再条件分页查询
		List<Integer> purchaseCourseIds = purchaseMapper.selectUserPurchaseCourseId(userId);
//		这样分步，可以使用Mybatis原生分页查询返回结果
		
//		如果没有就返回空页
		if(purchaseCourseIds.size() ==0)
			return new Page<>(pageNum,pageSize);
//		去除重复ID
		LinkedHashSet<Integer> 	purchaseCourseIdSet	= new LinkedHashSet<>(purchaseCourseIds);
		return courseMapper.selectPage(new Page<>(pageNum,pageSize),new QueryWrapper<Course>().in("id",purchaseCourseIdSet));
	}

	  /**
		 * @Title checkStarCourse
	     * @author 皮龙霖
	     * @description 查询用户收藏的所有课程，分页。
	     * @createdate 2022年7月2日 下午4:09:05
	     * @param userId
	     * @param pageSize
	     * @param pageNum
	     * @return 
		 * @see com.sx.oesb.service.UserService#checkStarCourse(int, int, int)
	     **/
	@Override
	public IPage<Course> checkStarCourse(int userId, int pageSize, int pageNum) {
//		先查purcahse表获得买的课程的id集合，再条件分页查询
		List<Integer> starCourseIds = starMapper.selectUserStarCourseId(userId);
//		这样分步，可以使用Mybatis原生分页查询返回结果
		
//		如果没有就返回空页
		if(starCourseIds.size() ==0)
			return new Page<>(pageNum,pageSize);
//		去除重复ID
		LinkedHashSet<Integer> 	starCourseIdSet	= new LinkedHashSet<>(starCourseIds);
		return courseMapper.selectPage(new Page<>(pageNum,pageSize),new QueryWrapper<Course>().in("id",starCourseIdSet));
	}
	
	
	

	  /**
		 * @Title isCoursePurchsed
	     * @author 皮龙霖
	     * @description purchaseMapper.selectList双条件查询QueryWrapper
	     * @createdate 2022年7月5日 下午4:43:40
	     * @param userId
	     * @param courseId
	     * @return 
		 * @see com.sx.oesb.service.UserService#isCoursePurchsed(int, int)
	     **/
	@Override
	public boolean isCoursePurchsed(int userId, int courseId) {
		return purchaseMapper.selectList(new QueryWrapper<Purchase>()
				.eq("course_id",courseId)
				.eq("user_id",userId)
				).size() > 0 ;
	}


	  /**
		 * @Title isCourseStared
	     * @author 皮龙霖
	     * @description starMapper.selectList双条件查询QueryWrapper
	     * @createdate 2022年7月5日 下午4:49:29
	     * @param userId
	     * @param courseId
	     * @return 
		 * @see com.sx.oesb.service.UserService#isCourseStared(int, int)
	     **/
	@Override
	public boolean isCourseStared(int userId, int courseId) {
		return starMapper.selectList(new QueryWrapper<Star>()
				.eq("course_id",courseId)
				.eq("user_id",userId)
				).size() > 0 ;
	}
	
	
	
	  /**
		 * @Title deleteUserStar
	     * @author 皮龙霖
	     * @description starMapper.selectList先查出starId，再删除
	     * @createdate 2022年7月12日 上午10:29:05
	     * @param userId
	     * @param courseId
	     * @return 
		 * @see com.sx.oesb.service.UserService#deleteUserStar(int, int)
	     **/
	@Override
	public boolean deleteUserStar(int userId, int courseId) {
			Star star =	starMapper.selectList(new QueryWrapper<Star>()
				.eq("course_id",courseId)
				.eq("user_id",userId)
				).get(0);
			if(star == null)
				return false;
			return starMapper.deleteById(star.getId()) > 0;
	}

	@Cacheable(cacheNames = "user")
	public User searchUserByUsername(String username) throws UsernameNotFoundException {

		System.out.println("------");

		List<User> users = userMapper.selectPage(new Page<>(1, 1),
				new QueryWrapper<User>().eq("username", username)).getRecords();
		if (users.isEmpty())
			throw new UsernameNotFoundException("Username: " + username + " not found");
		else
			return users.get(0);
	}

	@Override
	public User login(String username, String password) {
		User user = searchUserByUsername(username);
		if (user.getPassword().equals(password) || passwordEncoder.matches(password, user.getPassword())) {
			UserLog userLog = new UserLog();
			userLog.setUserId(user.getId());
			userLog.setTime(LocalDateTime.now());
			userLogMapper.insert(userLog);
			return user;
		}
		throw new RuntimeException();
	}

	@Value("${constraint.username}")
	private String usernameConstraint;

	@Value("${constraint.password}")
	private String passwordConstraint;

	@Override
	public void register(String username, String password) {
		if (username.matches(usernameConstraint) && password.matches(passwordConstraint)) {
			try {
				searchUserByUsername(username);
				throw new RuntimeException();
			} catch (UsernameNotFoundException e) {
				User user = new User();
				user.setUsername(username);
				user.setPassword(passwordEncoder.encode(password));
				user.setName(username);
				user.setRegisttime(LocalDateTime.now());
				userMapper.insert(user);
			}
		} else
			throw new RuntimeException();
	}

}
