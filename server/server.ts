import express from 'express';
import dotenv from 'dotenv';
import authorization from './auth/authorization';
import registration from './Authentication/authentication'

const app = express();
const PORT = process.env.PORT || 3006;
app.use(express.json());
dotenv.config();

app.use('/users', authorization);
app.use('/users', registration);

app.listen(PORT, () => console.log(`Server is listening on port: ${PORT}`));