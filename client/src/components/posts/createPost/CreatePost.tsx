import React, { useState } from 'react';
import '../../Styles/PostReactions.scss';
import { Post } from '../Post';
import { Grid, IconButton, TextareaAutosize } from '@mui/material';
import LocalOfferIcon from '@mui/icons-material/LocalOffer';
import AddPhotoAlternateIcon from '@mui/icons-material/AddPhotoAlternate';
import PinDropIcon from '@mui/icons-material/PinDrop';

export const CreatePost = (post: any) => {

    const [clicked, setClicked] = useState(false);

    const onClickHandler = () => {
        setClicked(true);
    }
    return (
        <>
            <Grid className='createPostGrid'>
                <TextareaAutosize className='createPostTxtArea' placeholder='Create post...'/>
                <div id='createPostDivBtn'>
                    <IconButton id='tag'>
                    <LocalOfferIcon />
                    </IconButton>
                    <IconButton id='addPhoto'>
                    <AddPhotoAlternateIcon />
                    </IconButton>
                    <IconButton id='location'>
                    <PinDropIcon />
                    </IconButton>
                    <button id='createPostBtn' onClick={onClickHandler}>Post</button>
                </div>
            </Grid>
            {
                clicked ? <Post /> : null
            }
        </>
    )
}
