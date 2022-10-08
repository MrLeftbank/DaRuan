package com.sx.oesb.controller;

import com.sx.oesb.auth.JwtTokenUtil;
import com.sx.oesb.entity.User;
import com.sx.oesb.service.UserService;
import com.sx.oesb.util.R;

import com.sx.oesb.util.ResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags="登录、注册")
@RestController
public class LoginController {

    private final UserService userService;

    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public LoginController(UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @ApiOperation("用户登录")
    @PostMapping("/user/login")
    public R login(@RequestParam("username")String username, @RequestParam("password") String password) {
    	
    	R responseBody = new R();
        try {
            User user = userService.login(username, password);
            
            //冻结了不能登录
            if(user.getFreeze()==1)
            	return responseBody.error(ResponseEnum.USER_FROZEN);
            
            return responseBody.success().data(jwtTokenUtil.createToken(user.getUsername(), false));
        } catch (Exception e) {
            return responseBody.error(ResponseEnum.USER_PASSWORD_FAIL);
        }
    }

    @ApiOperation("用户注册")
    @PostMapping("/user/register")
    public R register(@RequestParam("username") String username, @RequestParam("password") String password) {
        R responseBody = new R();
        try {
            userService.register(username, password);
            return responseBody.success();
        } catch (Exception exception) {
            exception.printStackTrace();
            return responseBody.error(ResponseEnum.USERNAME_CONFLICT);
        }
    }
}