<template>
    <div id="player" class="player">
        <div id="player-con"></div>
    </div>
</template>
<script type="text/javascript" charset="utf-8">
import { req } from "@/api/request"
export default {
    name: 'player',
    props: [
        'url'
    ],
    data() {
        return {
            // Url: "//player.alicdn.com/video/aliyunmedia.mp4"
            Url: ''
        }
    },
    methods: {
        createPlayer(url) {
            new Aliplayer({
                "id": "player-con",
                "source": this.Url,
                "width": "100%",
                "height": "610px",
                "autoplay": true,
                "isLive": false,
                "rePlay": false,
                "playsinline": true,
                "preload": true,
                "controlBarVisibility": "hover",
                "useH5Prism": true,
                "qualitySort": 'asc', // 清晰度排序
                // id: 'player-con',
                // vid: this.vid, // 视频id
                // playauth: this.playAuth, // 播放凭证
                // encryptType: '1', // 如果播放加密视频，则需设置encryptType=1，非加密视频无需设置此项
                // width: '100%',
                // height: '500px'
            }, function (player) {
                // console.log('播放器创建成功')
            })
        },
        getVideo() {
            // console.log(this.url)
            req('video?vId=' + this.url)
                .then(res => {
                    // console.log(res)

                    if (res.code != 200) {
                        this.$message.error('没有视频课件')
                        this.createPlayer()
                        return
                    }
                    this.Url = res.data[0]
                    this.createPlayer()
                })
        },
    },
    created() {
        this.getVideo()
    }
}
</script>

<style scoped>
.player {
    background-color: yellowgreen;
    padding: 5px;
    border: 1px solid #f0f0f0;
    box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
    border-radius: 5px;
}
</style>