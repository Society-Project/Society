import React from 'react';
import { Comment } from './Comment';
import ListItemAvatar from '@mui/material/ListItemAvatar';
import ListItem from '@mui/material/ListItem';
import { Box, Divider, Grid, IconButton, ListItemText, Paper } from '@mui/material';
import Avatar from '@mui/material/Avatar';
import { TextareaAutosize } from '@mui/base';
import Popover from '@mui/material/Popover';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import '../../../Styles/PostReactions.scss';
import PopupState, { bindTrigger, bindPopover } from 'material-ui-popup-state';
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
        <>
            {
                comments.length > 0 ? (
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
                            comments.map((comment) => (
                                <Grid key={comment.id}>
                                    {
                                        comment.editable ? (
                                            <>
                                                <Divider variant="middle" sx={{ marginTop: 2 }} />
                                                <ListItem alignItems="flex-start">
                                                    <ListItemAvatar>
                                                        <Avatar sx={{ marginLeft: 2, backgroundColor: 'green' }}>R</Avatar>
                                                    </ListItemAvatar>
                                                    <ListItemText
                                                        sx={{ marginLeft: 2 }}
                                                        primary="username"
                                                        secondary={
                                                            <React.Fragment>
                                                                <TextareaAutosize
                                                                    spellCheck="false"
                                                                    className='editTextArea'
                                                                    value={comment.text}
                                                                    onChange={(event) =>
                                                                        setComments(
                                                                            comments.map((com) =>
                                                                                com.id === comment.id ? { ...com, text: event.target.value } : com
                                                                            )
                                                                        )
                                                                    }
                                                                />
                                                            </React.Fragment>
                                                        }
                                                    />
                                                    <Grid id='saveBtn'>
                                                        <button onClick={() => handleSave(comment.id, comment.text)}>Save</button>
                                                    </Grid>
                                                </ListItem>
                                            </>
                                        ) : (
                                            <>
                                                <Grid className={'commentsBox'}>

                                                    <ListItem alignItems="flex-start">
                                                        <ListItemAvatar>
                                                            <Avatar sx={{ marginLeft: 2, backgroundColor: 'green' }}>R</Avatar>
                                                        </ListItemAvatar>
                                                        <ListItemText
                                                            sx={{ marginLeft: 2 }}
                                                            primary="username"
                                                            secondary={
                                                                <>
                                                                    <div className='cmnt'>
                                                                        <textarea
                                                                            disabled
                                                                            spellCheck="false"
                                                                            className='comTextArea'
                                                                            value={comment.text}
                                                                            onChange={(event) =>
                                                                                setComments(
                                                                                    comments.map((c) =>
                                                                                        c.id === comment.id ? { ...c, text: event.target.value } : c
                                                                                    )
                                                                                )
                                                                            }

                                                                        />
                                                                        <PopupState variant="popover" popupId="demo-popup-popover">
                                                                            {(popupState) => (
                                                                                <div className='popover'>
                                                                                    <IconButton {...bindTrigger(popupState)}>
                                                                                        <MoreVertIcon />
                                                                                    </IconButton>
                                                                                    <Popover
                                                                                        {...bindPopover(popupState)}
                                                                                        anchorOrigin={{
                                                                                            vertical: 'bottom',
                                                                                            horizontal: 'center',
                                                                                        }}
                                                                                        transformOrigin={{
                                                                                            vertical: 'top',
                                                                                            horizontal: 'center',
                                                                                        }}
                                                                                    >
                                                                                        <div className='editBtnsDiv'>
                                                                                            <button onClick={() => handleEdit(comment.id)}>Edit</button>
                                                                                            <button onClick={() => handleDelete(comment.id)}>Delete</button>
                                                                                        </div>

                                                                                    </Popover>
                                                                                </div>
                                                                            )}
                                                                        </PopupState>
                                                                    </div>
                                                                    <Divider sx={{ width: 380, marginLeft: 0 }} />
                                                                    <CommentsReactions />
                                                                </>
                                                            }
                                                        />
                                                    </ListItem>
                                                </Grid>
                                            </>
                                        )
                                    }

                                </Grid>
                            ))
                        }
                    </Grid>

                ) : null
            }

        </>
    )
}
