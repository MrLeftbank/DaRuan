/**
 * @Title CourseServiceImpl.java
 * @author 皮龙霖
 * @description
 * @date 2022年7月5日上午8:46:00
 */
package com.sx.oesb.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sx.oesb.entity.*;
import com.sx.oesb.mapper.*;
import com.sx.oesb.service.CourseService;
import com.sx.oesb.service.UserService;
import com.sx.oesb.service.VodService;
import com.sx.oesb.util.PictureFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 皮龙霖
 * @ClassName CourseServiceImpl
 * @Description 实现课程——教师服务
 * @date 2022年7月5日 上午8:46:00
 */
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseMapper courseMapper;
    private final PurchaseMapper purchaseMapper;
    private final StarMapper starMapper;
    private final ChapterMapper chapterMapper;
    private final PictureFileUtil pictureFileUtil;
    private final VodService vodService;
    private final UserService userService;
    private final SectionMapper sectionMapper;


    @Autowired
    public CourseServiceImpl(CourseMapper courseMapper, PurchaseMapper purchaseMapper, StarMapper starMapper,
                             ChapterMapper chapterMapper, PictureFileUtil pictureFileUtil, VodService vodService,
                             UserService userService, SectionMapper sectionMapper) {
        super();
        this.courseMapper = courseMapper;
        this.purchaseMapper = purchaseMapper;
        this.starMapper = starMapper;
        this.chapterMapper = chapterMapper;
        this.pictureFileUtil = pictureFileUtil;
        this.vodService = vodService;
        this.userService = userService;
        this.sectionMapper = sectionMapper;
    }

    /**
     * @param pageSize
     * @param pageNum
     * @return
     * @Title checkAllCourse
     * @author 皮龙霖
     * @description 调用courseMapper.selectPage实现
     * @createdate 2022年7月5日 上午8:46:00
     * @see com.sx.oesb.service.CourseService#checkAllCourse(int, int)
     **/

    @Override
    public IPage<Course> checkAllCourse(int pageSize, int pageNum) {
        return courseMapper.selectPage(new Page<>(pageNum, pageSize), null);
    }

    /**
     * @param name
     * @param pageSize
     * @param pageNum
     * @param orderTag
     * @return
     * @Title searchCourse
     * @author 皮龙霖
     * @description 调用courseMapper.selectPage + QueryWrapper实现
     * @createdate 2022年7月7日 上午9:12:25
     * @see com.sx.oesb.service.CourseService#searchCourse(java.lang.String, int, int, int)
     **/
    @Override
    public IPage<Course> searchCourse(String name, int pageSize, int pageNum, int orderTag) {
//		按时间或价格排序
        if (orderTag <= 1) {
            return courseMapper.selectPage(new Page<>(pageNum, pageSize),
                    new QueryWrapper<Course>().like("title", name)
                            .orderByDesc("time"));
        } else if (orderTag == 2) {
            return courseMapper.selectPage(new Page<>(pageNum, pageSize),
                    new QueryWrapper<Course>().like("title", name)
                            .orderByDesc("price"));
        } else {
            return new Page<>(pageNum, pageSize);
        }
    }

    /**
     * @param c
     * @Title postCourse
     * @author 皮龙霖
     * @description 不进行验证，courseMapper.insert直接插入，默认在Controller已经完成teacherId检验。
     * @createdate 2022年7月5日 上午8:46:01
     * @see com.sx.oesb.service.CourseService#postCourse(com.sx.oesb.entity.Course)
     **/

    @Override
    public boolean postCourse(Course c) {
        c.setTime(LocalDateTime.now());
        return courseMapper.insert(c) > 0;
    }


    /**
     * @param course
     * @param user
     * @return
     * @Title buyCourse
     * @author 皮龙霖
     * @description 先建purchase对象，purchaseMapper.insert()成功后用户扣款
     * @createdate 2022年7月5日 下午4:13:41
     * @see com.sx.oesb.service.CourseService#buyCourse(com.sx.oesb.entity.Course, com.sx.oesb.entity.User)
     **/
    @Override
    public boolean buyCourse(Course course, User user) {
        Purchase purchase = new Purchase();
        purchase.setCourseId(course.getId());
        purchase.setUserId(user.getId());
        //获取系统时间
        purchase.setTime(LocalDateTime.now());
        if (purchaseMapper.insert(purchase) > 0) {

            //扣用户余额
            user.setBalance(user.getBalance() - course.getPrice());
            userService.modifyUser(user);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param s
     * @return
     * @Title starCourse
     * @author 皮龙霖
     * @description 设置star时间，不做其他检验，直接starMapper.insert
     * @createdate 2022年7月5日 上午9:08:04
     * @see com.sx.oesb.service.CourseService#starCourse(com.sx.oesb.entity.Star)
     **/
    @Override
    public boolean starCourse(Star s) {
        s.setTime(LocalDateTime.now());
        return starMapper.insert(s) > 0;
    }

    /**
     * @param courseId
     * @return
     * @Title checkAllChaptersOfCourse
     * @author 皮龙霖
     * @description 调用查chapterMapper.select + QueryWrapper实现
     * @createdate 2022年7月5日 上午8:46:01
     * @see com.sx.oesb.service.CourseService#checkAllChaptersOfCourse(int)
     **/

    @Override
    public List<Chapter> checkAllChaptersOfCourse(int courseId) {
        return chapterMapper.selectList(new QueryWrapper<Chapter>().eq("course_id", courseId).orderByAsc("number"));
    }


    /**
     * @param courseId
     * @return
     * @Title searchCourseById
     * @author 皮龙霖
     * @description 调用courseMapper.selectById
     * @createdate 2022年7月5日 上午9:35:01
     * @see com.sx.oesb.service.CourseService#searchCourseById(int)
     **/
    @Override
    public Course searchCourseById(int courseId) {
        return courseMapper.selectById(courseId);
    }

    /**
     * @param c
     * @return
     * @Title postChapterOfCourse
     * @author 皮龙霖
     * @description 设置课程后，调用chapterMapper.insert
     * @createdate 2022年7月5日 上午9:17:05
     * @see com.sx.oesb.service.CourseService#postChapterOfCourse(com.sx.oesb.entity.Chapter)
     **/
    @Override
    public int postChapterOfCourse(Chapter c) {
        chapterMapper.insert(c);
        return c.getId();
    }

    /**
     * @param chapterId
     * @return
     * @Title searchChapterById
     * @author 皮龙霖
     * @description 直接调用chapterMapper.selectById
     * @createdate 2022年7月5日 上午8:46:01
     * @see com.sx.oesb.service.CourseService#searchChapterById(int)
     **/

    @Override
    public Chapter searchChapterById(int chapterId) {
        return chapterMapper.selectById(chapterId);
    }


    /**
     * @param sectionId
     * @return
     * @Title searchSectionById
     * @author 皮龙霖
     * @description 直接调用sectionMapper.selectById
     * @createdate 2022年7月7日 下午2:59:59
     * @see com.sx.oesb.service.CourseService#searchSectionById(int)
     **/
    @Override
    public Section searchSectionById(int sectionId) {
        return sectionMapper.selectById(sectionId);
    }

    /**
     * @param chapterId
     * @return
     * @Title deleteChapterById
     * @author 皮龙霖
     * @description 直接调用chapterMapper.deleteById
     * @createdate 2022年7月5日 上午8:46:01
     * @see com.sx.oesb.service.CourseService#deleteChapterById(int)
     **/

    @Override
    public boolean deleteChapterById(int chapterId) {
        boolean flag = true;

//		删除章节对应各小节
        List<Section> sList = checkAllSectionOfChapter(chapterId);
        for (Section section : sList) {
            flag = flag && deleteSectionById(section.getId());
        }
        return chapterMapper.deleteById(chapterId) > 0;
    }

    /**
     * @param courseId
     * @return
     * @Title deleteCourseAndChapter
     * @author 皮龙霖
     * @description 调用了本类的checkAllChaptersOfCourse，以及deleteChapterById。最后courseMapper.deleteById
     * @createdate 2022年7月5日 上午8:46:01
     * @see com.sx.oesb.service.CourseService#deleteCourseAndChapter(int)
     **/

    @Override
    public boolean deleteCourseAndChapter(int courseId) {
        boolean flag = true;
//		删除课程标题图片
        pictureFileUtil.deleteImg(courseMapper.selectById(courseId).getTitlePic());

//		删除课程对应各章节
        List<Chapter> cList = checkAllChaptersOfCourse(courseId);
        for (Chapter chapter : cList) {
            flag = flag && deleteChapterById(chapter.getId());
        }
        return flag && courseMapper.deleteById(courseId) > 0;
    }


    /**
     * @param chapter
     * @return
     * @Title modifyChapter
     * @author 皮龙霖
     * @description 修改章节，直接chapterMapper.updateById
     * @createdate 2022年7月5日 上午9:29:30
     * @see com.sx.oesb.service.CourseService#modifyChapter(com.sx.oesb.entity.Chapter)
     **/
    @Override
    public boolean modifyChapter(Chapter chapter) {
        return chapterMapper.updateById(chapter) > 0;
    }


    /**
     * @param section
     * @return
     * @Title modifySection
     * @author 皮龙霖
     * @description 修改小节，直接sectionMapper.updateById
     * @createdate 2022年7月7日 下午3:06:13
     * @see com.sx.oesb.service.CourseService#modifySection(com.sx.oesb.entity.Section)
     **/
    @Override
    public boolean modifySection(Section section) {
        return sectionMapper.updateById(section) > 0;
    }


    /**
     * @param chapterId
     * @return
     * @Title getCourseOfChapter
     * @author 皮龙霖
     * @description 调用本类的searchChapterById和searchCourseById
     * @createdate 2022年7月5日 上午8:46:01
     * @see com.sx.oesb.service.CourseService#getCourseOfChapter(int)
     **/

    @Override
    public Course getCourseOfChapter(int chapterId) {
        int courseId = searchChapterById(chapterId).getCourseId();
        return searchCourseById(courseId);
    }


    /**
     * @param courseId
     * @return
     * @Title getUserIdOfCourse
     * @author 皮龙霖
     * @description 调用courseMapper.findTeacherofCourse
     * @createdate 2022年7月5日 上午8:46:01
     * @see com.sx.oesb.service.CourseService#getUserIdOfCourse(int)
     **/

    @Override
    public int getUserIdOfCourse(int courseId) {
        return courseMapper.findTeacherofCourse(courseId).getUserId();
    }

    /**
     * @param chapterId
     * @return
     * @Title checkAllSectionOfChapter
     * @author 皮龙霖
     * @description 调用查sectionMapper.select + QueryWrapper实现
     * @createdate 2022年7月7日 下午2:43:00
     * @see com.sx.oesb.service.CourseService#checkAllSectionOfChapter(int)
     **/
    @Override
    public List<Section> checkAllSectionOfChapter(int chapterId) {
        return sectionMapper.selectList(new QueryWrapper<Section>().eq("chapter_id", chapterId).orderByAsc("number"));
    }

    /**
     * @param s
     * @return
     * @Title postSectionOfChapter
     * @author 皮龙霖
     * @description 调用sectionMapper.insert，无验证
     * @createdate 2022年7月7日 下午2:45:29
     * @see com.sx.oesb.service.CourseService#postSectionOfChapter(com.sx.oesb.entity.Section)
     **/
    @Override
    public int postSectionOfChapter(Section s) {
        sectionMapper.insert(s);
        return s.getId();
    }

    /**
     * @param sectionId
     * @return
     * @Title deleteSectionById
     * @author 皮龙霖
     * @description 删除小节，附带删除视频
     * @createdate 2022年7月7日 下午2:50:48
     * @see com.sx.oesb.service.CourseService#deleteSectionById(int)
     **/
    @Override
    public boolean deleteSectionById(int sectionId) {
        Section section = sectionMapper.selectById(sectionId);
        if (section == null)
            return false;
        vodService.removeAliVideo(section.getVideo());
        return sectionMapper.deleteById(sectionId) > 0;
    }

    /**
     * @param courseId
     * @return
     * @Title getTeacherOfCourse
     * @author 皮龙霖
     * @description 调用courseMapper.findTeacherofCourse
     * @createdate 2022年7月7日 下午2:55:43
     * @see com.sx.oesb.service.CourseService#getTeacherOfCourse(int)
     **/
    @Override
    public Teacher getTeacherOfCourse(int courseId) {
        return courseMapper.findTeacherofCourse(courseId);
    }


    /**
     * @param sectionId
     * @return
     * @Title getChapterOfSection
     * @author 皮龙霖
     * @description 调用本类的searchSectionById和searchCourseById
     * @createdate 2022年7月7日 下午3:01:50
     * @see com.sx.oesb.service.CourseService#getChapterOfSection(int)
     **/
    @Override
    public Chapter getChapterOfSection(int sectionId) {
        int chapterId = searchSectionById(sectionId).getChapterId();
        return searchChapterById(chapterId);
    }

    /**
     * @param courseId
     * @return
     * @Title getStarOfCourse
     * @author 皮龙霖
     * @description starMapper.selectList加QueryWrapper条件查询
     * @createdate 2022年7月7日 下午3:07:54
     * @see com.sx.oesb.service.CourseService#getStarOfCourse(int)
     **/
    @Override
    public List<Star> getStarOfCourse(int courseId) {
        return starMapper.selectList(new QueryWrapper<Star>().eq("course_id", courseId));
    }

    /**
     * @return
     * @Title checkAllPurchase
     * @author 皮龙霖
     * @description 直接调用purchaseMapper.selectList
     * @createdate 2022年7月8日 上午10:22:29
     * @see com.sx.oesb.service.CourseService#checkAllPurchase()
     **/
    @Override
    public List<Purchase> checkAllPurchase() {
        return purchaseMapper.selectList(null);
    }


}
