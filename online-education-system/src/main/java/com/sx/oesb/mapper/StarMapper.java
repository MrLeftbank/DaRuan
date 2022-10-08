package com.sx.oesb.mapper;

import com.sx.oesb.entity.Star;

import java.util.List;

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
public interface StarMapper extends BaseMapper<Star> {

	  /**
		 * @Title selectUserStarCourseId
	     * @author 皮龙霖
	     * @description 查询userId用户收藏的所有课程id
	     * @createdate 2022年7月2日 下午4:09:58
	     * @param userId
	     * @return List<Integer>
	     **/
	@Select("SELECT course_id FROM star"
			+ " WHERE user_id = #{userId}")
	public List<Integer> selectUserStarCourseId(int userId);
}
