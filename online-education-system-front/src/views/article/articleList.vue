<template>
    <div id="articleList" class="articleList_container">
        <el-row type="flex">
            <el-col :span="18">
                <div class="articleList">
                    <header class="comm-title">
                        <h2 class="tac">
                            <span>文章</span>
                            <el-input v-model="search" placeholder="文章搜索" class="search_input search_box" clearable>
                                <el-button slot="suffix" class="fa fa-search search_button" @click="getArticleSearch">
                                </el-button>
                            </el-input>
                        </h2>
                        <section class="c-tab-title"></section>
                    </header>
                    <el-empty v-if="articleList.length == 0" description="没有数据"></el-empty>
                    <div v-if="articleShow">
                        <div v-for="(article, article_index) in articleList" v-bind:key="article_index">
                            <articleCard :data="article"></articleCard>
                            <el-divider></el-divider>
                        </div>
                    </div>
                    <div class="page">
                        <el-pagination :hide-on-single-page="true" background
                            layout="prev, pager, next, jumper, ->, total, slot" :page-size="15" :total="total"
                            @current-change="getArticleList" :current-page.sync="current_page">
                        </el-pagination>
                    </div>
                </div>
            </el-col>
            <el-col :span="6">
                <div>
                    <header class="comm-title">
                        <h2>
                            <span>排行榜</span>
                        </h2>
                        <section class="c-tab-title"></section>
                    </header>
                    <el-empty v-if="AsideList.length == 0" description="没有数据"></el-empty>
                    <div v-if="hotArticleShow">
                        <div v-for="(aside, aside_index) in AsideList" v-bind:key="aside_index">
                            <articleAside :data="aside"></articleAside>
                            <el-divider></el-divider>
                        </div>
                    </div>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import articleCard from "@/components/articleCard"
import articleAside from "./components/articleAside"
import { req } from "@/api/request"
export default {
    name: "ArticleList",
    components: {
        articleCard,
        articleAside,
    },
    data() {
        return {
            articleList: [],
            AsideList: [],
            articleShow: true,
            hotArticleShow: true,
            current_page: 1,
            total: 0,
            show: true,
            search: '',
        }
    },
    created() {
        this.getArticleList();
        this.getAsideList();
    },
    methods: {
        getArticleList() {
            req('/article?pageNum=' + this.current_page + '&pageSize=15')
                .then((response) => {
                    this.articleList = response.data.records
                    this.total = response.data.total
                })
        },
        getAsideList() {
            req('/index/article')
                .then((response) => {
                    this.AsideList = response.data
                })
        },
        getArticleSearch() {
            req('/article/search?keyword=' + this.search + '&pageNum=1&pageSize=15')
                .then((response) => {
                    this.current_page = 1
                    const { data } = response;
                    this.total = data.total
                    this.articleList = data.records
                })
        }
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
    width: 1160px;
    margin: 0 auto;
}

.articleList {
    margin-right: 30px;
    display: flex;
    flex-direction: column;
}

.comm-title {
    overflow: hidden;
    clear: both;
    margin: 40px 0 0;
}

h2 {
    font-size: 24px;
    line-height: 30px;
    font-weight: normal;
}

.c-tab-title {
    border-bottom: 1px solid #e2e2e2;
    height: 34px;
    text-align: center;
    margin-bottom: 50px;
    margin-top: -15px;
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

.tac {
    font-size: 26px;
    display: flex;
    align-items: center;
}

h2 {
    font-weight: bolder;
}

::v-deep .el-input__inner {
    border-radius: 25px !important;
}

::v-deep .el-input__suffix-inner {
    flex-direction: row-reverse;
    -webkit-flex-direction: row-reverse;
    display: flex;
}
</style>