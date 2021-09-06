FROM node:14-alpine AS build
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY package*.json yarn.lock ./
ARG VITE_BACKEND_URL
ENV NODE_ENV=production
RUN yarn install --frozen-lockfile --production=false --registry https://registry.npm.taobao.org/
COPY . .
RUN yarn build

FROM nginx:stable-alpine
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /usr/src/app/dist /usr/share/nginx/html
STOPSIGNAL SIGQUIT
