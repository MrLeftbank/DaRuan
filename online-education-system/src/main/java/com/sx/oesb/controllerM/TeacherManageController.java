/**
 * @Title TeacherManageController.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月8日上午9:50:39
 */
package com.sx.oesb.controllerM;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sx.oesb.entity.Teacher;
import com.sx.oesb.service.TeacherService;
import com.sx.oesb.util.R;
import com.sx.oesb.util.ResponseEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @ClassName TeacherManageController 
* @Description 教师管理页控制器
* @author 皮龙霖
* @date 2022年7月8日 上午9:50:39 
*  
*/
@Api(tags="教师管理")
@RestController
@RequestMapping("/admin/teacher")
public class TeacherManageController {
	
	private final TeacherService teacherService;

	public TeacherManageController(TeacherService teacherService) {
		super();
		this.teacherService = teacherService;
	}
	
	@ApiOperation("更改id教师")
	@PutMapping("/{tId}")
	public R updateUser(@PathVariable(required = true)int tId,@RequestBody(required = true)Teacher t){
		R responseBody = new R();
		t.setId(tId);
		if(teacherService.changeTeacherInfo(t))
			return responseBody.success();
		else {
			return responseBody.error(ResponseEnum.SERVICE_FAIL);
		}
	}
	
	@ApiOperation("删除id教师")
	@DeleteMapping("/{tId}")
	public R deleteUser(@PathVariable(required = true)int tId){
		R responseBody = new R();
		if(teacherService.deleteTeacher(tId))
			return responseBody.success();
		else {
			return responseBody.error(ResponseEnum.SERVICE_FAIL);
		}
	}

}
