import React, { useState } from 'react'
import Checkbox from '@mui/material/Checkbox';
import ThumbUpIcon from '@mui/icons-material/ThumbUp';
import '../../Styles/PostReactions.scss';


const label = { inputProps: { 'aria-label': 'Checkbox demo' } };

export const Like: React.FC = () => {
    return (
        <Checkbox className='like-post' {...label} icon={<ThumbUpIcon />} checkedIcon={<Liked />} />
    )
}

const Liked = () => {
    let [count, setCount] = useState<number>(1);
    return (
        <>
            <ThumbUpIcon  onClick={() => setCount(count++)} sx={{ color: '#6495ED' }} />
            <div id='likesCounter' >{ count }</div>
        </>
    )
}

