import { Box, Button } from '@mui/material';
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import "../Styles/Stories.scss";

export const Stories = () => {
    const repeatParagraphElements: Array<object> = [];
    const predefinedLengthOfElements: number = 5;

    for (let i = 0; i < predefinedLengthOfElements; i++) {
        repeatParagraphElements.push(
            //This property should be replaced with person's profile picture
            <Button className='person-story'>.</Button>
        );
    }

    return (
        <Box className='stories-root-box'>

            <p className='stories-paragraph'>Stories</p>

            <Box className='story-data-box'>
                <Box className='add-story-box'>
                    <Button className='add-story-button'>+ add story</Button>
                    <Box className='break-line' />
                    {
                        repeatParagraphElements.map((item: any, index: number) => {
                            return <Box className='stories-of-followed-people' key={index}>
                                {item}
                            </Box>
                        })
                    }
                    <Box className='expand-icon'><MoreHorizIcon /></Box>
                </Box>
            </Box>
        </Box>
    );
}