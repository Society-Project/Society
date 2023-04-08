import { useState, useEffect } from 'react';
import { Box } from '@mui/material';
import profilePage from '../chat/friend.png';
import "../Styles/StoriesPageBody.scss";
import userStoryImage from '../../images/userStory.jpg';
import NavigateNextIcon from '@mui/icons-material/NavigateNext';
import useWindowScreenSize from '@/useWindowScreenSize';

const StoriesPageBody = () => {
    const [width, height]: any = useWindowScreenSize();
    const [friendBubble, setFriendBubble]: any = useState([]);
    const [currentScreenSize, setCurrentScreenSize]: any = useState(null);
    let [numberOfGeneratedBubblesForFriends, setNumberOfGeneratedBubblesForFriends]: any = useState(5)

    const arrayOfGeneratedBubblesForFriends: Array<object> = [];

    for (let i = 1; i <= numberOfGeneratedBubblesForFriends; i++) {
        arrayOfGeneratedBubblesForFriends.push(
            <Box className='friend-side'>
                <img src={profilePage.src} className='friend-image' alt="profile picture" />
                <h3 className='username'>Ivo Ivo</h3>
            </Box>
        );
    }


    return (
        <Box className={width > 900 ? 'stories-page-body-component' : 'stories-mobile-page-body-component'}>
            
            <Box className='friend-main-page-component'>
                {
                    arrayOfGeneratedBubblesForFriends.map((item: any, index: number) => {
                        return <Box className='friend-page-component' key={index}>
                            {item}

                        </Box>
                    })
                }
            </Box>
            <Box className='user-story-and-next-button-class'>
                <Box className='user-story-class'>
                    <h2 className='username'>Ivo Ivo</h2>
                    <img src={userStoryImage.src} className="user-story-image" alt="User's story image" />
                </Box>
                <Box className='next-story-button'>
                    <NavigateNextIcon className='next-button' />
                </Box>
            </Box>
        </Box>
    )
}

export default StoriesPageBody