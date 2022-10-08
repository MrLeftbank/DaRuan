/**
 * @Title AdminLoginController.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月8日上午10:59:05
 */
package com.sx.oesb.controllerM;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sx.oesb.auth.JwtTokenUtil;
import com.sx.oesb.entity.Administrator;
import com.sx.oesb.service.AdministratorService;
import com.sx.oesb.util.R;
import com.sx.oesb.util.ResponseEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @ClassName AdminLoginController 
* @Description 管理登录
* @author 皮龙霖
* @date 2022年7月8日 上午10:59:05 
*  
*/
@Api(tags="管理员登录、注册")
@RestController
@RequestMapping("/admin")
public class AdminLoginController {
	
	private final AdministratorService administratorService;
	private final JwtTokenUtil jwtTokenUtil;

	@Autowired
	public AdminLoginController(AdministratorService administratorService, JwtTokenUtil jwtTokenUtil) {
		super();
		this.administratorService = administratorService;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	  /**
		 * @Title adminLogin
	     * @author 皮龙霖
	     * @description 管理员登录，token设置了isAdmin=true
	     * @createdate 2022年7月8日 上午11:59:55
	     * @param name
	     * @param password
	     * @return R
	     **/
	@ApiOperation("管理员登录")
    @PostMapping("/login")
    public R adminLogin(@RequestParam("username")String name, @RequestParam("password") String password) {
    	
    	R responseBody = new R();
        try {
            Administrator administrator = administratorService.adminLogin(name, password);
            return  responseBody.success().data(jwtTokenUtil.createToken(administrator.getName(),true));
        } catch (Exception e) {
            return responseBody.error(ResponseEnum.USER_PASSWORD_FAIL);
        }
    }

      /**
    	 * @Title adminRegister
         * @author 皮龙霖
         * @description 管理员注册
         * @createdate 2022年7月8日 下午12:00:39
         * @param name
         * @param password
         * @return R
         **/
    @ApiOperation("用管理员注册")
    @PostMapping("/register")
    public R adminRegister(@RequestParam("name") String name, @RequestParam("password") String password) {
        R responseBody = new R();
        try {
           if( administratorService.adminRegister(name, password))
        	   return responseBody.success();
           else 
        	   return responseBody.error(ResponseEnum.SERVICE_FAIL);
        } catch (Exception exception) {
            exception.printStackTrace();
            return responseBody.error(ResponseEnum.USERNAME_CONFLICT);
        }
    }
}
