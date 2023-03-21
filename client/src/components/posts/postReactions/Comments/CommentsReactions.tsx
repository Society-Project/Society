import React, { useState } from 'react';
import ChatBubbleOutlineOutlinedIcon from '@mui/icons-material/ChatBubbleOutlineOutlined';
import { IconButton } from '@mui/material';
import Checkbox from '@mui/material/Checkbox';
import ThumbUpIcon from '@mui/icons-material/ThumbUp';


const label = { inputProps: { 'aria-label': 'Checkbox demo' } };


export const CommentsReactions = () => {

    return (
        <>
            <div id='cmntReactions'>
                <Checkbox {...label} icon={<ThumbUpIcon />} checkedIcon={<Liked />} />
                <IconButton >
                    <ChatBubbleOutlineOutlinedIcon />
                </IconButton>
            </div>

        </>
    )
}

const Liked = () => {
    let [count, setCount] = useState(1);
    return (
        <>
            <ThumbUpIcon onClick={() => setCount(count++)} sx={{ color: '#6495ED' }} />
            <div id='likesCounter' >{count}</div>
        </>
    )
}

