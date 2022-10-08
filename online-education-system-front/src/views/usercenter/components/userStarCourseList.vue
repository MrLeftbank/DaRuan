<template>
    <div id="courseList" class="courseList_container">
        <div class="sorter_container">
            <div class="sorter_title">
                <h2 class="tac">
                    <span>收藏课程列表</span>
                </h2>
            </div>
            <el-divider class="divider"></el-divider>
        </div>
        <div class="c_container">
            <div class="courseList">
                <el-empty class="empty" v-if="courseList.length == 0" description="你没有收藏课程"></el-empty>
                <ul class="of" v-if="show">
                    <li v-for="(course, course_index) in courseList" v-bind:key="course_index">
                        <div class="starcardbutton">
                            <courseCard :data="course"></courseCard>
                            <el-button class="unStar el-icon-star-on" @click="deleteStar(course.id)">取消收藏</el-button>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <div class="page">
            <el-pagination :hide-on-single-page="true" background layout="prev, pager, next, jumper, ->, total, slot"
                :total="total" @current-change="changePage" :page-size="page_size" :current-page.sync="current_page">
            </el-pagination>
        </div>
    </div>
</template>

<script>
import courseCard from "@/components/CourseCard"
import { req } from '@/api/request'
export default {
    name: "userStarCourseList",
    data() {
        return {
            courseList: [],
            current_page: 1,
            page_size: 8,
            show: true,
            total: 0,
        }
    },
    components: {
        courseCard,
    },
    created() {
    },
    mounted() {
        this.getCourseList();
    },
    methods: {
        getCourseList() {
            req('user/star' + '?pageSize=' + this.page_size + '&pageNum=' + this.current_page)
                .then((res) => {
                    this.total = res.data.total;
                    this.courseList = res.data.records;
                })
        },
        changePage() {
            this.getCourseList();
        },
        async deleteStar(courseId) {
            if (!(await this._box())) return;
            req('user/star/' + courseId, 'delete')
                .then((res) => {
                    // console.log(res)
                    this.getCourseList();
                })
        },
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
.courseList_container {
    display: flex;
    flex-direction: column;
    height: 100%;
}

.of {
    display: grid;
    grid-template-columns: 1fr 1fr 1fr 1fr;
    grid-template-rows: 1fr;
}

.sorter_container {
    margin: 0 auto;
}

.c_container {
    margin: 0 auto;
    min-height: 680px;
}

.starcardbutton {
    display: flex;
    -webkit-flex-direction: column;
    flex-direction: column;
}

.page {
    margin: 0 auto;
    padding: 20px;
}

.sorter_title {
    overflow: hidden;
    clear: both;
    margin: 40px 0 30px;
    display: block;
    margin-bottom: -15px;
}

.tac {
    text-align: center;
    float: left;
    font-size: 26px;
}

.divider {
    margin: 0;
    margin-top: 24px;
}


ol,
ul,
li {
    list-style: none;
}


.unStar {
    width: 250px;
    margin: 0 auto;
    color: #68cb9b;
    border-radius: 20px;
    font-size: 20px;
    border-color: #68cb9b;
}

.empty {
    margin-top: 180px;
}
</style>