const express = require('express');
const app = express();
const jwt = require('jsonwebtoken');
const dotenv = require('dotenv');
const bcrypt = require('bcrypt');
const db = require('./database/database.js');

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

// app.get('/user', async (req, res) => {
    
//     let usrs = db.query(`SELECT * FROM users`, (error, response) => {
//         if(!error){
//             console.log(response.rows);
//         } else{
//             console.log(error);
//         }
//         db.end;
//     })

//     console.log(usrs)
//     res.status(200).json(usrs)
// })

app.post('/register', async(req, res) => {
    let { username, email, password } = req.body;

    if(!username || !email || !password){
        return res.status(409).send("Input error");
    }

    const validPassword = password.match(/^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,16}$/)
    
    if(!validPassword){
        return res.status(409).send('Password should contain: Upper case, lower case letter and have special symbol with at least 8 characters.');
    }
    
    password = password.toString();
    const passwordHash = await bcrypt.hash(password, 10);
    password = passwordHash;

    if(username && email && password){
        try{
            //${account_enabled}, ${account_locked}, ${credential_expired}, ${created_at}, ${updated_at

            // db.query(`INSERT INTO users(${username}, ${email}, ${user_password}, ${account_enabled}, ${account_locked}, ${credential_expired}, ${created_at}, ${updated_at})
            // VALUES('${username}', '${email}', '${user_password}')`)

        } catch(error){
            console.log(error);
            return;
        }
    }

})

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

//Test porpouses only
app.post('/protected', auth, (req, res) => {
    res.send('Inside protected route')
})

app.post('/login', (req, res) => {
    const { username } = req.body;

    if(!username) {
        return res.status(401).send('Body empty');
    }

    let accessToken = jwt.sign(username, process.env.ACCESS_TOKEN_SECRET, { expiresIn: "20m" });
    let refreshToken = jwt.sign(username, process.env.REFRESH_TOKEN_ACCESS, { expiresIn: "7d" });
    refreshTokens.push(refreshToken);

    return res.status(201).json({
        accessToken,
        refreshToken
    });
});
module.exports = app