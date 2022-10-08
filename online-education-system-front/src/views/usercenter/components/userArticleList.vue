<template>
    <div id="articleList" class="articleList_container">

        <div class="sorter_container">
            <div class="sorter_title">
                <h2 class="tac">
                    <span>我的文章</span>
                </h2>
            </div>
            <el-divider class="divider"></el-divider>
        </div>
        <div class="c_container">
            <el-empty class="empty" v-if="articleList.length == 0" description="你没有发布任何文章"></el-empty>
            <div class="articleList">
                <div v-if="show" v-for="(article, article_index) in articleList" v-bind:key="article_index"
                    class="articlecardbutton">
                    <articleCard :author="true" :data="article" class="articleCard"></articleCard>
                    <el-button type="danger" icon="el-icon-delete" @click="deleteArticle(article.id)"></el-button>
                </div>
            </div>
        </div>
        <div class="page">
            <el-pagination :hide-on-single-page="true" background layout="prev, pager, next, jumper, ->, total, slot"
                :total="articlePage.total" @current-change="changePage()" :page-size="page_size"
                :current-page.sync="current_page">
            </el-pagination>
        </div>
    </div>
</template>

<script>
import articleCard from "@/components/articleCard.vue"
import { req } from "@/api/request"
export default {
    name: "userArticleList",
    props: [

    ],
    data() {
        return {
            articleList: [],
            articlePage: { "records": [], "total": 0, "size": 1, "current": 1, "pages": 1 },
            current_page: 1,
            page_size: 4,
            show: true,
        }
    },
    components: {
        articleCard,
    },
    created() {
    },
    mounted() {
        this.getArticleList(this.current_page, this.page_size);
    },
    methods: {
        getArticleList(pageNum, pageSize) {
            req('user/article' + '?pageSize=' + pageSize + '&pageNum=' + pageNum)
                .then((res) => {
                    // console.log(res)
                    this.articlePage = res.data;
                    this.articleList = this.articlePage.records;
                    // console.log(this.articleList);
                })
        },
        changePage(val) {
            this.getArticleList(this.current_page, this.page_size);
        },
        async deleteArticle(articleId) {
            if (!(await this._box())) return;
            req('user/article/' + articleId, 'delete')
                .then((res) => {
                    // console.log(res)
                    this.getArticleList(this.current_page, this.page_size);
                })
        },
    },
    watch: {
        articleList(newVal) {
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
.articleList_container {
    display: flex;
    flex-direction: column;
}

.articleList {
    width: 70%;
    margin: 20px auto;
}

.sorter_container {
    margin: 0 auto;
}

.articlecardbutton {
    display: flex;
    margin-bottom: 2%;
    padding: 10px;
    background-color: #FFF;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    border-radius: 15px;
}

.articleCard {
    width: 100%;
}

.c_container {
    min-height: 560px;
}

.page {
    margin: 0 auto;
    padding: 10px;
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
    margin-top: 180px;
}
</style>