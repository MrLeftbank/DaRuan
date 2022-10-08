package com.sx.oesb.mapper;

import com.sx.oesb.entity.Purchase;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 自动生成
 * @since 2022-07-01
 */
public interface PurchaseMapper extends BaseMapper<Purchase> {

	  /**
		 * @Title selectUserPurchaseCourseId
	     * @author 皮龙霖
	     * @description 查询userId用户购买的所有课程id
	     * @createdate 2022年7月2日 下午3:55:06
	     * @param userId
	     * @return List<Integer>
	     **/
	@Select("SELECT course_id FROM purchase"
			+ " WHERE user_id = #{userId}")
	public List<Integer> selectUserPurchaseCourseId(int userId);
	
	
	  /**
		 * @Title moneyOfmonth
	     * @author 皮龙霖
	     * @description 最近30天的日收入
	     * @createdate 2022年7月15日 下午1:56:26
	     * @return List<Map<Date,Double>>
	     **/
	@Select("SELECT DATE_FORMAT(purchase.time, '%Y-%m-%d') as date , SUM(price) AS sum  "
			+ "FROM purchase  JOIN course ON  purchase.course_id = course.id "
			+ "GROUP BY date \r\n"
			+ "HAVING DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date "
			+ "ORDER BY date DESC")
	public List<Map<Date, Double>> recentMoney();
	
	  /**
		 * @Title monthMoney
	     * @author 皮龙霖
	     * @description 今年的各个月的课程营业额
	     * @createdate 2022年7月15日 下午2:04:58
	     * @return List<Map<Date,Double>>
	     **/
	@Select("SELECT DATE_FORMAT(purchase.time, '%Y-%m') as date , SUM(price) AS sum "
			+ "FROM purchase  JOIN course ON  purchase.course_id = course.id "
			+ "WHERE YEAR (purchase.time) = YEAR (NOW()) "
			+ "GROUP BY date "
			+ "ORDER BY date DESC")
	public List<Map<Date, Double>> monthMoney();
}
