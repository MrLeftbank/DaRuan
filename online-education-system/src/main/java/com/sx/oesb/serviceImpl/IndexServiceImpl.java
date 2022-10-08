/**
 * @Title IndexServiceImpl.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月2日上午8:48:21
 */
package com.sx.oesb.serviceImpl;

import java.util.List;

import com.sx.oesb.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sx.oesb.entity.Teacher;
import com.sx.oesb.mapper.CourseMapper;
import com.sx.oesb.mapper.TeacherMapper;
import com.sx.oesb.service.IndexService;
import com.sx.oesb.vo.ArticleDetail;
import com.sx.oesb.vo.CourseDetail;

/** 
* @ClassName IndexServiceImpl 
* @Description 实现主页相关服务
* @author 皮龙霖
* @date 2022年7月2日 上午8:48:21 
*  
*/
@Service
public class IndexServiceImpl implements IndexService {
	
	private final CourseMapper courseMapper;
	private final TeacherMapper teacherMapper;

	private final ArticleMapper articleMapper;

	@Autowired
	public IndexServiceImpl(CourseMapper courseMapper, TeacherMapper teacherMapper, ArticleMapper articleMapper) {
		super();
		this.courseMapper = courseMapper;
		this.teacherMapper = teacherMapper;
		this.articleMapper = articleMapper;
	}

	/**
	 * @Title showTeacherPopularity
	   * @author 皮龙霖
	   * @description	调用TeacherMapper里的best相关方法
	   * @createdate 2022年7月2日 上午8:48:21
	   * @return 
	 * @see com.sx.oesb.service.IndexService#showTeacherPopularity()
	   **/

	@Override
	@Cacheable(cacheNames = "popularTeacher")
	public List<Teacher> showTeacherPopularity() {
		return teacherMapper.findBestTeachers();
	}

	/**
	 * @Title showCoursePopularity
	   * @author 皮龙霖
	   * @description	调用CourseMapper里的best相关方法
	   * @createdate 2022年7月2日 上午8:48:21
	   * @return 
	 * @see com.sx.oesb.service.IndexService#showCoursePopularity()
	   **/

	@Override
	@Cacheable(cacheNames = "popularCourse")
	public List<CourseDetail> showCoursePopularity() {
		return courseMapper.findBestCourseDetail();
	}

	@Override
	@Cacheable(cacheNames = "popularArticle")
	public List<ArticleDetail> showArticlePopularity() {
		return articleMapper.findBestArticleDetail();
	}

}
