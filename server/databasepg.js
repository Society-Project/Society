const { Client } = require('pg');

function initDB() {
    const client = new Client({
        host: 'localhost',
        user: 'postgres',
        port: `${process.env.DB_PORT}`,
        password: `${process.env.DB_PASSWORD}`,
        database: 'postgres',
    });

    return client;
}

module.exports = initDB;