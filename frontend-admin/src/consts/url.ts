export const VITE_BACKEND_URL = import.meta.env.DEV
  ? import.meta.env.VITE_BACKEND_URL
  : 'VITE_BACKEND_URL_RUNTIME_REPLACEMENT';
