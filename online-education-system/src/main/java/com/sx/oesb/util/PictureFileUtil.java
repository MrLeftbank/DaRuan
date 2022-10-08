/**
 * @Title PictureUploadUtil.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月6日下午4:20:19
 */
package com.sx.oesb.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;


/** 
* @ClassName PictureUploadUtil 
* @Description 上传图片的保存
* @author 皮龙霖
* @date 2022年7月6日 下午4:20:19 
*  
*/
@Component
public class PictureFileUtil {	 
	
	@Value("${upload-file-path}")
	String savePath;

	@Value("${visit-file-path}")
	String visitPath;
	
	static String imgPattern = "\\.(jpg|png|jpeg|png|bmp|webp)";
	static String docPattern = "\\.(doc|pdf|docx|ppt|pptx|odf)";
	
	  /**
		 * @Title uploadImage
	     * @author 皮龙霖
	     * @description  上传图片
	     * @createdate 2022年7月7日 上午8:36:14
	     * @param imageFile
	     * @return
	     * @throws IOException String
	     **/
	public String uploadImage(MultipartFile imageFile) throws IOException {
		return this.uploadFile(imageFile, imgPattern);
	}
	
	  /**
		 * @Title uploadDoc
	     * @author 皮龙霖
	     * @description 上传文本课件
	     * @createdate 2022年7月12日 下午4:38:51
	     * @param docFile
	     * @return
	     * @throws IOException String
	     **/
	public String uploadDoc(MultipartFile docFile) throws IOException {
		return this.uploadFile(docFile, docPattern);
	}
	
	  /**
		 * @Title uploadFile
	     * @author 皮龙霖
	     * @description 接受multipartFile,重命名并保存到savePath，返回visitPath的访问路径
	     * @createdate 2022年7月12日 下午4:39:02
	     * @param file
	     * @param pattern
	     * @return
	     * @throws IOException String
	     **/
	public String uploadFile(MultipartFile file,String pattern) throws IOException {
		String newFileName = null;
	    if (!file.isEmpty()) {
	    	
	        String originalName = file.getOriginalFilename();
	        
	        
	        String suffix = originalName.substring(originalName.lastIndexOf("."));
	        suffix = suffix.toLowerCase();

//			不是图片，不通过
	        Pattern r = Pattern.compile(pattern);
	        java.util.regex.Matcher m = r.matcher(suffix);
	        if(!m.find())
	        	return "";
	        
	        String uuidName = UUID.randomUUID().toString();
//	        新名字是uuidName加旧名字的文件后缀
	        newFileName = uuidName + suffix;
	        
	        File loclafile = new File(savePath +"/" + newFileName);
	        file.transferTo(loclafile);
	        
	        return visitPath + newFileName;
	    }else{
	        return  "";
	    }
	}
	
	
	  /**
		 * @Title deleteImg
	     * @author 皮龙霖
	     * @description 根据访问路径删除图片
	     * @createdate 2022年7月7日 上午10:21:56
	     * @param imgUrl void
	     **/
	public void deleteImg (String imgUrl) {
		if (imgUrl == null)
			return;
		String imgName;
		try {
			imgName = imgUrl.substring(imgUrl.lastIndexOf("/"));
		} catch (Exception e) {
			imgName = imgUrl;
		}
		File file = new File(savePath +"/" + imgName);
		file.delete();
	}
	
	
}
