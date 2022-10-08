/**
 * @Title VodService.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月6日上午8:42:09
 */
package com.sx.oesb.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/** 
* @ClassName VodService 
* @Description 视频点播的服务
* @author 皮龙霖
* @date 2022年7月6日 上午8:42:09 
*  
*/
public interface VodService {
	
      /**
    	 * @Title uploadAliVideo
         * @author 皮龙霖
         * @description 上传视频的MultipartFile，返回云点播视频
         * @createdate 2022年7月6日 上午8:56:20
         * @param file
         * @return String
         **/
    public String uploadAliVideo(MultipartFile file);

      /**
    	 * @Title removeAliVideo
         * @author 皮龙霖
         * @description 删除对应id的云视频
         * @createdate 2022年7月6日 上午8:56:58
         * @param videoId
         * @return boolean
         **/
    public boolean removeAliVideo(String videoId);
    
      /**
    	 * @Title getAliVideoUrl
         * @author 皮龙霖
         * @description 根据视频编号，获取视频链接
         * @createdate 2022年7月6日 下午4:06:39
         * @param videoId
         * @return List<String>
         **/
    public List<String> getAliVideoUrl(String videoId);
}
