/**
 * @Title MybatisPlusConfig.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月2日下午2:39:07
 */
package com.sx.oesb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

/** 
* @ClassName MybatisPlusConfig 
* @Description 配置页面插件
* @author 皮龙霖
* @date 2022年7月2日 下午2:39:07 
*  
*/
@Configuration
public class MybatisPlusConfig {
	 	@Bean
	    public MybatisPlusInterceptor mybatisPlusInterceptor() {
	        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
	        //配置数据库为MYSQL
	        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
	        return interceptor;
	    }
}
