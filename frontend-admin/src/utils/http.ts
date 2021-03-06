import axios                from 'axios';
import { VITE_BACKEND_URL } from 'consts/url';
import { message }          from 'antd';

const service = axios.create({
  baseURL: VITE_BACKEND_URL,
  timeout: 10000,
  headers: {},
});

service.interceptors.request.use((conf) => {
  conf.headers.Authorization = `Bearer ${localStorage.getItem('token') || ''}`;
  return conf;
}, (err) => Promise.reject(err));

service.interceptors.response.use((res) => res,
  ({ response }) => {
    switch (response?.status) {
      case 401:
        message.error('你没有权限访问该页面，请登录');
        window.location.href = '/auth/login';
        break;
      case 403:
        message.error('禁止访问！');
        // to login page
        break;
      case 404:
        // message.error('资源不存在！');
        // to login page
        break;
      case 409:
        message.error('用户名已存在！');
        // to login page
        break;
      default:
        message.error(response?.data?.message);
        break;
    }
    // eslint-disable-next-line prefer-promise-reject-errors
    return Promise.reject({
      status: response?.status,
      message: response?.data?.message,
    });
  });

export function get<T>(url: string, data: object = {}) {
  return new Promise<T>((resolve, reject) => {
    service.get(url, { params: data })
      .then((res: { data: T }) => {
        resolve(res.data);
      })
      .catch((err) => {
        reject(err);
      });
  });
}

export function post<T>(url: string, data: object = {}) {
  return new Promise<T>((resolve, reject) => {
    service.post(url, data)
      .then((res: { data: T }) => {
        resolve(res.data);
      })
      .catch((err) => {
        reject(err);
      });
  });
}

export function put<T>(url: string, data: object = {}) {
  return new Promise<T>((resolve, reject) => {
    service.put(url, data)
      .then((res: { data: T }) => {
        resolve(res.data);
      })
      .catch((err) => {
        reject(err);
      });
  });
}

export function del<T>(url: string, data: object = {}) {
  return new Promise<T>((resolve, reject) => {
    service.delete(url, { params: data })
      .then((res: { data: T }) => {
        resolve(res.data);
      })
      .catch((err) => {
        reject(err);
      });
  });
}

export default {
  get, post, put, del,
};
