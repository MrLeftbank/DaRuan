package com.sx.oesb.mapper;

import com.sx.oesb.entity.UserLog;
import com.sx.oesb.vo.UserVO;

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
public interface UserLogMapper extends BaseMapper<UserLog> {

	  /**
		 * @Title recentLogin
	     * @author 皮龙霖
	     * @description 近30天的每日登录人数
	     * @createdate 2022年7月15日 下午2:09:58
	     * @return List<Map<Date,Double>>
	     **/
	@Select("SELECT DATE_FORMAT(user_log.time, '%Y-%m-%d') as date , COUNT(*) AS count "
			+ "FROM user_log "
			+ "GROUP BY date "
			+ "HAVING DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date "
			+ "ORDER BY date DESC")
	public List<Map<Date, Integer>> recentLogin();
	
	
	  /**
		 * @Title frequentLogin
	     * @author 皮龙霖
	     * @description 近30天的登录次数最多的10人,失败
	     * @createdate 2022年7月15日 下午2:42:56
	     * @return List<Map<Integer,Integer>>
	     **/
	@Select("SELECT user.*, times "
			+ "FROM user JOIN (SELECT COUNT(*) AS times, user_id AS uid "
			+ "FROM user_log "
			+ "WHERE DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= time "
			+ "GROUP BY user_id  "
			+ "ORDER BY times DESC "
			+ "LIMIT 0,10) AS ul "
			+ "ON user.id  = ul.uid")
	public List<UserVO> frequentLogin();
}
