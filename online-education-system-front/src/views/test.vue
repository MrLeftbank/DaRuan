<template>
    <!-- <Card :data="data"></Card> -->
    <!-- <Card id="1" photo="test" name="test" info="testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest" ></Card> -->
    <!-- <home></home> -->
    <!-- <chapterPage :data="data"></chapterPage> -->
    <div>
        <addChapter></addChapter>
        <!-- <el-upload ref="file" :file-list="test" action="/video" :auto-upload="true" accept=".pdf,.doc,.docx" :limit="1"
            :on-success="fileSuccess">
            <el-button size="small" type="primary" icon="el-icon-upload">点击上传</el-button>
        </el-upload> -->
        <!-- <courseFrom></courseFrom> -->
    </div>

    <!-- <userCenterPage :data="data"></userCenterPage> -->
</template>

<script>
import Card from '@/components/CourseCard.vue'
import home from '@/views/home/home'
import chapterPage from '@/views/course/chapterPage'
import addChapter from '@/views/teacherOption/addChapter'
// import courseFrom from '@/views/user/components/courseFrom'
import userCenterPage from '@/views/usercenter/userCenterPage'

export default {
    name: 'test',
    props: [
        'id'
    ],
    data() {
        return {
            data: {
                id: '1',
                title: 'test',
                cover: "test",
                star: "30",
                price: "10"
            },
            test: []
        }
    },
    components: {
        home,
        Card,
        chapterPage,
        addChapter,
        // courseFrom
    },
    methods: {
        fileSuccess(res, file, fileList) {
            this.$notify.success({
                title: '成功',
                message: `文件上传成功`
            })
        }
    },
    mounted() {
        let loginForm = {
            username: '',
            password: '',
        }
        test(loginForm)
    }
}
</script>