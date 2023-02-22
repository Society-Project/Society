import { Icon } from '@mui/material'
import "../../../Styles/LoginRegisterStyles.scss"
import PersonOutlineOutlinedIcon from '@mui/icons-material/PersonOutlineOutlined';
import { BirthDate, PasswordInput, UserInputTextField } from './UserInputTextFields';

export const UserInput = () => {

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
      <BirthDate  />
    </>
  )
}

export const userInputSx = {
  borderRadius: 4,
  backgroundColor: 'rgba(74, 122, 99, 0.09)',
  fontFamily: 'Roboto',
  fontSize: 22,
  letterSpacing: 0.5,
  height: 55,
  display: 'flex',
  justifyContent: 'center',
  border: 'none',
  
}

export const datePickerSx = {
  borderRadius: 1,
  backgroundColor: 'rgba(74, 122, 99, 0.09)',
  fontFamily: 'Roboto',
  fontSize: 22,
  letterSpacing: 0.5,
  height: 55,
  display: 'flex',
  justifyContent: 'center',
}

export const userTextFieldIcon = {
  startAdornment: (
    <Icon sx={{ marginLeft: 3, marginRight: 2, }}>
      <PersonOutlineOutlinedIcon />
    </Icon>
  ),
  disableUnderline: true,
}