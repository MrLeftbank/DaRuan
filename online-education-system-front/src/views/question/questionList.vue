<template>
    <div class="questionContainer">
        <div class="sorter_title">
            <h2 class="tac">
                <span>问题列表</span>
                <el-input v-model="search" placeholder="问题搜索" class="search_input search_box" clearable>
                    <el-button slot="suffix" class="fa fa-search search_button" @click="getQuestionSearch"></el-button>
                </el-input>
            </h2>
        </div>
        <el-divider></el-divider>
        <ul class="of" id="bna" v-if="show">
            <el-empty v-if="questionList.length == 0" description="没有数据"></el-empty>
            <li v-for="(question, question_index) in questionList" v-bind:key="question_index">
                <QuestionCard :data="question"></QuestionCard>
                <el-divider></el-divider>
            </li>
        </ul>
        <div class="page">
            <el-pagination :hide-on-single-page="true" background layout="prev, pager, next, jumper, ->, total, slot"
                :page-size="15" :total="total" @current-change="getQuestions" :current-page.sync="current_page">
            </el-pagination>
        </div>
    </div>
</template>

<script>

import Tinymce from '@/components/editor'
import QuestionCard from '@/components/QuestionCard.vue'
import { req } from "@/api/request"
export default {
    name: "questionList",
    components: {
        Tinymce,
        QuestionCard
    },
    data() {
        return {
            questionList: [],
            current_page: 1,
            total: 0,
            show: true,
            search: '',
        }
    },
    methods: {
        getQuestions() {
            req('/question?pageNum=' + this.current_page + '&pageSize=15')
                .then((response) => {
                    const { data } = response;
                    this.total = data.total
                    this.questionList = data.records
                })
        },
        getQuestionSearch() {
            req('/question/search?keyword=' + this.search + '&pageNum=1&pageSize=15')
                .then((response) => {
                    this.current_page = 1
                    const { data } = response;
                    this.total = data.total
                    this.questionList = data.records
                })
        }
    },
    created() {
        this.getQuestions();
    },
    watch: {
        questionList(newVal) {
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
ul,
li {
    list-style: none;
}

.questionContainer {
    width: 1160px;
    margin: 30px auto;
    display: flex;
    flex-direction: column;
}

.page {
    margin: 0 auto;
    padding: 60px;
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

.tac {
    font-size: 26px;
    display: flex;
}
</style>