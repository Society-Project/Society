import { Box, Button, Typography } from '@mui/material'
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
      maxWidth={'45vw'}
      alignItems="center"
      justifyContent={"center"}
      marginTop={'7vh'}
      marginLeft={'5vh'}
      
      borderRadius={5}
      boxShadow={'3px 13px 80px rgba(0, 0, 0, 0.25);'}
    >
      <Typography
        variant="h2"
        padding={'2vh'}
        textAlign='center'
      >
        <img src={headerLogo.src} alt="HeaderLogo" />
      </Typography>

      <UserInput />

      <Button sx={{
        marginTop: 3,
        marginBottom: 3,
        borderRadius: 4,
        backgroundColor: darkGreen,
        boxShadow: '2px 4px 4px rgba(74, 122, 99, 0.54)',
        color: "white",
        fontFamily: 'Roboto',
        fontSize: 20,
        letterSpacing: 0.5,
        width: 130,
        height: 50,
      }}
        variant='contained'
        color='success'>
        Sign Up
      </Button>
      <SignUpLogInBtn />
    </Box>
  )
}