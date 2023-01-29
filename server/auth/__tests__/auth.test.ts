// import jest from 'jest';
// import mocha from 'mocha';
// import supertest from 'supertest';
// import app from '../authorization';

// const testUsers = {
//    correctUserData: {
//       user: {
//          name: 'Peter'
//       }
//    },
// }

// describe("Authorization tests", () => {
//    it('Should return jwt ONLY IF username is provided', async () => {
//       const response = await supertest(app).post('/login').send(testUsers.correctUserData);

//       expect(response.status).toBe(201);
//       expect(response.body).not.toBeNull();
//       expect(response.error).toBe(false);
//    });
// }) 

// describe('dd', () => {
//    it('It should return an error if number is provided instead of username', async () => {
//       let numberInsteadOfUsername = {
//             user: {
//                name: 1
//             }
//          }
//       const response = await supertest(app).post('/login').send(numberInsteadOfUsername)

//       expect(response.status).toBe(409);
//    });
// })