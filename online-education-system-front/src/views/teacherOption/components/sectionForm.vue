<template>
    <div>
        <el-dialog :visible.sync="innerVisible" v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose"
            title="小节信息">
            <el-row :gutter="15">
                <el-form ref="sectionForm" :model="formData" :rules="rules" size="medium" label-width="100px"
                    label-position="left">
                    <el-col :span="12">
                        <el-form-item label="小节名" prop="title">
                            <el-input v-model="formData.title" placeholder="请输入小节名" clearable>
                            </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="视频课件" prop="video">
                            <el-upload name="multipartFiles" ref="video" :file-list="formData.videoList" action=""
                                :before-upload="videoBeforeUpload" accept="video/*" :limit="1"
                                :http-request="importVideo" :on-remove="() => { this.formData.videoList = [] }">
                                <el-button size="small" type="primary" icon="el-icon-upload">点击上传</el-button>
                            </el-upload>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="文档课件" prop="file">
                            <el-upload ref="file" :file-list="formData.fileList" action=""
                                :before-upload="fileBeforeUpload" accept=".ppt,.pdf,.doc,.docx" :limit="1"
                                :http-request="importFile" :on-remove="() => { this.formData.fileList = [] }">
                                <el-button size="small" type="primary" icon="el-icon-upload">点击上传</el-button>
                            </el-upload>
                        </el-form-item>
                    </el-col>
                </el-form>
            </el-row>
            <div slot="footer">
                <!-- <el-button @click="test">取消</el-button> -->
                <el-button @click="close">取消</el-button>
                <el-button :disabled="!able" type="primary" @click="handelConfirm">确定</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
import { req } from "@/api/request"
export default {
    inheritAttrs: false,
    components: {},
    props: [
        'visible',
        'data'
    ],
    data() {
        return {
            formData: {
                title: '',
                videoList: undefined,
                fileList: undefined,
                number: undefined,
                id: undefined,
            },
            rules: {
                title: [{
                    required: true,
                    message: '请输入小节名',
                    trigger: 'blur',
                }],
            },
            fileList: undefined,
            video: '',
            file: false,
            able: true,
        }
    },
    computed: {
        innerVisible: {
            get: function () {
                return this.visible
            },
            set: function (val) {
                this.$emit('update:visible', val)
            }
        }
    },
    watch: {},
    created() { },
    mounted() { this.fileList = new FormData() },
    methods: {
        test() { console.log(this.formData) },
        onOpen() { this.getNumber() },
        onClose() {
            this.formData.videoList = []
            this.formData.fileList = [];
            this.$refs['sectionForm'].resetFields()
            this.fileList = new FormData()
        },
        close() {
            this.$emit('update:visible', false)
        },
        async handelConfirm() {
            this.fileList = new FormData()
            this.able = false
            this.$refs['sectionForm'].validate(valid => {
                if (!valid) {
                    this.able = true
                    return
                }
                if (this.file)
                    this.$notify.info({
                        title: '提醒',
                        message: '请耐心等待文件上传',
                        duration: 1000,
                    });
                this.fileList.append('file', this.formData.videoList)
                this.fileList.append('doc', this.formData.fileList)
                this.fileList.append('title', this.formData.title)
                this.fileList.append('number', this.formData.number)
                req('/chapter/' + this.data.id + '/section/', 'post', this.fileList, '', 30000000)
                    .then(res => {
                        if (res.code != 200) {
                            this.able = true
                            this.$message.error('上传失败')
                            return
                        }
                        this.$message.success('上传成功')
                        this.formData.id = res.data
                        this.formData.number++
                        this.$emit('section', this.formData)
                        this.close()
                        this.able = true
                    })
            })
        },
        videoBeforeUpload(file) {
            let isRightSize = file.size / 1024 / 1024 / 1024 < 2
            if (!isRightSize) {
                this.$message.error('文件大小超过 2GB')
            }
            let isAccept = new RegExp('video/*').test(file.type)
            if (!isAccept) {
                this.$message.error('应该选择video/*类型的文件')
            }
            return isRightSize && isAccept
        },
        fileBeforeUpload(file) {
            let isRightSize = file.size / 1024 / 1024 / 1024 < 2
            if (!isRightSize) {
                this.$message.error('文件大小超过 2GB')
            }
            let isAccept = new RegExp('application/*').test(file.type)
            if (!isAccept) {
                this.$message.error('应该选择.ppt.pdf,.doc,.docx类型的文件')
            }
            return isRightSize && isAccept
        },
        importVideo(val) {
            this.file = true
            this.formData.videoList = val.file
        },
        importFile(val) {
            this.file = true
            this.formData.fileList = val.file
        },
        getNumber() {
            req('/chapter/' + this.data.id + '/section/')
                .then(res => {
                    if (res.data.length == 0) { this.formData.number = 1 }
                    else { this.formData.number = res.data[res.data.length - 1].number + 1 }
                })
        }
    }
}

</script>
<style>
.el-upload__tip {
    line-height: 1.2;
}
</style>
