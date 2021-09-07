import {createApp} from 'vue'
import App from './App.vue'
import store from "./store";
import Antd, {message} from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'
import axios from "axios";
import router from "./router";

export const { VITE_BACKEND_URL } = import.meta.env;

const app = createApp(App)

app.use(store)
app.use(router)


app.use(Antd)
app.config.productionTip = false;

app.config.globalProperties.$store = store

axios.defaults.baseURL = VITE_BACKEND_URL
app.config.globalProperties.Axios = axios
export default axios
/**
 * axios请求拦截器
 */
axios.interceptors.request.use(function (config) {
  // Do something before request is sent
  config.headers.Authorization = 'Bearer ' + localStorage.getItem('token') || ""// 将token放到请求头发送给服务器
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

app.mount('#app')
