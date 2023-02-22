import { useState } from 'react';
import { Box, ListItem } from '@mui/material'
import HomeOutlinedIcon from '@mui/icons-material/HomeOutlined';
import NotificationsIcon from '@mui/icons-material/Notifications';
import TextsmsOutlinedIcon from '@mui/icons-material/TextsmsOutlined';
import PeopleIcon from '@mui/icons-material/People';
import SettingsOutlinedIcon from '@mui/icons-material/SettingsOutlined';
import LogoutOutlinedIcon from '@mui/icons-material/LogoutOutlined';

//File will execute automatically
import '../Styles/NavigationBar.scss';

export const NavigationBar = () => {
    let tokenMap: Map<number, boolean> = new Map();

    //I use any here because it throws an error when trying to set the type to Map<number, boolean>
    const [mapState, setMapState]: any = useState(null);


    const IconArray: any = [
        <HomeOutlinedIcon />,
        <NotificationsIcon />,
        <TextsmsOutlinedIcon />,
        <PeopleIcon />,
        <SettingsOutlinedIcon />,
        <LogoutOutlinedIcon className='log-out-icon' />,
    ]

    function onButtonClick(index: number) {
        tokenMap.set(index, true);
        setMapState(tokenMap);
    }


    return (
        <Box className='navigation-root-element'>
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
