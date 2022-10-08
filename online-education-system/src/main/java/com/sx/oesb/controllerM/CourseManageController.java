/**
 * @Title CourseManageController.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月8日上午9:58:43
 */
package com.sx.oesb.controllerM;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sx.oesb.entity.Domain;
import com.sx.oesb.service.CourseService;
import com.sx.oesb.service.DomainService;
import com.sx.oesb.util.R;
import com.sx.oesb.util.ResponseEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @ClassName CourseManageController 
* @Description 课程管理控制器
* @author 皮龙霖
* @date 2022年7月8日 上午9:58:43 
*  
*/
@Api(tags="课程管理")
@RestController
@RequestMapping("/admin")
public class CourseManageController {
	
	
	private final DomainService domainService;
	private final CourseService courseService;
	
	@Autowired
	public CourseManageController(DomainService domainService, CourseService courseService) {
		super();
		this.domainService = domainService;
		this.courseService = courseService;
	}

	@ApiOperation("类别列表")
	@GetMapping("/domain")
	public R getDomainList(){
		R responseBody = new R();
		return responseBody.success().data(domainService.checkAllDomains());    
	}
	
	@ApiOperation("添加类别")
	@PostMapping("/domain")
	public R postDomain(@RequestBody Domain domain){
		R responseBody = new R();
		if(domainService.postDomain(domain))
			return responseBody.success();
		else {
			return responseBody.error(ResponseEnum.SERVICE_FAIL);
		} 
	}
	
	@ApiOperation("删除类别")
	@DeleteMapping("/domain/{dId}")
	public R deleteDomain(@PathVariable int dId){
		R responseBody = new R();
		if(domainService.deleteDomainByID(dId))
			return responseBody.success();
		else {
			return responseBody.error(ResponseEnum.SERVICE_FAIL);
		}
	}
	
	
	@ApiOperation("删除课程")
	@DeleteMapping("/course/{cId}")
	public R deleteCourse(@PathVariable int cId){
		R responseBody = new R();
		if(courseService.deleteCourseAndChapter(cId))
			return responseBody.success();
		else {
			return responseBody.error(ResponseEnum.SERVICE_FAIL);
		}
	}
	
	@ApiOperation("购买列表")
	@GetMapping("/purchase")
	public R getPurcahseList(){
		R responseBody = new R();
		return responseBody.success().data(courseService.checkAllPurchase());    
	}
	
}
