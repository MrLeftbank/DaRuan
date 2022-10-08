<template>

    <div id="userCenterAside" class="userCenterAside_container">
        <div class="title">
            <span class="action">{{ name }}</span>
        </div>
        <img class="imageForm" :src="photoUrl"
            :onerror='`this.src="${require("@/assets/photo/customer/avatar-boy.gif")}"`'>
        <div>
            <el-menu :default-active="isSelected" class="el-menu">
                <el-menu-item index="1" @click="toStar()">
                    <i class="el-icon-menu"></i>
                    <span slot="title">我的收藏课程</span>
                </el-menu-item>
                <el-menu-item index="2" @click="toPurchase()">
                    <i class="el-icon-menu"></i>
                    <span slot="title">我的购买课程</span>
                </el-menu-item>
                <el-menu-item index="3" @click="toArticle()">
                    <i class="el-icon-document"></i>
                    <span slot="title">我的文章</span>
                </el-menu-item>
                <el-menu-item index="4" @click="toQuestion()">
                    <i class="el-icon-document"></i>
                    <span slot="title">我的提问</span>
                </el-menu-item>
                <el-menu-item index="5" @click="toAnswer()">
                    <i class="el-icon-document"></i>
                    <span slot="title">我的回答</span>
                </el-menu-item>
                <el-menu-item index="6" @click="toInfo()">
                    <i class="el-icon-setting"></i>
                    <span slot="title">我的信息</span>
                </el-menu-item>
                <el-menu-item class="deleteUser" @click="deleteUser()">
                    <i class="el-icon-setting"></i>
                    <span slot="title">删除账户</span>
                </el-menu-item>
            </el-menu>
        </div>
    </div>
</template>

<script>
import store from '@/store'
export default {
    name: "userCenterAside",
    props: [

    ],
    data() {
        return {
            photoUrl: store.getters.avatar || require("@/assets/photo/customer/avatar-boy.gif"),
            name: store.getters.name
        }
    },
    mounted() { },
    methods: {
        toStar() {
            this.$router.push({ path: '/my/star' })
        },
        toPurchase() {
            this.$router.push({ path: '/my/purchase' })
        },
        toArticle() {
            this.$router.push({ path: '/my/article' })
        },
        toQuestion() {
            this.$router.push({ path: '/my/question' })
        },
        toAnswer() {
            this.$router.push({ path: '/my/answer' })
        },
        toInfo() {
            this.$router.push({ path: '/my/info' })
        },
        async deleteUser() {
            if (!(await this._box())) return;
            req('user', 'delete').then((res) => {
                // console.log(res)
                if(res.code == 200) {
                    this.$message.success("删除成功");
                    this.$store.dispatch('user/logout');
                }
            })
        },
    },
    computed: {
        isSelected() {
            const val = this.$route.fullPath
            if (val == '/my/star') {
                return '1'
            } else if (val == '/my/purchase') {
                return '2'
            } else if (val == '/my/article') {
                return '3'
            } else if (val == '/my/question') {
                return '4'
            } else if (val == '/my/answer') {
                return '5'
            }
            return '6';
        },
    }
}
</script>

<style scoped>
.userCenterAside_container {
    padding: 10px;
    background-color: #FFF;
    /* border-radius: 15px; */
    /* box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1); */
}

.el-menu {
    border: 0;
}

.imageForm {
    display: flex;
    margin: 0 auto;
    width: 150px;
    height: 150px;
    border-radius: 50%;
}

.title {
    overflow: hidden;
    font-size: 18px;
    line-height: 27px;
    letter-spacing: 2px;
    text-align: center;
}

.action {
    cursor: pointer;
    text-align: center;
}

.action:hover {
    color: #68cb9b;
    /* text-decoration: underline; */
}

.deleteUser{
    color: red
}
</style>