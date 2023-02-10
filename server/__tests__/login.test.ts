import 'mocha';
import 'chai';
import supertest from 'supertest';
import app from '../auth/authorization';
import { expect, describe, it } from '@jest/globals';

const user = {
   userData: {
      user: {
         name: 'Peter'
      }
   },
}

const incorrectDataType = {
   userData: {
      user: {
         name: 1
      }
   }
}

const emptyInputData = {
   userData: {
      user: {
         name: '' || ' '
      }
   }
}

describe("Login tests", () => {
   it('Should return jwt ONLY IF username is provided', async() => {
      const response = await supertest(app).post('/login').send(user.userData);
      
      expect(response.status).toBe(201);
      expect(response.body).not.toBeNull();
      expect(response.error).toBe(false);
   });

   it('Should return error when user give a non-string value', async () => {
      const typeOfInputData = await supertest(app).post('/login').send(incorrectDataType.userData)
      expect(typeOfInputData.status).toBe(409);
      
   });

   it('Should return error if an empty input is send', async() => {
      const emptyInput = await supertest(app).post('/login').send(emptyInputData.userData);

      expect(emptyInput.status).toBe(404);
   })
}) 
