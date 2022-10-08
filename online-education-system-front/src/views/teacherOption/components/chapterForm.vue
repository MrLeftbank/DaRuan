<template>
    <div>
        <el-dialog :visible.sync="innerVisible" @open="onOpen" @close="onClose" title="章节详细">
            <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px"
                label-position="left">
                <el-form-item label="章节名" prop="title">
                    <el-input v-model="formData.title" placeholder="请输入章节名" clearable>
                    </el-input>
                </el-form-item>
            </el-form>
            <div slot="footer">
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
                id: undefined,
                title: '',
                number: undefined,
            },
            rules: {
                title: [{
                    required: true,
                    message: '请输入章节名',
                    trigger: 'blur'
                }],
            },
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
    created() { },
    mounted() { },
    methods: {
        onOpen() { },
        onClose() {
            this.$refs['elForm'].resetFields()
        },
        close() {
            this.$emit('update:visible', false)
        },
        async handelConfirm() {
            this.able = false,
                this.$refs['elForm'].validate(valid => {
                    if (!valid) {
                        this.able = true
                        return
                    }
                    // this.data.label = this.formData.field101
                    this.formData.number = this.data.num
                    this.uploadChapter()
                    this.able = true
                })
        },
        async uploadChapter() {
            await req('/course/' + this.data.course_id + '/chapter/', 'post', this.formData)
                .then(res => {
                    if (res.code != 200) {
                        this.able = true
                        this.$message.error('上传失败')
                        return
                    }
                    this.$message.success('上传成功')
                    this.formData.id = res.data
                    this.$emit('chapter', this.formData)
                    this.close()
                })
        }
    },
    // watch: {
    //     'data.num'(newVal, oldVal) {
    //         this.formData.number = this.data.num
    //     },
    //     deep: true
    // }
}

</script>
<style>
</style>
