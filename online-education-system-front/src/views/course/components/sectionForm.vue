<template>
    <div id="sectionForm" class="section-container">
        <div v-for="(section, section_index) in SectionList" :key="section_index" class="form1">
            <span class="span_form" @click="toCourseWare(section.id)"><i class="el-icon-caret-right iconForm"></i>{{
                    section.title
            }}</span>
            <el-divider class="divider"></el-divider>
        </div>
    </div>
</template>

<script>
import { req } from "@/api/request"
export default {
    name: "sectionForm",
    props: [
        'chapter_id',
        'course_id'
    ],
    data() {
        return {
            SectionList: []
        };
    },
    methods: {
        getSection() {
            req('/chapter/' + this.chapter_id + '/section/')
                .then(res => {
                    if (res.code != 200) {
                        this.$message.error('获取失败')
                        return
                    }
                    this.SectionList = res.data
                })
        },
        toCourseWare(val) {
            this.$router.push({ path: '/course/' + this.course_id + '/chapterPage/' + this.chapter_id + '/section/' + val })
        }
    },
    mounted() {
        this.getSection()
    }
}
</script>

<style scoped>
.section-container {}

.form1 {
    margin-top: 15px;
}

.span_form {
    padding-left: 30px;
    font-size: 16px;
    cursor: pointer;
}

.span_form:hover {
    color: #68cb9b;
}

.divider {
    margin: 0;
    margin-top: 15px
}

.iconForm {
    color: #68cb9b;
    padding-right: 10px;
}
</style>