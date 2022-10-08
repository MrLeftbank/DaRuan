/**
 * @Title mapperTest.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月1日下午2:37:45
 */
package com.sx.oesb.mapperTest;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sx.oesb.entity.Course;
import com.sx.oesb.entity.Teacher;
import com.sx.oesb.entity.User;
import com.sx.oesb.mapper.CourseMapper;
import com.sx.oesb.mapper.PurchaseMapper;
import com.sx.oesb.mapper.TeacherMapper;
import com.sx.oesb.mapper.UserLogMapper;
import com.sx.oesb.mapper.UserMapper;
import com.sx.oesb.vo.CourseDetail;
import com.sx.oesb.vo.UserVO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



/** 
* @ClassName mapperTest 
* @Description 测试mappepr层
* @author 皮龙霖
* @date 2022年7月1日 下午2:37:45 
*  
*/
@SpringBootTest
public class mapperTest {
	
	@Autowired
	private CourseMapper courseMapper;
	@Autowired
	private TeacherMapper teacherMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private PurchaseMapper purchaseMapper;
	@Autowired
	private UserLogMapper userLogMapper;
	
	
	@Test
    public void testBestCourse() {
        System.out.println(("-----courseMapper.findBestCourses()  method test ------"));
        List<Course> courseList = courseMapper.findBestCourse();
        System.out.println(courseList);
    }
	
	@Test
    public void testBestTeacher() {
        System.out.println(("-----courseMapper.findBestTeacher()  method test ------"));
        List<Teacher> teacherList = teacherMapper.findBestTeachers();
        System.out.println(teacherList);
    }
	
	@Test
    public void testPageUser() {
        System.out.println(("-----userMapper.selectPage()  method test ------"));
        IPage<User> userPage = userMapper.selectPage(new Page<>(2,2),null);
        System.out.println(userPage);
    }
	
	@Test
	public void testUserPurchase() {
        System.out.println(("-----userMapper.selectPage()  method test ------"));
        List<Integer> li = purchaseMapper.selectUserPurchaseCourseId(1);
        System.out.println(li);
    }
	
	@Test
    public void testBestCourseDetail() {
        System.out.println(("-----courseMapper.findBestCoursesDeatail()  method test ------"));
        List<CourseDetail> courseList = courseMapper.findBestCourseDetail();
        System.out.println(courseList);
    }
	
	@Test
    public void testMonthMoney() {
        System.out.println(("-----purchaseMapper.recentMoney()  method test ------"));
        List<Map<Date, Double>> dd = purchaseMapper.recentMoney();
        System.out.println(dd);
    }
	
	@Test
    public void testRecentLogin() {
        System.out.println(("-----purchaseMapper.recentMoney()userLogMapper.frequentLogin()  method test ------"));
        List<UserVO> II = userLogMapper.frequentLogin();
        System.out.println(II);
    }
}
