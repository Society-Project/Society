import express from 'express'
import dotenv from 'dotenv';
import bcrypt from 'bcrypt';
import client from '../../database/database'

const app = express();
app.use(express.json());
dotenv.config();


app.post('/registration', async (req: express.Request, res: express.Response) => {
    const { firstname, lastname, email, username, password, dayOfBirth, monthOfBirth, yearOfBirth } = req.body;

    const hashingPasswordWithBcrypt: string = await bcrypt.hash(password, 10);

    //Default values -> Should change in future
    const account_enabled: boolean = true;
    const account_locked: boolean = false;
    const credentials_expired: boolean = false;
    const created_at: string = new Date().toJSON();
    const updated_at: string = new Date().toJSON();

    if (firstname && lastname && email && username && dayOfBirth && monthOfBirth && yearOfBirth) {
        try {
            await client.query('SELECT COUNT(username) FROM users WHERE username=$1', [username.trim()], async function (err, response) {
                if (err) {
                    return err;
                } else {
                    
                    if (response.rows[0].count > 0) {
                        return res.status(409).send('Username is already taken');
                    }

                    await client.query(`INSERT INTO users(firstname, lastname, email, username, account_enabled, account_locked, credential_expired, dayOfBirth, monthOfBirth, yearOfBirth, created_at, updated_at, password) VALUES($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12, $13)`, [firstname.trim(), lastname.trim(), email.trim(), username.trim(), account_enabled, account_locked, credentials_expired, dayOfBirth, monthOfBirth.trim(), yearOfBirth, created_at.trim(), updated_at.trim(), hashingPasswordWithBcrypt.trim()]);

                    return res.status(201).send('User has been created!')
                }
            })

        } catch (error) {
            return res.status(400).send(error);
        }
    }

})

export default app;