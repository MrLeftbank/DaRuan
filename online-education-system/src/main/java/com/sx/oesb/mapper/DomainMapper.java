package com.sx.oesb.mapper;

import com.sx.oesb.entity.Domain;

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
public interface DomainMapper extends BaseMapper<Domain> {
	
	  /**
		 * @Title findCourseIdOfDomain
	     * @author 皮龙霖
	     * @description 查找属于某一类别的课程id列表
	     * @createdate 2022年7月5日 下午2:38:21
	     * @param domainId
	     * @return List<Integer>
	     **/
	@Select("SELECT course_id FROM course_domain "
			+ "WHERE domain_id = #{domainId}")
	public List<Integer> findCourseIdOfDomain(int domainId); 
	
	@Select("SELECT * FROM course_domain JOIN domain on domain.id = course_domain.domain_id "
			+ "WHERE course_domain.course_id = #{courseId}")
	public List<Domain> findDomainOfCourse(int courseId); 
}
