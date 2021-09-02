import axios from 'axios';
import { baseURL } from 'consts/url';

const service = axios.create({
  baseURL,
  timeout: 10000,
  headers: {},
});

service.interceptors.request.use((conf) => ({
  ...conf,
  Authorization: `Bearer ${localStorage.getItem('token') || ''}`,
}), (err) => Promise.reject(err));

// service.interceptors.response.use((res) => {
//     // Any status code that lie within the range of 2xx cause this function to trigger
//     // Do something with response data,
//     return res;
//   }, (err) => {
//     // Any status codes that falls outside the range of 2xx cause this function to trigger
//     // Do something with response error
//     return Promise.reject(err);
//   }
// );

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
