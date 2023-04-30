import { useState, useEffect, ChangeEvent } from 'react';
import '../../Styles/PostReactions.scss';
import { Post } from '../Post';
import { Grid, Box } from '@mui/material';
import LocalOfferIcon from '@mui/icons-material/LocalOffer';
import AddPhotoAlternateIcon from '@mui/icons-material/AddPhotoAlternate';
import PinDropIcon from '@mui/icons-material/PinDrop';

import useWindowScreenSize from '@/useWindowScreenSize';
import { CreatePostFunction } from '../../api';

export const CreatePost = (post: any) => {
    const [width, height] = useWindowScreenSize();
    const [clicked, setClicked] = useState<boolean>(false);
    const [textContent, setTextContent] = useState<string>("");
    const [imageUrl, setImageUrl] = useState<string>("");

    const onClickHandler = async () => {
        //@dev We should fix 401 error in order to send request to the server and create a post
        const postObject: object = { textContent, imageUrl };

        const receivedData = await CreatePostFunction(postObject);
        console.log(await CreatePostFunction(postObject))

        // setClicked(true)
    }

    return (
        <Box className={ width > 900 ? "create-post-class" : "create-post-mobile-class" }>
            
            <Grid className='create-post-grid'>
                <input className='create-post-input-field' placeholder='Create post...' onChange={(event: ChangeEvent<HTMLInputElement>) => setTextContent(event.target.value)} />
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
