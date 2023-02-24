import { useState, useEffect } from 'react';
import { Box, Button } from '@mui/material';
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import '../Styles/Stories.scss';

const Stories = () => {
    const [windowSize, setWindowSize]: any = useState({
        width: undefined,
        heigth: undefined
    })

    const arrayOfStoryBubbles: Array<object> = [];
    const storyBubbles: number = 5;

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
            {
                windowSize.width > 900 ? null : <Box className='stories-main-box'>
                <p className='stories-paragraph'>Stories</p>
    
                <Box className='stories-data-box'>
                    <Box className='add-story-box'>
                        <Button className='add-story-button'>+ add story</Button>
                        <Box className='straight-line'>.</Box>
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
            }
         </Box>
       
    )
}

export default Stories