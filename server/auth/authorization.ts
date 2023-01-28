export {};
const express = require('express');
const dotenv = require('dotenv');
const jwt = require('jsonwebtoken');

const app = express();
let refreshTokens: any = [];
app.use(express.json());
dotenv.config();

function auth(req: any, res: any, next: any){
    let token = req.headers['authorization'];
    token = token.split(' ')[1];

    jwt.verify(token, process.env.ACCESS_TOKEN_SECRET, (err: Error, user: string) => {
        if(!err){
            req.user = user;
            next();
        } else {
            return res.status(403).json({ message: 'User not authenticated' });
        }
    });
}

app.post('/renew-access-token', (req: any, res: any) => {
    const refreshToken = req.body.token;

    if(!refreshToken && !refreshTokens.includes(refreshToken)) {
        return res.status(403).send('User not authenticated');
    }

    jwt.verify(refreshToken, process.env.REFRESH_TOKEN_ACCESS, (err: Error, user: any) => {
        if(!err){
            const accessToken = jwt.sign({username: user.name}, process.env.ACCESS_TOKEN_SECRET, { expiresIn: '20m' });
            return res.status(201).json({ accessToken })
        } else {
            return res.status(403).send('User not authenticated');
        }
    })
});


app.post('/login', (req: any, res: any) => {
    const { user } = req.body;

    if(!user) {
        return res.status(403).send(user);
    }

    let accessToken = jwt.sign(user, process.env.ACCESS_TOKEN_SECRET, { expiresIn: "20m" });
    let refreshToken = jwt.sign(user, process.env.REFRESH_TOKEN_ACCESS, { expiresIn: "1h" });
    refreshTokens.push(refreshToken);

    return res.status(201).json({
        accessToken,
        refreshToken
    });
});
module.exports = app;