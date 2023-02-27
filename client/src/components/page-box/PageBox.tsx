import { Box } from "@mui/material";
import { CreatePost } from "../posts/createPost/CreatePost";
import { Stories } from "../StoriesBar/Stories";
import { SearchBar } from "../SearchBar";

export const MainPageBox = () => {
    return (
        <Box className='page-box'>
            <SearchBar />
            <CreatePost />
            <Stories />
        </Box>
    )
}