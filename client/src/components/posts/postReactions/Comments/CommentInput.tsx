import React, { useState, useEffect } from 'react';
import TextField from '@mui/material/TextField';
import { Box, Grid } from '@mui/material';
import Avatar from '@mui/material/Avatar';
import SendIcon from '@mui/icons-material/Send';
import IconButton from '@mui/material/IconButton';
import { postCommentFunction } from '../../../api';

export const CommentInput = (props: { postId: number }) => {
    const [commentText, setCommentText] = useState<string>("");
    const [imageUrl, setImageUrl] = useState<string>("none");
    const [errorMessage, setErrorMessage] = useState<string>("");

    const onSubmitHandler = async () => {
        
        if(commentText.length === 0) {
            return setErrorMessage("You must type something in the input box");
        }        
        const postCommentObject: object = { commentText, imageUrl };
        console.log(postCommentObject)
        await postCommentFunction(postCommentObject, props.postId);
        window.location.reload();
    }
 
    return (
        <>
            <Grid>
            <p data-testid="comment-error-message" style={{ color: 'red', textAlign: 'center' }}>{errorMessage}</p> 
                <Box className='comments-box-post-comment'>
                    <Avatar sx={{ marginLeft: 2, backgroundColor: 'green' }}></Avatar>
                    <TextField
                        data-testid="comment-input"
                        sx={{ width: '55ch', size: 'small', marginLeft: 2 }}
                        variant="standard"
                        value={commentText}
                        onChange={(event) => setCommentText(event.target.value)}
                    />
                    <IconButton sx={{ marginLeft: 2 }}>
                        <SendIcon onClick={onSubmitHandler} data-testid="upload-comment-button" />
                    </IconButton>
                </Box>
            </Grid>
        </>
    )
}
