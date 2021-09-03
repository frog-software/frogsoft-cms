FROM node:14-alpine AS builder

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

COPY package.json package-lock.json ./
RUN npm install --registry=https://registry.npm.taobao.org
COPY . .
ARG API
RUN npm run build

FROM nginx:alpine

COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /usr/src/app/dist /usr/share/nginx/html

STOPSIGNAL SIGQUIT