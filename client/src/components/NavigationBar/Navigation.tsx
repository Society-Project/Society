import { useState } from 'react';
import { Box, ListItem, styled } from '@mui/material'
import HomeOutlinedIcon from '@mui/icons-material/HomeOutlined';
import NotificationsIcon from '@mui/icons-material/Notifications';
import TextsmsOutlinedIcon from '@mui/icons-material/TextsmsOutlined';
import PeopleIcon from '@mui/icons-material/People';
import SettingsOutlinedIcon from '@mui/icons-material/SettingsOutlined';
import LogoutOutlinedIcon from '@mui/icons-material/LogoutOutlined';
import headerLogo from 'src/images/logo-mark.svg'
import { SvgIconProps } from '@mui/material/SvgIcon'

import '../Styles/NavigationBar.scss';
import '../Styles/Header.scss'
import { Logo } from '../Header';
import { useRouter } from 'next/navigation';

export const NavigationBar = () => {
    const router = useRouter();
    console.log(router)
    let tokenMap: Map<number, boolean> = new Map();

    //I use any here because it throws an error when trying to set the type to Map<number, boolean>
    const [mapState, setMapState]: any = useState(null);

    
    const [homeClk, setHomeClk] = useState(false);

    const homeOnClk = () => {
        router.push('/');
        setHomeClk(true);
    }

    type icon = SvgIconProps

    const IconArray: icon[] = [
        <HomeOutlinedIcon />,
        <NotificationsIcon />,
        <TextsmsOutlinedIcon />,
        <PeopleIcon />,
        <SettingsOutlinedIcon />,
        <LogoutOutlinedIcon className='log-out-icon' />,
    ]

    const onButtonClick = (index: number) => {
        tokenMap.set(index, true);
        setMapState(tokenMap);
    }

    return (
        <Box className='navigation-root-element'>
                <Box className="header-logo">
                    <Logo src={headerLogo.src} className='header-logo-image' alt="Header logo" />
                </Box>
            <Box className='icon-buttons'>
                {
                    IconArray.map((item: any, index: number) => {
                       return <ListItem key={index} className='list-item-element' sx={{ background: mapState?.get(index) ? 'rgb(235, 233, 233)' : 'white' }} onClick={() => {
                            return onButtonClick(index)
                        }}>{item}</ListItem>
                    })
                } 

            </Box>
        </Box>
    );
}


