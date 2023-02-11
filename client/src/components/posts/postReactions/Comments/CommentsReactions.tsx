import React from 'react';
import { Like } from '../Like';
import ChatBubbleOutlineOutlinedIcon from '@mui/icons-material/ChatBubbleOutlineOutlined';
import { IconButton } from '@mui/material';


export const CommentsReactions = () => {



    return (
        <>
        <div id='cmntReactions'>
            <Like />
            <IconButton >
                <ChatBubbleOutlineOutlinedIcon />
            </IconButton>
        </div>
    
    </>
  )
}

