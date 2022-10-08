package com.sx.oesb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sx.oesb.entity.Teacher;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 自动生成
 * @since 2022-07-01
 */
public interface TeacherMapper extends BaseMapper<Teacher> {
	
	
	  /**
		 * @Title findBestTeachers
	     * @author 皮龙霖
	     * @description 查询最受欢迎的10个老师，依据是找到课程订单收入最高的10位
	     * @createdate 2022年7月1日 下午6:58:11
	     * @return List<Teacher>
	     **/
//	@Select("SELECT * FROM teacher t "
//			+ "ORDER BY  ("
//					+ "SELECT SUM(c.price) FROM purchase p JOIN course c ON p.course_id = c.id  "
//					+ "where t.id  = c.teacher_id) "
//			+ "DESC LIMIT 0,10")
	@Select("SELECT * from teacher t JOIN "
			+ "(SELECT SUM(c.price) AS sum , c.teacher_id AS teacher_id "
				+ "FROM purchase p JOIN course c ON p.course_id = c.id "
				+ "GROUP BY c.teacher_id) AS teacher_sum "
			+ "ON t.id = teacher_sum.teacher_id "
			+ "ORDER BY sum DESC "
			+ "LIMIT 0,8")
	public List<Teacher> findBestTeachers();

}
