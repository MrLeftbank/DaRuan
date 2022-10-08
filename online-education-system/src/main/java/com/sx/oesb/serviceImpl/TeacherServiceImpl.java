/**
 * @Title TeacherServiceImpl.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月4日下午3:14:51
 */
package com.sx.oesb.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sx.oesb.entity.Course;
import com.sx.oesb.entity.Teacher;
import com.sx.oesb.mapper.CourseMapper;
import com.sx.oesb.mapper.TeacherMapper;
import com.sx.oesb.service.TeacherService;

/** 
* @ClassName TeacherServiceImpl 
* @Description 调用TeacherMapper实现 TeacherService
* @author 皮龙霖
* @date 2022年7月4日 下午3:14:51 
*  
*/
@Service
public class TeacherServiceImpl implements TeacherService {
	
	private final TeacherMapper teachermapper;
	private final CourseMapper courseMapper;

	
	@Autowired
	public TeacherServiceImpl(TeacherMapper teachermapper, CourseMapper courseMapper) {
		super();
		this.teachermapper = teachermapper;
		this.courseMapper = courseMapper;
	}



	/**
		 * @Title checkAllTeacher
	     * @author 皮龙霖
	     * @description	调用teachermapper.selectPage
	     * @createdate 2022年7月4日 下午3:28:39
	     * @param pageSize
	     * @param pageNum
	     * @return 
		 * @see com.sx.oesb.service.TeacherService#checkAllTeacher(int, int)
	     **/
	@Override
	public IPage<Teacher> checkAllTeacher(int pageSize, int pageNum) {
		return teachermapper.selectPage(new Page<>(pageNum,pageSize),null);
	}



	  /**
		 * @Title SearchTeacherByName
	     * @author 皮龙霖
	     * @description	调用teachermapper.selectPage
	     * @createdate 2022年7月4日 下午3:28:18
	     * @param name
	     * @param pageSize
	     * @param pageNum
	     * @return 
		 * @see com.sx.oesb.service.TeacherService#SearchTeacherByName(java.lang.String, int, int)
	     **/
	@Override
	public IPage<Teacher> SearchTeacherByName(String name,int pageSize, int pageNum) {
		return teachermapper.selectPage(new Page<>(pageNum,pageSize),new QueryWrapper<Teacher>().like("teacher_name",name));
	}



	  /**
		 * @Title checkTeacherById
	     * @author 皮龙霖
	     * @description 调用teachermapper.selectById()实现
	     * @createdate 2022年7月4日 下午3:50:29
	     * @param id
	     * @return Teacher
	     **/
	@Override
	public Teacher checkTeacherById(int id) {
		return teachermapper.selectById(id);
	}

	/**
	 * @Title postTeacherInfo
	   * @author 皮龙霖
	   * @description
	   * @createdate 2022年7月4日 下午3:14:51
	   * @param teacher
	   * @return 
	 * @see com.sx.oesb.service.TeacherService#postTeacherInfo(com.sx.oesb.entity.Teacher)
	   **/

	  /**
		 * @Title postTeacherInfo
	     * @author 皮龙霖
	     * @description	 teachermapper.insert直接写入，默认在Controller已经完成UserId检验。
	     * @createdate 2022年7月4日 下午3:54:26
	     * @param teacher
	     * @return 
		 * @see com.sx.oesb.service.TeacherService#postTeacherInfo(com.sx.oesb.entity.Teacher)
	     **/
	@Override
	public boolean postTeacherInfo(Teacher teacher) {
		return teachermapper.insert(teacher) > 0;
	}

	
	  /**
		 * @Title deleteTeacher
	     * @author 皮龙霖
	     * @description 调用teachermapper.deleteById
	     * @createdate 2022年7月8日 上午9:57:19
	     * @param id
	     * @return 
		 * @see com.sx.oesb.service.TeacherService#deleteTeacher(int)
	     **/
	@Override
	public boolean deleteTeacher(int id) {
		return teachermapper.deleteById(id) > 0 ;
	}



	/**
	 * @Title changeTeacherInfo
	   * @author 皮龙霖
	   * @description	teachermapper.insert写入，默认在Controller已经完成UserId检验和isTeacherOfUser检验。
	   * @createdate 2022年7月4日 下午3:14:51
	   * @param teacher
	   * @return 
	 * @see com.sx.oesb.service.TeacherService#changeTeacherInfo(com.sx.oesb.entity.Teacher)
	   **/

	@Override
	public boolean changeTeacherInfo(Teacher teacher) {
		return teachermapper.updateById(teacher) > 0;
	}



	  /**
		 * @Title checkCourseOfTeacher
	     * @author 皮龙霖
	     * @description 调用courseMapper.selectPage条件查询实现
	     * @createdate 2022年7月4日 下午3:28:25
	     * @param id
	     * @param pageSize
	     * @param pageNum
	     * @return 
		 * @see com.sx.oesb.service.TeacherService#checkCourseOfTeacher(int, int, int)
	     **/
	@Override
	public IPage<Course> checkCourseOfTeacher(int id,int pageSize, int pageNum) {
		return courseMapper.selectPage(new Page<>(pageNum,pageSize),new QueryWrapper<Course>().eq("teacher_id",id));
	}

	  /**
		 * @Title isTeacherOfUser
	     * @author 皮龙霖
	     * @description	条件查询,查看是否有此结果
	     * @createdate 2022年7月4日 下午4:10:47
	     * @param teacherId
	     * @param userId
	     * @return 
		 * @see com.sx.oesb.service.TeacherService#isTeacherOfUser(int, int)
	     **/
	@Override
	public boolean isTeacherOfUser(int teacherId, int userId) {
		return teachermapper.selectList(new QueryWrapper<Teacher>().eq("id", teacherId).eq("user_id",userId)).size() > 0;
	}



	  /**
		 * @Title getTeacherOfUser
	     * @author 皮龙霖
	     * @description
	     * @createdate 2022年7月4日 下午4:52:17
	     * @param userId
	     * @return 
		 * @see com.sx.oesb.service.TeacherService#getTeacherOfUser(int)
	     **/
	@Override
	public Teacher getTeacherOfUser(int userId) {
		return teachermapper.selectOne(new QueryWrapper<Teacher>().eq("user_id",userId));
	}
	
	

}
