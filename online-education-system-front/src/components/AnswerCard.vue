<template>
    <div class="answerContainer">
        <div class="answerBoard">
            <div class="user">
                <el-avatar>
                    <img :src="photo || require('@/assets/photo/customer/avatar-boy.gif')"
                        :onerror='`this.src="${require("@/assets/photo/customer/avatar-boy.gif")}"`'>
                </el-avatar>
                <div class="name">
                    {{ answer.userName }}
                </div>
            </div>
        </div>
        <div class="answerContent">
            <span :id="answer.id"></span>
        </div>
        <div class="toComment">
            <div class="at">
                回答于 {{ answer.time }}
            </div>
            <div  @click="showComment" class="commentBtn">
                <i  class="el-icon-s-comment"></i>
                评论
            </div>
        </div>

        <el-collapse-transition>
            <div v-show="commentShow" class="commentBoard">
                <component :is="component" :id="answer.id"></component>
                <!-- <CommentBoard :answer_id="answer.id"></CommentBoard> -->
            </div>
        </el-collapse-transition>
    </div>
</template>

<script>
import CommentBoard from '@/components/CommentBoard.vue'
export default {
    name: "AnswerCard",
    components: { CommentBoard },
    props: ['answer'],
    data() {
        return {
            activeNames: [],
            commentShow: false,
            component: undefined,
            photo: undefined
        };
    },
    methods: {
        showComment() {
            if (this.$store.getters.isLogin) {
                this.component = () => import('@/components/CommentBoard.vue')
                this.commentShow = !this.commentShow
            } else this.$router.push(`/login?redirect=` + this.$route.fullPath)
        }
    },
    mounted() {
        this.photo = this.$ip + this.answer.userPhotoUrl
        let t = document.getElementById(this.answer.id);
        t.innerHTML = this.answer.content;
    }
}

</script>


<style scoped>
.answerContainer {
    padding: 20px 20px 10px 20px;
}

.answerBoard {
    display: flex;
    align-items: center;
}

.user {
    display: flex;
    align-items: center;
}

.answerContent {
    margin-top: 15px;
    margin-left: 30px;
    margin-bottom: 15px;
    margin-right: 15px;
}

.name {
    margin-left: 10px;
    font-weight: 700;
}

.at {
    font-size: 12px;
}

.toComment {
    display: flex;
    justify-content: space-between;
}

.commentBtn {
    cursor: pointer;
}

.commentBtn:hover {
    color: #14BA4C;
}

.commentBoard {
    margin-top: 10px;
}
</style>