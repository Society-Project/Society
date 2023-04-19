import { useState } from 'react';
import { Box, Grid, Paper, Avatar, CardHeader, CardActions, IconButton } from '@mui/material';
import { blue } from '@mui/material/colors';
import ChatBubbleOutlineOutlinedIcon from '@mui/icons-material/ChatBubbleOutlineOutlined';
import ShareOutlinedIcon from '@mui/icons-material/ShareOutlined';
import Divider from '@mui/material/Divider';
import ThumbUpIcon from '@mui/icons-material/ThumbUp';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
import SentimentVerySatisfiedIcon from '@mui/icons-material/SentimentVerySatisfied';
import SentimentVeryDissatisfiedIcon from '@mui/icons-material/SentimentVeryDissatisfied';

import { PostImage } from './image/Image';
import '../Styles/PostReactions.scss';
import { Like } from './postReactions/Like';
import { Comment } from './postReactions/Comments/Comment';
import useWindowScreenSize from '@/useWindowScreenSize';

export const Post = () => {
    const [width, height] = useWindowScreenSize();
    const [comment, setComment] = useState<boolean>(false);
    const [hover, setHover] = useState<boolean>(false);
 
    return (
        <Box className={ width > 900 ? 'post-page-uploader' : 'post-page-uploader-mobile'}>
             <p className='news-feed-paragraph'>News feed</p>
            <Grid className={ width > 900 ? 'post-grid' : 'post-mobile-grid' }>
                <Paper className='post-paper'>

                    <Box className='user-profile-image'>
                        <Avatar sx={{ bgcolor: blue[500] }} className='user-post-icon' aria-label="recipe" />
                        <Box className='username'>Didi Didova</Box>
                    </Box>
                    <PostImage />
                    {
                        hover ? <Box className={ width > 900 ? 'reaction-pop-up-window' : 'reaction-mobile-pop-up' }>
                            <ThumbUpIcon className='like-icon' />
                            <FavoriteBorderIcon className='love-icon' />
                            <SentimentVerySatisfiedIcon className='funny-icon' />
                            <SentimentVeryDissatisfiedIcon className='sad-icon' />
                    </Box> : null
                    }

                    <Divider variant="middle" className='divider' />
                    <CardActions className='reactions'>
                        <button 
                            onClick={() => setHover(!hover)}
                            className='like-button'><Like /></button>

                        <p className='like-divider'></p>
                        <IconButton className='comment' onClick={() => setComment(state => !state)}>
                            <ChatBubbleOutlineOutlinedIcon />
                        </IconButton>
                        <p className='comment-divider'></p>
                        <IconButton>
                            <ShareOutlinedIcon />
                        </IconButton>
                    </CardActions>
                    {
                        comment ? <div><Comment /></div> : null
                    }
                </Paper>
            </Grid>
        </Box>
    );
}


