import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['login', 'course', 'teacher', 'article', 'question', 'home', 'coursePage', 'questionPage', 'teacherDetail', 'articlePage', 'questionPage'] // no redirect whitelist

const teacherList = ['addCourse', 'addChapter', 'courseManage']

router.beforeEach(async (to, from, next) => {
    // start progress bar
    NProgress.start()
    // determine whether the user has logged in
    const hasToken = getToken()
    const hasGetUserInfo = store.getters.name
    if (to.path == from.path) {
        NProgress.done()
        return
    }
    if (hasToken) {
        if (to.path === '/login') {
            // if is logged in, redirect to the home page
            next({ path: '/' })
            NProgress.done()
        } else {
            // next()
            if (hasGetUserInfo != '请登录') {
                next()
            } else {
                try {
                    //             // get user info
                    await store.dispatch('user/getInfo')
                    await store.dispatch('user/getIsTeacher')
                    // console.log(store.getters.isTeacher)
                    if (teacherList.indexOf(to.name) !== -1 && !store.getters.isTeacher) {
                        router.push('/')
                        NProgress.done()
                        Message({
                            message: '你不是教师，无法访问！',
                            type: 'error',
                            duration: 3 * 1000
                        })
                        return
                    } else if (to.path === '/teacherCertificate' && store.getters.isTeacher) {
                        router.push('/')
                        NProgress.done()
                        Message({
                            message: '你已经是教师！',
                            type: 'error',
                            duration: 3 * 1000
                        })
                        return
                    }
                    next()
                } catch (error) {
                    //             // remove token and go to login page to re-login
                    await store.dispatch('user/resetToken')
                    Message.error('pre' + error || 'Has Error')
                    //             next(`/login?redirect=${to.path}`)
                    NProgress.done()
                }
            }
        }
    } else {
        //     /* has no token*/
        if (hasGetUserInfo != '请登录') {
            store.dispatch('user/resetToken')
            Message({
                message: '登录过期，请重新登录！',
                type: 'warning',
                duration: 3 * 1000
            })
            setTimeout(() => {
                // location.reload();
                next('/')
            }, 3000);
        }
        if (whiteList.indexOf(to.name) !== -1) {
            //         // in the free login whitelist, go directly
            next()
        } else {
            // other pages that do not have permission to access are redirected to the login page.
            router.push(`/login?redirect=${to.path}`)
            NProgress.done()

        }
    }
})

router.afterEach(() => {
    // finish progress bar
    NProgress.done()
})
