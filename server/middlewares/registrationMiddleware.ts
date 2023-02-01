import { Request, Response, NextFunction } from 'express';

export function validateUserCredentials(req: Request, res: Response, next: NextFunction) {
    const { username, email, password } = req.body;

    if(!username || !email || !password){
        return res.status(400).send('User has provided an empty input');
    }

    next();
}
