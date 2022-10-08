<template>
    <div class="Container">
        <div class="card">
            <div>
                <img :src="cover || require('@/assets/photo/course/default.gif')"
                    :onerror='`this.src="${require("@/assets/photo/course/default.gif")}"`' class="image">
            </div>
            <div class="description">
                <div class="t1">
                    <span class="title">
                        {{ data.title }}
                    </span>
                    <span>
                        价格:
                        <div>
                            <span class="price" :id="data.id" text="Price" :class="isFree ? 'Green' : ''">
                                {{ data.price }}元
                            </span>
                        </div>
                    </span>
                </div>

                <div class="info">
                    <!-- <span class="el-icon-s-goods">
                        购买 
                    </span>
                    <span class="el-icon-star-on">
                        收藏
                    </span> -->
                    <span class="el-icon-time">
                        发布时间：{{ data.time }}
                    </span>
                </div>
            </div>
        </div>
        <div class="options">
            <el-button @click="() => { this.$router.push('/course/' + this.data.id) }">查看</el-button>
            <el-button @click="() => { this.$router.push('courseManage/addCourse/' + this.data.id) }">章节</el-button>
        </div>
    </div>
</template>

<script>
export default {
    name: 'courseManageCard',
    props: [
        'data'
    ],
    data() {
        return {
            cover: '',
            isFree: false,
        }
    },
    created() {
        // console.log(this.data)

        this.cover = this.$ip + this.data.titlePic
    },
    mounted() {
        if (this.data.price === 0) {
            this.isFree = true
            let t = document.getElementById(this.data.id)
            t.innerText = "免费"
        }
    }
}
</script>

<style scoped>
.Container {
    display: flex;
    margin: 20px;
}

.card {
    display: flex;
    align-items: center;
    width: 800px;
    height: 170px;
    margin: 0 auto;
}

.options {
    display: flex;
    width: 300px;
    margin: 0 auto;
    align-items: center;
    justify-content: space-evenly
}

.image {
    padding: 10px;
    margin-left: 15px;
    height: 150px;
    width: 200px;
    border-radius: 15px;
    border: 1px solid #f0f0f0;
    box-shadow: 5px 5px 0 rgb(0 0 0 / 4%);
}

.description {
    display: flex;
    margin: 0 20px;
    width: 100%;
    height: 140px;
    flex-direction: column;
    justify-content: space-between;
}

.t1 {
    display: flex;
    height: 100%;
}

.title {
    overflow: hidden;
    height: 100%;
    width: 80%;
    padding: 0 15px;
}

.info {
    padding: 0 15px;
}

.price {
    font-size: 20px;
    color: red;
}

.Green {
    color: #00CC7E !important;
}
</style>