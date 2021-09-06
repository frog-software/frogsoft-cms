export declare type HttpMethod = 'GET' | 'POST' | 'PUT' | 'DELETE';

export interface KeyValuePair<T> {
  [key: string]: T;
}

export interface IOptions extends KeyValuePair<any> {
  headers?: KeyValuePair<string>;
  params?: KeyValuePair<any>;
}
