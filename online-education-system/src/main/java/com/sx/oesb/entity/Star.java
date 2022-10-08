package com.sx.oesb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@ApiModel(value = "Star对象", description = "")
public class Star implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("收藏id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("评价者id")
    private Integer userId;

    @ApiModelProperty("被评课程id")
    private Integer courseId;

    @ApiModelProperty("收藏时间")
    private LocalDateTime time;

    @ApiModelProperty("课程评分")
    private Integer rate;

    @ApiModelProperty("课程评价")
    private String comment;

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
    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Star{" +
            "id=" + id +
            ", userId=" + userId +
            ", courseId=" + courseId +
            ", time=" + time +
            ", rate=" + rate +
            ", comment=" + comment +
        "}";
    }
}
