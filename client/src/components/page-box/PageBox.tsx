import { Box } from "@mui/material";
import { CreatePost } from "../posts/createPost/CreatePost";
import { SearchBar } from "../SearchBar";

import useWindowScreenSize from '@/useWindowScreenSize';
import SearchIcon from "@mui/icons-material/Search";

import headerLogo from '/src/images/logo-mark.svg'
import Vector from '../../images/Vector.png';

export const MainPageBox = () => {
    const [width, height] = useWindowScreenSize();

    return (
        <Box className={ width > 900 ? 'page-box' : 'mobile-page-box' }>
            
            { width > 900 ?  <SearchBar /> : <Box className="mobile-header-view">
                <img src={headerLogo.src} alt="Society logo" className='society-logo' />

                <Box className='search-bar-hamburger-menu'>
                    <SearchIcon />
                    <img src={Vector.src} alt="Hamburger menu" className='hamburger-menu-icon' />
                </Box>
            </Box> } 
            <CreatePost />
        </Box>
    )
}