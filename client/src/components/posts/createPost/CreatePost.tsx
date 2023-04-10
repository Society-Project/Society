import { useState } from 'react';
import '../../Styles/PostReactions.scss';
import { Post } from '../Post';
import { Grid, TextareaAutosize, Box } from '@mui/material';
import LocalOfferIcon from '@mui/icons-material/LocalOffer';
import AddPhotoAlternateIcon from '@mui/icons-material/AddPhotoAlternate';
import PinDropIcon from '@mui/icons-material/PinDrop';

import useWindowScreenSize from '@/useWindowScreenSize';

export const CreatePost = (post: any) => {
    const [width, height] = useWindowScreenSize();
    const [clicked, setClicked] = useState(false);

    const onClickHandler = () => {
        setClicked(true);
    }
    return (
        <Box className={ width > 900 ? "create-post-class" : "create-post-mobile-class" }>
            <Grid className='create-post-grid'>
                <input className='create-post-input-field' placeholder='Create post...' />
                <Box id='create-post-button' className='create-post-button-class'>
                    <Box className={ width < 900 ? "icon-post-class" : "icon-post-desktop" }>
                        <Box className="icons">
                            <LocalOfferIcon className='attach-image-icon' />
                            <AddPhotoAlternateIcon className='upload-photo-icon' />
                            <PinDropIcon className='post-location-icon' />
                        </Box>
                        <button id='create-post-button' className={ width > 900 ? "create-post-button" : "create-post-button-mobile" } onClick={onClickHandler}>Post</button>
                    </Box>
                </Box>
            </Grid>
            {
                clicked ? <Post /> : null
            }
        </Box>
    )
}
