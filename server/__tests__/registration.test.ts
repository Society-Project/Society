import 'mocha';
import 'chai';
import supertest from 'supertest';
import registration from '../Authentication/authentication';
import { expect, describe, it } from '@jest/globals';

const registerUser: object = {
    firstname: 'Maria',
    lastname: 'Ivanova',
    email: "Maria@example.com",
    username: 'Maria',
    password: "Password123!",
    dayOfBirth: 22,
    monthOfBirth: 'Janurary',
    yearOfBirth: 1999,

}
const wrongInputCredentials: object = {
    firstname: 1,
    lastname: 2,
    email: 3,
    username: 4,
    password: 5,
    dayOfBirth: '22',
    monthOfBirth: 5,
    yearOfBirth: '1999',
}

describe('Registration tests', () => {
    it('Should return status 200 if correct values are provided', async () => {
        const request = await supertest(registration).post('/registration').send(registerUser);
        expect(request.status).toBe(201);
    });
    it("Should return an error if user provides empty input values", async() => {
        const badRequest = await supertest(registration).post('/registration').send(wrongInputCredentials);

        expect(badRequest.status).toBe(409);
    });
    
})
