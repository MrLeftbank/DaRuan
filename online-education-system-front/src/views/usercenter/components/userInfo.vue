<template>
    <div id="infoList" class="infoList_container">

        <div class="sorter_container">
            <div class="sorter_title">
                <h2 class="tac">
                    <span>我的信息</span>
                </h2>
            </div>
            <el-divider class="divider"></el-divider>
        </div>
        <div class="c_container">
            <el-descriptions class="margin-top" :column="1" border>
                <template slot="title">
                    编辑模式：<el-switch v-model="inputEnable"></el-switch>
                </template>
                <template slot="extra">
                    <el-button size="small" class="infoButton" :disabled="!inputEnable" @click="updateUser()">提交更改
                    </el-button>
                </template>
                <el-descriptions-item>
                    <template slot="label">
                        <i class="fa fa-user-o"></i>
                        昵称
                    </template>
                    <span v-if="!inputEnable">{{ user.name }}</span>
                    <el-input v-else id="name" class="infoInput" v-model="user.name"></el-input>
                </el-descriptions-item>
                <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-mobile-phone"></i>
                        手机号
                    </template>
                    <span v-if="!inputEnable">{{ user.phone }}</span>
                    <el-input v-else id="phone" class="infoInput" :disabled="!inputEnable" v-model="user.phone">
                    </el-input>
                </el-descriptions-item>
                <el-descriptions-item>
                    <template slot="label">
                        <i class="fa fa-envelope-o"></i>
                        邮箱
                    </template>
                    <span v-if="!inputEnable">{{ user.email }}</span>
                    <el-input v-else id="email" class="infoInput" v-model="user.email"></el-input>
                </el-descriptions-item>
                <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-tickets"></i>
                        性别
                    </template>
                    <span v-if="!inputEnable" size="small">{{ user.gender }}</span>
                    <el-input v-else id="gender" class="infoInput" v-model="user.gender"></el-input>
                </el-descriptions-item>
                <el-descriptions-item>
                    <template slot="label">
                        <i class="fa fa-birthday-cake"></i>
                        生日
                    </template>
                    <span v-if="!inputEnable"> {{ user.birthday }}</span>
                    <el-date-picker v-else v-model="user.birthday" type="date" placeholder="选择日期">
                    </el-date-picker>
                </el-descriptions-item>
            </el-descriptions>
            <div>
                <div class="image">
                    修改头像：
                    <!-- action 是 发送的url-->
                    <el-upload class="avatar-uploader" :action="action" :show-file-list="false"
                        :on-success="handleAvatarSuccess">
                        <img v-if="newPhotoUrl" :src="ip + user.photoUrl" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </div>
                <div>
                    <div class="new">
                        修改密码：
                        <el-button size="small" :disabled="!inputEnable" class="infoButton"
                            @click="updateUserPassword(user.password)">更改密码
                        </el-button>
                    </div>
                    <el-input id="newPassword" :disabled="!inputEnable" v-model="user.password">
                    </el-input>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { req } from '@/api/request'
import store from '@/store'
export default {
    name: "userInfo",
    data() {
        return {
            newPhotoUrl: false,
            inputEnable: false,
            user: {
                "id": 0,
                "email": null,
                "balance": null,
                "name": store.getters.name,
                "phone": null,
                "gender": null,
                "birthday": null,
                "photoUrl": store.getters.avatar,
                "password": '',
            },
            action: this.$ip + '/picture',
            ip: this.$ip
        }
    },
    mounted() {
        this.getUserInfo();
    },
    methods: {
        switchEdit() {
            this.inputDisable = !this.inputDisable;
        },
        getUserInfo() {
            req('user', 'get').then((res) => {
                // console.log(res);
                this.user = res.data;
            })
        },
        handleAvatarSuccess(res, file) {
            this.user.photoUrl = res.data;
            this.newPhotoUrl = true;
            this.updateUser();
        },
        updateUser() {
            //req为带token和data的请求
            req('user', 'put', this.user).then((res) => {
                this.getUserInfo();
            })
            this.inputEnable = false;
        },
        async updateUserPassword(password) {
            if (!password) {
                this.$message.error("请输入密码");
                return
            }
            //req为带token和data的请求
            if (password.length > 20) {
                this.$message.error("密码长度不能大于20");
                return
            } else if (password.length < 6) {
                this.$message.error("密码长度不能小于6");
                return
            }
            if (!(await this._box())) return;

            req('user?password=' + password, 'put', this.user)
                .then((res) => {
                    if (res.code == 200) {
                        this.$message.success("修改成功");
                        this.getUserInfo();
                        this.inputEnable = false;
                        this.user.password = ''
                    }
                })
        }

    }
}

</script>

<style scoped>
.infoList_container {
    display: flex;
    flex-direction: column;
    height: 100%;
}

.sorter_container {
    margin: 0 auto;
}

.sorter_title {
    overflow: hidden;
    clear: both;
    margin: 40px 0 30px;
    display: block;
    margin-bottom: -15px;
}

.tac {
    text-align: center;
    float: left;
    font-size: 26px;
}

.c_container {
    width: 400px;
    background-color: #FFF;
    padding: 20px;
    margin: 20px auto;
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

.divider {
    margin: 0;
    margin-top: 24px;
}

.image {
    display: flex;
    margin-top: 10px;
}

::v-deep .el-descriptions-row {
    height: 65px;
}

.infoButton {
    margin-bottom: 10px;
}

.new {
    display: flex;
    justify-content: space-between;
}
</style>