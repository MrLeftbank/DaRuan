/**
 * @Title Swagger3Config.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月2日上午11:25:23
 */
package com.sx.oesb.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

/** 
* @ClassName Swagger3Config 
* @Description 配置类，配置Swagger
* @author 皮龙霖
* @date 2022年7月2日 上午11:25:23 
*  
*/
@Configuration
public class Swagger3Config {
	
	  /**
		 * @Title docket
	     * @author 皮龙霖
	     * @description 配置Swagger全局API身份验证。参考https://www.cnblogs.com/xiaoBastard/p/15504273.html
	     * @createdate 2022年7月4日 下午12:01:34
	     * @return Docket
	     **/
	@Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
        		 .securityContexts(Arrays.asList(SecurityContext.builder()
                         .securityReferences(Arrays.asList(SecurityReference.builder()
                                 .reference("Authorization")
                                 .scopes(new AuthorizationScope[]{new AuthorizationScope("global", "accessEverything")})
                                 .build()))
                         .build()))
        		 .securitySchemes(Arrays.asList(new ApiKey("Authorization", "Authorization", "header")))//根据约定,使用Authorization: Bearer {token}
                .apiInfo(apiInfo()).enable(true)
                .select()
                //apis： 添加swagger接口提取范围
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }
	
	 

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger3")
                .description("在线教育系统OESB后端API测试")
                .build();
    }

}
