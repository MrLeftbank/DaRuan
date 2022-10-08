/**
 * @Title UserTeacherController.java
 * @author 皮龙霖
 * @description
 * @date 2022年7月4日下午2:43:27
 */
package com.sx.oesb.controller;

import com.alibaba.fastjson.JSON;
import com.sx.oesb.entity.*;
import com.sx.oesb.service.CourseService;
import com.sx.oesb.service.DomainService;
import com.sx.oesb.service.TeacherService;
import com.sx.oesb.service.VodService;
import com.sx.oesb.util.PictureFileUtil;
import com.sx.oesb.util.R;
import com.sx.oesb.util.ResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author 皮龙霖
 * @ClassName UserTeacherController
 * @Description 用户可以申请成为教师，此为教师申请与教师个人信息相关控制器
 * @date 2022年7月4日 下午2:43:27
 */
@Api(tags = "用户教师")
@RestController
public class UserTeacherController {


    private final VodService vodService;
    private final PictureFileUtil pictureFileUtil;
    private final TeacherService teacherService;
    private final CourseService courseService;
    private final DomainService domainService;


    @Autowired
    public UserTeacherController(VodService vodService, PictureFileUtil pictureFileUtil, TeacherService teacherService,
                                 CourseService courseService, DomainService domainService) {
        super();
        this.vodService = vodService;
        this.pictureFileUtil = pictureFileUtil;
        this.teacherService = teacherService;
        this.courseService = courseService;
        this.domainService = domainService;
    }


    @ApiOperation("教师的所有课程")
    @GetMapping("/teacher/course")
    public R courseOfUserTeacher(@AuthenticationPrincipal User user, @RequestParam(defaultValue = "0") int pageNum, @RequestParam(defaultValue = "8") int pageSize) {
        R responseBody = new R();

        Teacher teacher = teacherService.getTeacherOfUser(user.getId());

        //如果用户不是teacher
        if (teacher == null)
            return responseBody.error(ResponseEnum.USER_NOT_TEACHER);
        return responseBody.success().data(teacherService.checkCourseOfTeacher(teacher.getId(), pageSize, pageNum));
    }


    /**
     * @param user
     * @param t
     * @return R
     * @Title postNewTeacher
     * @author 皮龙霖
     * @description 登录用户可以上传教师信息，形式是一个Teacher对象
     * @createdate 2022年7月4日 下午4:36:46
     **/
    @ApiOperation("提交教师申请")
    @PostMapping("/teacher")
    public R postNewTeacher(@AuthenticationPrincipal User user, @RequestBody(required = true) Teacher t) {
        R responseBody = new R();

        //确保用户建自己的教师。
        t.setUserId(user.getId());
        //教师初始未认证
        t.setStatus(0);
        t.setId(null);
        
        //不能重复提交教师信息
        Teacher teacher = teacherService.getTeacherOfUser(user.getId());
        if(teacher != null)
        	return responseBody.error(ResponseEnum.REPEATED);
        
        if (teacherService.postTeacherInfo(t))
            return responseBody.success();
        else
            return responseBody.error(ResponseEnum.SERVICE_FAIL);
    }

    /**
     * @param user
     * @return R
     * @Title getUserTeacher
     * @author 皮龙霖
     * @description 用户查看自己的教师信息
     * @createdate 2022年7月13日 上午11:28:18
     **/
    @ApiOperation("用户查看自己教师")
    @GetMapping("/user/teacher")
    public R getUserTeacher(@AuthenticationPrincipal User user) {
        R responseBody = new R();
        Teacher teacher = teacherService.getTeacherOfUser(user.getId());
        if (teacher == null)
            return responseBody.error(ResponseEnum.USER_NOT_TEACHER);
        else
            return responseBody.success().data(teacher);
    }

    /**
     * @param user
     * @param c
     * @return R
     * @Title postNewCourse
     * @author 皮龙霖
     * @description 教师提交课程，检验教师身份
     * @createdate 2022年7月5日 上午10:14:39
     **/
    @ApiOperation("教师提交课程")
    @PostMapping("/course")
    public R postNewCourse(@AuthenticationPrincipal User user,
                           @RequestPart(value = "course") String course,
                           @RequestPart(value = "domain") String domain) {
        R responseBody = new R();
        Course c = JSON.parseObject(course, Course.class);
        List<Integer> domainIds = JSON.parseArray(domain, Integer.class);
        Teacher teacher = teacherService.getTeacherOfUser(user.getId());
        c.setTeacherId(teacher.getId());
        if (courseService.postCourse(c)) {
            for (Integer integer : domainIds) {
                domainService.addCourseDomain(integer, c.getId());
            }
            return responseBody.success();
        } else {
            return responseBody.error(ResponseEnum.SERVICE_FAIL);
        }
//
//        //如果用户不是teacher
//        if (teacher == null)
//            return responseBody.error(ResponseEnum.USER_NOT_TEACHER);
//
//        //检验课程信息
//        c.setTeacherId(teacher.getId());
//        c.setId(null);
//
//        if (courseService.postCourse(c))
//            return responseBody.success();
//        else {
//            return responseBody.error(ResponseEnum.SERVICE_FAIL);
//        }
    }


    /**
     * @param user
     * @param course
     * @param domain
     * @param pic
     * @return R
     * @Title postCourseWithDomain
     * @author 皮龙霖
     * @description 教师提交课程和类别，检验教师身份，自动为课程加上类别。
     * @createdate 2022年7月13日 上午11:29:05
     **/
    @ApiOperation("教师提交课程及其类别")
    @PostMapping("/course-domain")
    public R postCourseWithDomain(@AuthenticationPrincipal User user,
                                  @RequestPart(value = "course") String course,
                                  @RequestPart(value = "domain") String domain,
                                  @RequestPart(value = "pic") MultipartFile pic) {
        R responseBody = new R();

        Teacher teacher = teacherService.getTeacherOfUser(user.getId());
        //如果用户不是teacher
        if (teacher == null)
            return responseBody.error(ResponseEnum.USER_NOT_TEACHER);

        Course c = JSON.parseObject(course, Course.class);

        List<Integer> domainIds = JSON.parseArray(domain, Integer.class);

        //检验课程信息
        c.setTeacherId(teacher.getId());
        c.setId(null);


        //文件课件上传
        String picUrl = "";
        try {
            picUrl = pictureFileUtil.uploadImage(pic);
        } catch (IOException e) {
            responseBody.error(ResponseEnum.SERVICE_FAIL);
            //自动生成的 catch 块
            e.printStackTrace();
        }
        //文件后缀不合格式，上传失败
        if (picUrl.equals(""))
            return responseBody.error(ResponseEnum.BAD_REQUEST);
        c.setTitlePic(picUrl);

        if (courseService.postCourse(c)) {
            for (Integer integer : domainIds) {
                domainService.addCourseDomain(integer, c.getId());
            }
            return responseBody.success();
        } else {
            return responseBody.error(ResponseEnum.SERVICE_FAIL);
        }
    }

    /**
     * @param user
     * @param c
     * @param courseId
     * @return R
     * @Title postNewChapter
     * @author 皮龙霖
     * @description 教师提交课程章节，检验教师身份，检验本人课程
     * @createdate 2022年7月5日 上午10:15:15
     **/
    @ApiOperation("教师提交章节")
    @PostMapping("/course/{cId}/chapter")
    public R postNewChapter(@AuthenticationPrincipal User user, @RequestBody(required = true) Chapter c, @PathVariable(name = "cId", required = true) int courseId) {
        R responseBody = new R();

        Teacher teacher = teacherService.getTeacherOfUser(user.getId());

        //如果用户不是teacher
        if (teacher == null)
            return responseBody.error(ResponseEnum.USER_NOT_TEACHER);

        //如果不是本人课程
        if (courseService.getUserIdOfCourse(courseId) != user.getId())
            return responseBody.error(ResponseEnum.USER_OTHER);

        //保证对应课程
        c.setCourseId(courseId);
        responseBody.data(courseService.postChapterOfCourse(c));
        return responseBody.success();
//        if (courseService.postChapterOfCourse(c))
//            return responseBody.success();
//        else {
//            return responseBody.error(ResponseEnum.SERVICE_FAIL);
//        }
    }


    /**
     * @param user
     * @param chapterId
     * @param title
     * @param number
     * @param file
     * @param file1
     * @return R
     * @Title postNewSection
     * @author 皮龙霖
     * @description 教师提交小节，检验教师身份，集成课件和视频上传。
     * @createdate 2022年7月13日 上午11:29:37
     **/
    @ApiOperation("教师提交小节")
    @PostMapping("/chapter/{cId}/section")
    public R postNewSection(@AuthenticationPrincipal User user, @PathVariable(name = "cId", required = true) int chapterId,
                            @RequestPart("title") String title,
                            @RequestPart("number") String number,
                            @RequestPart(name = "file", required = false) MultipartFile file,
                            @RequestPart(name = "doc", required = false) MultipartFile file1) {
        R responseBody = new R();


        Teacher teacher = teacherService.getTeacherOfUser(user.getId());

        //如果用户不是teacher
        if (teacher == null)
            return responseBody.error(ResponseEnum.USER_NOT_TEACHER);

        //如果不是本人课程
        if (courseService.getUserIdOfCourse(courseService.getCourseOfChapter(chapterId).getId()) != user.getId())
            return responseBody.error(ResponseEnum.USER_OTHER);

        //新建小节，保证对应章节
        Section section = new Section();
        section.setChapterId(chapterId);
        section.setTitle(title);
        section.setNumber(Integer.parseInt(number));

        if(file1 != null) {
            //文件课件上传
            String docUrl = "";
            try {
                docUrl = pictureFileUtil.uploadDoc(file1);
            } catch (IOException e) {
                responseBody.error(ResponseEnum.SERVICE_FAIL);
                //自动生成的 catch 块
                e.printStackTrace();
            }
            //文件后缀不合格式，上传失败
            if (docUrl.equals(""))
                return responseBody.error(ResponseEnum.BAD_REQUEST);
            section.setCourseware(docUrl);
        }

        if (file != null) {
            //上传视频
            String videoId = vodService.uploadAliVideo(file);
            //视频上传失败
            if (videoId == null)
                return responseBody.error(ResponseEnum.SERVICE_FAIL);
            section.setVideo(videoId);
        }

        responseBody.data(courseService.postSectionOfChapter(section));
        return responseBody.success();
//        if (courseService.postSectionOfChapter(section))
//            return responseBody.success();
//        else {
//            return responseBody.error(ResponseEnum.SERVICE_FAIL);
//        }
    }

    /**
     * @param user
     * @param c
     * @param courseId
     * @return R
     * @Title changeChapter
     * @author 皮龙霖
     * @description 教师修改课程章节，检验教师身份，验证章节父课程，检验本人课程
     * @createdate 2022年7月13日 上午11:30:05
     **/
    @ApiOperation("教师修改章节")
    @PutMapping("/course/{cId}/chapter")
    public R changeChapter(@AuthenticationPrincipal User user, @RequestBody(required = true) Chapter c, @PathVariable(name = "cId", required = true) int courseId) {
        R responseBody = new R();

        Teacher teacher = teacherService.getTeacherOfUser(user.getId());

        //如果用户不是teacher
        if (teacher == null)
            return responseBody.error(ResponseEnum.USER_NOT_TEACHER);

        //如果不是章节课程
        if (courseService.getCourseOfChapter(c.getId()).getId() != courseId)
            return responseBody.error(ResponseEnum.SERVICE_FAIL);

        //如果不是本人课程
        if (courseService.getUserIdOfCourse(courseId) != user.getId())
            return responseBody.error(ResponseEnum.USER_OTHER);


        if (courseService.modifyChapter(c))
            return responseBody.success();
        else {
            return responseBody.error(ResponseEnum.SERVICE_FAIL);
        }
    }


    /**
     * @param user
     * @param chapterId
     * @return R
     * @Title deleteChapter
     * @author 皮龙霖
     * @description 教师删除课程章节，检验教师身份，验证章节父课程，检验本人课程
     * @createdate 2022年7月13日 上午11:30:22
     **/
    @ApiOperation("教师删除章节")
    @DeleteMapping("chapter/{cId}")
    public R deleteChapter(@AuthenticationPrincipal User user, @PathVariable(name = "cId", required = true) int chapterId) {
        R responseBody = new R();

        Teacher teacher = teacherService.getTeacherOfUser(user.getId());

        //如果用户不是teacher
        if (teacher == null)
            return responseBody.error(ResponseEnum.USER_NOT_TEACHER);

        //如果课程不存在
        Course course = courseService.getCourseOfChapter(chapterId);
        if (course == null)
            return responseBody.error(ResponseEnum.NOT_FOUND);

        //如果不是本人课程
        if (courseService.getUserIdOfCourse(course.getId()) != user.getId())
            return responseBody.error(ResponseEnum.USER_OTHER);


        if (courseService.deleteChapterById(chapterId))
            return responseBody.success();
        else {
            return responseBody.error(ResponseEnum.SERVICE_FAIL);
        }
    }

    /**
     * @param user
     * @param section
     * @param chapterId
     * @return R
     * @Title deleteSection
     * @author 皮龙霖
     * @description 教师删除章节小节，检验教师身份，验证章节父课程，检验本人课程
     * @createdate 2022年7月7日 下午3:35:22
     **/
    @ApiOperation("教师删除小节")
    @DeleteMapping("section/{sId}")
    public R deleteSection(@AuthenticationPrincipal User user, @PathVariable(name = "sId", required = true) int sectionId) {
        R responseBody = new R();

        Teacher teacher = teacherService.getTeacherOfUser(user.getId());

        //如果用户不是teacher
        if (teacher == null)
            return responseBody.error(ResponseEnum.USER_NOT_TEACHER);

        //如果章节不存在
        Chapter chapter = courseService.getChapterOfSection(sectionId);
        if (chapter == null)
            return responseBody.error(ResponseEnum.NOT_FOUND);

        //如果课程不存在
        Course course = courseService.getCourseOfChapter(chapter.getId());
        if (course == null)
            return responseBody.error(ResponseEnum.NOT_FOUND);

        //如果不是本人课程
        if (courseService.getUserIdOfCourse(course.getId()) != user.getId())
            return responseBody.error(ResponseEnum.USER_OTHER);


        if (courseService.deleteSectionById(sectionId))
            return responseBody.success();
        else {
            return responseBody.error(ResponseEnum.SERVICE_FAIL);
        }
    }

}
