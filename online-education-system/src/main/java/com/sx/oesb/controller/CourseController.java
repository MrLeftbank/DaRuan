/**
 * @Title CourseController.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月7日上午8:57:21
 */
package com.sx.oesb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sx.oesb.entity.Chapter;
import com.sx.oesb.entity.Course;
import com.sx.oesb.entity.Section;
import com.sx.oesb.entity.Star;
import com.sx.oesb.entity.User;
import com.sx.oesb.service.CourseService;
import com.sx.oesb.service.DomainService;
import com.sx.oesb.service.UserService;
import com.sx.oesb.util.R;
import com.sx.oesb.util.ResponseEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @ClassName CourseController 
* @Description 课程页面与课程中心控制器
* @author 皮龙霖
* @date 2022年7月7日 上午8:57:21 
*  
*/
@Api(tags="课程")
@RestController
public class CourseController {

	private final DomainService domainService;
	private final CourseService courseService;
	private final UserService userService;
	
	@Autowired
	public CourseController(DomainService domainService, CourseService courseService, UserService userService) {
		super();
		this.domainService = domainService;
		this.courseService = courseService;
		this.userService = userService;
	}

	/**
		 * @Title getParentDomain
	     * @author 皮龙霖
	     * @description 查询所有父类别，返回类别列表
	     * @createdate 2022年7月7日 上午9:56:31
	     * @return R
	     **/
	@ApiOperation("查询所有父类别")
	@GetMapping("/parent-domain")
	public R getParentDomain(){
		R responseBody = new R();
		return responseBody.success().data(domainService.checkAllParentDomains());    
	}
	
	  /**
		 * @Title getchildDomain
	     * @author 皮龙霖
	     * @description 查询父类别的子类别，返回类别列表
	     * @createdate 2022年7月7日 上午9:57:18
	     * @param id
	     * @return R
	     **/
	@ApiOperation("查询父类别的子类别")
	@GetMapping("/domain/{dId}/domain")
	public R getchildDomain(@PathVariable(required = true,name = "dId") Integer id){
		R responseBody = new R();
		
		return responseBody.success().data(domainService.searchChildDomainByID(id));    
	}
	
	  /**
		 * @Title getCourseList
	     * @author 皮龙霖
	     * @description 查询所有课程
	     * @createdate 2022年7月7日 上午9:57:52
	     * @param pageNum
	     * @param pageSize
	     * @return R
	     **/
	@ApiOperation("查询所有课程")
	@GetMapping("/course")
	public R getCourseList(@RequestParam(defaultValue = "0")int pageNum,@RequestParam(defaultValue = "8")int pageSize){
		R responseBody = new R();
		return responseBody.success().data(courseService.checkAllCourse(pageSize,pageNum));    
	}
	
	  /**
		 * @Title searchCourseList
	     * @author 皮龙霖
	     * @description 多条件搜索课程，类别参数非必须，name模糊匹配
	     * @createdate 2022年7月7日 上午9:58:30
	     * @param name
	     * @param domainId
	     * @param pageNum
	     * @param pageSize
	     * @param orderTag
	     * @return R
	     **/
	@ApiOperation("多条件搜索课程")
	@GetMapping("/course/search")
	public R searchCourseList(@RequestParam(required = false) String name,@RequestParam(required = false) Integer domainId,@RequestParam(defaultValue = "0")int pageNum,@RequestParam(defaultValue = "8")int pageSize,@RequestParam(defaultValue = "1") int orderTag){
		R responseBody = new R();
		if(domainId == null)
			return responseBody.success().data(courseService.searchCourse(name, pageSize, pageNum, orderTag));
		else
			return responseBody.success().data(domainService.searchCourseByDomain(domainId, name, pageSize, pageNum, orderTag));    
	}
	
	  /**
		 * @Title getCourse
	     * @author 皮龙霖
	     * @description 获取id课程详情
	     * @createdate 2022年7月7日 上午9:58:58
	     * @param id
	     * @return R
	     **/
	@ApiOperation("获取课程详情")
	@GetMapping("/course/{cId}")
	public R getCourse(@PathVariable(required = true,name = "cId") Integer id){
		R responseBody = new R();
		return responseBody.success().data(courseService.searchCourseById(id));    
	}
	
	  /**
		 * @Title starCourse
	     * @author 皮龙霖
	     * @description 收藏课程，接受包含评论与评分的完整star，其中userId覆盖为登录用户的
	     * @createdate 2022年7月7日 上午9:59:19
	     * @param user
	     * @param star
	     * @return R
	     **/
	@ApiOperation("收藏课程")
	@PostMapping("/course/star")
	public R starCourse(@AuthenticationPrincipal User user,@RequestBody Star star){
		R responseBody = new R();
//		没给参数返回错误
		if(star.getCourseId() == null)
			return responseBody.error(ResponseEnum.BAD_REQUEST);
		
//		课程不存在返回错误
		Course course = courseService.searchCourseById(star.getCourseId());
		if(course == null)
			return responseBody.error(ResponseEnum.NOT_FOUND);
		
		star.setUserId(user.getId());
		if(courseService.starCourse(star))
			return responseBody.success();
		else 
			return responseBody.error(ResponseEnum.SERVICE_FAIL);
	}
	
	  /**
		 * @Title buyCourse
	     * @author 皮龙霖
	     * @description 购买课程
	     * @createdate 2022年7月7日 上午10:00:16
	     * @param user
	     * @param courseId
	     * @return R
	     **/
	@ApiOperation("购买课程")
	@PostMapping("/course/purchase")
	public R buyCourse(@AuthenticationPrincipal User user,@RequestParam(required = true) Integer courseId){
		R responseBody = new R();
		
//		课程不存在返回错误
		Course course = courseService.searchCourseById(courseId);
		if(course == null)
			return responseBody.error(ResponseEnum.NOT_FOUND);
		
//		余额不够价格，返回错误
		if(courseService.searchCourseById(courseId).getPrice() > user.getBalance())
			return responseBody.error(ResponseEnum.USER_BALANCE_NOT_ENOUGH);
		user.setPassword(null);
		if(courseService.buyCourse(course,user))
			return responseBody.success();
		else 
			return responseBody.error(ResponseEnum.SERVICE_FAIL);
	}
	
	
	  /**
		 * @Title getCourseChapterList
	     * @author 皮龙霖
	     * @description 查看id课程的章节列表
	     * @createdate 2022年7月7日 上午10:00:33
	     * @param id
	     * @return R
	     **/
	@ApiOperation("查看课程章节列表")
	@GetMapping("/course/{cId}/chapter")
	public R getCourseChapterList(@PathVariable(required = true,name = "cId") Integer id){
		R responseBody = new R();
	
		return responseBody.success().data(courseService.checkAllChaptersOfCourse(id));
	}
	
	
	  /**
		 * @Title getCourseChapterDetail
	     * @author 皮龙霖
	     * @description 查看id章节详情
	     * @createdate 2022年7月7日 上午10:00:48
	     * @param id
	     * @return R
	     **/
	@ApiOperation("查看章节详情")
	@GetMapping("/chapter/{cId}")
	public R getCourseChapterDetail(@PathVariable(required = true,name = "cId") Integer id){
		R responseBody = new R();
		
		Chapter chapter = courseService.searchChapterById(id);
		if(chapter == null)
			return responseBody.error(ResponseEnum.NOT_FOUND);
	
		return responseBody.success().data(chapter);
	}
	
	  /**
		 * @Title isStarCourse
	     * @author 皮龙霖
	     * @description 课程详情显示收藏按钮，是否收藏了
	     * @createdate 2022年7月7日 下午3:22:47
	     * @param user
	     * @param id
	     * @return R
	     **/
	@ApiOperation("查询是否收藏课程")
	@GetMapping("/course/{cId}/is-star")
	public R isStarCourse(@AuthenticationPrincipal User user,@PathVariable(required = true,name = "cId") int id){
		R responseBody = new R();
//		课程不存在返回错误
		Course course = courseService.searchCourseById(id);
		if(course == null)
			return responseBody.error(ResponseEnum.NOT_FOUND);
		
		return responseBody.success().data(userService.isCourseStared(user.getId(), id));
	}

	  /**
		 * @Title isPurchaseCourse
	     * @author 皮龙霖
	     * @description 课程详情显示购买按钮，是否购买过
	     * @createdate 2022年7月7日 下午3:23:19
	     * @param user
	     * @param id
	     * @return R
	     **/
	@ApiOperation("查询是否购买课程")
	@GetMapping("/course/{cId}/is-purchase")
	public R isPurchaseCourse(@AuthenticationPrincipal User user,@PathVariable(required = true,name = "cId") int id){
		R responseBody = new R();
//		课程不存在返回错误
		Course course = courseService.searchCourseById(id);
		if(course == null)
			return responseBody.error(ResponseEnum.NOT_FOUND);
		
		return responseBody.success().data(userService.isCoursePurchsed(user.getId(), id));
	}
	
	
	  /**
		 * @Title getChapterSectionList
	     * @author 皮龙霖
	     * @description 查看id章节的小节列表
	     * @createdate 2022年7月7日 下午3:27:07
	     * @param id
	     * @return R
	     **/
	@ApiOperation("查看章节小节列表")
	@GetMapping("/chapter/{cId}/section")
	public R getChapterSectionList(@PathVariable(required = true,name = "cId") Integer id){
		R responseBody = new R();
	
		return responseBody.success().data(courseService.checkAllSectionOfChapter(id));
	}
	
	  /**
		 * @Title getSectionDetail
	     * @author 皮龙霖
	     * @description 查看id小节详情
	     * @createdate 2022年7月7日 下午3:27:29
	     * @param id
	     * @return R
	     **/
	@ApiOperation("查看小节详情")
	@GetMapping("/section/{sId}")
	public R getSectionDetail(@PathVariable(required = true,name = "sId") Integer id){
		R responseBody = new R();
		
		Section section = courseService.searchSectionById(id);
		if(section == null)
			return responseBody.error(ResponseEnum.NOT_FOUND);
	
		return responseBody.success().data(section);
	}
	
	
	@ApiOperation("查询课程的类别")
	@GetMapping("/course/{cId}/domain")
	public R getDomainOfCourse(@PathVariable(required = true,name = "cId") int id){
		R responseBody = new R();	
		return responseBody.success().data(domainService.checkDomainOfCourse(id));
	}

}
