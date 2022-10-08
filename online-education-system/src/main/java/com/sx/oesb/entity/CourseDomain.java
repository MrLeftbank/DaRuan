package com.sx.oesb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("course_domain")
@ApiModel(value = "CourseDomain对象", description = "")
public class CourseDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("课程—类别编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("课程id")
    private Integer courseId;

    @ApiModelProperty("类别id")
    private Integer domainId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
    public Integer getDomainId() {
        return domainId;
    }

    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }

    @Override
    public String toString() {
        return "CourseDomain{" +
            "id=" + id +
            ", courseId=" + courseId +
            ", domainId=" + domainId +
        "}";
    }

    
	public CourseDomain() {
		super();
	}

	public CourseDomain(Integer courseId, Integer domainId) {
		super();
		this.courseId = courseId;
		this.domainId = domainId;
	}
    
}
