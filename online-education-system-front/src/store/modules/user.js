import { getToken, setToken, removeToken } from '@/utils/auth'
import { req } from '@/api/request'

const getDefaultState = () => {
  return {
    token: getToken(),
    name: '请登录',
    avatar: '',
    isLogin: false,
    isTeacher: false,
    teacherID: '',
    balance: 0,
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_IS_LOGIN: (state, isLogin) => {
    state.isLogin = isLogin
  },
  SET_IS_TEACHER: (state, isTeacher) => {
    state.isTeacher = isTeacher
  },
  SET_TEACHER_ID: (state, teacherID) => {
    state.teacherId = teacherID
  },
  SET_BALANCE: (state, balance) =>{
    state.balance = balance
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    return new Promise((resolve, reject) => {
      req('user/login?username=' + userInfo.username + '&password=' + userInfo.password, 'post', null)
        .then(response => {
          if (response.code === 200) {
            const { data } = response
            commit('SET_TOKEN', 'Bearer ' + data)
            setToken('Bearer ' + data)
          }
          resolve(response)
        }).catch(error => {
          reject(error)
        })
    })
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      req('user')
        .then(response => {
          const { data } = response
          commit('SET_NAME', data.name)
          let avatar = ''
          if (data.photoUrl) avatar = process.env.VUE_APP_BASE_API + data.photoUrl
          else avatar = require('@/assets/img/avatar-boy.gif')
          commit('SET_AVATAR', avatar)
          commit('SET_IS_LOGIN', true)
          commit('SET_BALANCE', data.balance)
          console.log(state.balance)
          resolve()
          // if (!data) {
          // }

          // const { name, avatar } = data

          // commit('SET_NAME', name)
          // commit('SET_AVATAR', avatar)
          // resolve(data)
        }).catch(error => {
          reject(error)
        })
    })
  },
  getIsTeacher({ commit, state }) {
    return new Promise((resolve, reject) => {
      // logout(state.token).then(() => {
      req('/user/teacher')
        .then((res) => {
          // console.log(res)
          if (res.code === 200 && res.data.status === 1) {
            commit('SET_IS_TEACHER', true)
            commit('SET_TEACHER_ID', res.data.id)
          }
          resolve()
        }).catch(error => {
          reject(error)
        })
      //   }).catch(error => {
      //     reject(error)
      //   })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      // logout(state.token).then(() => {
      removeToken() // must remove  token  first
      location.reload();
      commit('RESET_STATE')
      resolve()
      //   }).catch(error => {
      //     reject(error)
      //   })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}