import 'mocha';
import 'chai';
import supertest from 'supertest';
import registration from '../Authentication/authentication';
import { expect, describe, it } from '@jest/globals';

const registerUser: object = {
    username: 'Peter',
    email: "Peter@example.com",
    password: "Password123!",

}
const wrongInputCredentials: object = {
    username: '',
    email: '',
    password: '',
}

describe('Registration tests', () => {
    it('Should return status 200 if correct values are provided', async () => {
        const request = await supertest(registration).post('/registration').send(registerUser);
        expect(request.status).toBe(201);
    });
    it("Should return an error if user provides empty input values", async() => {
        const badRequest = await supertest(registration).post('/registration').send(wrongInputCredentials);

        expect(badRequest.status).toBe(400);
    });
})
