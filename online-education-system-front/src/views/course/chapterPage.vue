<template>
    <div id="chapterPage" class="chapterPage_container">
        <div class="courseInfo">
            <div id="courseInfo" class="">
                <courseAside :course_id="course_id"></courseAside>
            </div>
        </div>
        <div>
            <div class="chapterInfo">
                <el-collapse v-model="activeNames" @change="" class="chapter_item">
                    <div class="h">
                        课件
                    </div>
                    <el-collapse-item :title="Chapter.title" v-for="(Chapter, index) in ChapterList" :key="index"
                        :name="index" class=" item_form">
                        <sectionForm :chapter_id="Chapter.id" :course_id="course_id"></sectionForm>
                    </el-collapse-item>
                </el-collapse>
            </div>
        </div>
    </div>
</template>

<script>
import sectionForm from "./components/sectionForm"
import courseAside from "./components/courseAside"
import NProgress from 'nprogress'
import { req } from "@/api/request"
import store from "@/store"
import $ from "jquery"
export default {
    name: "chapterPage",
    props: [
        'course_id'
    ],
    components: {
        sectionForm,
        courseAside,
    },
    data() {
        return {
            activeNames: 0,
            ChapterList: [],
        };
    },
    methods: {
        getChapter() {
            req('/course/' + this.course_id + '/chapter/')
                .then((response) => {
                    if (response.code != 200) {
                        this.$message.error('获取失败')
                        return
                    }
                    // console.log(response)
                    this.ChapterList = response.data
                })
        },
        handleScroll(e) {
            let scrollTop =
                document.documentElement.scrollTop || document.body.scrollTop;
            if (scrollTop > 91) {
                $('#courseInfo').addClass('courseInfo_position')
            } else $('#courseInfo').removeClass('courseInfo_position')
        },

    },
    mounted() {
        this.getChapter()
        this.handleScroll()
        window.addEventListener("scroll", this.handleScroll, true);
    },
    beforeRouteEnter(to, from, next) {
        console.log(store.getters)
        if (store.getters.teacherId) {
            next();
            NProgress.done()
            return
        }
        req('/course/' + to.params.course_id + '/is-purchase')
            .then((response) => {
                if (response.data) {
                    next();
                    NProgress.done()
                }
                else next(_this => {
                    NProgress.done()
                    _this.$router.push(from.path)
                    _this.$message({
                        message: "请购买课程",
                        type: 'warning',
                    })
                })
                NProgress.done()
            }).catch((error) => {
                next(_this => {
                    NProgress.done()
                    _this.$router.push(from.path)
                    _this.$message({
                        message: error,
                        type: 'error',
                    })
                })
                NProgress.done()
            })
        NProgress.done()
        // 此处处理在组件渲染之前，先跳转到其他地址
        // 注意next之前不能调用this获取实例
        // next(_this=>{
        //   console.log(_this);//此处_this即为组件实例
        // });
    },
}
</script>

<style scoped>
.chapterPage_container {
    width: 1500px;
    margin: 15px auto;
    display: flex;
    justify-content: space-evenly;
}

.chapter_item {
    margin: 20px;
    padding: 20px;
}

.item_form {
    padding: 8px;
}


.chapterInfo {
    width: 1160px;
    background-color: #FFF;
    border-radius: 15px;
    box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
}

.courseInfo {
    width: 270px;
    margin-top: 20px;
}

.courseInfo_position {
    width: 270px;
    top: 35px;
    position: fixed;
}

::v-deep .el-collapse-item__header {
    border: 1px solid #EBEEF5;
    padding-left: 15px;
    background: #fafafa;
    font-size: 18px;
    border-radius: 5px;
}

::v-deep .el-collapse-item__header:hover {
    border: 1px solid #68cb9b;
    box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.2);
}

::v-deep .el-collapse-item__content {
    margin-bottom: 0px;
    padding-bottom: 12px;
}

::v-deep .el-collapse-item__wrap {
    border-bottom: #FFF;
}

::v-deep .el-collapse {
    border: #FFF;
}

.h {
    margin-bottom: 30px;
    padding: 8px;
    font-size: 25px;
    letter-spacing: 10px;
}
</style>