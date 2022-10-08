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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sx.oesb.entity.Article;
import com.sx.oesb.entity.Course;
import com.sx.oesb.entity.User;
import com.sx.oesb.service.UserService;


/** 
* @ClassName UserServiceTest 
* @Description TODO
* @author 皮龙霖
* @date 2022年7月5日 上午9:49:20 
*  
*/
@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@Test
	public void testUserPage() {
        System.out.println(("-----userService.checkAllUseres()  method test ------"));
        IPage<User> userPage = userService.checkAllUsers(0, 0);
        System.out.println(userPage.getRecords());
    }
	
	@Test
	public void testUserId() {
        System.out.println(("-----userService.searchUserByID()  method test ------"));
        User user = userService.searchUserByID(2);
        System.out.println(user);
    }
	@Test
	public void testUserArticle() {
        System.out.println(("-----userService.checkArticleOfUser()  method test ------"));
        IPage<Article> pa = userService.checkArticleOfUser(1, 2, 0);
        System.out.println(pa.getRecords());
    }
	
	@Test
	public void testUserPurchseCourse() {
        System.out.println(("-----userService.checkPurchaseCourse()  method test ------"));
        IPage<Course> pa = userService.checkPurchaseCourse(1, 2, 0);
        System.out.println(pa.getRecords());
    }
}
