<template>
    <div id="articleAside">
        <el-card :body-style="{ padding: '0px', width: '100%' }" class="card_container">
            <div style="margin-right: 10px;">
                <div class="title">
                    <span class="a" @click="toArticle">
                        <!-- 加标题({{data.XXX}}) -->
                        {{ data.title }}
                    </span>
                </div>

                <div class="info">
                    <div class="right">{{ data.time }}</div>
                    <div>
                        <i class="el-icon-s-comment">
                            <!-- 加评论数({{data.XXX}}) -->
                            {{ data.commentCount }}
                        </i>
                        <!-- <i class="fa fa-thumbs-o-up">
                            加点赞数({{data.XXX}})
                            {{ data.thumbsUp }}
                        </i> -->
                    </div>
                </div>
            </div>
        </el-card>
    </div>
</template>

<script>
export default {
    name: "articleAside",
    props: [
        'data'
    ],
    methods: {
        toArticle() {
            this.$router.push({ path: '/article/' + this.data.id })
        }
    }
}

</script>

<style scoped>
.card_container {
    min-height: 100px;
    background-color: transparent !important;
    box-shadow: none !important;
    border: none !important;
}

.image {
    height: 100px;
    width: 180px;
}

.title {
    height: 60px;
    overflow: hidden;
    line-height: 30px;
}

.a {
    font-size: 20px;
    font-weight: normal;
    cursor: pointer;
}

.a:hover {
    color: #68cb9b;
}

.info {
    display: flex;
    justify-content: space-between;
    align-items: flex-end;
    height: 40px;
    width: 100%;
    font-size: 14px;
}

.left {
    margin-right: 15px;
}
</style>