import { useState, useEffect } from 'react';
import { Box, Button } from '@mui/material';
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import '../Styles/Stories.scss';
import AccountCircleRoundedIcon from '@mui/icons-material/AccountCircleRounded';


export const Stories = () => {

    const [windowSize, setWindowSize]: any  = useState({
        width: window.innerWidth,
        heigth: window.innerHeight
    })


    const arrayOfStoryBubbles: Array<object> = [];
    let storyBubbles: number = 5;

    if (windowSize.width < 600) storyBubbles = 4

    if (windowSize.width < 500) storyBubbles = 2

    for (let i = 0; i < storyBubbles; i++) {
        //Should replace the . with user's profile picture
        arrayOfStoryBubbles.push(
            <Button className='person-story'>.</Button>
        );
    }
    useEffect(() => {

        window.addEventListener('resize', () => {
            setWindowSize({
                width: window.innerWidth,
                height: window.innerHeight
            })
        })


    }, [])

    return (
        <Box>

            <Box className={windowSize.width > 900 ? 'stories-main-box' : 'stories-main-box-for-mobile'}>
                <AccountCircleRoundedIcon className='user-icon'/>
                <p className='stories-paragraph'>Stories</p>

                <Box className='stories-data-box'>
                    <Box className='add-story-box'>
                        <Button className='add-story-button'>+ add story</Button>
                        <Box className='straight-line'></Box>
                    </Box>
                    <Box className='other-people-stories'>
                        {
                            arrayOfStoryBubbles.map((item: any, index: number) => {
                                return <Box className='stories-of-followed-people' key={index}>
                                    {item}
                                </Box>
                            })
                        }
                    </Box>
                    <Box className='more-options'><MoreHorizIcon /></Box>
                </Box>
            </Box>

        </Box>

    )
}
