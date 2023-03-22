import { Box } from '@mui/material';
import { NavigationBar } from '@/components/NavigationBar/Navigation';
import WindowScreenSize from '@/WindowScreenSize';
import {Stories} from '@/components/StoriesBar/Stories';
import headerLogo from '../../images/logo-mark.svg';
import ChatBody from './ChatBody/ChatBody';

import "./Chat.scss";

const Chat = () => {
    const [width, height] = WindowScreenSize();

    return (
        <Box className={width > 1100 ? 'main-chat-component' : 'main-chat-mobile-version'}>
            <Box className='navigation-component'>
                <NavigationBar />
            </Box>
            <Box className='chat-component'>
                <ChatBody />
            </Box>
            { width > 1100 ? <Stories /> : null }
        </Box>
    )
}
export default Chat;