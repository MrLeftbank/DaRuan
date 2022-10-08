package com.sx.oesb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sx.oesb.entity.Course;
import com.sx.oesb.entity.Teacher;
import com.sx.oesb.vo.CourseDetail;
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
public interface CourseMapper extends BaseMapper<Course> {

	  /**
		 * @Title findBestCourses
	     * @author 皮龙霖
	     * @description 查询最受欢迎的10个课程，依据是收藏数
	     * @createdate 2022年7月1日 下午4:46:19
	     * @return List<Course>
	     **/
	@Select("SELECT * FROM course c "
			+ "ORDER BY ( SELECT COUNT(*) FROM star s WHERE s.course_id = c.id) "
			+ "DESC "
			+ "LIMIT 0,10")
	public List<Course> findBestCourse(); 
	
	  /**
		 * @Title findBestCourseDetail
	     * @author 皮龙霖
	     * @description 联表查询最受欢迎的10个课程，依据是收藏数。返回课程详情。
	     * @createdate 2022年7月2日 下午4:58:21
	     * @return List<CourseDetail>
	     **/
	@Select("SELECT * FROM course c Join ( SELECT COUNT(*)  AS count , course_id FROM star s group by course_id) AS course_star "
			+ "on c.id = course_star.course_id "
			+ "ORDER BY count DESC LIMIT 0,8 ")
	public List<CourseDetail> findBestCourseDetail(); 
	
	

	  /**
		 * @Title findRecentCourseDetail
	     * @author 皮龙霖
	     * @description 联表查询近30天最受欢迎的10个课程，依据是收藏数。返回课程详情。
	     * @createdate 2022年7月15日 下午2:27:36
	     * @return List<CourseDetail>
	     **/
	@Select("SELECT COUNT(*) AS count,course_id AS id,title,price,title_pic,intro,teacher_id,time FROM "
			+ "(SELECT course_id,user_id FROM star WHERE DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= star.time ) AS s "
			+ "JOIN course c "
			+ "ON c.id = s.course_id "
			+ "GROUP BY course_id "
			+ "ORDER BY count DESC "
			+ "LIMIT 0 , 10")
	public List<CourseDetail> findRecentCourseDetail(); 
	
	
	  /**
		 * @Title findUserIdofCourse
	     * @author 皮龙霖
	     * @description 找到课程创建老师对应的用户id
	     * @createdate 2022年7月5日 下午2:33:42
	     * @param courseId
	     * @return int
	     **/
	@Select("SELECT * FROM course JOIN teacher ON teacher.id = course.teacher_id "
			+ "WHERE course.id = #{courseId} ")
	public Teacher findTeacherofCourse(int courseId); 
}
