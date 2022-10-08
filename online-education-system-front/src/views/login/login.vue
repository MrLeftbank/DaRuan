<template>
    <div class="dashboard">
        <div class="container">
            <div class="form-box">
                <div class="register-box hidden">
                    <h1>register</h1>
                    <el-form ref="registerForm" :model="registerForm" :rules="registerRules" class="register-box">
                        <el-form-item prop="username">
                            <el-input type="text" v-model="registerForm.username" placeholder="用户名"
                                @keyup.enter.native="handleRegister"></el-input>
                        </el-form-item>
                        <!-- prop="email" -->
                        <el-form-item>
                            <el-input type="email" v-model="registerForm.email" placeholder="邮箱"
                                @keyup.enter.native="handleRegister"></el-input>
                        </el-form-item>
                        <el-form-item prop="password">
                            <el-input type="password" v-model="registerForm.password" placeholder="密码" show-password
                                @keyup.enter.native="handleRegister">
                            </el-input>
                        </el-form-item>
                        <el-form-item prop="secondPassword">
                            <el-input type="password" v-model="registerForm.secondPassword" placeholder="确认密码"
                                @keyup.enter.native="handleRegister" ref="registerSecondPassword" show-password>
                            </el-input>
                        </el-form-item>
                        <el-button :loading="loading" type="primary" @click="handleRegister">注册</el-button>
                    </el-form>
                </div>

                <div class="login-box">
                    <h1>login</h1>
                    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-box">
                        <el-form-item prop="username">
                            <el-input type="text" v-model="loginForm.username" placeholder="用户名" tabindex="1"
                                ref="username" auto-complete="on" @keyup.enter.native="handleLogin"></el-input>
                        </el-form-item>
                        <el-form-item prop="password">
                            <el-input type="password" v-model="loginForm.password" placeholder="密码" ref="loginPassword"
                                tabindex="2" auto-complete="on" show-password @keyup.enter.native="handleLogin">
                            </el-input>
                        </el-form-item>
                        <el-button :loading="loading" type="primary" @click="handleLogin">登录</el-button>
                    </el-form>
                </div>
            </div>
            <div class="con-box left">
                <h2>欢迎到来<span></span></h2>
                <p>已有账号</p>
                <button id="login" @click="toLogin">去登录</button>
            </div>
            <div class="con-box right">
                <h2>欢迎到来<span></span></h2>
                <p>没有账号</p>
                <button id="register" @click="toRegister">去注册</button>
            </div>
        </div>
    </div>
</template>

<script>
import $ from 'jquery';
import { req } from '@/api/request';
export default {
    name: 'login',
    data() {
        var validatePass2 = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
                // } else if (value < 6) {
                //     callback(new Error('长度需大于6'));
            } else if (value !== this.registerForm.password) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        return {
            loginForm: {
                username: '',
                password: '',
            },
            registerForm: {
                username: '',
                email: '',
                password: '',
                secondPassword: '',
            },
            loginRules: {
                username: [{ required: true, message: '请输⼊⽤户名称', trigger: 'blur' }],
                password: [{ required: true, message: '请输⼊密码', trigger: 'blur' }],
            },
            registerRules: {
                username: [{ required: true, message: '请输⼊⽤户名称', trigger: 'blur' },
                { min: 4, message: '长度需大于4 ', trigger: 'blur' },
                { max: 20, message: '长度不能大于20 ', trigger: 'blur' }],
                email: [{ required: true, message: '请输入邮箱地址', trigger: 'blur' },
                { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }],
                password: [{ required: true, message: '请输⼊密码', trigger: 'blur' },
                { min: 8, message: '长度需大于8 ', trigger: 'blur' },
                { max: 20, message: '长度不能大于20 ', trigger: 'blur' }],
                secondPassword: [{ required: true, validator: validatePass2, trigger: 'blur' }]
            },
            loading: false,
            passwordType: 'password',
            passwordType1: 'password',
            redirect: undefined,
            isSelected1: false,
            isSelected2: false,
        }
    },
    methods: {
        toLogin() {
            $(".form-box").css("transform", "translateX(0%)")
            $(".register-box").addClass('hidden');
            $(".login-box").removeClass('hidden');
        },
        toRegister() {
            $(".form-box").css("transform", "translateX(80%)")
            $(".login-box").addClass('hidden');
            $(".register-box").removeClass('hidden');
        },
        dis(val) {
            if (val === 1) {
                this.isSelected1 = false;
            } else this.isSelected2 = false;
        },
        show(val) {
            if (val === 1) {
                this.isSelected1 = true;
            } else this.isSelected2 = true;
        },
        showPwd(REF) {
            console.log(REF)
            if (REF !== this.$refs.registerSecondPassword) {
                if (this.passwordType === 'password') {
                    this.passwordType = ''
                } else {
                    this.passwordType = 'password'
                }
            } else {
                if (this.passwordType1 === 'password') {
                    this.passwordType1 = ''
                } else {
                    this.passwordType1 = 'password'
                }
            }
            this.$nextTick(() => {
                REF.focus()
            })
        },
        handleLogin() {
            this.$refs.loginForm.validate(valid => {
                if (valid) {
                    this.loading = true
                    this.$store.dispatch('user/login', this.loginForm).then((res) => {
                        if (res.code == 605) {
                            this.$message.error("你的账户被冻结，无法登陆");
                            this.loading = false
                            return
                        }
                        if (res.code != 200) {
                            this.$message.error(res.message);
                            this.loading = false
                            return
                        }
                        this.$router.push({ path: this.redirect || '/home' })
                        this.loading = false
                    }).catch(() => {
                        this.loading = false
                    })
                } else {
                    console.log('error submit!!')
                    return false
                }
            })
        },
        handleRegister() {
            this.$refs.registerForm.validate(valid => {
                if (valid) {
                    this.loading = true
                    req('user/register?username=' + this.registerForm.username + '&password=' + this.registerForm.password, 'post')
                        .then((response) => {
                            if (response.code != 200) {
                                this.$message.error(response.message);
                                this.loading = false
                                return
                            }
                            this.$message.success("注册成功")
                            this.loginForm.username = this.registerForm.username
                            this.loading = false
                            this.toLogin()
                        }).catch(() => {
                            this.loading = false
                        })
                } else {
                    console.log('error submit!!')
                    return false
                }
            })
        }
    },
    watch: {
        $route: {
            handler: function (route) {
                this.redirect = route.query && route.query.redirect
            },
            immediate: true
        }
    }
}
</script>

<style>
body {
    background: aliceblue;
}
</style>

<style scoped>
* {
    /* 全局配置 */
    margin: 0;
    padding: 0;
}

.dashboard {
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
}

.container {
    background-color: #fff;
    width: 650px;
    height: 415px;
    border-radius: 5px;
    box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.1);
    position: relative;
}

.form-box {
    position: absolute;
    top: -10%;
    left: 5%;
    background-color: #d3b7d8;
    width: 320px;
    height: 500px;
    border-radius: 5px;
    box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 2;
    transition: 0.5s ease-in-out;
}

.register-box,
.login-box {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
}

.hidden {
    display: none;
    transition: 0.5s;
}

h1 {
    text-align: center;
    margin-bottom: 25px;
    /* 大写 */
    text-transform: uppercase;
    color: #fff;
    /* 字间距 */
    letter-spacing: 5px;
}

.el-input {
    /* width: 70%; */
    border: none;
    /* 下边框样式 */

    padding: 5px 0;
    /* text-indent: 10px; */
    margin: 8px 0;
    font-size: 14px;
    letter-spacing: 2px;
}

::v-deep .el-input__inner {
    background-color: transparent;
    color: #fff;
    border: none;
    /* 下边框样式 */
    border-bottom: 1px solid rgba(255, 255, 255, 0.4);
}

::v-deep .el-input__inner::placeholder {
    color: #fff;
}

::v-deep .el-input__inner:focus {
    color: #a262ad;
    outline: none;
    border-bottom: 1px solid #a262ad80;
    transition: 0.5s;
}

::v-deep .el-input__inner:focus::placeholder {
    opacity: 0;
}

.el-form-item {
    margin-bottom: 0;
    width: 70%;
}

::v-deep .el-form-item__error {
    margin-left: 15px;
    line-height: 0;
}

::v-deep .el-button [class*=el-icon-]+span {
    margin-right: 20px;
}

::v-deep .el-input__suffix {
    height: 40px;
    color: black;
    margin-top: 5px;
}

input {
    background-color: transparent;
    width: 70%;
    color: #fff;
    border: none;
    /* 下边框样式 */
    border-bottom: 1px solid rgba(255, 255, 255, 0.4);
    padding: 10px 0;
    text-indent: 10px;
    margin: 8px 0;
    font-size: 14px;
    letter-spacing: 2px;
}

input::placeholder {
    color: #fff;
}

input:focus {
    color: #a262ad;
    outline: none;
    border-bottom: 1px solid #a262ad80;
    transition: 0.5s;
}

input:focus::placeholder {
    opacity: 0;
}

.form-box button {
    width: 70%;
    margin-top: 35px;
    background-color: #f6f6f6;
    outline: none;
    border-radius: 8px;
    padding: 13px;
    color: #a262ad;
    letter-spacing: 2px;
    border: none;
    cursor: pointer;
}

::v-deep .el-input__icon {
    color: #000000 !important;
}

.con-box {
    width: 50%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
}

.con-box.left {
    left: -2%;
}

.con-box.right {
    right: -2%;
}

.con-box h2 {
    color: #8e9aaf;
    font-size: 25px;
    font-weight: bold;
    letter-spacing: 3px;
    text-align: center;
    margin-bottom: 4px;
}

.con-box p {
    font-size: 12px;
    letter-spacing: 2px;
    color: #8e9aaf;
    text-align: center;
}

.con-box span {
    color: #d3b7d8;
}

.con-box img {
    width: 150px;
    height: 150px;
    opacity: 0.9;
    margin: 40px 0;
}

.con-box button {
    margin-top: 3%;
    background-color: #fff;
    color: #a262ad;
    border: 1px solid #d3b7d8;
    padding: 6px 10px;
    border-radius: 5px;
    letter-spacing: 1px;
    outline: none;
    cursor: pointer;
}

.con-box button:hover {
    background-color: #d3b7d8;
    color: #fff;
}


.form-box button:hover {
    background-color: #a262ad;
    color: #f6f6f6;
    transition: background-color 0.5s ease;
}

.form-box button:active {
    background-color: #e00510;
    color: #160c0c;
    transition: background-color 0.5s ease;
}

.con-box button:hover {
    background-color: #a262ad;
    color: #f6f6f6;
    transition: background-color 0.5s ease;
}

.con-box button:active {
    background-color: #e00510;
    color: #160c0c;
    transition: background-color 0.5s ease;
}
</style>
