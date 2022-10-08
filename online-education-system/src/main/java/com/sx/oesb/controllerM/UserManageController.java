/**
 * @Title UserManageController.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月8日上午9:39:39
 */
package com.sx.oesb.controllerM;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sx.oesb.entity.User;
import com.sx.oesb.service.UserService;
import com.sx.oesb.util.R;
import com.sx.oesb.util.ResponseEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @ClassName UserManageController 
* @Description 用户管理页面的控制器
* @author 皮龙霖
* @date 2022年7月8日 上午9:39:39 
*  
*/
@Api(tags="用户管理")
@RestController
@RequestMapping("/admin/user")
public class UserManageController {
	private final UserService userService;

	@Autowired
	public UserManageController(UserService userService) {
		super();
		this.userService = userService;
	}
	

	@ApiOperation("用户列表")
	@GetMapping("")
	public R getUserList(@RequestParam(defaultValue = "0")int pageNum,@RequestParam(defaultValue = "8")int pageSize){
		R responseBody = new R();
		return responseBody.success().data(userService.checkAllUsers(pageSize, pageNum));    
	}
	
	
	@ApiOperation("查看id用户")
	@GetMapping("/{uId}")
	public R getUser(@PathVariable(required = true)int uId){
		R responseBody = new R();
		return responseBody.success().data(userService.searchUserByID(uId));    
	}
	
	@ApiOperation("删除id用户")
	@DeleteMapping("/{uId}")
	public R deleteUser(@PathVariable(required = true)int uId){
		R responseBody = new R();
		if(userService.deleteUser(uId))
			return responseBody.success();
		else {
			return responseBody.error(ResponseEnum.SERVICE_FAIL);
		}
	}
	
	
	@ApiOperation("更改id用户")
	@PutMapping("/{uId}")
	public R updateUser(@PathVariable(required = true)int uId,@RequestBody(required = true)User u){
		R responseBody = new R();
		u.setId(uId);
		if(userService.modifyUser(u))
			return responseBody.success();
		else {
			return responseBody.error(ResponseEnum.SERVICE_FAIL);
		}
	}
}
