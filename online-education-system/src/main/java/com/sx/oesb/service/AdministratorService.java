/**
 * @Title AdministratorService.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月8日上午11:02:35
 */
package com.sx.oesb.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sx.oesb.entity.Administrator;

/** 
* @ClassName AdministratorService 
* @Description 管理员表的服务
* @author 皮龙霖
* @date 2022年7月8日 上午11:02:35 
*  
*/
public interface AdministratorService {
	
	  /**
		 * @Title adminLogin
	     * @author 皮龙霖
	     * @description 管理员登录
	     * @createdate 2022年7月8日 上午11:03:06
	     * @param name
	     * @param password
	     * @return Administrator
	     **/
	public Administrator adminLogin(String name, String password);

	
	  /**
		 * @Title adminRegister
	     * @author 皮龙霖
	     * @description 管理员注册
	     * @createdate 2022年7月8日 下午12:47:39
	     * @param name
	     * @param password
	     * @return boolean
	     **/
	public boolean adminRegister(String name, String password);

	  /**
		 * @Title searchAdminByAdminname
	     * @author 皮龙霖
	     * @description 按名查找管理员
	     * @createdate 2022年7月8日 上午11:03:45
	     * @param name
	     * @return
	     * @throws UsernameNotFoundException Administrator
	     **/
	Administrator searchAdminByAdminName(String name) throws UsernameNotFoundException;
}
