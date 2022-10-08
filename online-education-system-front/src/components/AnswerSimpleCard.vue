<template>
    <div class="container">
        <div :id="answer.id"></div>
        <div class="at el-icon-time">回答时间：{{ answer.time }} </div>
    </div>
</template>

<script>

export default {
    name: "AnswerSimpleCard",
    components: {},
    props: [
        'answer',
    ],
    data() {
        return {
            content: ""
        };
    },
    mounted() {
        let t = document.getElementById(this.answer.id)
        t.innerHTML = this.answer.content;
    }
}

</script>


<style scoped>
.container {
    margin-top: 20px;
    margin-left: 3%;
    margin-right: 3%;
    overflow-x: hidden;
    margin-bottom: 30px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    background-color: #ffffff
}

#bar {
    margin-top: 20px;
    margin-left: 8%;
    margin-bottom: 5px;
}

#name {
    margin-left: 13%;
}

.at {

    margin-top: 8%;
}
</style>