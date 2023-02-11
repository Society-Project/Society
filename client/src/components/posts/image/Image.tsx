import CardMedia from '@mui/material/CardMedia';

export const PostImage = () => {
    return (
        <CardMedia
            sx={{
                width: '95%',
                marginLeft: 1.8,

            }}
            component="img"
            height="300"
            image="https://th.bing.com/th/id/OIP.hC_vqh5DJMZLXAenddSP9gHaE8?w=264&h=180&c=7&r=0&o=5&dpr=1.3&pid=1.7"
            alt="Image"
        />
    )
}