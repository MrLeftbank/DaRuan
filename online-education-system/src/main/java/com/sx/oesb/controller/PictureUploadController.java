/**
 * @Title PictureUploadController.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月6日下午4:44:07
 */
package com.sx.oesb.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sx.oesb.entity.User;
import com.sx.oesb.util.PictureFileUtil;
import com.sx.oesb.util.R;
import com.sx.oesb.util.ResponseEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @ClassName PictureUploadController 
* @Description 图片上传的控制器
* @author 皮龙霖
* @date 2022年7月6日 下午4:44:07 
*  
*/
@Api(tags="图片上传")
@RestController
@RequestMapping("/picture")
public class PictureUploadController {
	
	private final PictureFileUtil pictureFileUtil;
	
	@Autowired
    public PictureUploadController(PictureFileUtil pictureUploadUtil) {
		super();
		this.pictureFileUtil = pictureUploadUtil;
	}


	/**
    	 * @Title uploadAliVideo
         * @author 皮龙霖
         * @description 上传图片，返回访问地址
         * @createdate 2022年7月6日 下午4:49:58
         * @param file
         * @return R
         **/
    @ApiOperation("上传图片到服务器")
    @PostMapping(value= "" )
    public R uploadPicture(MultipartFile file) {
    	R responseBody = new R();
        //返回上传图片的访问链接
        String picUrl = "";
		try {
			picUrl = pictureFileUtil.uploadImage(file);
		} catch (IOException e) {
			responseBody.error(ResponseEnum.SERVICE_FAIL);
			//自动生成的 catch 块
			e.printStackTrace();
		}
		if(picUrl.equals(""))
			return responseBody.error(ResponseEnum.BAD_REQUEST);
		else
			return responseBody.success().data(picUrl);
    }
    
    
      /**
    	 * @Title deletePicture
         * @author 皮龙霖
         * @description 删除单张图片，测试用
         * @createdate 2022年7月7日 上午10:27:35
         * @param picUrl
         * @return R
         **/
    @ApiOperation("删除服务器图片")
    @DeleteMapping(value= "" )
    public R deletePicture(@RequestParam String picUrl) {
    	R responseBody = new R();
    	pictureFileUtil.deleteImg(picUrl);
    	return responseBody.success();
    }
    
    
    @ApiOperation("多文件加多参数上传测试")
	@PostMapping("/{cId}")
	public R postNewSection(@AuthenticationPrincipal User user,@PathVariable(name = "cId",required = true) int chapterId,
			@RequestPart("title") String title, 
			@RequestPart("number") String number, 
			@RequestPart("file") MultipartFile file, 
			@RequestPart("doc") MultipartFile file1) throws IOException{
		R responseBody = new R();
		System.out.println(title);
		System.out.println(chapterId);

		System.out.println(Integer.parseInt(number));

		System.out.println(file1.getOriginalFilename());
		System.out.println(file.getOriginalFilename());
		System.out.print(pictureFileUtil.uploadDoc(file1));
		return responseBody;
	}
}
