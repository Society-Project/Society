import { Button, Icon } from '@mui/material'
import React, { useState } from 'react'
import '../../Styles/LoginRegisterStyles.scss'
import BedtimeIcon from '@mui/icons-material/Bedtime';
import { logInBtnSx, logInIconHelper, logInSecIconHelper, signUpBtnSx, signUpIconHelper, signUpSecIconHelper } from './btns-sx';

export const SignUpLogInBtn = () => {
    const [logclick, setLogClick] = useState(false);
    const [signclick, setSignClick] = useState(false);


    const logOnClick = (event:React.FormEvent<HTMLButtonElement>) => {
        setLogClick(true);
        setSignClick(false);
    }
    const signOnClick = (event:React.FormEvent<HTMLButtonElement>) => {
        setSignClick(true);
        setLogClick(false);
    }

    return (
        <>
            <Button className={logclick ? 'clickedBtn' : ''} id='li-su-btns' onClick={logOnClick} sx={logInBtnSx} >Log In</Button>
            <Button className={signclick ? 'clickedBtn' : ''} id='li-su-btns' onClick={signOnClick} sx={signUpBtnSx} >Sign Up</Button>
            <Icon sx={signclick ? signUpIconHelper : logInIconHelper}><BedtimeIcon /></Icon>
            <Icon sx={signclick ? signUpSecIconHelper : logInSecIconHelper}><BedtimeIcon /></Icon>
            
        </>
    )
}



