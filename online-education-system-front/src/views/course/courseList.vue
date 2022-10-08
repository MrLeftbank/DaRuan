<template>
    <div id="courseList" class="courseList_container">

        <div class="sorter_title">
            <h2 class="tac">
                <span>课程列表</span>
                <el-input v-model="search" placeholder="课程搜索" class="search_input search_box" clearable>
                    <el-button slot="suffix" class="fa fa-search search_button" @click="getCourseList"></el-button>
                </el-input>
            </h2>
        </div>
        <el-divider></el-divider>
        <div class="domain_container">
            <div class="domain_title">
                课程类别
            </div>
            <div class="domain_content">
                <div>
                    <el-radio-group v-model="first_domain" @change="getSecondDomain">
                        <el-radio-button label="全部"></el-radio-button>
                        <el-radio-button :label="domain.id" v-for="(domain, domain_index) in firstDomainList"
                            v-bind:key="domain_index">{{ domain.title }}</el-radio-button>
                    </el-radio-group>
                </div>
            </div>
        </div>
        <el-divider></el-divider>
        <div class="second_domain_content">
            <el-radio-group v-model="second_domain" @change="getCourseList" v-if="!isNull & domainShow">
                <el-radio-button label="全部">
                </el-radio-button>
                <el-radio-button :label="domain.id" v-for="(domain, domain_index) in secondDomainList"
                    v-bind:key="domain_index">{{ domain.title }}</el-radio-button>
            </el-radio-group>
        </div>
        <el-divider></el-divider>
        <div class="js-wrap">
            <div>
                <el-radio-group v-model="sorter" @change="getCourseList">
                    <el-radio-button :label="1">最新</el-radio-button>
                    <el-radio-button :label="2">价格</el-radio-button>
                </el-radio-group>
            </div>
        </div>
        <ul class="table" v-if="courseShow">
            <el-empty v-if="courseList.length == 0" description="没有数据"></el-empty>
            <li v-for="(course, course_index) in courseList" v-bind:key="course_index">
                <CourseCard :data="course"></CourseCard>
            </li>
        </ul>
        <div class="page">
            <el-pagination :hide-on-single-page="true" background layout="prev, pager, next, jumper, ->, total, slot"
                :page-size="8" :total="total" @current-change="getCourseList" :current-page.sync="current_page">
            </el-pagination>
        </div>
    </div>
</template>

<script>
// import $ from 'jquery';
import CourseCard from "@/components/CourseCard"
import { req } from "@/api/request"
export default {
    name: "courseList",
    props: [

    ],
    data() {
        return {
            firstDomainList: [],
            secondDomainList: [],
            courseList: [],
            current_page: 1,
            total: 0,
            first_domain: '全部',
            second_domain: '全部',
            sorter: '1',
            search: '',
            courseShow: true,
            domainShow: true,
        }
    },
    components: {
        CourseCard,
    },
    created() {
        this.getCourseList();
        this.getFirstDomain();
    },
    mounted() {

    },
    watch: {
        courseList(newVal) {
            this.courseShow = false
            if (true) {
                this.$nextTick(() => { // $nextTick 是在 DOM 更新循环结束之后执行延迟回调
                    this.courseShow = true
                })
            }
        },
        secondDomainList(newVal) {
            this.domainShow = false
            if (true) {
                this.$nextTick(() => { // $nextTick 是在 DOM 更新循环结束之后执行延迟回调
                    this.domainShow = true
                })
            }
        }
    },
    computed: {
        isNull() {
            if (JSON.stringify(this.secondDomainList) === '[]') {
                return true;
            }
            return false;
        },
        domain() {
            if (this.first_domain === '全部') return ''
            if (this.second_domain === '全部') return this.first_domain;
            return this.second_domain
        }
    },
    methods: {
        getCourseList() {
            req('/course/search?name=' + this.search + '&domainId=' + this.domain + '&orderTag=' + this.sorter + '&pageNum=' + this.current_page)
                // req('course/search?name=1&domainId=null&orderTag=1')
                .then((response) => {
                    if (response.code !== 200) {
                        this.$message.error('获取失败')
                        return
                    }
                    const { data } = response;
                    this.current_page = data.current;
                    this.total = data.total;
                    this.courseList = data.records
                })
        },
        getFirstDomain() {
            req('/parent-domain')
                .then((response) => {
                    if (response.code !== 200) {
                        this.$message.error('获取分类失败')
                        return
                    }
                    this.firstDomainList = response.data
                })
        },
        async getSecondDomain(val) {
            this.second_domain = '全部'
            this.getCourseList()
            if (this.first_domain === '全部') {
                this.secondDomainList = []
                return
            }
            await req('/domain/' + this.first_domain + '/domain')
                .then((response) => {
                    if (response.code !== 200) {
                        this.$message.error('获取子类失败')
                        return
                    }
                    this.secondDomainList = response.data
                })
        }
    }
}

</script>

<style scoped>
.courseList_container {
    width: 1160px;
    margin: 0 auto;
    display: flex;
    flex-direction: column;
}


.domain_container {
    display: flex;
    flex-direction: row;
}

.domain_title {
    width: 80px;
    padding: 0 20px;
    font-size: 14px;
    margin-top: 17px;
}

.domain_content {
    padding: 0 20px;
    font-size: 20px;
}

.second_domain_content {
    width: 1002px;
    margin: 0 0 0 auto;
    padding: 0 20px;
    font-size: 20px;
}

.page {
    margin: 0 auto;
    padding: 60px;
}

.sorter_title {
    margin: 40px 0;
    margin-bottom: -15px;
}

.tac {
    font-size: 26px;
    display: flex;
}

.js-wrap {
    height: 40px;
    line-height: 40px;
    padding: 0 15px;
    margin-top: -15px;
}

.bg-green {
    background-color: #14BA4C;
}

ol,
ul,
li {
    list-style: none;
}

::v-deep .el-radio-button__inner {
    border: 1px solid #fafafa !important;
    background: none !important;
    font-size: 20px;
    border-radius: 8px;
    margin: 3px;
    padding: 12px 10px;
}

::v-deep .el-radio-button__inner:hover {
    border: 1px solid #14BA4C !important;
    font-size: 20px;
}

::v-deep .el-radio-button__orig-radio:checked+.el-radio-button__inner {
    color: #FFF;
    border: 1px solid #14BA4C !important;
    background-color: #14BA4C !important;
    border-color: #14BA4C;
    box-shadow: -1px 0 0 0 #14BA4C;
    border-radius: 8px;
}

::v-deep .el-radio-button__inner:hover {
    color: #14BA4C;
}

::v-deep .el-radio-button__inner {
    border-radius: 8px
}

::v-deep .el-radio-button:first-child .el-radio-button__inner {
    border-radius: 8px
}

::v-deep .el-radio-button:last-child .el-radio-button__inner {
    border-radius: 8px
}

.search_button {
    border-color: #14BA4C !important;
    vertical-align: baseline !important;
    margin-right: -5px;
    /* background: #14BA4C !important; */
    border-radius: 25px;
}

.search_box {
    width: 300px;
    border-bottom: none !important;
    cursor: default !important;
    margin-left: 15px !important;
}

::v-deep .el-input__inner {
    border-radius: 25px !important;
}

::v-deep .el-input__suffix-inner {
    flex-direction: row-reverse;
    -webkit-flex-direction: row-reverse;
    display: flex;
}

.table {
    margin-top: 20px;
    min-height: 600px;
}
</style>