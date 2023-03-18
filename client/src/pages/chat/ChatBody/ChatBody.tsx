import { useState } from 'react';
import { Box } from '@mui/material';
import profileImage from '../friend.png';
import headerLogo from '../../../images/logo-mark.svg';
import expandIcon from '../../../images/Vector.png'

import SearchIcon from '@mui/icons-material/Search';
import CloseIcon from '@mui/icons-material/Close';
import LocalPhoneOutlinedIcon from '@mui/icons-material/LocalPhoneOutlined';
import VideocamOutlinedIcon from '@mui/icons-material/VideocamOutlined';
import MoreVertOutlinedIcon from '@mui/icons-material/MoreVertOutlined';
import AttachFileOutlinedIcon from '@mui/icons-material/AttachFileOutlined';
import SentimentSatisfiedOutlinedIcon from '@mui/icons-material/SentimentSatisfiedOutlined';
import SendOutlinedIcon from '@mui/icons-material/SendOutlined';

import WindowScreenSize from '@/WindowScreenSize';

import "./ChatBody.scss";
import "./ChatBodyMobile.scss";

const ChatBody = () => {
    const [width, height] = WindowScreenSize();
    const [userStatus, setUserStatus]: any = useState(false);
    const [isSearchIconOnMobileClicked, setIsSearchIconOnMobileClicked]: any = useState(false);
    const [userInput, setUserInput]: any = useState("");
    const [userSearchInput, setUserInputSearch]: any = useState("");
    const [userMessages, setUserMessages]: Array<any> = useState([]);
    const [isUnreadMessagesAvaiable, setIsUnreadMessagesAvaiable]: any = useState(true);

    const numberOfBubbles: number = 6;
    const arrayOfAvaiableFriends: Array<Object> = [];

    const searchIconHandler = () => {
        setIsSearchIconOnMobileClicked(!isSearchIconOnMobileClicked);
    }

    const clearButtonHandler = () => {
        setUserInputSearch("");
    }

    const sendMessageHandler = () => {
        setUserMessages([...userMessages, userInput]);
        setUserInput("");
    }

    for (let i = 1; i <= numberOfBubbles; i++) {
        arrayOfAvaiableFriends.push(
            <Box className='friend-page-and-unread-messages'>
                <Box className='friends-page'>
                    <Box className='profile-image-class'>
                        <img src={profileImage.src} className='profile-image' />
                    </Box>
                    <Box className='username-and-last-message-class'>
                        <h3 className='username'>Petya Petrova</h3>
                        <p className='last-message'>Blalala</p>
                    </Box>
                </Box>
                <Box className='last-sended-message-and-unread-messages'>
                    <p className='last-sended-message'>12:45</p>

                    <p className='unread-messages'>{isUnreadMessagesAvaiable ? '2' : null}</p>
                </Box>
            </Box>
        );
    }


    return (
        <Box className={width > 1100 ? 'main-chat-body-component' : 'main-chat-component-mobile'}>
            <Box className='search-part'>
                {
                    width > 1100 ? <Box className='search-bar-on-desktop'>
                        <SearchIcon className="search-icon" />
                        <Box className='user-input-and-close-button'>
                            <input
                                type="text"
                                placeholder="Search..."
                                className="user-input"
                                value={userSearchInput}
                                onChange={(event) => setUserInputSearch(event.target.value)}
                            />
                            <CloseIcon className="close-button" onClick={clearButtonHandler} />
                        </Box>
                    </Box> : <Box className='search-bar-on-tablet-and-mobile'>
                        <img src={headerLogo.src} className='header-logo-on-tablet-and-mobile' alt="Society logo" />

                        <Box className='search-components-on-mobile'>
                            <SearchIcon className="search-icon" onClick={searchIconHandler} />

                            {
                                isSearchIconOnMobileClicked ? <Box className='user-input-and-close-button'>
                                    <input
                                        type="text"
                                        placeholder="Search..."
                                        className="user-input"
                                        value={userSearchInput}
                                        onChange={(event) => setUserInputSearch(event.target.value)}
                                    />
                                    <CloseIcon className="close-button" onClick={clearButtonHandler} />
                                </Box> : null
                            }
                            <img src={expandIcon.src} alt="Show more icon" className='expand-icon' />
                        </Box>
                    </Box>
                }
            </Box>

            <h1 className='messages-subtitle'>Messages</h1>
            <Box className="chat-with-friends">

                <Box className="friends-and-person-chat">
                    <h3 className='all-messages-title'>All messages</h3>
                    {
                        arrayOfAvaiableFriends.map((item: any, index: number) => {
                            return <Box key={index} className='friend-page-main-class'>
                                {item}
                            </Box>
                        })
                    }
                </Box>
                <Box className='person-chat'>
                    <Box className='user-data-and-options'>
                        <Box className='profile-picture-and-username'>
                            <Box className='profile-picture'>
                                <img src={profileImage.src} alt="User profile picture" />
                            </Box>
                            <Box className='user-data'>
                                <h3 className='username'>Petya Petrova</h3>
                                <p className='status'>{userStatus ? 'online' : 'offline'}</p>
                            </Box>
                        </Box>
                        <Box className='person-chat-options'>
                            <LocalPhoneOutlinedIcon className='option-icon' />
                            <VideocamOutlinedIcon className='option-icon' />
                            <MoreVertOutlinedIcon className='option-icon' />
                        </Box>
                    </Box>

                    <Box className='user-chat-history'>
                        <Box className='user-message'>
                            {
                                userMessages.map((item: any, index: number) => {
                                    return <Box key={index} className='message-block'>
                                        <Box className='concrete-message'>
                                            {item}
                                        </Box>
                                    </Box>
                                })
                            }
                        </Box>
                    </Box>

                    <Box className='user-text-and-emoji'>

                        <AttachFileOutlinedIcon className='attach-icon' />
                        <Box className='input-field-and-emoji'>
                            <input
                                type="text"
                                className='user-input-message'
                                placeholder='Type your message here...'
                                value={userInput}
                                onChange={(event) => setUserInput(event.target.value)}
                            />
                            <Box className='send-and-emoji-icon'>
                                {userInput.length > 0 ? <SendOutlinedIcon className='send-message-icon' onClick={sendMessageHandler} /> : null}
                                <SentimentSatisfiedOutlinedIcon className='emoji-icon' />
                            </Box>
                        </Box>

                    </Box>
                </Box>
            </Box>
        </Box>
    )
}

export default ChatBody