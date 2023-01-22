FROM node:14-alpine
WORKDIR /society/app
COPY ../server/package.json /society/app/
RUN npm install
EXPOSE 3001
CMD ["node"]