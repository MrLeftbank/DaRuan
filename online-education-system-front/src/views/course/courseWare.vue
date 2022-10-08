<template>
    <div id="courseWare" class="courseWare_container">
        <div class="courseInfo">
            <div id="courseInfo" class="">
                <courseAside :course_id="course_id"></courseAside>
            </div>
        </div>
        <div>
            <div class="sectionInfo">
                <div class="chapter_item">
                    <div class="h">
                        <el-breadcrumb separator-class="el-icon-arrow-right">
                            <el-breadcrumb-item :to="{ path: '/course/' + this.course_id + '/chapterPage' }">课件
                            </el-breadcrumb-item>
                            <el-breadcrumb-item>
                                <el-select v-model="chapter_value" placeholder="请选择" @change="changeSectionList"
                                    Popper-append-to-body="false">
                                    <el-option v-for="item in ChapterList" :key="item.index" :label="item.title"
                                        :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-breadcrumb-item>
                            <el-breadcrumb-item>
                                <el-select v-model="section_value" placeholder="请选择" @change="toSection">
                                    <el-option v-for="item in sectionList" :key="item.index" :label="item.title"
                                        :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-breadcrumb-item>
                        </el-breadcrumb>
                        <el-button v-if="file != null" @click="exportFile">文档下载</el-button>
                    </div>
                    <div class="player">
                        <component :is="player" :url="videUrl"></component>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
  
<script>
import courseAside from "./components/courseAside"
import NProgress from 'nprogress'
import { req } from "@/api/request"
import $ from "jquery"
import store from "@/store"
export default {
    name: "CourseWare",
    props: [
        'course_id',
        'chapter_id',
        'section_id',
    ],
    components: {
        courseAside,
    },
    data() {
        return {
            activeNames: 0,
            ChapterList: [],
            sectionList: [],
            chapter_value: parseInt(this.chapter_id),
            section_value: parseInt(this.section_id),
            // videUrl: "//player.alicdn.com/video/aliyunmedia.mp4",
            videUrl: '',
            file: '',
            sectionName: '',
            player: undefined,
        }
    },
    methods: {
        getChapterList() {
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
        getSectionList() {
            req('/chapter/' + this.chapter_value + '/section/')
                .then(res => {
                    if (res.code != 200) {
                        this.$message.error('获取失败')
                        return
                    }
                    this.sectionList = res.data
                })
        },
        changeSectionList() {
            this.section_value = ''
            this.getSectionList()
        },
        handleScroll(e) {
            let scrollTop =
                document.documentElement.scrollTop || document.body.scrollTop;
            if (scrollTop > 91) {
                $('#courseInfo').addClass('courseInfo_position')
            } else $('#courseInfo').removeClass('courseInfo_position')
        },
        toSection() {
            if (this.$route.fullPath != '/course/' + this.course_id + '/chapterPage/' + this.chapter_value + '/section/' + this.section_value)
                this.$router.push({ path: '/course/' + this.course_id + '/chapterPage/' + this.chapter_value + '/section/' + this.section_value })
        },
        getSelection() {
            // console.log("获取")
            req('/section/' + this.section_id)
                .then(res => {
                    if (res.code != 200) {
                        this.$message.error('获取失败')
                        return
                    }
                    this.videUrl = res.data.video
                    this.file = res.data.courseware
                    this.sectionName = res.data.title
                    this.player = (() => import('./components/player'))
                })
        },
        exportFile() {
            // this.createAndRemove(this.$ip + this.file, "fileName")
            function getBlob(url) {
                return new Promise((resolve) => {
                    const xhr = new XMLHttpRequest()
                    xhr.open('GET', url, true)
                    xhr.responseType = 'blob'
                    xhr.onload = () => {
                        if (xhr.status === 200) {
                            resolve(xhr.response)
                        }
                    }
                    xhr.send()
                })
            }
            function saveAs(blob, filename) {
                if (window.navigator.msSaveOrOpenBlob) {
                    navigator.msSaveBlob(blob, filename)
                } else {
                    const link = document.createElement('a')
                    const exportBtn = document.getElementById('exportBtn')

                    link.href = window.URL.createObjectURL(blob)
                    link.download = filename

                    // fix Firefox
                    link.style.display = 'none'
                    // exportBtn.appendChild(link)

                    link.click()
                    // exportBtn.removeChild(link)

                    window.URL.revokeObjectURL(link.href)
                    return
                }
            }
            this.$message("正在获取文件，请耐心等待！")
            getBlob(this.$ip + this.file).then((blob) => {
                saveAs(blob, this.sectionName)
            })
            // console.log("下载")
            // let link = document.createElement("a");
            // // link.href = this.$ip + this.file;
            // link.setAttribute('href', this.$options.filters(this.$ip + this.file));
            // link.setAttribute("download", "文件名称.xlsx");
            // link.click();
            // link = null;
            // this.$message.success("获取文件成功");
        },
    },
    created() {
        // this.getChapter()
        this.getChapterList()
        this.getSectionList()
        this.handleScroll()
        this.getSelection()
        // console.log(this.section_value === this.sectionList[0].id)
        window.addEventListener("scroll", this.handleScroll, true);
    },
    computed: {},
    beforeRouteEnter(to, from, next) {
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
.courseWare_container {
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


.sectionInfo {
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

.h {
    display: flex;
    justify-content: space-between;
    margin-bottom: 30px;
    padding: 8px;
    font-size: 25px;
    letter-spacing: 10px;
}

.player {
    padding-bottom: 20px;
}

::v-deep .el-breadcrumb__item {
    float: none;
}

::v-deep .el-breadcrumb__inner:hover {
    color: #68cb9b !important;
}

.el-select-dropdown__item.selected {
    color: #68cb9b !important;
}

::v-deep .el-select .el-input.is-focus .el-input__inner {
    border-color: #68cb9b;
}
</style>