<template>
    <div id="teacherList" class="teacherList_container">
        <ul class="of">
            <el-empty class="empty" v-if="teacherList.length == 0" description="没有数据"></el-empty>
            <li v-for="(teacher, teacher_index) in teacherList" v-bind:key="teacher_index">
                <TeacherCard :data="teacher"></TeacherCard>
            </li>
        </ul>
        <div class="page">
            <el-pagination :hide-on-single-page="true" background layout="prev, pager, next, jumper, ->, total, slot"
                :page-size="8" :total="total" @current-change="getTeacherList" :current-page.sync="current_page">
            </el-pagination>
        </div>
    </div>
</template>

<script>
import TeacherCard from '@/components/TeacherCard.vue'
import { req } from "@/api/request"
export default {
    name: "TeacherList",
    components: {
        TeacherCard,
    },
    data() {
        return {
            teacherList: [],
            current_page: 1,
            total: 0,
            teacherShow: true,
        }
    },
    methods: {
        getTeacherList() {
            req('/teacher?pageNum=' + this.current_page)
                .then((response) => {
                    const { data } = response
                    this.total = data.total
                    this.teacherList = data.records
                })
        }
    },
    created() {
        this.getTeacherList();
    },
    watch: {
        courseList(newVal) {
            this.teacherShow = false
            if (true) {
                this.$nextTick(() => { // $nextTick 是在 DOM 更新循环结束之后执行延迟回调
                    this.teacherShow = true
                })
            }
        },
    }
}

</script>

<style scoped>
.teacherList_container {
    width: 1160px;
    margin: 30px auto;
    display: flex;
    flex-direction: column;
}


.of {
    text-align: left;
    min-height: 700px;
}

.page {
    margin: 0 auto;
    padding: 60px;
}

ol,
ul,
li {
    list-style: none;
}

.empty {
    margin-top: 205px;
}
</style>