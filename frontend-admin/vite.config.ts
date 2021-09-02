import path from "path"
import { defineConfig } from "vite"
import reactRefresh from "@vitejs/plugin-react-refresh"

export default defineConfig({
  resolve: {
    alias: {
      "src": path.resolve(__dirname, './src'),
      "components": path.resolve(__dirname, './src/components'),
      "services": path.resolve(__dirname, './src/services'),
      "consts": path.resolve(__dirname, './src/consts'),
      "utils": path.resolve(__dirname, './src/utils'),
      "types": path.resolve(__dirname, './src/types'),
    }
  },
  define: {
    'process.env.API': JSON.stringify(process.env.API)
  },
  css: {
    preprocessorOptions: {
      less: {
        javascriptEnabled: true
      }
    }
  },
  server: {
    port: 8187,
    open: true
  },
  plugins: [
    reactRefresh()
  ]
})
