<template>
    <div id="home-page" class="home_container">
        <div>
            <el-carousel height="600px">
                <el-carousel-item v-for="item in banner" :key="item.id">
                    <img class="image" :src="ip + item.titlePic"
                        :onerror='`this.src="${require("@/assets/photo/course/default.gif")}"`' @click="toCourse(item.id)">
                </el-carousel-item>
            </el-carousel>
        </div>
        <div class="title">
            <span>
                热门课程
            </span>
        </div>
        <div class="hot-course">
            <ul class="of" id="bna">
                <el-empty v-if="courseList.length == 0" description="没有数据"></el-empty>
                <li v-for="(course, course_index) in courseList" v-bind:key="course_index">
                    <CourseCard :data="course"></CourseCard>
                </li>
            </ul>
        </div>
        <div class="title">
            <el-button @click="() => { this.$router.push('/course'); }" class="btn">全部课程</el-button>
        </div>

        <div class="title">
            <span>
                热门教师
            </span>
        </div>
        <div class="hot-teacher">
            <ul class="of" id="bna">
                <el-empty v-if="teacherList.length == 0" description="没有数据"></el-empty>
                <li v-for="(teacher, teacher_index) in teacherList" v-bind:key="teacher_index">
                    <div>
                        <TeacherCard :data="teacher"></TeacherCard>
                    </div>
                </li>
            </ul>
        </div>
        <div class="title">
            <el-button @click="() => { this.$router.push('/teacher'); }" class="btn">全部教师</el-button>
        </div>
    </div>
</template>



<script>
import CourseCard from '@/components/CourseCard.vue'
import TeacherCard from '@/components/TeacherCard.vue'
import { req } from '@/api/request'
export default {
    name: 'home',
    data() {
        return {
            courseList: [],
            teacherList: [],
            banner: [],
            ip: this.$ip,
        }
    },
    components: {
        CourseCard,
        TeacherCard
    },
    created() {
        this.getCourseList();
        this.getTeacherList();
        this.getBanner();
    },
    methods: {
        getCourseList() {
            req('/index/course').then((res) => {
                this.courseList = res.data
            })
        },
        getBanner() {
            req('/index/course').then((res) => {
                let num = 0
                res.data.forEach(element => {
                    if (num < 4) {
                        this.banner.push(element)
                        num++
                    }
                });
            })
        },
        getTeacherList() {
            req('/index/teacher').then((res) => {
                this.teacherList = res.data
            })
        },
        toCourse(id){
            this.$router.push('/course/'+id)
        }
    }
}
</script>

<style scoped>
.home_container {
    display: flex;
    flex-direction: column;
    margin: 0 auto;
}

.of {
    list-style: none;
    text-align: left;
}

.hot-course {
    width: 1160px;
    margin: 0px auto;
    align-items: center;
}

.hot-teacher {
    width: 1160px;
    margin: 0px auto 0px auto;
    align-items: center;
}

.title {
    margin: 0 auto;
    padding: 40px;
    font-size: 40px;
}

.btn {
    color: #68cb9b;
    border-radius: 20px;
    width: 160px;
    font-size: 20px;
    border-color: #68cb9b;
    /* line-height: 36px; */
}

.el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 150px;
}

.el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
}

.image {
    width: 1065px;
    height: 600px;
    cursor: pointer;
}

::v-deep .el-carousel__item{
    display: flex;
    justify-content: center;
}
</style>