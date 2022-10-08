<template>
    <div class="addQuestion">
        <el-form ref="questionForm" :model="formData" :rules="rules">
            <div class="question">
                <h2 class="header">问题题目:</h2>
                <el-form-item prop="title">
                    <el-input class="title" v-model="formData.title" type="textarea" autosize></el-input>
                </el-form-item>
            </div>
            <el-divider class="divider"></el-divider>
            <div id="content">
                <el-form-item>
                    <editor v-model="formData.content"></editor>
                </el-form-item>
            </div>
        </el-form>

        <div class="btn">
            <el-button type="primary" @click="question">发布提问</el-button>
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
                    message: '请输入问题题目',
                    trigger: 'blur'
                }]
            },
        }
    },
    methods: {
        question() {
            this.$refs['questionForm'].validate(valid => {
                if (!valid) return
                req('/question', 'post', this.formData)
                    .then((response) => {
                        if (response.code == 200) {
                            this.$message({
                                message: '发布成功',
                                type: 'success',
                                duration: 3 * 1000
                            })
                            setTimeout(() => {
                                this.$router.push('/my/question');
                                this.formData.title = '';
                                this.formData.myQuestion = '';
                            }, 3000);
                        }
                    })
            })
        }
    }
}
</script>

<style scoped>
.addQuestion {
    width: 1160px;
    margin: 30px auto;
    padding: 20px;
    background: #FFF;
    box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
    border-radius: 15px;
}

.question {
    display: flex;
}

.header {
    width: 120px;
}

.title {
    line-height: 18px;
    font-size: 18px;
}

.divider {
    margin: 12px auto;
}

.btn {
    display: flex;
    flex-direction: row-reverse;
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