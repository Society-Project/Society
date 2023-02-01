import pkg from 'pg'
import dotenv from 'dotenv';

const { Client } = pkg
dotenv.config();

const client = new Client({
    host: process.env.DATABASE_HOST,
    user: process.env.DATABASE_USER,
    password: process.env.DATABASE_PASSWORD,
    database: process.env.DATABASE_NAME,
}); 
client.connect();

client.query('SELECT * FROM users', (err, res) => {
    if(err){
        console.log(err.message);
        return;
    } else{
        console.log(res.rows)
    }

    client.end;
})
export default client