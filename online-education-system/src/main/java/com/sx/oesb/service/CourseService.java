/**
 * @Title CourseService.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月4日下午4:41:29
 */
package com.sx.oesb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sx.oesb.entity.*;

import java.util.List;

/** 
* @ClassName CourseService 
* @Description 课程表相关服务
* @author 皮龙霖
* @date 2022年7月4日 下午4:41:29 
*  
*/
public interface CourseService {
	
	  /**
		 * @Title checkAllCourse
	     * @author 皮龙霖
	     * @description 分页返回所有的课程（管理用）
	     * @createdate 2022年7月4日 下午4:59:12
	     * @param pageSize
	     * @param pageNum
	     * @return IPage<Course>
	     **/
	public IPage<Course> checkAllCourse(int pageSize, int pageNum);
	
	  /**
		 * @Title searchCourse
	     * @author 皮龙霖
	     * @description 按名搜索，分页返回课程；ot为2则价格排序、为1则时间排序
	     * @createdate 2022年7月7日 上午9:13:53
	     * @param name
	     * @param pageSize
	     * @param pageNum
	     * @param orderTag
	     * @return IPage<Course>
	     **/
	public IPage<Course> searchCourse(String name, int pageSize, int pageNum, int orderTag);
	
	
	  /**
		 * @Title searchCourseById
	     * @author 皮龙霖
	     * @description 返回id课程的详情
	     * @createdate 2022年7月5日 上午9:34:14
	     * @param courseId
	     * @return Course
	     **/
	public Course searchCourseById(int courseId);
	

	  /**
		 * @Title postCourse
	     * @author 皮龙霖
	     * @description 上传课程，默认Controller已经验证了userId与teacherId
	     * @createdate 2022年7月5日 上午8:53:41
	     * @param c
	     * @return boolean
	     **/
	public boolean postCourse (Course c);
	

	  /**
		 * @Title buyCourse
	     * @author 皮龙霖
	     * @description 购买课程，默认controller已经验证了course和user存在，余额足够
	     * @createdate 2022年7月5日 下午4:11:55
	     * @param course
	     * @param user
	     * @return boolean
	     **/
	public boolean buyCourse(Course course,User user);
	

	  /**
		 * @Title starCourse
	     * @author 皮龙霖
	     * @description 收藏课程，默认userId已验证
	     * @createdate 2022年7月5日 上午9:07:21
	     * @param s
	     * @return boolean
	     **/
	public boolean starCourse(Star s);

	  /**
		 * @Title checkAllChaptersOfCourse
	     * @author 皮龙霖
	     * @description 查看id课程的所有章节列表按number排序
	     * @createdate 2022年7月4日 下午5:03:04
	     * @param courseId
	     * @return List<Chapter>
	     **/
	public List<Chapter> checkAllChaptersOfCourse(int courseId);

	  /**
		 * @Title checkAllSectionOfChapter
	     * @author 皮龙霖
	     * @description 查看id章节的所有小节列表按number排序
	     * @createdate 2022年7月7日 下午2:37:22
	     * @param chapterId
	     * @return List<Section>
	     **/
	public List<Section> checkAllSectionOfChapter(int chapterId);

	  /**
		 * @Title postChapterOfCourse
	     * @author 皮龙霖
	     * @description 添加id课程的章节
	     * @createdate 2022年7月5日 上午8:58:28
	     * @param c
	     * @return boolean
	     **/
	public int postChapterOfCourse(Chapter c);
	
	  /**
		 * @Title postSectionOfChapter
	     * @author 皮龙霖
	     * @description 添加id章节的小节
	     * @createdate 2022年7月7日 下午2:38:30
	     * @param s
	     * @return boolean
	     **/
	public int postSectionOfChapter(Section s);

	  /**
		 * @Title searchChapterOfCourse
	     * @author 皮龙霖
	     * @description 返回id章节的详情
	     * @createdate 2022年7月4日 下午5:04:03
	     * @param chapterId
	     * @return Chapter
	     **/
	public Chapter searchChapterById(int chapterId);
	
	
	  /**
		 * @Title searchSectionById
	     * @author 皮龙霖
	     * @description 返回id小节的详情
	     * @createdate 2022年7月7日 下午3:00:50
	     * @param sectionId
	     * @return Section
	     **/
	public Section searchSectionById(int sectionId);

	  /**
		 * @Title deleteChapterOfCourse
	     * @author 皮龙霖
	     * @description 删除章节
	     * @createdate 2022年7月4日 下午5:04:41
	     * @param course_id
	     * @param chapter_id
	     * @return boolean
	     **/
	public boolean deleteChapterById(int chapterId);
	
	  /**
		 * @Title deleteSectionById
	     * @author 皮龙霖
	     * @description 删除id小节
	     * @createdate 2022年7月7日 下午2:38:54
	     * @param sectionId
	     * @return boolean
	     **/
	public boolean deleteSectionById(int sectionId);
	  /**
		 * @Title deleteCourse
	     * @author 皮龙霖
	     * @description 删除id课程并删除其章节
	     * @createdate 2022年7月4日 下午5:05:24
	     * @param courseId
	     * @return boolean
	     **/
	public boolean deleteCourseAndChapter(int courseId);


	
	  /**
		 * @Title modifyChapter
	     * @author 皮龙霖
	     * @description 修改章节，默认已经验证了chapterId
	     * @createdate 2022年7月5日 上午9:30:45
	     * @param chapter
	     * @return boolean
	     **/
	public boolean modifyChapter(Chapter chapter);
	
	
	  /**
		 * @Title modifySection
	     * @author 皮龙霖
	     * @description 修改小节，默认已经验证了sectionId
	     * @createdate 2022年7月7日 下午3:03:45
	     * @param section
	     * @return boolean
	     **/
	public boolean modifySection(Section section);
	
	  /**
		 * @Title getCourseOfChapter
	     * @author 皮龙霖
	     * @description 找到章节的课程，验证用
	     * @createdate 2022年7月4日 下午5:10:46
	     * @param chapterId
	     * @return Course
	     **/
	public Course getCourseOfChapter(int chapterId); 
	

	  /**
		 * @Title getChapterOfSection
	     * @author 皮龙霖
	     * @description  找到小节的章节，验证用
	     * @createdate 2022年7月7日 下午3:43:38
	     * @param sectionId
	     * @return Chapter
	     **/
	public Chapter getChapterOfSection(int sectionId); 
	
	  /**
		 * @Title getUserIdOfCourse
	     * @author 皮龙霖
	     * @description 找到课程作者的userId，验证用
	     * @createdate 2022年7月4日 下午5:12:27
	     * @param courseId
	     * @return int
	     **/
	public int getUserIdOfCourse(int courseId);

	  /**
		 * @Title getTeacherOfCourse
	     * @author 皮龙霖
	     * @description 找到课程的老师
	     * @createdate 2022年7月7日 下午2:47:01
	     * @param courseId
	     * @return Teacher
	     **/
	public Teacher getTeacherOfCourse(int courseId);
	
	  /**
		 * @Title getStarOfCourse
	     * @author 皮龙霖
	     * @description 获取课程的收藏列表
	     * @createdate 2022年7月7日 下午3:04:47
	     * @param courseId
	     * @return List<Star>
	     **/
	public List<Star> getStarOfCourse(int courseId);
	
	  /**
		 * @Title checkAllPurchase
	     * @author 皮龙霖
	     * @description 系统所有购买记录列表
	     * @createdate 2022年7月8日 上午10:21:44
	     * @return List<Purchase>
	     **/
	public List<Purchase> checkAllPurchase();

}
