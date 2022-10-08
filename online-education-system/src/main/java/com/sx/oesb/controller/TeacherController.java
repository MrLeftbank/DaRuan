/**
 * @Title TeacherController.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月8日上午9:14:34
 */
package com.sx.oesb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sx.oesb.service.TeacherService;
import com.sx.oesb.util.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @ClassName TeacherController 
* @Description 名师页面
* @author 皮龙霖
* @date 2022年7月8日 上午9:14:34 
*  
*/
@Api(tags="名师")
@RestController
@RequestMapping("/teacher")
public class TeacherController {
	
	private final TeacherService teacherService;
	
	
	@Autowired
	public TeacherController(TeacherService teacherService) {
		super();
		this.teacherService = teacherService;
	}


	  /**
		 * @Title getTeacherList
	     * @author 皮龙霖
	     * @description 全部教师页面
	     * @createdate 2022年7月8日 上午9:24:23
	     * @param pageNum
	     * @param pageSize
	     * @return R
	     **/
	@ApiOperation("名师列表")
	@GetMapping("")
	public R getTeacherList(@RequestParam(defaultValue = "0")int pageNum,@RequestParam(defaultValue = "8")int pageSize){
		R responseBody = new R();
		return responseBody.success().data(teacherService.checkAllTeacher(pageSize,pageNum));    
	}
	
	  /**
		 * @Title searchTeacherList
	     * @author 皮龙霖
	     * @description 按名搜索教师页面
	     * @createdate 2022年7月14日 下午5:04:00
	     * @param name
	     * @param pageNum
	     * @param pageSize
	     * @return R
	     **/
	@ApiOperation("搜索名师列表")
	@GetMapping("/search")
	public R searchTeacherList(@RequestParam(name = "name")String name,@RequestParam(defaultValue = "0")int pageNum,@RequestParam(defaultValue = "8")int pageSize){
		R responseBody = new R();
		return responseBody.success().data(teacherService.SearchTeacherByName(name, pageSize, pageNum));    
	}
	

	  /**
		 * @Title getTeacher
	     * @author 皮龙霖
	     * @description 获得id教师对象
	     * @createdate 2022年7月8日 上午9:24:48
	     * @param id
	     * @return R
	     **/
	@ApiOperation("名师详情")
	@GetMapping("/{id}")
	public R getTeacher(@PathVariable(required = true)int id){
		R responseBody = new R();
		return responseBody.success().data(teacherService.checkTeacherById(id));    
	}
	
	  /**
		 * @Title getTeacherCourse
	     * @author 皮龙霖
	     * @description  获得id教师全部课程页面
	     * @createdate 2022年7月8日 上午9:25:06
	     * @param id
	     * @param pageNum
	     * @param pageSize
	     * @return R
	     **/
	@ApiOperation("名师课程")
	@GetMapping("/{id}/course")
	public R getTeacherCourse(@PathVariable(required = true)int id,@RequestParam(defaultValue = "0")int pageNum,@RequestParam(defaultValue = "8")int pageSize){
		R responseBody = new R();
		return responseBody.success().data(teacherService.checkCourseOfTeacher(id, pageSize, pageNum));    
	}
	
}
