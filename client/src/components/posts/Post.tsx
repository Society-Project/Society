import { ChangeEvent, useEffect, useState } from 'react';
import { Box, Grid, Paper, Avatar, CardActions, IconButton } from '@mui/material';
import { blue } from '@mui/material/colors';
import ChatBubbleOutlineOutlinedIcon from '@mui/icons-material/ChatBubbleOutlineOutlined';
import ShareOutlinedIcon from '@mui/icons-material/ShareOutlined';
import Divider from '@mui/material/Divider';

import '../Styles/PostReactions.scss';
import { Like } from './postReactions/Like';
import { Comment } from './postReactions/Comments/Comment';
import useWindowScreenSize from '../../useWindowScreenSize';
import { getAllPosts, editPostFunction, deletePostFunction } from '../api';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import SendOutlinedIcon from '@mui/icons-material/SendOutlined';

export const Post = () => {
    const [width, height] = useWindowScreenSize();  
    const [userPostsData, setUserPostsData] = useState<any>([]);
    const [textContent, setEditTextContent] = useState<string>("");
    const [imageUrl, setImageUrl] = useState<string>("");
    const [editButton, setEditButton] = useState<boolean>(false);
    const [errorMessage, setErrorMessage] = useState<string>("");

    async function getAllPostsHandler() {
        const responseToGetAllPostRequest = await getAllPosts();

        setUserPostsData(responseToGetAllPostRequest.content)
    }

    async function deletePost(id: number) {
        const responseStatusCode = await deletePostFunction(id);
        
        if(responseStatusCode.status === 401){
            return setErrorMessage(responseStatusCode.message);
        }
        window.location.reload();
    }
    const editPostHandler = async (id: number) => {
        const editPostObject: object = { textContent, imageUrl }
        await editPostFunction(id, editPostObject);
        window.location.reload();
    }

    useEffect(() => {
        getAllPostsHandler();
    }, [])
 
    return (
        <Box className={ width > 900 ? 'post-page-uploader' : 'post-page-uploader-mobile'}>
             <p className='news-feed-paragraph'>News feed</p>
             { errorMessage ? <p>{errorMessage}</p> : null }
            {
                userPostsData.length > 0 ? userPostsData?.map((item: any) => {
                    return <Grid key={item.id} className={ width > 900 ? 'post-grid' : 'post-mobile-grid' }>
                    <Paper className='post-paper'>
                        <Box className='user-profile-image'>
                            <Box style={{ display: 'flex', alignItems: 'center' , width: '100% ' }}>
                                <Avatar sx={{ bgcolor: blue[500] }} className='user-post-icon' aria-label="recipe" />
                                <Box className='username'>{item.authorUsername}</Box>
                            
                            </Box>
                            
                            <Box style={{ display: 'flex', justifyContent: 'space-around', marginRight: '1rem' }}>
                                <EditIcon onClick={() => setEditButton(oldState => !oldState)} style={{ cursor: 'pointer' }} /> 
                                <DeleteIcon onClick={() => deletePost(item.id)} style={{ cursor: 'pointer', color: 'red' }} />
                                <SendOutlinedIcon onClick={() => editPostHandler(item.id)} style={{ cursor: 'pointer' }} />
                            </Box>
                        </Box>
                        <Box className='user-content'>
                            <textarea 
                             className="post-title"
                             onChange={(event: ChangeEvent<HTMLTextAreaElement>) => setEditTextContent(event.target.value)}
                             style={{ 
                                textAlign: item.imageUrl ? 'left' : 'center',
                                border: 'none',
                                width: '99%',
                                resize: 'none',
                                fontSize: '1.3rem',
                                outline: 'none',

                             }}
                              readOnly={ editButton ? false : true }
                             >{item.textContent}</textarea>
                            { item.imageUrl ? <img src={item.imageUrl} alt="User uploaded image" /> : null }
                        </Box>
                            
    
                        <Divider variant="middle" className='divider' />
                        <CardActions className='reactions'>
                            <button className='like-button'><Like /></button>
    
                            <p className='like-divider'></p>
                            <IconButton className='comment'>
                                <ChatBubbleOutlineOutlinedIcon />
                            </IconButton>
                            <p className='comment-divider'></p>
                            <IconButton>
                                <ShareOutlinedIcon />
                            </IconButton>
                        </CardActions>
                        <div><Comment postId={item.id} /></div> 
                    </Paper>
                </Grid>
                }) : <h2 style={{ textAlign: 'center' }}>No posts are avaiable</h2>
            }
        </Box>
    );
}


