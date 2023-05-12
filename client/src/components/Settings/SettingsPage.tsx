import { Box } from '@mui/material';
import "../Styles/SettingsPage.scss";
import { NavigationBar } from '../NavigationBar/Navigation';
import useWindowScreenSize from '@/useWindowScreenSize';
import SettingsPageBody from './SettingsPageBody';

const SettingsPage = () => {
    const [width, height] = useWindowScreenSize();

    return (
        <Box className='settings-page'>
            {width > 1200 ? <NavigationBar /> : null}

            {
                width < 1200 ? <SettingsPageBody /> : <Box className= "settings-page-body">
                    <SettingsPageBody />
                </Box>
            }
                
        </Box>
    )
}

export default SettingsPage