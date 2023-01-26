const express = require('express');
const dotenv = require('dotenv');
const authorization = require('./authorization.js');

const app = express();
dotenv.config();
const PORT = process.env.PORT || 8081;

app.use('/users', authorization)

app.listen(PORT, () => console.log(`Server is listening on port ${PORT}`))