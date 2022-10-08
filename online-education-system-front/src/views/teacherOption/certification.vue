<template>
    <div>
        <el-page-header @back="goBack" content="教师认证" class="header">
        </el-page-header>
        <div class="TCContainer">
            <div class="TC">
                <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px"
                    label-position="left">
                    <el-form-item label="教师名字" prop="teacherName">
                        <el-input v-model="formData.teacherName" placeholder="请输入教师名字" clearable
                            :style="{ width: '100%' }">
                        </el-input>
                    </el-form-item>
                    <el-form-item label="教师简历" prop="resume">
                        <editor v-model="formData.resume"></editor>
                    </el-form-item>
                    <el-form-item label="教师头像" prop="cover">
                        <el-upload class="avatar-uploader" :action="action" :show-file-list="false"
                            :on-success="handleCoverSuccess" :before-upload="coverBeforeUpload">
                            <img v-if="imgUrl" :src="imgUrl" class="avatar">
                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="footer">
                    <el-button type="primary" @click="handelConfirm">确定</el-button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import editor from '@/components/editor'
import { req } from '@/api/request'
import NProgress from 'nprogress'
export default {
    name: 'courseInfo',
    data() {
        return {
            formData: {
                teacherName: '',
                teacherPhoto: '',
                resume: ''
            },
            props: {
                multiple: true,
                value: 'id',
                label: 'name'
            },
            rules: {
                teacherName: [{
                    required: true,
                    message: '请输入名字',
                    trigger: 'blur'
                }],
                resume: [{
                    required: true,
                    message: '请输入简历',
                    trigger: 'blur'
                }],
                teacherPhoto: [{
                    required: true,
                    message: '请添加头像',
                    trigger: 'blur'
                }],
            },
            action: this.$ip + '/picture',
            imgUrl: ''
        }
    },
    components: {
        editor
    },
    methods: {
        coverBeforeUpload(file) {
            let isRightSize = file.size / 1024 / 1024 / 1024 < 2
            if (!isRightSize) {
                this.$message.error('文件大小超过 2GB')
            }
            let isAccept = new RegExp('image/*').test(file.type)
            if (!isAccept) {
                this.$message.error('应该选择image/*类型的文件')
            }
            return isRightSize && isAccept
        },
        handleCoverSuccess(res, file) {
            if (res.code != 200) {
                this.$message.error('上传失败！')
                return
            }
            this.formData.teacherPhoto = res.data
            this.imgUrl = this.$ip + res.data
        },
        handelConfirm() {
            this.$refs['elForm'].validate(valid => {
                if (!valid) return
                this.uploadCourse()
            })
        },
        uploadCourse() {
            req('/teacher', 'post', this.formData)
                .then((response) => {
                    if (response.code != 200) {
                        this.$message.error('上传失败')
                        return
                    }
                    this.$message.success('上传成功，请等待审核')
                    this.$router.push('/home')
                })
        },
        goBack() {
            this.$router.push('/home')
        }
    },
    mounted() {
    },
    beforeRouteEnter(to, from, next) {
        req('/user/teacher')
            .then((response) => {
                if (!response.data) {
                    next();
                    NProgress.done()
                }
                else next(_this => {
                    NProgress.done()
                    _this.$router.push(from.path)
                    _this.$message({
                        message: "请等待审核",
                        type: 'warning',
                    })
                })
                NProgress.done()
            })
        NProgress.done()
        // 此处处理在组件渲染之前，先跳转到其他地址
        // 注意next之前不能调用this获取实例
        // next(_this=>{
        //   console.log(_this);//此处_this即为组件实例
        // });
    },
}
</script>

<style scoped>
.header {
    height: 60px;
    align-items: center;
    background: #FFF;
    box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
}

.TCContainer {
    width: 1160px;
    margin: 30px auto;
    background: #FFF;
    border-radius: 15px;
}

.TC {
    padding: 20px;
}

::v-deep .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    border-radius: 50%;
}

::v-deep .avatar-uploader .el-upload:hover {
    border-color: #14BA4C;
}

.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
}

.avatar {
    width: 178px;
    height: 178px;
    display: block;
}

.footer {
    display: flex;
    margin: 10px auto;
    justify-content: center;
}
</style>