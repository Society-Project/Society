import React, { useState } from 'react';
import '../../Styles/PostReactions.scss';
import { Post } from '../Post';
import { Grid, IconButton, TextareaAutosize } from '@mui/material';
import LocalOfferIcon from '@mui/icons-material/LocalOffer';
import AddPhotoAlternateIcon from '@mui/icons-material/AddPhotoAlternate';
import PinDropIcon from '@mui/icons-material/PinDrop';
import Card from '@mui/material/Card';


export const CreatePost = (post: any) => {

    const [clicked, setClicked] = useState(false);

    const onClickHandler = () => {
        setClicked(true);
    }
    return (
        <>
            <Grid className='createPostGrid'>
                <TextareaAutosize className='createPostTxtArea' />
                <div id='createPostDivBtn'>
                    <IconButton className='CreatePostIconBtn' id='tag'>
                    <LocalOfferIcon />
                    </IconButton>
                    <IconButton className='CreatePostIconBtn' id='addPhoto'>
                    <AddPhotoAlternateIcon />
                    </IconButton>
                    <IconButton className='CreatePostIconBtn' id='location'>
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
