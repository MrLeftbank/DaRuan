import Vue from 'vue'
import App from './App.vue'
import axios from 'axios'
import store from './store'
import router from './router'
import Element from 'element-ui';
// import "element-ui/lib/theme-chalk/index.css"
import "../theme/index.css"
import * as echarts from 'echarts'
import 'font-awesome/css/font-awesome.css'
import { box } from '@/utils/delete'
import { buy } from "@/utils/buy";
// import './mock'
import '@/permission'

import Router from 'vue-router'
const routerPush = Router.prototype.push
Router.prototype.push = function push(location) {
return routerPush.call(this, location).catch(error=> error)
}

Vue.config.productionTip = false
Vue.prototype.$echarts = echarts
Vue.prototype.$http = axios
Vue.prototype._box = box;
Vue.prototype._buy = buy;
Vue.prototype.$ip = process.env.VUE_APP_BASE_API
// axios.defaults.baseURL = 'http://localhost:8088'
Vue.use(Element);

new Vue({
  render: h => h(App),
  router,
  store
}).$mount('#app')