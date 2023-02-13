import { Button } from '@mui/material'
import React from 'react'
import '../../../Styles/LoginRegisterStyles.scss'
import { onClickObj } from '../on-click/onClickFunc'

export const SignUpBtn = () => {
    return (
        <Button className='su-li-btns' id='sign-up-btn' sx={[signUpBtnSx, onClickObj]} >Sign Up</Button>
    )
}

const signUpBtnSx = {
    marginTop: '10vh',
    backgroundColor: 'white',
    color: 'rgba(0, 0, 0, 1)',
    fontFamily: 'Arial',
    border: 'none',
    position: 'absolute',
    left: '46vw',
    '&:hover': {
        backgroundColor: 'white',
        borderColor: '#0062cc',
        boxShadow: 'none',
      }
}

