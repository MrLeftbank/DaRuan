/**
 * @Title TeacherService.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月4日下午2:57:17
 */
package com.sx.oesb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sx.oesb.entity.Teacher;
import com.sx.oesb.entity.Course;

/** 
* @ClassName TeacherService 
* @Description 教师表相关服务
* @author 皮龙霖
* @date 2022年7月4日 下午2:57:17 
*  
*/
public interface TeacherService {
	
	  /**
		 * @Title checkAllTeacher
	     * @author 皮龙霖
	     * @description 返回所有教师列表，分页
	     * @createdate 2022年7月4日 下午3:00:35
	     * @return IPage<Teacher>
	     **/
	public IPage<Teacher> checkAllTeacher(int pageSize, int pageNum);
	
	  /**
		 * @Title SearchTeacherByName
	     * @author 皮龙霖
	     * @description 按名字查询，返回教师列表，分页
	     * @createdate 2022年7月4日 下午3:09:58
	     * @param name
	     * @return IPage<Teacher>
	     **/
	public IPage<Teacher> SearchTeacherByName(String name,int pageSize, int pageNum);
	
	
	  /**
		 * @Title checkTeacherById
	     * @author 皮龙霖
	     * @description 找到id老师的详情
	     * @createdate 2022年7月4日 下午3:51:08
	     * @param id
	     * @return Teacher
	     **/
	public Teacher checkTeacherById(int id);
	
	  /**
		 * @Title postTeacherInfo
	     * @author 皮龙霖
	     * @description 上传教师信息
	     * @createdate 2022年7月4日 下午3:12:27
	     * @param teacher
	     * @return boolean
	     **/
	public boolean postTeacherInfo (Teacher teacher);
	
	  /**
		 * @Title deleteTeacher
	     * @author 皮龙霖
	     * @description 删除id教师记录
	     * @createdate 2022年7月8日 上午9:56:47
	     * @param id
	     * @return boolean
	     **/
	public boolean deleteTeacher (int id);
	
	  /**
		 * @Title changeTeacherInfo
	     * @author 皮龙霖
	     * @description 更改教师信息
	     * @createdate 2022年7月4日 下午3:12:44
	     * @param teacher
	     * @return boolean
	     **/
	public boolean changeTeacherInfo(Teacher teacher);
	
	  /**
		 * @Title checkCourseOfTeacher
	     * @author 皮龙霖
	     * @description 查询id教师的所有课程
	     * @createdate 2022年7月4日 下午3:13:52
	     * @param id
	     * @return IPage<Course>
	     **/
	public IPage<Course> checkCourseOfTeacher(int id,int pageSize, int pageNum);
	
	  /**
		 * @Title isTeacherOfUser
	     * @author 皮龙霖
	     * @description 判断改用户是否有这个teacher号，避免错误请求使某用户改别人的teacher
	     * @createdate 2022年7月4日 下午4:08:22
	     * @param teacherId
	     * @param userId
	     * @return boolean
	     **/
	public boolean isTeacherOfUser(int teacherId,int userId);
	
	  /**
		 * @Title getTeacherOfUser
	     * @author 皮龙霖
	     * @description 一个用户只有唯一的teacher，返回id用户的teacher
	     * @createdate 2022年7月4日 下午4:51:10
	     * @param userId
	     * @return Teacher
	     **/
	public Teacher getTeacherOfUser(int userId);
	
}
