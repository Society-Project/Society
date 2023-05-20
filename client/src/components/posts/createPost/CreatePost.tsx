import { useState, ChangeEvent } from 'react';
import '../../Styles/PostReactions.scss';
import { Post } from '../Post';
import { Grid, Box } from '@mui/material';
import LocalOfferIcon from '@mui/icons-material/LocalOffer';
import AddPhotoAlternateIcon from '@mui/icons-material/AddPhotoAlternate';
import PinDropIcon from '@mui/icons-material/PinDrop';

import useWindowScreenSize from '../../../useWindowScreenSize';
import { CreatePostFunction } from '../../api';

export const CreatePost = (post: any) => {
    const [width, height] = useWindowScreenSize();
    const [clicked, setClicked] = useState<boolean>(false);
    const [textContent, setTextContent] = useState<string>("");
    const [imageUrl, setImageUrl] = useState<string>("");
    const [errorMessage, setErrorMessage] = useState<string>("");

    const onClickHandler = async () => {
        const postObject: object = { textContent, imageUrl };

        if(textContent.length === 0) {
            return setErrorMessage("You must enter some text");
        }

        const uploadPostResponse = await CreatePostFunction(postObject);

        if(!uploadPostResponse) {
            return setErrorMessage("Something went wrong");
        }

        setClicked(true);
        setTextContent("");
        window.location.reload();
    }

    return (
        <Box className={ width > 900 ? "create-post-class" : "create-post-mobile-class" }>
            <Box data-testid="error-message" className='error-message'>{errorMessage}</Box>
            <Grid className={ errorMessage.length > 0 ? 'create-post-grid-on-error' : 'create-post-grid' }>
                <input data-testid="create-post-input-field" className='create-post-input-field' placeholder='Create post...' onChange={(event: ChangeEvent<HTMLInputElement>) => setTextContent(event.target.value)} />
                <Box id='create-post-button' className='create-post-button-class'>
                    <Box className={ width < 900 ? "icon-post-class" : "icon-post-desktop" }>
                        <Box className="icons">
                            <LocalOfferIcon className='attach-image-icon' />
                            <AddPhotoAlternateIcon className='upload-photo-icon' />
                            <PinDropIcon className='post-location-icon' />
                        </Box>
                        <button data-testid="create-post-button" id='create-post-button' className={ width > 900 ? "create-post-button" : "create-post-button-mobile" } onClick={onClickHandler}>Post</button>
                    </Box>
                </Box>
            </Grid>
            <Post />
        </Box>
    )
}
