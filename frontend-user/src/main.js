import { createApp }     from 'vue';
import Antd, { message } from 'ant-design-vue';
import App               from './App.vue';
import store             from './store';
import 'ant-design-vue/dist/antd.css';
import axios             from 'axios';
import router            from './router';

const app = createApp(App);

app.use(store);
app.use(router);

app.use(Antd);
app.config.productionTip = false;

app.config.globalProperties.$store = store;

export const VITE_BACKEND_URL     = import.meta.env.DEV
  ? import.meta.env.VITE_BACKEND_URL
  : 'VITE_BACKEND_URL_RUNTIME_REPLACEMENT';
axios.defaults.baseURL            = VITE_BACKEND_URL;
app.config.globalProperties.Axios = axios;
export default axios;
/**
 * axios请求拦截器
 */
axios.interceptors.request.use((config) => {
  // Do something before request is sent
  config.headers.Authorization = `Bearer ${localStorage.getItem('token') || ''}`;// 将token放到请求头发送给服务器
  return config;
}, (error) => {
  // Do something with request error
  message.error(error.toString());
  return Promise.reject(error);
});
/**
 * axios回应拦截器
 */
axios.interceptors.response.use((response) =>
    // 对响应数据做点什么
    response,
  (error) => {
    // 对响应错误做点什么
    message.error(error.response.data.message);
    // console.log(error.response)
    return Promise.reject(error);
  });

app.mount('#app');
