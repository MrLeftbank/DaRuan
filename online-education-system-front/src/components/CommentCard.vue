<template>
    <div class="commentContainer">
        <div class="commentBoard">
            <div class="user">
                <el-avatar>
                    <img :src="photo || require('@/assets/photo/customer/avatar-boy.gif')"
                        :onerror='`this.src="${require("@/assets/photo/customer/avatar-boy.gif")}"`'>
                </el-avatar>
                <div class="name">
                    {{ data.userName }}
                </div>
            </div>

        </div>
        <div class="commentContent"> {{ data.content }} </div>
        <el-row>
            <el-col class="at">
                <div> 评论时间: {{ data.time }} </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>

export default {
    name: "CommentCard",
    props: ['data'],
    data() {
        return {
            photo: undefined
        }
    },
    mounted() {
        this.photo = this.$ip + this.data.userPhotoUrl
    }
}

</script>


<style scoped>
.commentContainer {
    padding: 10px 20px 10px 30px;
}

.commentBoard {
    display: flex;
    align-items: center;
}

.user {
    display: flex;
    align-items: center;
}

.name {
    margin-left: 10px;
    font-weight: 700;
}
.commentContent {
    margin-top: 15px;
    margin-left: 30px;
    margin-bottom: 15px;
    margin-right: 15px;
}

.at {
    font-size: 12px;
}
</style>