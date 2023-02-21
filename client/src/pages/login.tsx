import React from "react";
import { Box, Button } from "@mui/material";
import headerLogo from "/src/images/headerLogo.png";
import "../../src/components/Styles/LoginRegisterStyles.scss";
import { SignUpLogInBtn } from "@/components/logIn-signUp-btns/SignUp-LogIn-Btn";
import { LaptopImg } from "@/components/register-page/laptop-img/LaptopImg";
import { darkGreen } from "@/components/register-page/register-form/form/BoxElement";
import { PasswordInput, UserInputTextField } from "@/components/register-page/register-form/user-input/UserInputTextFields";

const Login = () => {
  return (
    <div className='wrapper'>
      <LaptopImg />
      <Box className="Form"
        display="flex"
        flexDirection={"column"}
        alignItems="center"
        justifyContent={"center"}
        marginTop={'7vh'}
        borderRadius={5}
        boxShadow={'3px 13px 80px rgba(0, 0, 0, 0.25)'}
      >
        <img src={headerLogo.src} alt="HeaderLogo" className="headerLogoLogin" />
        {
          UserInputTextField('Username')
        }
        <PasswordInput />
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
          Log In
        </Button>
        <SignUpLogInBtn />
      </Box>
    </div>
  );
};

export default Login