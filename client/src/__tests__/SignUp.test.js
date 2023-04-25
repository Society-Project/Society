import { render, fireEvent, screen } from '@testing-library/react';
import { UserInput } from '../components/register-page/register-form/user-input/UserInput';

describe("Sign Up component testing", () => {
    it('Should check if every keystroke is included in input field', () => {
        render(<UserInput />);

        const firstName = screen.getByTestId('first-name-input');
        const lastName = screen.getByTestId('last-name-input');
        const email = screen.getByTestId('email-input');
        const username = screen.getByTestId('username-input');
        const password = screen.getByTestId('password-input');

        fireEvent.change(firstName, { target: { value: 'Peter' } });
        fireEvent.change(lastName, { target: { value: 'Anderson' } });
        fireEvent.change(email, { target: { value: 'peter@example.com' } });
        fireEvent.change(username, { target: { value: 'peter123' } });
        fireEvent.change(password, { target: { value: 'Password123!' } });

        expect(firstName.value).toBe('Peter');
        expect(lastName.value).toBe('Anderson');
        expect(email.value).toBe('peter@example.com');
        expect(username.value).toBe('peter123');
        expect(password.value).toBe('Password123!');

    })

    it('Should throw an error if user does not provide any data', () => {
        render(<UserInput />);

        const submitButton = screen.getByTestId('sign-up-submit-button');
        const errorMessage = screen.getByTestId('error-message');

        fireEvent.click(submitButton);

        expect(errorMessage.textContent).toBe('Please make sure to fill all the fields');
    })
})