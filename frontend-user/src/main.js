import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import 'ant-design-vue/dist/antd.css'
import Antd, { message } from 'ant-design-vue'
import axios from 'axios'

Vue.config.productionTip = false
Vue.use(Antd)

// axios
Vue.prototype.$axios = axios
axios.defaults.baseURL = 'https://api.pxm.edialect.top/'

/**
 * axios请求拦截器
 */
axios.interceptors.request.use(function (config) {
  // Do something before request is sent
  config.headers.token = localStorage.getItem('token') || [] // 将token放到请求头发送给服务器
  return config
}, function (error) {
  // Do something with request error
  message.error(error.toString())
  return Promise.reject(error)
})

/**
 * axios回应拦截器
 */
axios.interceptors.response.use(function (response) {
  // 对响应数据做点什么
  return response
}, function (error) {
  // 对响应错误做点什么
  switch (error.response.status) {
    case 401: {
      message.error('权限错误！你无权进行该操作！')
      break
    }
    default: {
      message.error(error.toString())
      console.log(error.response)
      break
    }
  }
  return Promise.reject(error)
})

export default axios

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
