<template>
    <el-container class="container">
        <el-header class="header">
            <el-page-header @back="goBack" content="个人中心"></el-page-header>
        </el-header>
        <el-container class="main-container">
            <el-aside width="200px">
                <div id="userInfo" class="userInfo">
                    <userCenterAside></userCenterAside>
                </div>
            </el-aside>
            <el-main>
                <div class="userCenterPageContent">
                    <router-view :key="key"></router-view>
                </div>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
import userCenterAside from "./components/userCenterAside"
export default {
    name: "userCenterPage",
    props: [

    ],
    components: {
        userCenterAside
    },
    computed: {
        key() {
            return this.$route.fullPath;
        }
    },
    data() {
        return {
            activeNames: 0,
            ChapterList: [],
        };
    },
    methods: {
        goBack() {
            this.$router.push('/home')
        }
    }
}
</script>

<style scoped>
.container {
    height: 100vh;
}

.userCenterPageContent {
    height: 95%;
}

.el-header {
    padding: 20px;
    align-items: center;
    background: #FFF;
    box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
    height: 7% !important;
}

.main-container {
    width: 100%;
    height: 93%;
}

.el-aside {
    box-shadow: 2px 10px 10px rgba(0, 0, 0, 0.1);
}

.userInfo {
    height: 100%;
    background: #FFF;
}

.el-main {
    padding: 0;
}

</style>