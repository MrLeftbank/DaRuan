/**
 * @Title serviceTest.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月2日下午2:53:31
 */
package com.sx.oesb.serviceTest;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sx.oesb.service.CourseService;
import com.sx.oesb.service.DomainService;


/** 
* @ClassName CourseServiceTest 
* @Description TODO
* @author 皮龙霖
* @date 2022年7月5日 上午9:49:20 
*  
*/
@SpringBootTest
public class CourseServiceTest {

	@Autowired
	private CourseService courseService;
	@Autowired
	private DomainService domainService;
	
	
	@Test
	public void testAllChaptersOfCourse() {
        System.out.println(("-----courseService.checkAllChaptersOfCourse()  method test ------"));
        System.out.println(courseService.checkAllChaptersOfCourse(1));
    }
	
	@Test
	public void searchCourse() {
        System.out.println(("-----domainService.searchCourseByDomain()  method test ------"));
        System.out.println(domainService.searchCourseByDomain(1, "2", 5, 0, 1).getRecords());
    }
}
