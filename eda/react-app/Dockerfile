FROM node:20 AS build
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm ci
COPY . .
COPY .env.docker .env
RUN npm run build

FROM nginx:1.25.4-alpine-slim as prod
COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx.conf  /etc/nginx/conf.d
EXPOSE 4001
CMD ["nginx", "-g", "daemon off;"]