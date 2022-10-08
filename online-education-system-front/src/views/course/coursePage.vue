<template>
    <div class="pageContainer">
        <div class="header">
            <div>
                <img :src="cover || require('@/assets/photo/course/default.gif')"
                    :onerror='`this.src="${require("@/assets/photo/course/default.gif")}"`' id=" pic" class="cover">
            </div>
            <div class="content">
                <div id="title">
                    {{ course.title }}
                </div>
                <div>
                    价格:
                    <span id="price" text="Price" :class="isFree ? 'Green' : ''">
                        {{ course.price }}元
                    </span>
                </div>
                <el-divider></el-divider>
                <div id="teacher">
                    授课老师：{{ teacher.teacherName }}
                </div>
                <div id="star">
                    <el-button v-if="!isStar" type="warning" icon="el-icon-star-off" @click="star">收藏</el-button>
                    <el-button v-if="isStar" type="warning" icon="el-icon-star-on" @click="unStar">已收藏</el-button>
                </div>
                <div id="learn">
                    <div>
                        <el-button v-if="isBuy" type="success" @click="toChapter">立刻观看</el-button>
                    </div>
                    <div>
                        <el-button v-if="!isBuy" type="success" @click="toBuy">立刻购买</el-button>
                    </div>
                </div>
            </div>
        </div>

        <div id="info">
            <div id="details">
                <h2>
                    课程详情
                </h2>
                <el-divider></el-divider>
                <span id="intro">
                    {{ course.intro }}
                </span>
            </div>
            <div id="Aside">
                <h2>
                    主讲老师:
                </h2>
                <el-divider></el-divider>
                <div class="teacher" @click="toTeacher">
                    <img class="image" :src="photo || require('@/assets/photo/teacher/default.jpg')"
                        :onerror='`this.src="${require("@/assets/photo/teacher/default.jpg")}"`'>
                    <span>
                        {{ teacher.teacherName }}
                    </span>
                </div>
                <el-divider class="bottom"></el-divider>
            </div>
        </div>
    </div>
</template>

<script>
import { req } from '@/api/request'
import store from "@/store"
export default {
    name: 'coursePage',
    props: [
        'course_id'
    ],
    data() {
        return {
            course: [],
            teacher: [],
            isBuy: false,
            isStar: false,
            isFree: false,
            photo: '',
            cover: ''
        };
    },

    components: {
    },
    created() {
        this.getDetail()
        this.setIsBuy()
        this.setIsStar()
    },
    mounted() { },
    watch: {
        teacher() {
            if (store.getters.teacherId == this.teacher.id) this.isBuy = true
        }
    },
    methods: {
        isLogin() {
            if (store.getters.isLogin) return true
            this.$router.push(`/login?redirect=` + this.$route.fullPath)
        },
        getDetail() {
            req('/course/' + this.course_id).then((res) => {
                this.course = res.data
                this.cover = this.$ip + this.course.titlePic
                let t1 = document.getElementById('intro')
                t1.innerHTML = this.course.intro
                if (this.course.price === 0) {
                    this.isFree = true
                    let t = document.getElementById("price")
                    t.innerText = "免费"
                }
                this.getTeacher()
            })
        },
        getTeacher() {
            req('/teacher/' + this.course.teacherId).then((res) => {
                this.teacher = res.data
                this.photo = this.$ip + this.teacher.teacherPhoto
            })
        },
        setIsBuy() {
            if (store.getters.isLogin)
                req('/course/' + this.course_id + '/is-purchase')
                    .then((res) => {
                        this.isBuy = res.data
                    })
        },
        setIsStar() {
            if (store.getters.isLogin)
                req('/course/' + this.course_id + '/is-star')
                    .then((res) => {
                        this.isStar = res.data
                    })
        },
        star() {
            if (this.isLogin()) {
                req('/course/star', 'post', { 'courseId': this.course_id })
                    .then((res) => {
                        this.isStar = true
                        this.$message({
                            message: "收藏成功",
                            type: 'success'
                        })
                    })
            }

        },
        unStar() {
            req('/user/star/' + this.course_id, 'delete')
                .then((res) => {
                    this.isStar = false
                    this.$message({
                        message: "取消收藏",
                        type: 'success'
                    })
                })
        },
        toChapter() {
            this.$router.push({ path: '/course/' + this.course_id + '/chapterPage' })
        },
        async toBuy() {
            if (this.isLogin()) {
                if (!(await this._buy())) return;
                req('/course/purchase?courseId=' + this.course_id, 'post')
                    .then(res => {
                        if (res.code == 604) {
                            this.$message({
                                message: res.message,
                                type: 'error',
                            })
                        } else if (res.code != 200) return;
                        else {
                            this.$message({
                                message: "购买成功",
                                type: 'success',
                            })
                            this.isBuy = true;
                        }
                    })
            }
        },
        toTeacher() {
            this.$router.push('/teacher/' + this.teacher.id)
        }
    }
}
</script>

<style scoped>
.pageContainer {
    width: 1160px;
    margin: 0 auto;
}

.header {
    width: 100%;
    background: #FFF;
    box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
    margin-top: 30px;
    padding: 25px;
    border-radius: 10px;
    display: flex;
}

.content {
    padding: 20px;
    width: 100%;
}

#title {
    font-size: 28px;
    height: 60px;
    line-height: 30px;
    margin-bottom: 10px;
}

#price {
    margin-left: 8px;
    font-size: 30px;
    color: red;
}

#star {
    margin-top: 20px;
    margin-bottom: 20px;
}

#learn {
    margin-top: 10px;
}

#info {
    width: 1210px;
    display: flex;
    align-items: flex-start
}

#details {
    width: 80%;
    background: #FFF;
    box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
    margin-top: 30px;
    margin-right: 30px;
    padding: 25px;
    border-radius: 10px;
    min-height: 300px;
    margin-bottom: 30px;
}

#intro {
    padding: 10px;
}

#Aside {
    width: 20%;
    background: #FFF;
    box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
    margin-top: 30px;
    padding: 25px;
    border-radius: 10px;
    min-height: 100px;
}

.teacher {
    display: flex;
    align-items: center;
    cursor: pointer;
}

.image {
    border-radius: 50%;
    height: 50px;
    width: 50px;
    margin-right: 10px;
}

.bottom {
    margin-bottom: 5px;
}

.Green {
    color: #00CC7E !important;
}

.cover {
    width: 650px;
    height: 370px;
}
</style>