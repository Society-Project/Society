import { Button, Icon } from '@mui/material'
import { useState } from 'react'
import '../Styles/LoginRegisterStyles.scss'
import BedtimeIcon from '@mui/icons-material/Bedtime';
import { disabled, logInBtnSx, logInIconHelper, logInSecIconHelper, signUpBtnSx, signUpIconHelper, signUpSecIconHelper } from './btns-sx';
import { useRouter } from 'next/router';

export const SignUpLogInBtn = () => {

    const router = useRouter();

    const [logclick, setLogClick] = useState(false);
    const [signclick, setSignClick] = useState(false);


    const logOnClick = () => {
        router.push('/login');
        setLogClick(true);
    }
    const signOnClick = () => {
        router.push('/register');
        setSignClick(true);

    }

    return (
        <>
            <Button
                className={router.asPath == '/login' ? 'clickedBtn' : ''}
                id='li-su-btns' onClick={logOnClick}
                sx={logInBtnSx}
                 >
                Log In
            </Button>

            <Button
                className={router.asPath == '/register' ? 'clickedBtn' : ''}
                id='li-su-btns' onClick={signOnClick}
                sx={signUpBtnSx} >
                Sign Up
            </Button>

            <Icon sx={router.asPath == '/login' ? logInIconHelper : disabled}><BedtimeIcon /></Icon>
            <Icon sx={router.asPath == '/login' ? logInSecIconHelper : disabled}><BedtimeIcon /></Icon>
            <Icon sx={router.asPath == '/register' ? signUpIconHelper : disabled}><BedtimeIcon /></Icon>
            <Icon sx={router.asPath == '/register' ? signUpSecIconHelper : disabled}><BedtimeIcon /></Icon>
        </>
    )
}



