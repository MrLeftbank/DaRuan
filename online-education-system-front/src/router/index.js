import VueRouter from 'vue-router'
import Vue from 'vue'
import HelloWorld from '@/components/HelloWorld.vue'
import index from '@/layout/index.vue'
import test from '@/views/test'
import { contains } from 'jquery'

//将VueRouter设置为Vue的插件
Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        redirect: '/home'
    },
    {
        path: '/#/',
        redirect: '/home'
    },
    // {
    //     path: '/test',
    //     name: 'test',
    //     component: test
    // },
    {
        path: '/login',
        name: 'login',
        component: () => import('@/views/login/login.vue')
    },
    {
        path: '/home',
        component: index,
        children: [
            {
                path: '',
                name: 'home',
                component: () => import('@/views/home/home.vue')
            }]
    },
    {
        path: '/course',
        component: index,
        children: [
            {
                path: '',
                name: 'course',
                component: () => import('@/views/course/courseList.vue')
            },
            {
                path: ':course_id',
                component: () => import('@/views/index'),
                children: [
                    {
                        path: '',
                        name: 'coursePage',
                        component: () => import('@/views/course/coursePage.vue'),
                        props: true,
                    },
                    {
                        path: 'chapterPage',
                        name: 'chapterPage',
                        component: () => import('@/views/course/chapterPage.vue'),
                        props: true,
                    },
                    {
                        path: 'chapterPage/:chapter_id/section/:section_id',
                        name: 'section',
                        component: () => import('@/views/course/courseWare.vue'),
                        props: true,
                    }
                ]
            },
        ]
    },
    {
        path: '/teacher',
        component: index,
        children: [
            {
                path: '',
                name: 'teacher',
                component: () => import('@/views/teacher/teacherList.vue')
            }, {
                path: ':teacher_id',
                name: 'teacherDetail',
                component: () => import('@/views/teacher/teacherDetail.vue'),
                props: true,
            }]
    },
    {
        path: '/article',
        component: index,
        children: [
            {
                path: '',
                name: 'article',
                component: () => import('@/views/article/articleList.vue')
            }, {
                path: ':article_id',
                name: 'articlePage',
                component: () => import('@/views/article/articlePage.vue'),
                props: true,
            }]
    },
    {
        path: '/question',
        component: index,
        children: [
            {
                path: '',
                name: 'question',
                component: () => import('@/views/question/questionList.vue')
            },
            {
                path: ':question_id',
                name: 'questionPage',
                component: () => import('@/views/question/question'),
                props: true
            }]
    },
    {
        path: '/my',
        component: () => import('@/views/usercenter/userCenterPage'),
        children: [
            {
                path: 'star',
                name: 'myStar',
                component: () => import('@/views/usercenter/components/userStarCourseList')
            }, {
                path: 'purchase',
                name: 'myPurchase',
                component: () => import('@/views/usercenter/components/userPurchaseCourseList')
            }, {
                path: 'article',
                name: 'myArticle',
                component: () => import('@/views/usercenter/components/userArticleList')
            }, {
                path: 'question',
                name: 'myQuestion',
                component: () => import('@/views/usercenter/components/userQuestionList')
            }, {
                path: 'answer',
                name: 'myAnswer',
                component: () => import('@/views/usercenter/components/userAnswerList')
            }, {
                path: 'info',
                name: 'myInfo',
                component: () => import('@/views/usercenter/components/userInfo')
            },]
    },
    {
        path: '',
        component: index,
        children: [
            {
                path: 'courseManage',
                component: () => import('@/views/index'),
                children: [
                    {
                        path: '',
                        name: 'courseManage',
                        component: () => import('@/views/teacherOption/courseManage'),
                    },
                    {
                        path: 'addCourse',
                        component: () => import('@/views/index'),
                        children: [
                            {
                                path: 'formation',
                                name: 'addCourse',
                                component: () => import('@/views/teacherOption/courseForm')
                            },
                            {
                                path: ':course_id',
                                name: 'addChapter',
                                component: () => import('@/views/teacherOption/addChapter'),
                                props: true,
                            }
                        ]
                    },
                ]
            },
            {
                path: 'addArticle',
                component: () => import('@/views/article/articleAdd')
            },
            {
                path: 'addQuestion',
                component: () => import('@/views/question/addQuestion')
            }
        ]
    },
    {
        path: '/teacherCertificate',
        name: 'teacherCertificate',
        component: () => import('@/views/teacherOption/certification')
    },
    {
        path: '*',//匹配未定义的路由
        redirect: '/home'//重定向
    }
]

const router = new VueRouter({
    // 指定hash属性与组件的对应关系
    routes, // `routes: routes` 的缩写
})

export default router