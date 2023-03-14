import { useState } from 'react';
import { Box } from "@mui/material";
import "./PersonChatStyle.scss";
import friendImage from "../friend.png";

const PersonChat = () => {
    //By default the user will be offline
    const [status, setStatus]: any = useState(false);


    return (
        <Box className='person-chat'>
            <Box className='person-chat-header'>
                <Box className='profile-picture-username-and-status'>
                    <Box className='profile-image-class'>
                        <img src={friendImage.src} className='profile-picture' alt="Friend profile picture" />
                    </Box>
                    <Box className='username-and-status'>
                        <h3>Viki</h3>
                        <p>{status ? 'online' : 'offline'}</p>
                    </Box>
                </Box>

                <Box></Box>
            </Box>
        </Box>
    )
}

export default PersonChat