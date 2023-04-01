import { useState } from 'react';
import { Box } from "@mui/system";

import { NavigationBar } from "@/components/NavigationBar/Navigation";
import "../Styles/StoriesPage.scss";
import StoriesPageBody from "./StoriesPageBody";
import SearchIcon from '@mui/icons-material/Search';
import CloseIcon from '@mui/icons-material/Close';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';

const StoriesPage = () => {
    const [userSearchInput, setSearchInput]: any = useState("");

    const deleteSearchField = () => {
        setSearchInput("")
    }
    return (
        <Box className='stories-page-main-component'>
            <Box className='navigation-bar'>
                <NavigationBar />
            </Box>
            <Box className='stories-body'>
                <Box className='search-bar'>
                    <Box className='user-input-and-close-button'>
                     <SearchIcon className="search-icon" />
                        <input
                            type="text"
                            placeholder="Search..."
                            className="user-input"
                            value={userSearchInput}
                            onChange={(event) => setSearchInput(event.target.value)}
                        />
                        <CloseIcon className="close-button" onClick={deleteSearchField} />
                    </Box>
                </Box>
                <Box className='stories-and-title'>
                    <h1 className='stories-title'>Stories</h1>
                    <StoriesPageBody />
                </Box>
            </Box>
        </Box>
    )
}

export default StoriesPage