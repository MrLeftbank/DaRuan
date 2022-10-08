package com.sx.oesb.mapper;

import com.sx.oesb.entity.User;

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
public interface UserMapper extends BaseMapper<User> {

	  /**
		 * @Title recentRegister
	     * @author 皮龙霖
	     * @description 近30天的每日注册人数
	     * @createdate 2022年7月15日 下午2:17:23
	     * @return List<Map<Date,Integer>>
	     **/
	@Select("SELECT DATE_FORMAT(user.registtime, '%Y-%m-%d') as date , COUNT(*) AS count "
			+ "FROM user "
			+ "GROUP BY date "
			+ "HAVING DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date "
			+ "ORDER BY date DESC")
	public List<Map<Date, Integer>> recentRegister();
}
