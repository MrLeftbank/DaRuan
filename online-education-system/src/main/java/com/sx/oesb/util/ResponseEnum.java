package com.sx.oesb.util;


/** 
* @ClassName ResponseEnum 
* @Description 返回码与信息的对应，参考：https://www.bilibili.com/read/cv12521183/
* @author 皮龙霖
* @date 2022年7月2日 上午10:00:25 
*  
*/
public enum ResponseEnum {

    SUCCESS(200,"成功"),

    //可以在这里定义code与message的组合，为各个Controller共用

    BAD_REQUEST(400, "错误请求"),
    USER_PASSWORD_FAIL(401,"用户名和密码错误"),
    USER_NOT_TEACHER(402,"用户非教师"),

    NOT_FOUND(404, "找不到资源"),
    USERNAME_CONFLICT(409, "用户名已存在"),
    USER_OTHER(602,"访问非本人信息"),
    USER_BALANCE_NOT_ENOUGH(604,"用户余额不足"),
    USER_FROZEN(605,"用户冻结"),
    SERVICE_FAIL(603,"service写入失败"),
    REPEATED(606,"重复"),
    USER_UPDATE_ERROR(601,"用户更新失败");
//    USER_REG_USER_PASSWORD_CONFIRM(402,"密码和确认密码不一致");
    

    private Integer code;
    private String message;

    ResponseEnum(Integer code,String mesage){
        this.code = code;
        this.message =mesage;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
