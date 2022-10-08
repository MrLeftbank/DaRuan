package com.sx.oesb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sx.oesb.service.IndexService;
import com.sx.oesb.util.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @ClassName HomeController 
* @Description 主页会用到的控制器
* @author 皮龙霖
* @date 2022年7月1日 上午11:04:23 
*  
*/
@Api(tags="门户")
@RestController
@RequestMapping("/index")
public class HomeController {
	
	private final IndexService indexService;

	@Autowired
	public HomeController(IndexService indexService) {
		super();
		this.indexService = indexService;
	}

	/**
		 * @Title getBestCourseList
	     * @author 皮龙霖
	     * @description  接受"/course"的GET请求,reponseBody.data返回前端JSON格式的受欢迎课程列表，大小<=10
	     * @createdate 2022年7月2日 上午10:45:46
	     * @return R
	     **/
    @ApiOperation("受欢迎的课程")
	@GetMapping("/course")
	public R getBestCourseList(){
		R responseBody = new R();
		return responseBody.success().data(indexService.showCoursePopularity());    
	}
	
	  /**
		 * @Title getBestTeacherList
	     * @author 皮龙霖
	     * @description 接受"/teacher"的GET请求,reponseBody.data返回前端JSON格式的受欢迎老师列表，大小<=10
	     * @createdate 2022年7月2日 上午10:45:32
	     * @return R
	     **/
    @ApiOperation("受欢迎的老师")
	@GetMapping("/teacher")
	public R getBestTeacherList(){
		R responseBody = new R();
		return responseBody.success().data(indexService.showTeacherPopularity());     
	}

	@ApiOperation("最受欢迎的文章")
	@GetMapping("/article")
	public R getPopularArticleList() {
		return new R().success().data(indexService.showArticlePopularity());
	}
}
