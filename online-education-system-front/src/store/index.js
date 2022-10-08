import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import user from './modules/user'

Vue.use(Vuex)

const store = new Vuex.Store({
    modules: {
        user,
    },
    getters
})

export default store
// const store = new Vuex.Store(
//     {
//         state: {
//             user: {
//                 nickname: '登录',
//                 userPhoto: '',
//                 isLogin: false,
//             },
//             current_course_page: 1,
//             domain: {
//                 first_domain: '',
//                 second_domain: '',
//                 sorter: '',
//             },
//             data: {
//                 sectionName: 'test',
//                 videoList: [{
//                     'name': 'test video',
//                     'url': 'test',
//                 }],
//                 fileList: [{
//                     'name': 'test file',
//                     'url': 'test',
//                 }],
//                 innerSectionID: ''
//             },
//             step: 0
//         },
//         mutations: {
//             setState(state, data) {
//                 state.user.nickname = data.nickname;
//                 state.user.userPhoto = data.userPhoto;
//             },
//             setIsLogin(state, data) {
//                 state.user.isLogin = data;
//             },
//             setCoursePage(state, data) {
//                 state.current_course_page = data;
//             },
//             setStep(state, data) {
//                 state.step = data
//             }
//         }
//     }
// )
// export default store