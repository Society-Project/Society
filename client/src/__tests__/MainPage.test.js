import { render, screen, fireEvent } from '@testing-library/react';
import { CreatePost } from '../components/posts/createPost/CreatePost';
import { CommentInput } from '../components/posts/postReactions/Comments/CommentInput';

describe("Main page component testing", () => {
    it('Should throw error if user tries to post nothing', () => {
        render(<CreatePost />);

        const createPostInput = screen.getByTestId('create-post-input-field');
        const errorMessage = screen.getByTestId('error-message');
        const createPostButton = screen.getByTestId('create-post-button');

        fireEvent.change(createPostInput, { target: { value: '' } })
        fireEvent.click(createPostButton);

        expect(errorMessage.textContent).toBe('You must enter some text');
    })
    it('Should throw an error if user tries to send empty comment', () => {
        render(<CommentInput />);

        const errorMessage = screen.getByTestId("comment-error-message");
        const commentButton = screen.getByTestId("upload-comment-button");
    
        fireEvent.click(commentButton);

        expect(errorMessage.textContent).toBe("You must type something in the input box");
    })
})