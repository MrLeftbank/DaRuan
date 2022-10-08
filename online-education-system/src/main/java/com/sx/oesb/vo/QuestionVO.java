/**
 * @Title QuestionDetail.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月11日下午2:24:26
 */
package com.sx.oesb.vo;

import com.sx.oesb.entity.Question;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName QuestionDetail 
* @Description 补充问题信息实体，没有数据表，只用于展示
* @author 皮龙霖
* @date 2022年7月11日 下午2:24:26 
*  
*/
@ApiModel(value = "QuestionDetail对象", description = "")
public class QuestionVO extends Question {

    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty("提问作者用户名")
    private String userName;
    
    @ApiModelProperty("提问作者用户头像")
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
