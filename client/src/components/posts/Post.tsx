import * as React from 'react';
import CardHeader from '@mui/material/CardHeader';
import CardActions from '@mui/material/CardActions';
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import { blue } from '@mui/material/colors';
import Grid from '@mui/material/Grid';
import Paper from '@mui/material/Paper';
import { PostImage } from './image/Image';
import { Comment } from './postReactions/Comments/Comment';
import ChatBubbleOutlineOutlinedIcon from '@mui/icons-material/ChatBubbleOutlineOutlined';
import ShareOutlinedIcon from '@mui/icons-material/ShareOutlined';
import { Like } from './postReactions/Like';
import '../Styles/PostReactions.scss'
import Divider from '@mui/material/Divider';


export const Post = () => {
    const [comment, setComment] = React.useState(false);

    return (
        <>
            <p className='news-feed-p'>News feed</p>
            <Grid className='post-grid'>
                <Paper className='post-paper'>

                    <CardHeader
                        avatar={
                            <Avatar sx={{ bgcolor: blue[500] }} className='user-post-icon' aria-label="recipe">
                                S
                            </Avatar>
                        }
                    />
                    <PostImage />

                    <Divider variant="middle" className='divider' />
                    <CardActions className='reactions'>
                        <Like />
                        <IconButton className='comment' onClick={() => setComment(state => !state)}>
                            <ChatBubbleOutlineOutlinedIcon />
                        </IconButton>
                        <IconButton>
                            <ShareOutlinedIcon />
                        </IconButton>
                    </CardActions>
                    {
                        comment ? <div><Comment /></div> : null
                    }
                </Paper>
            </Grid>
        </>
    );
}


