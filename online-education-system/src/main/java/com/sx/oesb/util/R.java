/**
 * @Title R.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月2日上午9:55:53
 */
package com.sx.oesb.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName R 
* @Description 统一返回类型，参考https://www.bilibili.com/read/cv12521183/
* @author 皮龙霖
* @date 2022年7月2日 上午9:55:53 
*  
*/

@ApiModel()
public class R {
	
	@ApiModelProperty(notes = "返回码", required = true)
    private int code;//返回码

	@ApiModelProperty(notes = "返回码信息")
    private String message;//返回信息

	@ApiModelProperty(notes = "返回内容")
    private Object data;//返回数据
    
      /**
    	 * @Title success
         * @author 皮龙霖
         * @description 设置信息为成功，链式
         * @createdate 2022年7月2日 上午10:12:48
         * @return R
         **/
    public R success() {
    	this.code = ResponseEnum.SUCCESS.getCode();
    	this.message = ResponseEnum.SUCCESS.getMessage();
    	return this;
    }
  
      /**
    	 * @Title error
         * @author 皮龙霖
         * @description 设置错误信息，链式。使用枚举作为参数，例如ResponseEnum.USER_PASSWORD_FAIL。
         * @createdate 2022年7月2日 上午10:12:50
         * @param r
         * @return R
         **/
    public R error(ResponseEnum r) {
    	this.code = r.getCode();
    	this.message = r.getMessage();
    	return this;
    }
    
      /**
    	 * @Title error
         * @author 皮龙霖
         * @description 设置错误信息，链式；不用枚举（不推荐）。
         * @createdate 2022年7月2日 上午10:25:19
         * @param code
         * @param message
         * @return R
         **/
    public R error(int code,String message) {
    	this.code = code;
    	this.message = message;
    	return this;
    }
    
      /**
    	 * @Title data
         * @author 皮龙霖
         * @description 添加data为d
         * @createdate 2022年7月2日 上午10:12:53
         * @param d
         * @return R
         **/
    public R data(Object d) {
    	this.data = d;
    	return this;
    }

	@Override
	public String toString() {
		return "R [code=" + code + ", message=" + message + ", data=" + data + "]";
	}

	public R() {
		super();
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}
	
}