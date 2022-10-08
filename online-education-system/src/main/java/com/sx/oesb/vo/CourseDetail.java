/**
 * @Title CourseDetail.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月2日下午4:54:13
 */
package com.sx.oesb.vo;

import java.io.Serializable;
import java.time.LocalDateTime;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName CourseDetail 
* @Description 补充课程信息实体，没有数据表，只用于展示
* @author 皮龙霖
* @date 2022年7月2日 下午4:54:13 
*  
*/
@ApiModel(value = "CourseDetail对象", description = "")
public class CourseDetail implements Serializable {

	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		@ApiModelProperty("课程id")
	    private Integer id;

	    @ApiModelProperty("教师id")
	    private Integer teacherId;

	    @ApiModelProperty("课程简介")
	    private String intro;

	    @ApiModelProperty("课程标题")
	    private String title;

	    @ApiModelProperty("价格")
	    private Double price;

	    @ApiModelProperty("发布时间")
	    private LocalDateTime time;

	    @ApiModelProperty("课程封面链接")
	    private String titlePic;
	    
	    @ApiModelProperty("课程收藏数")
	    private int count;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Integer getTeacherId() {
			return teacherId;
		}

		public void setTeacherId(Integer teacherId) {
			this.teacherId = teacherId;
		}

		public String getIntro() {
			return intro;
		}

		public void setIntro(String intro) {
			this.intro = intro;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Double getPrice() {
			return price;
		}

		public void setPrice(Double price) {
			this.price = price;
		}

		public LocalDateTime getTime() {
			return time;
		}

		public void setTime(LocalDateTime time) {
			this.time = time;
		}

		public String getTitlePic() {
			return titlePic;
		}

		public void setTitlePic(String titlePic) {
			this.titlePic = titlePic;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public CourseDetail(Integer id, Integer teacherId, String intro, String title, Double price, LocalDateTime time,
				String titlePic, int count) {
			super();
			this.id = id;
			this.teacherId = teacherId;
			this.intro = intro;
			this.title = title;
			this.price = price;
			this.time = time;
			this.titlePic = titlePic;
			this.count = count;
		}

		public CourseDetail() {
			super();
		}

		@Override
		public String toString() {
			return "CourseDetail [id=" + id + ", teacherId=" + teacherId + ", intro=" + intro + ", title=" + title
					+ ", price=" + price + ", time=" + time + ", titlePic=" + titlePic + ", count=" + count + "]";
		}
	    

}
