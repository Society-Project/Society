import React from 'react';
import { Comment } from './Comment';
import { Box, Divider, Grid, IconButton, ListItemText, Paper } from '@mui/material';
import Avatar from '@mui/material/Avatar'
import "../../../Styles/CommentStyles/CommentFunctionality.scss";

import { TextareaAutosize } from '@mui/base';
import { CommentsReactions } from './CommentsReactions';

interface CmntFunctionalityProps {
    comments: Comment[];
    setComments: (comments: Comment[]) => void;
}


export const CmntFunctionality: React.FC<CmntFunctionalityProps> = ({
    comments,
    setComments,
}) => {

    const handleSave = (commentId: number, newText: string) => {

        setComments(
            comments.map((comment) => {
                if (comment.id == commentId && comment.text != '') {

                    return { ...comment, text: newText, editable: false };
                }
                return comment;
            })
        )
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
    }
    const handleDelete = (commentId: number) => {
        setComments(comments.filter((comment) => comment.id !== commentId));
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
                            <Avatar className="user-profile-image">R</Avatar>

                            <Box className="username-and-comment">
                                <p>Didi Didova</p>

                                <ListItemText
                                    sx={{ marginLeft: 2 }}
                                    secondary={

                                        <TextareaAutosize
                                            spellCheck="false"
                                            className='edit-text-area'
                                            value={comment.text}
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
                            </Box>

                            {/* <Box className="comment-reactions">
                                <CommentsReactions />
                            </Box> */}
                        </Box>
                    })
                }

            </Grid>
        </Box >
    )
}
