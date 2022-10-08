package com.sx.oesb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author 自动生成
 * @since 2022-07-02
 */
@ApiModel(value = "Teacher对象", description = "")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("教师id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("对应用户id")
    private Integer userId;

    @ApiModelProperty("简历")
    private String resume;

    @ApiModelProperty("认证状态")
    private Integer status;

    @ApiModelProperty("教师名字")
    private String teacherName;

    @ApiModelProperty("教师头像")
    private String teacherPhoto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
    public String getTeacherPhoto() {
        return teacherPhoto;
    }

    public void setTeacherPhoto(String teacherPhoto) {
        this.teacherPhoto = teacherPhoto;
    }

    @Override
    public String toString() {
        return "Teacher{" +
            "id=" + id +
            ", userId=" + userId +
            ", resume=" + resume +
            ", status=" + status +
            ", teacherName=" + teacherName +
            ", teacherPhoto=" + teacherPhoto +
        "}";
    }
}
