import { Box } from '@mui/material'
import headerLogo from "/src/images/headerLogo.png"
import "../../../Styles/LoginRegisterStyles.scss"
import { UserInput } from '../user-input/UserInput'
import { SignUpLogInBtn } from '../../../logIn-signUp-btns/SignUp-LogIn-Btn'

export const darkGreen = "#206F4F";

export const BoxElement = () => {
  return (
    <Box className="Form"
      display="flex"
      flexDirection={"column"}
      alignItems="center"
      justifyContent={"center"}
      marginTop={'7vh'}
      
      borderRadius={5}
      boxShadow={'3px 13px 80px rgba(0, 0, 0, 0.25);'}
    >
        <img className='HeaderLogo' src={headerLogo.src} alt="HeaderLogo" />

      <UserInput />
      <SignUpLogInBtn />
    </Box>
  )
}