/**
 * @Title DomainServiceImpl.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月5日下午2:39:17
 */
package com.sx.oesb.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sx.oesb.entity.Course;
import com.sx.oesb.entity.CourseDomain;
import com.sx.oesb.entity.Domain;
import com.sx.oesb.mapper.CourseDomainMapper;
import com.sx.oesb.mapper.CourseMapper;
import com.sx.oesb.mapper.DomainMapper;
import com.sx.oesb.service.DomainService;


/** 
* @ClassName DomainServiceImpl 
* @Description 调用DomainMapper实现
* @author 皮龙霖
* @date 2022年7月5日 下午2:39:17 
*  
*/
@Service
public class DomainServiceImpl implements DomainService{
	
	private final CourseMapper courseMapper;
	private final DomainMapper domainMapper;
	private final CourseDomainMapper courseDomainMapper;

	
	@Autowired
	public DomainServiceImpl(CourseMapper courseMapper, DomainMapper domainMapper,
			CourseDomainMapper courseDomainMapper) {
		super();
		this.courseMapper = courseMapper;
		this.domainMapper = domainMapper;
		this.courseDomainMapper = courseDomainMapper;
	}

	/**
	 * @Title checkAllRootDomains
	   * @author 皮龙霖
	   * @description 调用domainMapper.selectList加QueryWrapper.isNull条件查询
	   * @createdate 2022年7月5日 下午2:39:17
	   * @return 
	 * @see com.sx.oesb.service.DomainService#checkAllParentDomains()
	   **/

	@Override
	public List<Domain> checkAllParentDomains() {
		return domainMapper.selectList(new QueryWrapper<Domain>().isNull("parent_id"));
	}

	/**
	 * @Title searchDomainByID
	   * @author 皮龙霖
	   * @description 直接调用domainMapper.selectById实现
	   * @createdate 2022年7月5日 下午2:39:17
	   * @param id
	   * @return 
	 * @see com.sx.oesb.service.DomainService#searchDomainByID(int)
	   **/

	@Override
	public Domain searchDomainByID(int id) {
		return domainMapper.selectById(id);
	}

	/**
	 * @Title searchChildDomainByID
	   * @author 皮龙霖
	   * @description 调用domainMapper.selectList加QueryWrapper.eq条件查询
	   * @createdate 2022年7月5日 下午2:39:17
	   * @param id
	   * @return 
	 * @see com.sx.oesb.service.DomainService#searchChildDomainByID(int)
	   **/

	@Override
	public List<Domain> searchChildDomainByID(int id) {
		return domainMapper.selectList(new QueryWrapper<Domain>().eq("parent_id",id));
	}


	  /**
		 * @Title searchCourseByDomain
	     * @author 皮龙霖
	     * @description 先查出列表courseIdOfDomain，再调用courseMapper.selectPage + QueryWrapper实现。
	     * @createdate 2022年7月5日 下午3:22:00
	     * @param domainId
	     * @param name
	     * @param pageSize
	     * @param pageNum
	     * @param orderTag
	     * @return 
		 * @see com.sx.oesb.service.DomainService#searchCourseByDomain(int, java.lang.String, int, int, int)
	     **/
	@Override
	public IPage<Course> searchCourseByDomain(int domainId, String name, int pageSize, int pageNum, int orderTag) {
		
//		先查符合本类别条件的课程id列表
		List<Integer> courseIdOfDomain = domainMapper.findCourseIdOfDomain(domainId);
		
//		找到本类所有子类别
		List<Domain> domainWithChildDomain = searchChildDomainByID(domainId);
		
//		课程id列表加上子类的 “本类课程id列表”
		for (Domain domain : domainWithChildDomain) {
			courseIdOfDomain.addAll(domainMapper.findCourseIdOfDomain(domain.getId()));
		}

//		这样分步，可以使用Mybatis原生分页查询返回结果
		
//		如果没有符合类别条件的课程id列表就返回空页
		if(courseIdOfDomain.size() ==0)
			return new Page<>(pageNum,pageSize);
		
//		按时间或价格排序
		if(orderTag == 1) {
			return courseMapper.selectPage(new Page<>(pageNum,pageSize), 
					new QueryWrapper<Course>()
					.in("id", courseIdOfDomain).like("title", name)
					.orderByDesc("time"));
		}else if(orderTag == 2) {
			return courseMapper.selectPage(new Page<>(pageNum,pageSize), 
					new QueryWrapper<Course>()
					.in("id", courseIdOfDomain).like("title", name)
					.orderByDesc("price"));
		}else {
			return new Page<>(pageNum,pageSize);
		}
	}

	  /**
		 * @Title postDomain
	     * @author 皮龙霖
	     * @description 直接domainMapper.insert
	     * @createdate 2022年7月5日 下午3:54:09
	     * @param d
	     * @return 
		 * @see com.sx.oesb.service.DomainService#postDomain(com.sx.oesb.entity.Domain)
	     **/
	@Override
	public boolean postDomain(Domain d) {
		return domainMapper.insert(d) > 0;
	}

	  /**
		 * @Title addCourseDomain
	     * @author 皮龙霖
	     * @description 直接courseDomainMapper.insert
	     * @createdate 2022年7月5日 下午3:54:47
	     * @param domianId
	     * @param courseId
	     * @return 
		 * @see com.sx.oesb.service.DomainService#addCourseDomain(int, int)
	     **/
	@Override
	public boolean addCourseDomain(int domianId, int courseId) {
		return courseDomainMapper.insert(new CourseDomain(courseId,domianId)) > 0;
	}

	  /**
		 * @Title checkAllDomains
	     * @author 皮龙霖
	     * @description 调用domainMapper.selectList(null)
	     * @createdate 2022年7月5日 下午4:01:53
	     * @return 
		 * @see com.sx.oesb.service.DomainService#checkAllDomains()
	     **/
	@Override
	public List<Domain> checkAllDomains() {
		return domainMapper.selectList(null);
	}

	  /**
		 * @Title deleteDomainByID
	     * @author 皮龙霖
	     * @description 
	     * @createdate 2022年7月5日 下午4:02:15
	     * @param id
	     * @return 
		 * @see com.sx.oesb.service.DomainService#deleteDomainByID(int)
	     **/
	@Override
	public boolean deleteDomainByID(int id) {
		return domainMapper.deleteById(id) > 0;
	}

	  /**
		 * @Title checkDomainOfCourse
	     * @author 皮龙霖
	     * @description 直接调用domainMapper.findDomainOfCourse，自定义sql句
	     * @createdate 2022年7月7日 下午4:23:42
	     * @param courseId
	     * @return 
		 * @see com.sx.oesb.service.DomainService#checkDomainOfCourse(int)
	     **/
	@Override
	public List<Domain> checkDomainOfCourse(int courseId) {
		return domainMapper.findDomainOfCourse(courseId);
	}
	

}
