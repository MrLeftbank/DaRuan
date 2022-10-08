<template>
    <div id="answerList" class="answerList_container">

        <div class="sorter_container">
            <div class="sorter_title">
                <h2 class="tac">
                    <span>我的回答</span>
                </h2>
            </div>
            <el-divider class="divider"></el-divider>
        </div>
        <div class="c_container">
            <el-empty class="empty" v-if="answerList.length == 0" description="你没有进行任何回答"></el-empty>
            <div v-if="show" v-for="(answer, answer_index) in answerList" v-bind:key="answer_index">
                <div class="answerbutton">
                    <div class="card">
                        <AnswerSimpleCard :answer="answer" class="AnswerCard"></AnswerSimpleCard>
                    </div>
                    <el-button type="danger" icon="el-icon-delete" @click="deleteAnswer(answer.id)"></el-button>
                </div>
            </div>
        </div>
        <div class="page">
            <el-pagination :hide-on-single-page="true" background layout="prev, pager, next, jumper, ->, total, slot"
                :total="answerPage.total" @current-change="changePage()" :page-size="page_size"
                :current-page.sync="current_page">
            </el-pagination>
        </div>
    </div>
</template>

<script>
import AnswerSimpleCard from "@/components/AnswerSimpleCard.vue"
import { req } from "@/api/request"
export default {
    name: "userAnswerList",
    props: [

    ],
    data() {
        return {
            answerList: [],
            answerPage: { "records": [], "total": 0, "size": 1, "current": 1, "pages": 1 },
            current_page: 1,
            page_size: 4,
            show: true,
        }
    },
    components: {
        AnswerSimpleCard
    },
    created() {
    },
    mounted() {
        this.getAnswerList(this.current_page, this.page_size);
    },
    computed: {

    },
    methods: {
        getAnswerList(pageNum, pageSize) {
            req('user/answer' + '?pageSize=' + pageSize + '&pageNum=' + pageNum)
                .then((res) => {
                    // console.log(res)
                    this.answerPage = res.data;
                    this.answerList = this.answerPage.records;
                })
        },
        changePage(val) {
            this.getAnswerList(this.current_page, this.page_size);
        },
        async deleteAnswer(qId) {
            if (!(await this._box())) return;
            req('user/answer/' + qId, 'delete')
                .then((res) => {
                    // console.log(res)
                    this.getAnswerList(this.current_page, this.page_size);
                })
        },
    },
    watch: {
        answerList(newVal) {
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
.answerList_container {
    display: flex;
    flex-direction: column;
    word-break: break-all;
    word-wrap: break-word;
}

.sorter_container {
    margin: 0 auto;
}

.c_container {
    width: 70%;
    margin: 20px auto;
    min-height: 600px;
}

.answerbutton {
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

.AnswerCard {
    box-shadow: none !important;
}

.page {
    margin: 0 auto;
    padding: 20px;
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

.hidden {
    visibility: hidden;
}

.divider {
    margin: 0;
    margin-top: 24px;
}

.empty{
    margin-top: 160px;
}
</style>