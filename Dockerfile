FROM node:10 AS builder

WORKDIR /app

COPY ./package.json ./

RUN npm install

COPY . .

EXPOSE 3000:3000

CMD ["npm", "start"]