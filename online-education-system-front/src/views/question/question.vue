<template>
    <div id="question">
        <div class="question">
            <div class="questionContainer">
                <div class="user">
                    <el-avatar :size="50">
                        <img :src="photo || require('@/assets/photo/customer/avatar-boy.gif')"
                            :onerror='`this.src="${require("@/assets/photo/customer/avatar-boy.gif")}"`'>
                    </el-avatar>
                    <div class="name">
                        {{ question.userName }}
                    </div>
                </div>
                <el-divider class="divider"></el-divider>
                <div>
                    <div class="title fa fa-question-circle-o"> {{ question.title }} </div>
                    <div id="content" class="content"> {{ question.content }}</div>
                    <div id="question-footer">
                        <div class="time">发布于 {{ question.time }}</div>
                        <el-button type="primary" class="el-icon-edit" @click="toAnswer"> 写回答 </el-button>
                    </div>
                </div>
            </div>
        </div>
        <div class="answer">
            <el-collapse-transition class>
                <div v-show="answerShow">
                    <editor v-model="myAnswer"></editor>
                    <div class="answer-btn">
                        <div class="btn-c">
                            <el-button type="primary" class="btn" @click="answer">发布回答</el-button>
                        </div>
                    </div>
                </div>
            </el-collapse-transition>
            <div class="other">
                <ul v-if="show">
                    <el-empty class="empty" v-if="answerList.length == 0" description="暂时没有人回答"></el-empty>
                    <li class="of" v-for="(answer, answer_index) in answerList" v-bind:key="answer_index">
                        <AnswerCard :answer="answer" class="ol"></AnswerCard>
                        <el-divider class="answerDivider"></el-divider>
                    </li>
                </ul>
                <div class="page">
                    <el-pagination :hide-on-single-page="true" background
                        layout="prev, pager, next, jumper, ->, total, slot" :page-size="page_size" :total="total"
                        @current-change="getAnswers" :current-page.sync="current_page">
                    </el-pagination>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

import AnswerCard from '@/components/AnswerCard.vue'
import QuestionCard from '@/components/QuestionCard.vue'
import { req } from "@/api/request"
import editor from '@/components/editor';
export default {
    name: "Question",
    props: [
        'question_id'
    ],
    components: {
        QuestionCard,
        AnswerCard,
        editor
    },
    data() {
        return {
            photo: undefined,
            question: '',
            answerList: [],
            myAnswer: '',
            current_page: 1,
            total: 0,
            page_size: 15,
            show: true,
            answerShow: false,
        }
    },
    methods: {
        getQuestion() {
            req('/question/' + this.question_id)
                .then((response) => {
                    this.question = response.data
                    this.photo = this.$ip + this.question.userPhotoUrl
                    let t = document.getElementById("content")
                    t.innerHTML = this.question.content
                })
        },
        getAnswers() {
            req('question/' + this.question_id + '/answer?pageNum=' + this.current_page + '&pageSize=' + this.page_size)
                .then((response) => { 
                    this.answerList = response.data.records
                })
        },
        toAnswer() {
            if (this.$store.getters.isLogin) this.answerShow = !this.answerShow;
            else this.$router.push(`/login?redirect=` + this.$route.fullPath)
        },
        answer() {
            if (this.myAnswer == '') {
                this.answerShow = false
                return
            }
            this.current_page = 1
            let data = {
                'content': this.myAnswer,
                'questionId': this.question_id,
            }
            req('/question/' + this.question_id + '/answer', 'post', data)
                .then((response) => {
                    if (response.code == 200) {
                        this.myAnswer = ''
                        this.answerShow = false
                        this.getAnswers()
                    }
                })
        }
    },
    created() {
        this.getAnswers();
    },
    mounted(){
        this.getQuestion();
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
ul,
li {
    list-style: none;
}

.question {
    background: #FFF;
    border: 1px solid #f0f0f0;
    box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
}

.questionContainer {
    margin-left: auto;
    margin-right: auto;
    margin-top: 30px;
    margin-bottom: 10px;
    width: 1160px;
    border-radius: 10px;
}

.user {
    display: flex;
    align-items: center;
}

.name {
    margin-left: 15px;
}

.divider {
    margin: 10px auto;
}

.title {
    padding-left: 30px;
    padding-right: 20px;
    font-size: 25px;
}

.content {
    padding: 10px;
}

.time {
    padding: 10px;
    font-size: 12px;
}

.page {
    margin: 0 auto;
    padding-top: 30px;
    padding-bottom: 10px;
}

.answer {
    width: 1160px;
    margin: 10px auto;
}

.answer-btn {
    width: 1160px;
    background-color: #FFF;
}

.btn-c {
    display: flex;
    border: 1px solid #ccc;
    flex-direction: row-reverse;
}

.btn {
    margin: 10px;
}

.other {
    width: 100%;
    display: flex;
    flex-direction: column;
    margin-top: 24px;
}

#question-footer {
    display: flex;
    justify-content: space-between
}

.of {
    border-radius: 5px;
    background: #FFF;
    box-sizing: border-box;
    box-shadow: 0 1px 3px rgb(18 18 18 / 10%);
}

.answerDivider {
    margin-top: 10px;
    margin-bottom: 24px;
}
</style>