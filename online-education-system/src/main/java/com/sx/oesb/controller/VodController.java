/**
 * @Title VodController.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月6日上午10:53:26
 */
package com.sx.oesb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sx.oesb.service.VodService;
import com.sx.oesb.util.R;
import com.sx.oesb.util.ResponseEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @ClassName VodController 
* @Description 视频点播相关服务的Controller
* @author 皮龙霖
* @date 2022年7月6日 上午10:53:26 
*  
*/
@Api(tags="视频上传、点播")
@RestController
@RequestMapping("/video")
public class VodController {
	

    private final VodService vodService;
    
    @Autowired
    public VodController(VodService vodService) {
		super();
		this.vodService = vodService;
	}


    @ApiOperation("上传视频到阿里云 测试")
    @PostMapping(value= "" )
    public R uploadAliVideo(MultipartFile file) {
    	R responseBody = new R();
        //返回上传视频id
        String videoId = vodService.uploadAliVideo(file);
        return responseBody.success().data(videoId);
    }

    @ApiOperation("删除阿里云视频 测试")
    @DeleteMapping(value= "" )
    public R uploadAliVideo(@RequestParam("vId") String vId) {
    	R responseBody = new R();
    	
        //返回上传视频id
        if(vodService.removeAliVideo(vId))
        	return responseBody.success();
        else
        	return responseBody.error(ResponseEnum.SERVICE_FAIL);
    }

    @ApiOperation("删除阿里云视频 测试")
    @GetMapping(value= "" )
    public R getAliVideo(@RequestParam("vId") String vId) {
    	R responseBody = new R();
    	responseBody.data(vodService.getAliVideoUrl(vId));
    	
    	if(null != responseBody.getData())
        	return responseBody.success();
        else
        	return responseBody.error(ResponseEnum.SERVICE_FAIL);
    }
    
}
