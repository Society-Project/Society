const express = require('express');
const dotenv = require('dotenv');
const authorization = require('./authorization');

const app = express();
const PORT = process.env.PORT || 3006;
app.use(express.json());
dotenv.config();

app.use('/users', authorization)

app.listen(PORT, () => console.log(`Server is listening on port: ${PORT}`))