<template>
    <div id="CourseCard" class="container" @click="toCourse">
        <el-card :body-style="{ padding: '0px', height: '100%' }">
            <img :src="cover || require('@/assets/photo/course/default.gif')"
                :onerror='`this.src="${require("@/assets/photo/course/default.gif")}"`' class="image">
            <div style="padding: 14px;">
                <div class="title">
                    <span class="a">
                        {{ data.title }}
                    </span>
                </div>

                <div class="info">
                    <div v-if="data.count" class="left">收藏人数：{{ data.count }}</div>
                    <div class="right" :class="isFree ? 'Green' : ''">￥{{ course_price }}</div>
                </div>
            </div>
        </el-card>
    </div>
</template>

<script>
export default {
    name: 'CourseCard',
    props: ['data'],
    data() {
        return {
            isFree: false,
            course_price: this.data.price,
            cover: this.$ip + this.data.titlePic
        }
    },
    methods: {
        toCourse() {
            this.$router.push({ path: '/course/' + this.data.id })
        },
        change(val) {
            this.course_price = val
        }
    },
    mounted() {
        if (this.data.price === 0) {
            this.isFree = true
            this.change('免费')
        }
    }
}
</script>

<style scoped>
.container {
    width: 250px;
    height: 250px;
    float: left;
    margin: 20px;
    cursor: pointer;
    word-break: break-all;
    word-wrap: break-word;
}

.image {
    width: 250px;
    height: 150px;
    display: block;
}

.el-card {
    width: 100%;
    height: 100%;
    border-radius: 10px !important;
}

.el-card:hover {
    border: 1px solid #68cb9b;
    box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.2);
}

.el-card:hover .a {
    color: #68cb9b;
}

.title {
    height: 50px;
    font-size: 18px;
    line-height: 25px;
    overflow: hidden;
    color: #333;
}

.a {
    letter-spacing: 2px;
}

.a:hover {
    text-decoration: underline;
}

.info {
    display: flex;
    justify-content: space-between;
    margin-top: 7px;
    height: 20px;
    width: 100%;
}

.left {
    font-size: 12px;
    line-height: 20px;
}

.right {
    display: inline-block;
    color: #FF7A3E;
    font-size: 30px;
    min-width: 50px;
    line-height: 20px;
}

.Green {
    color: #00CC7E;
}
</style>