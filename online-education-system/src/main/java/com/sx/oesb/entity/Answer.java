package com.sx.oesb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 自动生成
 * @since 2022-07-02
 */
@ApiModel(value = "Answer对象", description = "")
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("回答编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("回答作者用户id")
    private Integer userId;

    @ApiModelProperty("回答对应问题id")
    private Integer questionId;

    @ApiModelProperty("回答内容")
    private String content;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("回答发布时间")
    private LocalDateTime time;

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
    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Answer{" +
            "id=" + id +
            ", userId=" + userId +
            ", questionId=" + questionId +
            ", content=" + content +
            ", time=" + time +
        "}";
    }
}
