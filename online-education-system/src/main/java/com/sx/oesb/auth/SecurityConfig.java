package com.sx.oesb.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.RetryWithHttpEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sx.oesb.config.MyFilter;

import net.sf.jsqlparser.expression.operators.conditional.AndExpression;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;
    private final MyFilter myFilter;
    
    public SecurityConfig(JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter, MyFilter myFilter) {
		super();
		this.jwtTokenAuthenticationFilter = jwtTokenAuthenticationFilter;
		this.myFilter = myFilter;
	}

	@Override
    public void configure(WebSecurity web) throws Exception {
    	
    	/*
    	放行swagger
    	*/
    	web.ignoring().antMatchers("/v3/api-docs/**");
        web.ignoring().antMatchers("/swagger.json");
        web.ignoring().antMatchers("/swagger-ui/**");
        web.ignoring().antMatchers("/swagger-resources/**");
        web.ignoring().antMatchers("/webjars/**");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()//放行preflight
                .antMatchers("/index/**","/user/login", "/user/register","/admin/login","/admin/register").permitAll()
                .antMatchers(HttpMethod.POST, "/picture","/**/search","/video","/picture/**").permitAll() 	//优先放行搜索的post
                //管理员身份的请求
                .antMatchers("/user/**").authenticated()
                .antMatchers("/admin/**").hasAnyAuthority("ADMIN")
                //更改数据库的操作都要登录
                .antMatchers(HttpMethod.POST, "/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/**").authenticated()
                .and()
                .addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(myFilter,  JwtTokenAuthenticationFilter.class);//跨域过滤器
    }
}