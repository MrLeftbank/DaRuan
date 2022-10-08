const getters = {
    token: state => state.user.token,
    avatar: state => state.user.avatar,
    name: state => state.user.name,
    isLogin: state => state.user.isLogin,
    isTeacher: state => state.user.isTeacher,
    teacherId: state => state.user.teacherId,
    balance: state => state.user.balance
}
export default getters