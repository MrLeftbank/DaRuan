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
 * @since 2022-07-07
 */
@ApiModel(value = "Section对象", description = "")
public class Section implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("小节表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("对应章节id")
    private Integer chapterId;

    @ApiModelProperty("小节标题")
    private String title;

    @ApiModelProperty("章节内小节序号")
    private Integer number;

    @ApiModelProperty("课件")
    private String courseware;

    @ApiModelProperty("视频编号")
    private String video;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
    public String getCourseware() {
        return courseware;
    }

    public void setCourseware(String courseware) {
        this.courseware = courseware;
    }
    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    @Override
    public String toString() {
        return "Section{" +
            "id=" + id +
            ", chapterId=" + chapterId +
            ", title=" + title +
            ", number=" + number +
            ", courseware=" + courseware +
            ", video=" + video +
        "}";
    }
}
