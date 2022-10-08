/**
 * @Title MyFilter.java
 * @author 皮龙霖
 * @description 
 * @date 2022年6月29日上午8:38:11
 */
package com.sx.oesb.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;


/** 
* @ClassName MyFilter 
* @Description 过滤器修改respone，让各种跨域能执行
* @author 皮龙霖
* @date 2022年7月9日 下午4:19:39 
*  
*/
@Component
@WebFilter(filterName = "myFilterAJAX",urlPatterns = {"/*"})
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        System.out.println("初始化过滤器");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        
    	
    	HttpServletResponse response = (HttpServletResponse)  servletResponse;
    	HttpServletRequest request = (HttpServletRequest)servletRequest;
//    	下面配置reponse的header：
  
//    	不设置为*，否则axios不允许带cookies
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin")); 
        
//        axios POST带cookie:
//        response.setHeader("Access-Control-Allow-Credentials", "true"); 
        
        response.setHeader("Access-Control-Allow-Headers", "authorization, content-type"); 
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, OPTIONS, PATCH"); 
        
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
//        System.out.println("过滤器被销毁了");
    }
}