import axios                from 'axios';
import { VITE_BACKEND_URL } from 'consts/url';

const service = axios.create({
  baseURL: VITE_BACKEND_URL,
  timeout: 10000,
  headers: {},
});

service.interceptors.request.use((conf) => ({
  ...conf,
  headers: {
    ...conf.headers,
    Authorization: `Bearer ${localStorage.getItem('token') || ''}`,
  },
}), (err) => Promise.reject(err));

service.interceptors.response.use((res) => res,
  (err) => {
    console.log(err);
    return Promise.reject(err);
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
