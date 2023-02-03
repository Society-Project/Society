import { Request, Response, NextFunction } from 'express';

export function validateUserCredentials(req: Request, res: Response, next: NextFunction) {
    const { firstname, lastname, email, username, password, dayOfBirth, monthOfBirth, yearOfBirth } = req.body;

    if (!firstname || !lastname || !email || !username || !password || !dayOfBirth || !monthOfBirth || !yearOfBirth) {
        return res.status(409).send('User has provided an empty input');
    }

    if (typeof firstname !== 'string' || typeof lastname !== 'string' || typeof email !== 'string' || typeof username !== 'string' || typeof password !== 'string' || typeof dayOfBirth !== 'number' || typeof monthOfBirth !== 'string' || typeof yearOfBirth !== 'number') {
        return res.status(409).send("Incorrect input value");
    }

    next();
}
