import { Button } from '@mui/material'
import { useState } from 'react'
import '../Styles/LoginRegisterStyles.scss'
import { logInBtnSx, signUpBtnSx } from './btns-sx';
import { useRouter } from 'next/router';

export const    SignUpLogInBtn = () => {

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
                className={router.asPath == '/login' ? 'clickedBtn' : 'inactiveTab'}
                id='LoginPanel' onClick={logOnClick}
                sx={logInBtnSx}
                 >
                Log In
            </Button>

            <Button
                className={router.asPath == '/register' ? 'clickedBtn' : 'inactiveTab'}
                id='SignPanel' onClick={signOnClick}
                sx={signUpBtnSx} >
                Sign Up
            </Button>

        </>
    )
}






