import express from 'express'
import dotenv from 'dotenv';
import bcrypt from 'bcrypt';
import { validateUserCredentials } from '../middlewares/registrationMiddleware'
import client from '../database/database'

const app = express();
app.use(express.json());
dotenv.config();

//Test porpouses only
app.get('/registration', (req: express.Request, res: express.Response) => {
    const test = client.query('SELECT * FROM users');
    console.log(test);
    res.status(200).send(test)
})

app.post('/registration', validateUserCredentials, async(req: express.Request, res: express.Response) => {
    const { username, email, password } = req.body;

    //Creating new variable in order to hash the password
    let hashedPassword: string = password;
    let hashingPasswordWithBcrypt = await bcrypt.hash(hashedPassword, 10);
    hashedPassword = hashingPasswordWithBcrypt;
    
    //Default values -> Should change in future
    const account_enabled: boolean = true;
    const account_locked: boolean = false;
    const credentials_expired: boolean = false;
    const created_at: string = '2023-01-20T22:00:00.000Z';
    const updated_at: string = '2023-01-20T22:00:00.000Z';

    try {
        await client.query(`INSERT INTO users(username, email, user_password, account_enabled, account_locked, credential_expired, created_at, updated_at) VALUES('${username}', '${email}','${hashedPassword}', '${account_enabled}', '${account_locked}', '${credentials_expired}', '${created_at}', '${updated_at}')`)
        res.status(201).send('User created!')
    } catch(error){
        return res.status(400).send(error)
    }
    
})

export default app;