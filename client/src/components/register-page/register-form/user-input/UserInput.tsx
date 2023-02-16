import React from 'react'
import { Icon, TextField } from '@mui/material'
import "../../../Styles/LoginRegisterStyles.scss"
import PersonOutlineOutlinedIcon from '@mui/icons-material/PersonOutlineOutlined';
import { BirthDate, PasswordInput, UserInputTextField } from './UserInputTextFields';

export const UserInput = ({ }: object) => {

  const placeholdersArr = [
    'First name',
    'Last name',
    'Email address',
    'Username'
  ]

  return (
    <>
      {placeholdersArr.map((a)=>{
        return UserInputTextField(a)
      })}
      <PasswordInput />
      <BirthDate />
    </>
  )
}

export const userInputSx = {
  borderRadius: 1,
  backgroundColor: 'rgba(74, 122, 99, 0.09)',
  fontFamily: 'Robotto',
  fontSize: 22,
  letterSpacing: 0.5,
  width: 300,
  height: 55,
  display: 'flex',
  justifyContent: 'center',
  '&:hover': {
    backgroundColor: 'rgba(74, 122, 99, 0.09)',
    border: 'none'
  }
}

export const userTextFieldIcon = {
  startAdornment: (
    <Icon sx={{ marginLeft: 3, marginRight: 2, }}>
      <PersonOutlineOutlinedIcon />
    </Icon>
  ),
  disableUnderline: true,
}