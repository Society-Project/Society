import { useState, useEffect } from 'react';
import { Box } from '@mui/material';
import { NavigationBar } from '@/components/NavigationBar/Navigation';
import headerLogo from '../../images/headerLogo.png'
import { Logo } from '../../components/Header';
import ChatBody from './ChatBody/ChatBody';
import SearchIcon from '@mui/icons-material/Search';
import CloseIcon from '@mui/icons-material/Close';
import WindowScreenSize from '@/WindowScreenSize';
import Stories from '@/components/StoriesBar/Stories';
import Header from '../../components/Header';

import "../../components/Styles/NavigationBar.scss";
import "./Chat.scss";

const Chat = () => {
    const [width, height] = WindowScreenSize();
    const [searchInput, setSearchInput]: any = useState("");
    const [showInputField, setShowInputField]: any = useState(true);

    const showSearchInputField = () => {
        if (width < 900) {
            setShowInputField(!showInputField)
        }
    }

    useEffect(() => {
        if (width < 900) {
            setShowInputField(false);
        }
    }, [])

    return (
        <Box className={width > 900 ? 'main-chat-component' : 'main-chat-mobile-version'}>
        
            <Box className='navigation-bar'>
                {/* <NavigationBar /> */}
            </Box>
            <Box className='search-bar-component'>
                <SearchIcon className='search-icon' onClick={showSearchInputField} />
                {
                    !showInputField ? <div>
                        <input
                            type="text"
                            className='search-field'
                            placeholder='Search...'
                            onChange={(event) => setSearchInput(event.target.value)}
                        />
                        <CloseIcon className='close-icon' />
                    </div> : null
                }
            </Box>

            <Box className='main-chat-box'>
                <h1 className='chat-box-subtitle'>Messages</h1>

                <Box className='chat-box-body'>
                    <ChatBody />
                </Box>
            </Box>

            <Box className='stories-container'>
                {/* <Stories />  */}
            </Box>
        </Box>
    )
}
export default Chat;