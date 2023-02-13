import React from "react";
import { Box, Button, TextField, Typography } from "@mui/material";
import laptopPicture from "/src/images/laptopPicture.jpg";
import headerLogo from "/src/images/headerLogo.png";
import styled from "styled-components";
import "../../src/components/Styles/LoginRegisterStyles.scss";
import { SignUpLogInBtn } from "@/components/register-page/sign-in__sign-up-Btns/SignUp-LogIn-Btn";
 
export const Login = () => {
  return (
    <Box className="wrapper" margin="-8px">
      <img className="Image" src={laptopPicture.src} alt="Picture" />
      <Box
        className="Form"
        display="flex"
        flexDirection={"column"}
        maxWidth={560}
        alignItems="center"
        justifyContent={"center"}
        margin="auto"
        borderRadius={5}
        boxShadow={"3px 13px 80px rgba(0, 0, 0, 0.25);"}
        color={"red"}
      >
        {/* <img src='https://images.unsplash.com/photo-1486312338219-ce68d2c6f44d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1472&q=80' width="500" height="600" alt=''/> */}
        <Typography variant="h2" padding={5} width="245px" height="290px">
          <img
            src={headerLogo.src}
            width="245px"
            height="130px"
            alt="HeaderLogo"
          />
        </Typography>
        <TextField className="UsernameField"
          variant="standard"
          InputProps={{
            disableUnderline: true,
          }}
          margin="dense"
          type={"text"}
          placeholder="username"
          sx={{
            borderRadius: 3,
            backgroundColor: "rgba(74, 122, 99, 0.09)",
            fontFamily: "Robotto",
            fontSize: 22,
            letterSpacing: 0.5,
            width: 300,
            height: 55,
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
          }}
        />
        <TextField
          variant="standard"
          InputProps={{
            disableUnderline: true,
          }}
          margin="dense"
          type={"password"}
          placeholder="password"
          sx={{
            borderRadius: 3,
            backgroundColor: "rgba(74, 122, 99, 0.09)",
            fontFamily: "Robotto",
            fontSize: 22,
            letterSpacing: 0.5,
            width: 300,
            height: 55,
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
          }}
        />
        <Button className="LoginButton">Log In</Button>
        <Button
          sx={{
            marginTop: 3,
            borderRadius: 10,
            backgroundColor: "white",
            color: "black",
          }}
          variant="contained"
          color="success"
        >
          Sign Up
        </Button>
        <SignUpLogInBtn />
      </Box>
    </Box>
  );
};
 