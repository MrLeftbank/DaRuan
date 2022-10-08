<template>
    <div class="titleContainer">
        <div>
            <h1 class="title">{{ article.title }} </h1>
            <div class="info">
                <span class="author  el-icon-user">作者：{{ article.userName }}</span>
                <span class="time  el-icon-time">{{ article.time }}</span>
            </div>
        </div>
        <el-divider></el-divider>
        <div id="content" class="content">
            {{ article.content }}
        </div>
        <el-divider></el-divider>
        <div>
            <commentBoard :id="article_id"></commentBoard>
        </div>
    </div>
</template>

<script>
import commentBoard from "./components/CommentBoard"
import { req } from '@/api/request'
export default {
    name: "articlePage",
    components: {
        commentBoard
    },
    props: [
        "article_id"
    ],
    data() {
        return {
            article: {},
            myComment: ''
        };
    },
    methods: {
        getArticle() {
            req('/article/' + this.article_id)
                .then((response) => {
                    this.article = response.data
                    let t = document.getElementById("content")
                    t.innerHTML = response.data.content
                })
        }
    },
    created() {
        this.getArticle()
    },
    mounted() { },
}
</script>

<style scoped>
.titleContainer {
    width: 1160px;
    background-color: #FFF;
    border: 1px solid #f0f0f0;
    box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
    margin: 30px auto;
    padding: 20px;
    border-radius: 15px;
}

.content {
    padding: 0 10px;
}

.title {
    margin-top: 10px;
    text-align: center;
}

.editor {
    width: 1000px;
}

.info {
    display: flex;
    justify-content: space-between;
    align-items: flex-end;
    height: 40px;
    width: 100%;
    font-size: 14px;
}

.author {
    margin-left: 10px;
}

.time {
    margin-right: 10px;
}
</style>