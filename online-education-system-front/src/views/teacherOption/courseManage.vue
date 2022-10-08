<template>
    <div class="CMcontainer">
        <div class="header">
            <h2>我的课程</h2>
            <el-button @click="() => { this.$router.push('/courseManage/addCourse/formation'); }">发布课程</el-button>
        </div>
        <div>
            <!-- <el-empty class="empty" description="没有课程"></el-empty> -->
            <ul>
                <li v-for="item in courseList">
                    <el-divider class="divider"></el-divider>
                    <Card :data="item"></Card>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
import Card from "./components/courseManageCard"
import { req } from "@/api/request"
export default {
    name: "courseManage",
    components: {
        Card
    },
    data() {
        return {
            courseList:[],
        }
    },
    methods: {
        getMyCourses() {
            req('/teacher/course')
                .then((response) => {
                    // console.log(response);
                    this.courseList = response.data.records
                })
        }
    },
    created() { this.getMyCourses() }
}
</script>

<style scoped>
.CMcontainer {
    width: 1160px;
    background-color: #FFF;
    margin: 30px auto;
    border-radius: 15px;
    min-height: 800px;
    border: 1px solid #f0f0f0;
    box-shadow: 5px 5px 0 rgb(0 0 0 / 4%);
}

.header {
    padding: 20px 30px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.divider {
    margin: 0 auto;
    width: 95%
}

.empty {
    margin: 200px 0;
}

ul li {
    list-style: none;
}
</style>