<template>
    <div id="teacherCard" class="container" @click="toTeacher">
        <el-card :body-style="{ padding: '15px', height: '100%' }">
            <img :src="photo || require('@/assets/photo/teacher/default.jpg')"
                :onerror='`this.src="${require("@/assets/photo/teacher/default.jpg")}"`' class="image">
            <div style="padding: 14px;" class="name">
                <span class="name_form">
                    {{ data.teacherName }}
                </span>
            </div>
            <div class="teacher_info">
                <span class="info_form" :id="data.id">
                </span>
            </div>
        </el-card>
    </div>
</template>



<script>
import $ from 'jquery';

export default {
    name: 'TeacherCard',
    props: ['data'],
    data() {
        return {
            photo: this.$ip + this.data.teacherPhoto
        }
    },
    methods: {
        toTeacher() {
            this.$router.push({ path: '/teacher/' + this.data.id })
        },
    },
    mounted(){
        let t = document.getElementById(this.data.id);
        t.innerHTML = this.data.resume
    }
}
</script>

<style scoped>
.container {
    width: 250px;
    height: 320px;
    cursor: pointer;
    float: left;
    margin: 20px;
    word-break: break-all;
    word-wrap: break-word;
}

.el-card {
    width: 100%;
    height: 100%;
    border: none !important;
    box-shadow: none !important;
    border-radius: 15px;
}

.el-card:hover {
    border: 1px solid #68cb9b !important;
    box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.2) !important;
}

.el-card img {
    transition: .5s;
}

.el-card:hover img {
    transform: rotateY(180deg);
    transition: .5s;
}

.el-card:hover .name {
    color: #68cb9b;
}

.image {
    width: 140px;
    height: 140px;
    margin: 10px auto 0;
    display: block;
    border-radius: 50%;
    max-width: 100%;
    align-items: center;
}

.name {
    height: 25px;
    letter-spacing: 2px;
    text-align: center;
    font-size: 18px;
    overflow: hidden;
}

.name_form:hover {
    text-decoration: underline;
}

.teacher_info {
    display: block;
    height: 60px;
    line-height: 20px;
    border-top: 1px dotted #e2e2e2;
    padding-top: 15px;
    font-size: 12px;
    overflow: hidden;
    font-family: SimSun;
    color: #999;
}

.info_form {
    font-family: SimSun;
    line-height: 20px;
}
</style>