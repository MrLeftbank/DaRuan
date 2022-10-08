/**
 * @Title VodServiceImpl.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月6日上午8:58:47
 */
package com.sx.oesb.serviceImpl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.sx.oesb.service.VodService;

/** 
* @ClassName VodServiceImpl 
* @Description 视频服务类，参考：https://www.cnblogs.com/innn/p/15255954.html
* @author 皮龙霖
* @date 2022年7月6日 上午8:58:47 
*  
*/
@Service
public class VodServiceImpl implements VodService {
	
    @Value("${aliyun.vod.region}")
	String regionId;  // 点播服务接入区域
	
    @Value("${aliyun.vod.file.keyid}")
    private String keyid;

    @Value("${aliyun.vod.file.keysecret}")
    private String keysecret;

	/**
	 * @Title uploadAliVideo
	   * @author 皮龙霖
	   * @description UploadStreamRequest流式上传，参考：https://help.aliyun.com/document_detail/53406.html
	   * @createdate 2022年7月6日 上午8:58:47
	   * @param file
	   * @return 
	 * @see com.sx.oesb.service.VodService#uploadAliVideo(org.springframework.web.multipart.MultipartFile)
	   **/

	@Override
	public String uploadAliVideo(MultipartFile file) {
		
		 try {
	            //fileName：上传文件原始名称
	            String fileName = file.getOriginalFilename();
	            //title：上传之后显示名称
	            String title = fileName.substring(0, fileName.lastIndexOf("."));
	            //inputStream：上传文件输入流
	            InputStream inputStream = file.getInputStream();
	            UploadStreamRequest request = new UploadStreamRequest(keyid,keysecret, title, fileName, inputStream);

	            UploadVideoImpl uploader = new UploadVideoImpl();
	            UploadStreamResponse response = uploader.uploadStream(request);

	            String videoId = null;
	            if (response.isSuccess()) {
	                videoId = response.getVideoId();
	            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
	                videoId = response.getVideoId();
	            }
	            return videoId;
	        }catch(Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	}

	/**
	 * @Title removeAliVideo
	   * @author 皮龙霖
	   * @description 集成创建client与发送删除请求
	   * @createdate 2022年7月6日 上午8:58:47
	   * @param videoId
	   * @return 
	 * @see com.sx.oesb.service.VodService#removeAliVideo(java.lang.String)
	   **/

	@Override
	public boolean removeAliVideo(String videoId) {
		if(videoId == null)
			return false;
		try {
            //初始化对象 
			DefaultProfile profile = DefaultProfile.getProfile(regionId, keyid, keysecret);
	        DefaultAcsClient client = new DefaultAcsClient(profile);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            //向request设置视频id
            request.setVideoIds(videoId);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
            return true;
        }catch(Exception e) {
            e.printStackTrace();
        }
		return false;
	}

	  /**
		 * @Title getAliVideoUrl
	     * @author 皮龙霖
	     * @description 集成创建client与发送播放信息请求，参考：https://help.aliyun.com/document_detail/61064.html
	     * @createdate 2022年7月6日 下午3:58:55
	     * @param videoId
	     * @return 
		 * @see com.sx.oesb.service.VodService#getAliVideoUrl(java.lang.String)
	     **/
	@Override
	public List<String> getAliVideoUrl(String videoId) {
		if(videoId == null)
			return null;
		try {
			 //初始化对象 
			DefaultProfile profile = DefaultProfile.getProfile(regionId, keyid, keysecret);
	        DefaultAcsClient client = new DefaultAcsClient(profile);
	        
	        //设置请求
	        GetPlayInfoRequest request = new GetPlayInfoRequest();
	        request.setVideoId(videoId);
	        
	        //对象发请求
	        GetPlayInfoResponse response = client.getAcsResponse(request);
	        //获得播放信息列表
	        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
	        
	        List<String> urls = new ArrayList<>();
	        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
	           urls.add(playInfo.getPlayURL());
	        }
	 
	        return urls;
		}catch(Exception e) {
	        e.printStackTrace();
	    }
		return null;
	}
	
	

}
