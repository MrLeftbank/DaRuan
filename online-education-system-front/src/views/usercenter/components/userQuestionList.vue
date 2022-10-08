<template>
    <div id="questionList" class="questionList_container">
        <div class="sorter_container">
            <div class="sorter_title">
                <h2 class="tac">
                    <span>我的提问</span>
                </h2>
            </div>
            <el-divider class="divider"></el-divider>
        </div>
        <div class="c_container">
            <el-empty class="empty" v-if="questionList.length == 0" description="你没有进行提问"></el-empty>
            <div v-if="show" v-for="(question, question_index) in questionList" v-bind:key="question_index">
                <div class="questionbutton">
                    <div class="card">
                        <QuestionCard :show="true" :data="question" class="QuestionCard"></QuestionCard>
                    </div>
                    <el-button type="danger" icon="el-icon-delete" @click="deleteQuestion(question.id)"></el-button>
                </div>
            </div>
        </div>
        <div class="page">
            <el-pagination :hide-on-single-page="true" background layout="prev, pager, next, jumper, ->, total, slot"
                :total="questionPage.total" @current-change="changePage()" :page-size="page_size"
                :current-page.sync="current_page">
            </el-pagination>
        </div>
    </div>
</template>

<script>
import QuestionCard from "@/components/QuestionCard.vue"
import { req } from "@/api/request"
export default {
    name: "userQuestionList",
    props: [

    ],
    data() {
        return {
            questionList: [],
            questionPage: { "records": [], "total": 0, "size": 1, "current": 1, "pages": 1 },
            current_page: 1,
            page_size: 4,
            show: true,
        }
    },
    components: {
        QuestionCard
    },
    created() {
    },
    mounted() {
        this.getQuestionList(this.current_page, this.page_size);
    },
    computed: {

    },
    methods: {
        getQuestionList(pageNum, pageSize) {
            req('user/question' + '?pageSize=' + pageSize + '&pageNum=' + pageNum)
                .then((res) => {
                    // console.log(res)
                    this.questionPage = res.data;
                    this.questionList = this.questionPage.records;
                })
        },
        changePage(val) {
            this.getQuestionList(this.current_page, this.page_size);
        },
        async deleteQuestion(qId) {
            if (!(await this._box())) return;
            req('user/question/' + qId, 'delete')
                .then((res) => {
                    // console.log(res)
                    this.getQuestionList(this.current_page, this.page_size);
                })
        },
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
.questionList_container {
    display: flex;
    flex-direction: column;
    height: 100%;
    word-break: break-all;
    word-wrap: break-word;
}

.sorter_container {
    margin: 0 auto;
}

.questionbutton {
    display: flex;
    margin-bottom: 2%;
    padding: 10px;
    background-color: #FFF;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    border-radius: 15px;
}

.card {
    width: 100%;
}

.QuestionCard {
    box-shadow: none !important;
}

.page {
    margin: 0 auto;
    padding: 60px;
}

.c_container {
    min-height: 520px;
    width: 70%;
    margin: 20px auto;
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

ol,
ul,
li {
    list-style: none;
}


.divider {
    margin: 0;
    margin-top: 24px;
}

.empty {
    margin-top: 160px;
}
</style>