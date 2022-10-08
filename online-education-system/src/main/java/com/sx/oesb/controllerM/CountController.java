/**
 * @Title CountController.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月15日下午3:48:00
 */
package com.sx.oesb.controllerM;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sx.oesb.service.StasticService;
import com.sx.oesb.util.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @ClassName CountController 
* @Description 统计信息相关控制器
* @author 皮龙霖
* @date 2022年7月15日 下午3:48:00 
*  
*/

@RestController
@Api(tags = "统计管理")
@RequestMapping("/admin/count")
public class CountController {
	
	private final StasticService stasticService;

	
	@Autowired
	public CountController(StasticService stasticService) {
		super();
		this.stasticService = stasticService;
	}

	@ApiOperation("最近天营业额")
	@GetMapping("/day-money")
	public R getDayMoney(){
		R responseBody = new R();
		return responseBody.success().data(stasticService.checkRecentDayMoney());    
	}
	
	@ApiOperation("本年月营业额")
	@GetMapping("/month-money")
	public R getMonthMoney(){
		R responseBody = new R();
		return responseBody.success().data(stasticService.checkRecentMonthMoney());    
	}
	
	@ApiOperation("最近课程流行")
	@GetMapping("/hot-course")
	public R getHotCourse(){
		R responseBody = new R();
		return responseBody.success().data(stasticService.checkRecnetHotCourse());    
	}
	
	@ApiOperation("最近活跃用户")
	@GetMapping("/hot-user")
	public R getHotUser(){
		R responseBody = new R();
		return responseBody.success().data(stasticService.checkFrequentLogin());    
	}
	
	@ApiOperation("最近登录数")
	@GetMapping("/recent-login")
	public R getLogin(){
		R responseBody = new R();
		return responseBody.success().data(stasticService.checkRecentLogin());    
	}
	
	@ApiOperation("最近注册数")
	@GetMapping("/recent-register")
	public R getRegister(){
		R responseBody = new R();
		return responseBody.success().data(stasticService.checkRecentRegister());    
	}
}
