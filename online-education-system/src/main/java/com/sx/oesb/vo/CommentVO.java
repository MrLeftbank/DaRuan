/**
 * @Title CommentDetail.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月11日下午2:47:49
 */
package com.sx.oesb.vo;

import com.sx.oesb.entity.Comment;

import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName CommentDetail 
* @Description TODO
* @author 皮龙霖
* @date 2022年7月11日 下午2:47:49 
*  
*/
public class CommentVO extends Comment {

    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty("评论作者用户名")
    private String userName;
    
    @ApiModelProperty("评论作者用户头像")
    private String userPhotoUrl;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhotoUrl() {
		return userPhotoUrl;
	}

	public void setUserPhotoUrl(String userPhotoUrl) {
		this.userPhotoUrl = userPhotoUrl;
	}
}
