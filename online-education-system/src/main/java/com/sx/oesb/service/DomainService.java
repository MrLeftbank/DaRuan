/**
 * @Title DomainService.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月5日下午2:16:48
 */
package com.sx.oesb.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sx.oesb.entity.Course;
import com.sx.oesb.entity.Domain;

/** 
* @ClassName DomainService 
* @Description 类别表相关的服务
* @author 皮龙霖
* @date 2022年7月5日 下午2:16:48 
*  
*/
public interface DomainService {
	
	  /**
		 * @Title postDomain
	     * @author 皮龙霖
	     * @description 上传类别
	     * @createdate 2022年7月5日 下午3:51:07
	     * @param d
	     * @return boolean
	     **/
	public boolean postDomain(Domain d);
	
	  /**
		 * @Title addCourseDomain
	     * @author 皮龙霖
	     * @description 为课程添加分类
	     * @createdate 2022年7月5日 下午3:53:07
	     * @param domianId
	     * @param courseId
	     * @return boolean
	     **/
	public boolean addCourseDomain(int domianId,int courseId);
	
      /**
    	 * @Title checkAllDomains
         * @author 皮龙霖
         * @description 查找所有类别列表
         * @createdate 2022年7月5日 下午3:51:21
         * @return List<Domain>
         **/
    public List<Domain> checkAllDomains();

	  /**
		 * @Title checkAllRootDomains
	     * @author 皮龙霖
	     * @description 查找所有父级类别列表
	     * @createdate 2022年7月5日 下午2:30:39
	     * @return List<Domain>
	     **/
	public List<Domain> checkAllParentDomains();

	  /**
		 * @Title searchDomainByID
	     * @author 皮龙霖
	     * @description 按id找类别
	     * @createdate 2022年7月5日 下午2:30:59
	     * @param id
	     * @return Domain
	     **/
	public Domain searchDomainByID(int id);

	  /**
		 * @Title deleteDomainByID
	     * @author 皮龙霖
	     * @description 按id删除类别
	     * @createdate 2022年7月5日 下午3:50:42
	     * @param id
	     * @return boolean
	     **/
	public boolean deleteDomainByID(int id);

	  /**
		 * @Title searchChildDomainByID
	     * @author 皮龙霖
	     * @description 按父类别的id查找子类别列表
	     * @createdate 2022年7月5日 下午2:31:17
	     * @param id
	     * @return List<Domain>
	     **/
	public List<Domain> searchChildDomainByID(int id);
	
	
	  /**
		 * @Title searchCourseByDomain
	     * @author 皮龙霖
	     * @description 加上类别条件的课程分页查找，支持多条件联合：名字（模糊查询）、类别（包含子类别）。orderTag为1|2，排序依据为时间|价格。
	     * @createdate 2022年7月5日 下午3:20:50
	     * @param domainId
	     * @param name
	     * @param pageSize
	     * @param pageNum
	     * @param orderTag
	     * @return IPage<Course>
	     **/
	public IPage<Course> searchCourseByDomain(int domainId,String name,int pageSize, int pageNum, int orderTag);

	  /**
		 * @Title checkDomainOfCourse
	     * @author 皮龙霖
	     * @description 查询id课程的所有类别
	     * @createdate 2022年7月7日 下午4:19:46
	     * @param courseId
	     * @return List<Domain>
	     **/
	public List<Domain> checkDomainOfCourse(int courseId);
}
