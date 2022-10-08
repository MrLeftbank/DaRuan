<template>
    <div class="CFContainer">
        <div class="CF">
            <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px"
                label-position="left">
                <el-form-item label="课程名字" prop="title">
                    <el-input v-model="formData.title" placeholder="请输入课程名" clearable :style="{ width: '100%' }">
                    </el-input>
                </el-form-item>
                <el-form-item label="课程类型" prop="domain">
                    <el-cascader class="price" :show-all-levels="false" v-model="formData.domain" :options="options"
                        :props="props" clearable>
                    </el-cascader>
                </el-form-item>
                <el-form-item label="课程价格" prop="price">
                    <el-input type="number" class="price" v-model="formData.price" placeholder="请输入课程价格" clearable>
                    </el-input>
                </el-form-item>
                <el-form-item label="课程介绍" prop="intro">
                    <editor v-model="formData.intro"></editor>
                </el-form-item>
                <el-form-item label="课程封面" prop="titlePic">
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
</template>

<script>
import editor from '@/components/editor'
import { req } from '@/api/request'
let temp = 0;
export default {
    name: 'courseInfo',
    data() {
        return {
            formData: {
                titlePic: '',
                title: '',
                price: '',
                intro: '',
                domain: '',
            },
            props: {
                multiple: true,
                value: 'id',
                label: 'title',
                leaf: 'parentId',
                checkStrictly: true,
                lazy: true,
                emitPath: false,
                lazyLoad(node, resolve) {
                    if (node.value)
                        req('/domain/' + node.value + '/domain')
                            .then((response) => {
                                if (response.code !== 200) {
                                    this.$message.error('获取子类失败')
                                    return
                                }
                                resolve(response.data)
                            })
                    // 通过调用resolve将子节点数据返回，通知组件数据加载完成
                }
            },
            options: [],
            rules: {
                title: [{
                    required: true,
                    message: '请添加课程名',
                    trigger: 'blur'
                }],
                price: [{
                    required: true,
                    message: '请添加课程价格',
                    trigger: 'blur'
                }],
                domain: [{
                    required: true,
                    message: '请添加课程分类',
                    trigger: 'blur'
                }],
                intro: [{
                    required: true,
                    message: '请输入课程简介',
                    trigger: 'blur'
                }],
                titlePic: [{
                    required: true,
                    message: '请添加课程封面',
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
            this.formData.titlePic = res.data
            this.imgUrl = this.$ip + res.data
        },
        handelConfirm() {
            this.$refs['elForm'].validate(valid => {
                if (!valid) return
                this.uploadCourse()
            })
            // this.formList.append('formData', JSON.stringify(this.formData))
            // this.uploadCourse()
        },
        uploadCourse() {
            let data = new FormData()
            data.append('course', JSON.stringify(this.formData))
            data.append('domain', JSON.stringify(this.formData.domain))
            req('/course', 'post', data)
                .then((response) => {
                    if (response.code != 200) {
                        this.$message.error('发布失败')
                        return
                    }
                    this.$router.push('/courseManage')
                    this.$message.success('发布成功')
                })
        },
        getImage(file) {
            this.cover = new FormData();
            this.cover.append('pic', file.file);
        },
        getFirstDomain() {
            req('/parent-domain')
                .then((response) => {
                    if (response.code !== 200) {
                        this.$message.error('获取分类失败')
                        return
                    }
                    this.options = response.data
                })
        }
    },
    created() {
        this.getFirstDomain()
    }

}
</script>

<style scoped>
.CFContainer {
    width: 1160px;
    background-color: #FFF;
    margin: 30px auto;
    border-radius: 15px;
    min-height: 800px;
    border: 1px solid #f0f0f0;
    box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
}

.CF {
    padding: 20px;
}

.price {
    width: 220px;
}

::v-deep .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    border-radius: 10px;
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

::v-deep input::-webkit-outer-spin-button,
::v-deep input::-webkit-inner-spin-button {
    -webkit-appearance: none !important;
}

::v-deep input[type=‘number’] {
    -moz-appearance: textfield !important;
}
</style>