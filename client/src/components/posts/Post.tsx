import * as React from 'react';
import { styled } from '@mui/material/styles';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardActions from '@mui/material/CardActions';
import Avatar from '@mui/material/Avatar';
import IconButton, { IconButtonProps } from '@mui/material/IconButton';
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
        <Grid sx={{
            p: 1,
            margin: 'auto',
            maxWidth: 600,
            marginTop: 2,
        }}>
            <Paper sx={{
                paddingTop: 1,
                borderRadius: 5,
                paddingBottom: 1,
                maxWidth: 1000,
                maxHeight: 3000,
                backgroundColor: 'white',
            }}>
                    
                        <CardHeader
                            avatar={
                                <Avatar sx={{ bgcolor: blue[500] }} aria-label="recipe">
                                    S
                                </Avatar>
                            }
                            action={
                                <IconButton aria-label="settings">
                                </IconButton>
                            }
                            title="username"
                        />
                        <PostImage />

                        <Divider variant="middle" sx={{marginTop: 2}}/>
                        <CardActions className='reactions'>
                                <Like />
                                <IconButton onClick={() => setComment(state => !state)}>
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
    );
}


