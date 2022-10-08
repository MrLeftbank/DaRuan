/**
 * @Title IndexService.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月1日上午11:20:09
 */
package com.sx.oesb.service;

import java.util.List;
import com.sx.oesb.entity.Teacher;
import com.sx.oesb.vo.ArticleDetail;
import com.sx.oesb.vo.CourseDetail;

/** 
* @ClassName IndexService 
* @Description 为HomeController提供服务
* @author 皮龙霖 v
* @date 2022年7月1日 上午11:20:09 
*  
*/
public interface IndexService {
	
	  /**
		 * @Title showTeacherPopularity
	     * @author 皮龙霖
	     * @description 显示受欢迎的老师
	     * @createdate 2022年7月1日 上午11:22:11
	     * @return List<Teacher>
	     **/
	public List<Teacher> showTeacherPopularity();
	
	
	  /**
		 * @Title showCoursePopularity
	     * @author 皮龙霖
	     * @description 显示受欢迎的课程信息
	     * @createdate 2022年7月1日 上午11:22:43
	     * @return List<Course>
	     **/
	public List<CourseDetail> showCoursePopularity();

	public List<ArticleDetail> showArticlePopularity();

}
