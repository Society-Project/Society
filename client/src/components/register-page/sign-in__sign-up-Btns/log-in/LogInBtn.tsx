import { Button } from '@mui/material'
import React from 'react'
import '../../../Styles/LoginRegisterStyles.scss'
import { onClickObj } from '../on-click/onClickFunc'

export const LogInBtn = () => {
    return (
        <Button className='su-li-btns' id='log-in-btn' sx={[logInBtnSx]} >Log In</Button>
    )
}

const logInBtnSx = {
    top: '46.5vh',
     height: '5.5vh',
     width: '6vw',
    backgroundColor: 'white',
    color: 'rgba(0, 0, 0, 1)',
    fontFamily: 'Arial',
     fontSize: '1.2rem',
    border: 'none',
    position: 'absolute',
    left: '46vw',
    borderRadius: '0.5rem',
    '&:hover': {
        backgroundColor: 'white',
        borderColor: '#0062cc',
        boxShadow: 'none',
      }
}