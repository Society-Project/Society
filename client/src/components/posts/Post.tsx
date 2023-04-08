import { useState } from 'react';
import { Box, Grid, Paper, Avatar, CardHeader, CardActions, IconButton } from '@mui/material';
import { blue } from '@mui/material/colors';
import ChatBubbleOutlineOutlinedIcon from '@mui/icons-material/ChatBubbleOutlineOutlined';
import ShareOutlinedIcon from '@mui/icons-material/ShareOutlined';
import Divider from '@mui/material/Divider';

import { PostImage } from './image/Image';
import '../Styles/PostReactions.scss';
import { Like } from './postReactions/Like';
import { Comment } from './postReactions/Comments/Comment';
import useWindowScreenSize from '@/useWindowScreenSize';

export const Post = () => {
    const [width, height] = useWindowScreenSize();
    const [comment, setComment] = useState(false);

    return (
        <Box className={ width > 900 ? 'post-page-uploader' : 'post-page-uploader-mobile'}>
             <p className='news-feed-paragraph'>News feed</p>
            <Grid className='post-grid'>
                <Paper className='post-paper'>

                    <Box className='user-profile-image'>
                        <Avatar sx={{ bgcolor: blue[500] }} className='user-post-icon' aria-label="recipe" />
                        <Box className='username'>Didi Didova</Box>
                    </Box>
                    <PostImage />

                    <Divider variant="middle" className='divider' />
                    <CardActions className='reactions'>
                        <Like />
                        <p className='like-divider'></p>
                        <IconButton className='comment' onClick={() => setComment(state => !state)}>
                            <ChatBubbleOutlineOutlinedIcon />
                        </IconButton>
                        <p className='like-divider'></p>
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


