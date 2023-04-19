import React, { useState } from 'react';
import { Box, Grid, ListItemText, Avatar } from '@mui/material';

import ChatBubbleOutlineOutlinedIcon from '@mui/icons-material/ChatBubbleOutlineOutlined';
import DeleteOutlineOutlinedIcon from '@mui/icons-material/DeleteOutlineOutlined';
import EditOutlinedIcon from '@mui/icons-material/EditOutlined';
import SendOutlinedIcon from '@mui/icons-material/SendOutlined';

import "../../../Styles/CommentStyles/CommentFunctionality.scss";
import { Like } from '../Like';
import { Comment } from './Comment';

interface CmntFunctionalityProps {
    comments: Comment[];
    setComments: (comments: Comment[]) => void;
}


export const CmntFunctionality: React.FC<CmntFunctionalityProps> = ({
    comments,
    setComments,
}) => {
    const [isEditButtonClicked, setIsEditButtonClicked] = useState(false);

    const handleSave = (commentId: number, newText: string) => {
        setComments(
            comments.map((comment) => {
                if (comment.id == commentId && comment.text != '') {

                    return { ...comment, text: newText, editable: false };
                }
                return comment;
            })
        )

        setIsEditButtonClicked(false);
    } 
    const handleEdit = (commentId: number) => {
        setComments(
            comments.map((comment) => {
                if (comment.id === commentId) {
                    return { ...comment, editable: true };
                }
                return comment;
            })
        )

        setIsEditButtonClicked(true);
    }
    const handleDelete = (commentId: number) => {
        setComments(comments.filter((comment) => comment.id !== commentId));
        setIsEditButtonClicked(false);
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
                    comments.map((comment) => {
                        return <Box key={comment.id} className='user-comment'>
                            <Box className='username-and-profile-picture'>
                              <Avatar className="user-profile-image">R</Avatar>
                              <p className='username-paragraph'>Didi Didova</p>

                            </Box>
                            <Box className="username-and-comment">

                                <ListItemText
                                    sx={{ marginLeft: 2 }}
                                    secondary={

                                        <input
                                            className={ !isEditButtonClicked ? 'disabled-input-field' : 'edit-text-area' }
                                            value={comment.text}
                                            disabled={isEditButtonClicked ? false : true}
                                            onChange={(event) =>
                                                setComments(
                                                    comments.map((com) =>
                                                        com.id === comment.id ? { ...com, text: event.target.value } : com
                                                    )
                                                )
                                            }   
                                        />
                                    }
                                />
                               <Box className='like-and-comment-icons'> 
                                   <Like />
                                   <ChatBubbleOutlineOutlinedIcon className='comment-icon' />
                                   <EditOutlinedIcon onClick={() => handleEdit(comment.id)} className='edit-comment' />
                                   <DeleteOutlineOutlinedIcon onClick={() => handleDelete(comment.id)} className='delete-comment' />
                                   
                                   {
                                     isEditButtonClicked ? <SendOutlinedIcon onClick={(event: any) => handleSave(comment.id, event.target.value)} className='save-icon' /> : null
                                   }
                               </Box>
                            </Box>
                        </Box>
                    })
                }
            </Grid>
        </Box >
    )
}
