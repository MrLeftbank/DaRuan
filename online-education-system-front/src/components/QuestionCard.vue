<template>
    <div class="questionCard">
        <div class="user" v-if="!show">
            <el-avatar class="i_box">
                <img :src="photo || require('@/assets/photo/customer/avatar-boy.gif')"
                    :onerror='`this.src="${require("@/assets/photo/customer/avatar-boy.gif")}"`'>
            </el-avatar>
            <div class="name">
                {{ data.userName }}
            </div>
        </div>
        <div class="question">
            <div class="title">
                <span class="box  fa fa-question-circle-o" @click="toAnswer">
                    {{ data.title }}
                </span>
            </div>
            <div class="time el-icon-time">
                {{ data.time }}
            </div>
        </div>
    </div>
</template>

<script>

export default {
    name: "QuestionCard",
    props: [
        'data',
        'show'
    ],
    data() {
        return {
            photo: '',
        }
    },
    methods: {
        toAnswer() {
            this.$router.push('/question/' + this.data.id)
        }
    },
    mounted() {
        this.photo = this.$ip + this.data.userPhotoUrl
    }
}

</script>


<style scoped>
.questionCard {
    display: flex;
}

.user {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100px;
    height: 120px;
}

.i_box {
    width: 90px;
    height: 90px;
}

.name {
    height: 20px;
    font-size: 15px;
    margin-top: 10px;
    overflow: hidden;
}

.title {
    width: 700px;
    height: 70px;
    line-height: 35px;
    font-size: 22px;
    overflow: hidden;
}

.question {
    margin-left: 25px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    padding-top: 15px;
}

.time {
    font-size: 12px;
    align-items: baseline;
}

.box {
    cursor: pointer;
}

.box:hover {
    color: #14BA4C;
    /* text-decoration: underline; */
}
</style>