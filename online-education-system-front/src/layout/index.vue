<template>
    <el-container>
        <el-header>
            <layout_head></layout_head>
        </el-header>
        <el-main>
            <router-view :key="key"></router-view>
        </el-main>
        <el-footer height="150px">
            <layout_footer></layout_footer>
        </el-footer>
    </el-container>
</template>

<script>
import layout_head from '@/layout/components/head'
import layout_footer from '@/layout/components/footer'
export default {
    name: 'home',
    components: {
        layout_head,
        layout_footer
    },
    computed: {
        key() {
            // console.log(this.$route.matched[0].path)
            return this.$route.fullPath;
        }
    },
}
</script>

<style scoped>
.el-container {
    height: 100%;
    background-color: #fafafa;
}

.el-header {
    height: 100% !important;
    padding: 0;
    box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
}

.el-footer {
    width: 100%;
    background-color: #323232;
    border-top-width: 5px;
    border-top-style: solid;
    color: #999;
    overflow: hidden;
    padding-top: 30px;
    border-color: #68cb9b;
}

.el-main {
    min-height: 92vh;
    padding: 0px;
}

body>.el-container {
    margin-bottom: 40px;
}
</style>