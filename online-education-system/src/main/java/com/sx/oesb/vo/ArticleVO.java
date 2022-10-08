/**
 * @Title ArticleVO.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月11日下午3:23:34
 */
package com.sx.oesb.vo;

import com.sx.oesb.entity.Article;

import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName ArticleVO 
* @Description TODO
* @author 皮龙霖
* @date 2022年7月11日 下午3:23:34 
*  
*/
public class ArticleVO extends Article{
	
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
