<template>
    <div class="container">
        <el-menu :default-active="isSelected" class="el-menu-one" mode="horizontal" active-text-color="#68cb9b" router>
            <el-menu-item index="1" title="首页" route="/home">首页</el-menu-item>
            <el-menu-item index="2" route="/course">课程</el-menu-item>
            <el-menu-item index="3" route="/teacher">教师</el-menu-item>
            <el-menu-item index="4" route="/article">文章</el-menu-item>
            <el-menu-item index="5" route="/question">问答</el-menu-item>
        </el-menu>
        <el-menu class="el-menu-two" mode="horizontal" active-text-color="#68cb9b" :router="!isLogin">
            <!-- <el-menu-item index="6" class="search_box">
                <el-input placeholder="课程搜索" class="search_input">
                    <el-button slot="suffix" class="fa fa-search search_button"></el-button>
                </el-input>
            </el-menu-item> -->
            <el-menu-item index="7" class="login_box" route="/login">
                <template slot="title">
                    <el-dropdown placement="bottom-start">
                        <div>
                            <el-avatar>
                                <img v-if="isLogin" :src="userPhoto"
                                    :onerror='`this.src="${require("@/assets/photo/customer/avatar-boy.gif")}"`'>
                                <i class="avatar" :class="isLogin ? '' : 'el-icon-user-solid'"></i>
                            </el-avatar>
                            <span class="el-dropdown-link">
                                {{ nickname }}
                            </span>
                        </div>
                        <el-dropdown-menu slot="dropdown" class="dropdown">
                            <el-dropdown-item v-if="!isTeacher" @click.native="toCertificate">教师认证</el-dropdown-item>
                            <el-dropdown-item v-if="isTeacher" @click.native="toCourseManage">管理课程</el-dropdown-item>
                            <el-dropdown-item :divided="true" @click.native="toAddArticle">发布文章</el-dropdown-item>
                            <el-dropdown-item :divided="true" @click.native="toQuestion">我要提问</el-dropdown-item>
                            <el-dropdown-item :divided="true" @click.native="toProfile">个人中心</el-dropdown-item>
                            <el-dropdown-item v-if="isLogin" class="logout" :divided="true" @click.native="logout">登出
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </template>
            </el-menu-item>
            <el-menu-item>
                <span class="balance" v-if="isLogin" @click="getA">余额：￥{{ balance }}</span>
            </el-menu-item>
        </el-menu>
    </div>
</template>

<script>
import store from '@/store'
export default {
    name: "layout_header",
    data() {
        return {
            nickname: store.getters.name,
            isLogin: store.getters.isLogin,
            userPhoto: store.getters.avatar || require("@/assets/photo/customer/avatar-boy.gif"),
            isTeacher: store.getters.isTeacher,
            balance: store.getters.balance
        }
    },
    methods: {
        toProfile() {
            this.$router.push('/my/star');
        },
        toCertificate() {
            this.$router.push('/teacherCertificate')
        },
        toAddArticle() {
            this.$router.push('/addArticle')
        },
        toQuestion() {
            this.$router.push('/addQuestion')
        },
        toCourseManage() {
            this.$router.push('/courseManage')
        },
        getA() {
            console.log(this.balance);
            this.$message.info("使用qq联系3516143285进行充值")
        },
        logout() {
            this.$store.dispatch('user/logout').then((res) => {
            })
        }
    },
    computed: {
        isSelected() {
            const val = this.$route.matched[0].path
            if (val == '/home') {
                return '1'
            } else if (val == '/course') {
                return '2'
            } else if (val == '/teacher') {
                return '3'
            } else if (val == '/article') {
                return '4'
            } else if (val == '/question') {
                return '5'
            }
            return '0';
        }
    },
    mounted() { },
}
</script>

<style scoped>
.container {
    height: 80px;
    display: flex;
}

.el-menu-one {
    width: 60%;
    height: 100%;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center
}

.el-menu-two {
    width: 40%;
    height: 100%;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center
}

::v-deep .el-input__inner {
    border-color: #68cb9b !important;
    border-radius: 25px;
}

.search_input {
    border-color: #68cb9b !important;
}

.search_box {
    width: 300px;
    border-bottom: none !important;
    cursor: default !important;
    margin-right: 15px !important;
}

.login_box {
    right: 0%;
    border-bottom: none !important;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center
}

.i_box {
    margin-left: 10px;
}

.el-menu-item {
    font-size: 20px;
}

.search_button {
    border-color: #68cb9b !important;
    vertical-align: baseline !important;
    margin-right: -5px;
    background: #68cb9b !important;
    border-radius: 25px;
}

.avatar {
    margin-right: 0 !important;
}

.logout {
    color: red;
}

.dropdown {
    text-align: justify;
    text-align-last: justify;
}

.balance {
    font-size: 14px;
    line-height: 0;
    color: black;
}
</style>