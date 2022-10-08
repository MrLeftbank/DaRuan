 <template>
    <div class="teacherContainer">
        <div>
            <h1>讲师介绍</h1>
        </div>
        <el-divider></el-divider>
        <div class="teacher">
            <div class="img-container">
                <img :src="photo || require('@/assets/photo/teacher/default.jpg')"
                    :onerror='`this.src="${require("@/assets/photo/teacher/default.jpg")}"`' class="image">
            </div>
            <div class="teacherInfo">
                <div class="teacherName">
                    {{ teacher.teacherName }}
                </div>
                <div id="teacherResume" class="teacherResume">
                    {{ teacher.resume }}
                </div>
            </div>

        </div>

        <div class="teacherCourse">
            <h1>教授课程</h1>
        </div>
        <el-divider></el-divider>
        <div class="teacher-course">
            <div>
                <ul class="of" v-if="show">
                    <el-empty class="empty" v-if="courseList.length == 0" description="该教师没有发布课程"></el-empty>
                    <li v-for="(course, course_index) in courseList" v-bind:key="course_index">
                        <CourseCard :data="course" class="ol"></CourseCard>
                    </li>
                </ul>
            </div>
            <div class="page">
                <el-pagination :hide-on-single-page="true" background
                    layout="prev, pager, next, jumper, ->, total, slot" :page-size="8" :total="total"
                    @current-change="getCourse" :current-page.sync="current_page">
                </el-pagination>
            </div>
        </div>

    </div>
</template>

<script>

import { req } from '@/api/request'
import CourseCard from '@/components/CourseCard.vue'

export default {
    name: "TeacherDetail",
    props: [
        'teacher_id'
    ],
    components: { CourseCard },
    methods: {

    },
    data() {
        return {
            teacher: {},
            courseList: [],
            current_page: 1,
            total: 0,
            photo: undefined,
            show: true,
        }
    },
    methods: {
        getTeacher() {
            req('/teacher/' + this.teacher_id)
                .then((response) => {
                    this.teacher = response.data
                    this.photo = this.$ip + this.teacher.teacherPhoto
                    let t = document.getElementById('teacherResume')
                    t.innerHTML = response.data.resume
                })
        },
        getCourse() {
            req('/teacher/' + this.teacher_id + '/course?pageNum=' + this.current_page)
                .then((response) => {
                    this.total = response.data.total
                    this.courseList = response.data.records
                });
        }
    },
    created() {
        this.getTeacher()
        this.getCourse()
    },
    watch: {
        courseList(newVal) {
            this.show = false
            if (true) {
                this.$nextTick(() => { // $nextTick 是在 DOM 更新循环结束之后执行延迟回调
                    this.show = true
                })
            }
        },
    }

}
</script>

<style scoped>
.teacherContainer {
    margin: 35px auto;
    width: 1260px;
}

.teacher {
    display: flex;
}

.img-container {
    width: 320px;
    height: 320px;
    margin: 10px 30px;
    background-color: #FFF;
    border: 1px solid #f0f0f0;
    box-shadow: 5px 5px 0 rgb(0 0 0 / 4%);
}

.image {
    width: 290px;
    height: 290px;
    margin: 15px 15px;
    background: white;
}

.teacherInfo {
    margin-top: 15px;
}

.teacherName {
    font-size: 25px;
    height: 60px;
    line-height: 30px;
}

.teacherResume {
    margin-top: 20px;
    width: 700px;
}

.teacherCourse {
    margin-top: 40px;
}

li,
ul {
    list-style: none;
}

.teacher-course {
    display: flex;
    flex-direction: column;
}

.page {
    margin: 0 auto;
    padding: 60px;
}
</style>