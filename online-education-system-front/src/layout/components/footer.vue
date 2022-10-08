<template>
    <div id="footer" class="footer">
        <div>
            <h4>
                <span>友情链接</span>
            </h4>
        </div>
        <el-divider class="divider"></el-divider>
        <div class="a">
            <a href="javascript:void(0)" title="关于我们" class="_blank">关于我们</a>|
            <a href="javascript:void(0)" title="联系我们" class="_blank" @click="test">联系我们</a>|
            <a href="javascript:void(0)" title="帮助中心" class="_blank">帮助中心</a>|
            <a href="javascript:void(0)" title="资源下载" class="_blank">资源下载</a>|
            <span> © 京ICP备12132131231号</span>
        </div>
    </div>
</template>

<script>
export default {
    methods:{
        test(){
            this.$message.success("qq:3516143285")
        }
    }
}
</script>

<style scoped>
.footer {
    width: 1160px;
    height: 130px;
    margin: 0 auto;
}

._blank {
    font-size: 12px;
    color: #666;
    display: block;
    margin-left: 10px;
    margin-right: 10px;
    display: inline-block;
    vertical-align: middle;
}

.a {
    display: flex;
    align-items: center;
    margin-top: 20px;
}

.divider{
    background-color: #393939;
    margin-top: 12px;
    margin-bottom: 12px;
}
</style>