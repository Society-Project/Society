import CardMedia from '@mui/material/CardMedia';
import '../../Styles/PostReactions.scss';

export const PostImage = () => {
    return (
        <CardMedia
            className='post-picture'
            component="img"
            height="300"
            image="https://th.bing.com/th/id/OIP.hC_vqh5DJMZLXAenddSP9gHaE8?w=264&h=180&c=7&r=0&o=5&dpr=1.3&pid=1.7"
            alt="Image"
        />
    )
}