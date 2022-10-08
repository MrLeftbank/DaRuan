<template>
    <div class="articleContainer">
        <el-form ref="articleForm" :model="formData" :rules="rules">
            <div id="title">
                <h2 class="header">文章标题:</h2>
                <el-form-item prop="title">
                    <el-input v-model="formData.title" class="title" type="textarea" autosize></el-input>
                </el-form-item>
            </div>
            <div id="content">
                <el-form-item prop="content">
                    <editor v-model="formData.content"></editor>
                </el-form-item>
            </div>
        </el-form>
        <div class="btn">
            <el-button type="primary" @click="addArticle">发布文章</el-button>
        </div>
        <div id="preview">
            <h2>内容预览</h2>
            <el-divider></el-divider>
            <div class="contentPreview">
                <span id="contentPreview"></span>
            </div>
        </div>
    </div>
</template>

<script>
import editor from "@/components/editor"
import { req } from "@/api/request"
export default {
    name: 'articleAdd',
    components: {
        editor
    },
    data() {
        return {
            formData: {
                title: '',
                content: '',
            },
            rules: {
                title: [{
                    required: true,
                    message: '请输入文章标题',
                    trigger: 'blur'
                }],
                content: [{
                    required: true,
                    message: '请输入文章内容',
                    trigger: 'blur'
                }]
            },
        }
    },
    methods: {
        addArticle() {
            this.$refs['articleForm'].validate(valid => {
                if (!valid) return
                req('/article', 'post', this.formData)
                    .then((response) => {
                        // console.log(response);
                        if (response.code == 200) {
                            this.$message({
                                message: '发布成功',
                                type: 'success',
                                duration: 3 * 1000
                            })
                            setTimeout(() => {
                                this.$router.push('/my/article');
                                this.formData.title = '';
                                this.formData.content = '';
                            }, 3000);
                        }
                    })
            })
        }
    },
    watch: {
        'formData.content': {
            handler(newName, oldName) {
                var t = document.getElementById('contentPreview');
                t.innerHTML = newName;
            }
        }
    }
}
</script>

<style scoped>
.articleContainer {
    width: 1160px;
    padding: 20px;
    margin: 30px auto;
    background: #FFF;
    box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
    border-radius: 15px;
}

#title {
    display: flex;
}

.header {
    width: 120px;
}

.btn {
    display: flex;
    flex-direction: row-reverse;
}

.contentPreview {
    margin: 10px;
}

::v-deep .el-textarea__inner {
    color: black !important;
}

.el-form-item {
    margin-bottom: 12px;
    width: 100%;
}

::v-deep .el-form-item__content {
    line-height: 0 !important;
}

::v-deep .el-textarea__inner {
    color: black !important;
}
</style>