/**
 * @Title StasticService.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月15日下午2:23:29
 */
package com.sx.oesb.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.sx.oesb.vo.CourseDetail;
import com.sx.oesb.vo.UserVO;

/** 
* @ClassName StasticService 
* @Description 后台管理的统计相关服务。
* @author 皮龙霖
* @date 2022年7月15日 下午2:23:29 
*  
*/
public interface StasticService {
	
	  /**
		 * @Title checkRecentDayMoney
	     * @author 皮龙霖
	     * @description 获取近期每天的营业额
	     * @createdate 2022年7月15日 下午2:24:29
	     * @return List<Map<Date,Double>>
	     **/
	public List<Map<Date, Double>> checkRecentDayMoney();
	
	  /**
		 * @Title checkRecentMonthMoney
	     * @author 皮龙霖
	     * @description 获取近期每月的营业额
	     * @createdate 2022年7月15日 下午2:25:09
	     * @return List<Map<Date,Double>>
	     **/
	public List<Map<Date, Double>> checkRecentMonthMoney();
	
	  /**
		 * @Title checkRecnetHotCourse
	     * @author 皮龙霖
	     * @description 获取近期的热门课程
	     * @createdate 2022年7月15日 下午2:28:21
	     * @return List<CourseDetail>
	     **/
	public List<CourseDetail> checkRecnetHotCourse(); 
	
	  /**
		 * @Title checkRecentRegister
	     * @author 皮龙霖
	     * @description 获取近期的每日注册人数
	     * @createdate 2022年7月15日 下午2:29:30
	     * @return List<Map<Date,Integer>>
	     **/
	public List<Map<Date, Integer>> checkRecentRegister();
	
	  /**
		 * @Title checkRecentLogin
	     * @author 皮龙霖
	     * @description 获取近期的每日登录人数
	     * @createdate 2022年7月15日 下午2:30:03
	     * @return List<Map<Date,Integer>>
	     **/
	public List<Map<Date, Integer>> checkRecentLogin();

	  /**
		 * @Title checkFrequentLogin
	     * @author 皮龙霖
	     * @description 近期登录次数最多的用户及其登录次数
	     * @createdate 2022年7月15日 下午3:46:54
	     * @return List<UserVO>
	     **/
	public List<UserVO> checkFrequentLogin();

}
