/**
 * @Title StasticServiceImpl.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月15日下午2:31:00
 */
package com.sx.oesb.serviceImpl;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sx.oesb.mapper.CourseMapper;
import com.sx.oesb.mapper.PurchaseMapper;
import com.sx.oesb.mapper.UserLogMapper;
import com.sx.oesb.mapper.UserMapper;
import com.sx.oesb.service.StasticService;
import com.sx.oesb.vo.CourseDetail;
import com.sx.oesb.vo.UserVO;

/** 
* @ClassName StasticServiceImpl 
* @Description TODO
* @author 皮龙霖
* @date 2022年7月15日 下午2:31:00 
*  
*/
@Service
public class StasticServiceImpl implements StasticService {
	
	private final UserMapper userMapper;
	private final CourseMapper courseMapper;
	private final UserLogMapper userLogMapper;
	private final PurchaseMapper purchaseMapper;
	
	
	@Autowired
	public StasticServiceImpl(UserMapper userMapper, CourseMapper courseMapper, UserLogMapper userLogMapper,
			PurchaseMapper purchaseMapper) {
		super();
		this.userMapper = userMapper;
		this.courseMapper = courseMapper;
		this.userLogMapper = userLogMapper;
		this.purchaseMapper = purchaseMapper;
	}

	/**
	 * @Title checkRecentDayMoney
	   * @author 皮龙霖
	   * @description 30天记录，purchaseMapper.recentMoney()
	   * @createdate 2022年7月15日 下午2:31:00
	   * @return 
	 * @see com.sx.oesb.service.StasticService#checkRecentDayMoney()
	   **/

	@Override
	public List<Map<Date, Double>> checkRecentDayMoney() {
		return purchaseMapper.recentMoney();
	}

	/**
	 * @Title checkRecentMonthMoney
	   * @author 皮龙霖
	   * @description 本年记录，purchaseMapper.monthMoney()
	   * @createdate 2022年7月15日 下午2:31:00
	   * @return 
	 * @see com.sx.oesb.service.StasticService#checkRecentMonthMoney()
	   **/

	@Override
	public List<Map<Date, Double>> checkRecentMonthMoney() {
		return purchaseMapper.monthMoney();
	}

	/**
	 * @Title checkRecnetHotCourse
	   * @author 皮龙霖
	   * @description 30天记录，courseMapper.findRecentCourseDetail()
	   * @createdate 2022年7月15日 下午2:31:00
	   * @return 
	 * @see com.sx.oesb.service.StasticService#checkRecnetHotCourse()
	   **/

	@Override
	public List<CourseDetail> checkRecnetHotCourse() {
		return courseMapper.findRecentCourseDetail();
	}

	/**
	 * @Title checkRecentRegister
	   * @author 皮龙霖
	   * @description 30天记录，userMapper.recentRegister()
	   * @createdate 2022年7月15日 下午2:31:00
	   * @return 
	 * @see com.sx.oesb.service.StasticService#checkRecentRegister()
	   **/

	@Override
	public List<Map<Date, Integer>> checkRecentRegister() {
		return userMapper.recentRegister();
	}

	/**
	 * @Title checkRecentLogin
	   * @author 皮龙霖
	   * @description 30天记录，userLogMapper.recentLogin()
	   * @createdate 2022年7月15日 下午2:31:00
	   * @return 
	 * @see com.sx.oesb.service.StasticService#checkRecentLogin()
	   **/

	@Override
	public List<Map<Date, Integer>> checkRecentLogin() {
		return userLogMapper.recentLogin();
	}

	  /**
		 * @Title checkFrequentLogin
	     * @author 皮龙霖
	     * @description  30天记录，10人，userLogMapper.frequentLogin()
	     * @createdate 2022年7月15日 下午3:47:06
	     * @return 
		 * @see com.sx.oesb.service.StasticService#checkFrequentLogin()
	     **/
	
	@Override
	public List<UserVO> checkFrequentLogin() {
		return userLogMapper.frequentLogin();
	}
	
	

}
