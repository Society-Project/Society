import { useState, useEffect } from 'react';
import { Box, TextField } from '@mui/material';
import "./ChatBody.scss";
import friendImage from '../friend.png';
import WindowScreenSize from '../../../WindowScreenSize';
import PersonChat from '../PersonChat/PersonChat';

const ChatBody = () => {
    const [width, height] = WindowScreenSize();

    const generateFriends: number = 5;
    const arrayOfGeneratedFriends: Array<object> = [];

    (() => {
        for (let i = 0; i < generateFriends; i++) {
            arrayOfGeneratedFriends.push(
                <Box className='friend-profile'>
                    <Box className='user-profile-picture'>
                        <img src={friendImage.src} />
                    </Box>
                    <Box className='username-and-last-message'>
                        <h4>Vasko the frog</h4>
                        <p>Blalala</p>
                    </Box>
                </Box>
            )
        }
    })()

    return (
        <Box className={ width > 900 ? 'chat-body-main-component' : 'chat-mobile-version' }>

            <Box className='header-component'>
                <h3 className='sub-title'>All messages</h3>
                {
                    arrayOfGeneratedFriends.map((item: any, index: number) => {
                        return <Box key={index} className='mapped-info'>{item}</Box>
                    })
                }
            </Box>

            <PersonChat />
        </Box>
    )
}
export default ChatBody;