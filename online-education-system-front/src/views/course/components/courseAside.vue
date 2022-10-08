<template>
    <div id="courseAside" class="courseAside_container">
        <div class="image">
            <img class="imageForm" :src="cover || require('@/assets/photo/course/default.gif')"
                :onerror='`this.src="${require("@/assets/photo/course/default.gif")}"`'>
        </div>
        <div class="title">
            <span class="action" @click="toCourse">{{ data.title }}</span>
        </div>
    </div>
</template>

<script>
import { req } from "@/api/request"
export default {
    name: "CourseAside",
    props: [
        'course_id'
    ],
    data() {
        return {
            data: [],
            cover: undefined
        }
    }, mounted() {
        this.getCourse();
    },
    methods: {
        toCourse() {
            this.$router.push({ path: '/course/' + this.course_id })
        },
        getCourse() {
            req('/course/' + this.course_id)
                .then((response) => {
                    if (response.code != 200) {
                        this.$message.error('获取失败')
                        return
                    }
                    this.data = response.data
                    this.cover = this.$ip + response.data.titlePic
                })
        }
    }
}
</script>

<style scoped>
.courseAside_container {
    padding: 20px;
    background-color: #FFF;
    border-radius: 15px;
    box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
}

.image {
    width: 100%;
}

.imageForm {
    width: 100%;
    height: 150px;
    border-radius: 10px;
}

.title {
    width: 100%;
    margin: 10px;
    overflow: hidden;
    font-size: 18px;
    line-height: 27px;
    letter-spacing: 2px;
}

.action {
    cursor: pointer;
    text-align: center;
}

.action:hover {
    color: #68cb9b;
    /* text-decoration: underline; */
}
</style>