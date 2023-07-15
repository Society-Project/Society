import { useState } from 'react';
import { Box, ListItem } from '@mui/material'
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
import Cookie from 'universal-cookie';
 
export const NavigationBar = () => {
    const router = useRouter();
    let tokenMap: Map<number, boolean> = new Map();
    const cookie: Cookie = new Cookie();

    //I use any here because it throws an error when trying to set the type to Map<number, boolean>
    const [mapState, setMapState] = useState<any>(null);

    const homeOnClick = () => {
        router.push('/');
    }
    const settingsOnClick = () => {
        router.push('/settings');
    }

    const notificationsOnClick = () => {
        router.push('/notifications');
    }

    const friendsOnClick = () => {
        router.push('/friends');
    }
        
    const chatOnClick = () => {
        router.push('/chat');
    }

    const logOutOnClick = () => {
        router.push('/login');
        cookie.remove("accessToken");
    }

    type icon = SvgIconProps

    const IconArray: icon[] = [
        <HomeOutlinedIcon key={1} onClick={homeOnClick} />,
        <NotificationsIcon key={2} onClick={notificationsOnClick} />,
        <TextsmsOutlinedIcon key={3} onClick={chatOnClick} />,
        <PeopleIcon key={4} onClick={friendsOnClick} />,
        <SettingsOutlinedIcon key={5} onClick={settingsOnClick}  />,
        <LogoutOutlinedIcon key={6}
             className='log-out-icon' 
             onClick={logOutOnClick} />,
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


