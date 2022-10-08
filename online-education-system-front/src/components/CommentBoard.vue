<template>
    <div class="commentBoard">
        <el-input class="comment" type="textarea" autosize v-model="myComment"></el-input>
        <div class="btn">
            <el-button type="primary" @click="comment">发布评论</el-button>
        </div>
        <ul class="of" v-if="show">
            <li v-for="(comment, comment_index) in commentList" v-bind:key="comment_index">
                <el-divider class="divider"></el-divider>
                <CommentCard :data="comment" class="ol"></CommentCard>
            </li>
        </ul>
        <div class="page">
            <el-pagination :hide-on-single-page="true" background layout="prev, pager, next, jumper, ->, total, slot"
                :page-size="page_size" :total="total" @current-change="getComment" :current-page.sync="current_page">
            </el-pagination>
        </div>
    </div>
</template>

<script>
import CommentCard from '@/components/CommentCard.vue'
import { req } from "@/api/request"
export default {
    name: "CommentBoard",
    components: { CommentCard },
    props: [
        'id',
    ],
    data() {
        return {
            commentList: [],
            myComment: '',
            show: true,
            current_page: 1,
            total: 0,
            page_size: 10,
        }
    },
    methods: {
        getComment() {
            req('/answer/' + this.id + '/comment?pageNum=' + this.current_page + '&pageSize=' + this.page_size)
                .then((res) => {
                    this.total = res.data.total
                    this.commentList = res.data.records
                })
        },
        comment() {
            if (this.myComment == '') {
                this.$message({
                    message: 'Please enter a comment',
                    type: 'warning'
                })
                return
            }
            let data = {
                'content': this.myComment,
                'parentId': this.id,
                'type': 0,
            }
            req('/answer/' + this.id + '/comment', 'post', data)
                .then((res) => {
                    this.myComment = ''
                    this.current_page = 1
                    this.getComment()
                })
        }
    },
    watch: {
        commentList(newVal) {
            this.show = false
            if (true) {
                this.$nextTick(() => { // $nextTick 是在 DOM 更新循环结束之后执行延迟回调
                    this.show = true
                })
            }
        }
    },
    created() {
        this.getComment()
    }
}
</script>

<style scoped>
.commentBoard {
    display: flex;
    flex-direction: column;
}

ul,
li {
    list-style: none;
}

.divider {
    margin-top: 8px;
    margin-bottom: 0;
}

.btn {
    margin-top: 8px;
    display: flex;
    flex-direction: row-reverse;
}

.page {
    margin: 0 auto;
    padding-top: 30px;
    padding-bottom: 10px;
}

.comment {
    line-height: 25px;
    font-size: 20px;
}

::v-deep .el-textarea__inner {
    color: black !important;
}
</style>