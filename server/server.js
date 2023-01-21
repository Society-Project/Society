const express = require('express');
const app = express();
const jwt = require('jsonwebtoken');
const dotenv = require('dotenv');


let refreshTokens = [];
app.use(express.json());
dotenv.config();

function auth(req, res, next){
    let token = req.headers['authorization'];
    token = token.split(' ')[1];

    jwt.verify(token, process.env.ACCESS_TOKEN_SECRET, (err, user) => {
        if(!err){
            req.user = user;
            next();
        } else {
            return res.status(403).json({ message: 'User not authenticated' });
        }
    });
}

app.post('/renewAccessToken', (req, res) => {
    const refreshToken = req.body.token;

    if(!refreshToken && !refreshTokens.includes(refreshToken)) {
        return res.status(403).send('User not authenticated');
    }

    jwt.verify(refreshToken, process.env.REFRESH_TOKEN_ACCESS, (err, user) => {
        if(!err){
            const accessToken = jwt.sign({username: user.name}, process.env.ACCESS_TOKEN_SECRET, { expiresIn: '20s' });
            return res.status(201).json({ accessToken })
        } else {
            return res.status(403).send('User not authenticated');
        }
    })
});


app.post('/login', (req, res) => {
    const { user } = req.body;

    if(!user) {
        return res.status(404).send('Body empty');
    }

    let accessToken = jwt.sign(user, process.env.ACCESS_TOKEN_SECRET, { expiresIn: "20s" });
    let refreshToken = jwt.sign(user, process.env.REFRESH_TOKEN_ACCESS, { expiresIn: "7d" });
    refreshTokens.push(refreshToken);

    return res.status(201).json({
        accessToken,
        refreshToken
    });
});

app.listen(3000);