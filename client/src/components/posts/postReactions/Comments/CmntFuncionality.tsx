import React, { ChangeEvent, useState } from 'react';
import { Box, Grid, ListItemText, Avatar } from '@mui/material';

import ChatBubbleOutlineOutlinedIcon from '@mui/icons-material/ChatBubbleOutlineOutlined';
import DeleteOutlineOutlinedIcon from '@mui/icons-material/DeleteOutlineOutlined';
import EditOutlinedIcon from '@mui/icons-material/EditOutlined';
import SendOutlinedIcon from '@mui/icons-material/SendOutlined';

import "../../../Styles/CommentStyles/CommentFunctionality.scss";
import { Like } from '../Like';
import { editCommentFunction, deleteCommentFunction } from '../../../api';


export const CmntFunctionality: React.FC<any> = ({ comments, setComments }) => {
    const [isEditButtonClicked, setIsEditButtonClicked] = useState<boolean>(false);
    const [commentText, setEditCommentText] = useState<string>("");

    const saveEditedComment = async (commentId: number) => {
        const putObject: object = { commentText };
        await editCommentFunction(commentId, putObject);
    }

    const editCommentHandler = () => {
        setIsEditButtonClicked(oldState => !oldState)
    }

    const deleteCommentHandler = async (commentId: number) => {
        await deleteCommentFunction(commentId);
        
    }



    return (
        <Box className="comment-functionality-main-component">
            <Grid className='comments-box' sx={{
                paddingTop: 1,
                borderRadius: 5,
                paddingBottom: 1,
                maxWidth: 500,
                maxHeight: 3000,
                backgroundColor: 'white',
                boxShadow: 'none',

            }} >
                {
                    comments.length > 0 ? comments?.map((comment: any) => {
                        return <Box key={comment.id} className='user-comment'>
                            <Box className='username-and-profile-picture'>
                                <Avatar className="user-profile-image"></Avatar>
                                <p className='username-paragraph'>{comment.creatorUsername}</p>

                            </Box>
                            <Box className="username-and-comment">

                                <ListItemText
                                    sx={{ marginLeft: 2 }}
                                    secondary={

                                        <input
                                            className={!isEditButtonClicked ? 'disabled-input-field' : 'edit-text-area'}
                                            value={comment.commentText}
                                            disabled={isEditButtonClicked ? false : true}
                                            onChange={(event: ChangeEvent<HTMLInputElement>) => setEditCommentText(event.target.value)}
                                            style={{ fontSize: '1rem' }}
                                        />
                                    }
                                />
                                <Box className='like-and-comment-icons'>
                                    <Like />
                                    <ChatBubbleOutlineOutlinedIcon className='comment-icon' />
                                    <EditOutlinedIcon onClick={() => editCommentHandler()} className='edit-comment' />
                                    <DeleteOutlineOutlinedIcon onClick={() => deleteCommentHandler(comment.id)} className='delete-comment' />

                                    {
                                        isEditButtonClicked ? <SendOutlinedIcon onClick={() => saveEditedComment(comment.id)} className='save-icon' /> : null
                                    }
                                </Box>
                            </Box>
                        </Box>
                    }) : null
                }
            </Grid>
        </Box >
    )
}
