export {};
import express, { Request, Response } from 'express';
import jwt from 'jsonwebtoken';
import dotenv from 'dotenv';



const app = express();
let refreshTokens: string[] = [];
app.use(express.json());
dotenv.config();

app.post('/renew-access-token', (req: express.Request, res: express.Response) => {
    const refreshToken: string = req.body.token;

    if(!refreshToken && !refreshTokens.includes(refreshToken)) {
        return res.status(403).send('User not authenticated');
    }

    jwt.verify(refreshToken, process.env.REFRESH_TOKEN_ACCESS as string, (err, user: any) => {
        if(!err){
            const accessToken = jwt.sign({username: user.name}, process.env.ACCESS_TOKEN_SECRET as string, { expiresIn: '20m' });
            return res.status(201).json({ accessToken })
        } else {
            return res.status(403).send('User not authenticated');
        }
    })
});


app.post('/login', (req: express.Request, res: express.Response) => {
    const { user } = req.body;

    if(!user) {
        return res.status(403).send(user);
    }
    if(typeof(user) !== 'string'){
        return res.status(409).send('Incorrect input value');
    }

    let accessToken = jwt.sign(user, process.env.ACCESS_TOKEN_SECRET as string, { expiresIn: "20m" });
    let refreshToken = jwt.sign(user, process.env.REFRESH_TOKEN_ACCESS as string, { expiresIn: "1h" });
    refreshTokens.push(refreshToken);

    return res.status(201).json({
        accessToken,
        refreshToken
    });
});
export default app;