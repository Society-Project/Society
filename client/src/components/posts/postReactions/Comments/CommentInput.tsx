import React, { useState } from 'react';
import TextField from '@mui/material/TextField';
import { Box, Grid } from '@mui/material';
import Avatar from '@mui/material/Avatar';
import '../../../Styles/PostReactions.scss';
import SendIcon from '@mui/icons-material/Send';
import IconButton from '@mui/material/IconButton';


export interface CommentInputProps {
    onSubmit: (text: string) => void;
}

export const CommentInput: React.FC<CommentInputProps> = ({ onSubmit }) => {
    const [text, setText] = useState<string>('');
    const [test, setTest] = useState(false);

    const onSubmitHandler = (e: React.FormEvent<HTMLButtonElement>) => {
        if(text != ''){
            e.preventDefault();
            onSubmit(text);
            setText('');
            setTest(true);
        }
    }
    return (
        <>
            <Grid>
                <Box sx={{ display: 'flex', alignItems: 'flex-end' }}>
                    <Avatar sx={{ marginLeft: 2, backgroundColor: 'green' }}>R</Avatar>
                    <TextField
                        sx={{ width: '55ch', size: 'small', marginLeft: 2 }}
                        variant="standard"
                        value={text}
                        onChange={(e) => setText(e.target.value)}
                    />
                    <IconButton onClick={onSubmitHandler} sx={{ marginLeft: 2 }}
                    >
                        <SendIcon />
                    </IconButton>
                </Box>
            </Grid>
        </>
    )
}
