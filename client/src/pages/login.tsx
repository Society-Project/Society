import React from 'react'
import {Box, Button, TextField, Typography } from '@mui/material'
import laptopPicture from "/src/images/laptopPicture.jpg";
import headerLogo from "/src/images/headerLogo.png"
import styled from "styled-components";



const Login = () => {
  return (
    <div className='wrapper'>
        <Logo src={laptopPicture.src} alt="Picture" />

          <Box 
                display="flex" 
                flexDirection={"column"} 
                maxWidth={381}
                alignItems="center" 
                justifyContent={"center"}
                margin="auto"
                marginTop={6}
                padding={10}
                borderRadius={5}
                boxShadow={'3px 13px 80px rgba(0, 0, 0, 0.25);'}    
                color={"red"} 
                >
                {/* <img src='https://images.unsplash.com/photo-1486312338219-ce68d2c6f44d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1472&q=80' width="500" height="600" alt=''/> */}
                <Typography 
                variant="h2" 
                padding={3} 
                textAlign='center'
                >
                <img src={headerLogo.src} alt="HeaderLogo" 
                width= "434px" 
                height= "241px"/>
                  Login
                  </Typography>
                <TextField 
                variant="standard"
                InputProps={{
                       disableUnderline: true,
                     }}
                margin='dense' 
                type={'text'} 
                placeholder='username'
                sx={{
                  borderRadius:3, 
                  backgroundColor:"rgba(74, 122, 99, 0.09)", 
                  fontFamily: 'Robotto',
                  fontSize: 22,
                  letterSpacing: 0.5,
                  width: 300,
                  height: 55,
                  display: 'flex',
                  alignItems: 'center',
                  justifyContent: 'center'
                }}
                />
                <TextField 
                variant="standard"
                InputProps={{
                       disableUnderline: true,
                     }}
                margin='dense' 
                type={'password'}  
                placeholder='password'
                sx={{
                  borderRadius:3, 
                  backgroundColor:"rgba(74, 122, 99, 0.09)", 
                  fontFamily: 'Robotto',
                  fontSize: 22,
                  letterSpacing: 0.5,
                  width: 300,
                  height: 55,
                  display: 'flex',
                  alignItems: 'center',
                  justifyContent: 'center'
                }}
                />
                <Button sx={{
                  marginTop:3, 
                  borderRadius:10, 
                  backgroundColor:"white", 
                  color:"rgba(74, 122, 99, 1)", 
                  fontFamily: 'Robotto',
                  fontSize: 20,
                  letterSpacing: 0.5,
                  width: 130,
                  height: 50,                  
                }}
                 variant='contained' 
                 color='success'>
                    Log In
                    </Button>
                    <Button 
                    sx={{marginTop:3, borderRadius:10, 
                    backgroundColor:"white",
                    color:"black",
                  }}
                    variant='contained'
                    color='success'>
                    Sign Up
                    </Button>
          </Box>

    </div>
  )
}
const Logo = styled.img`
  height: 350px;
  width: 200px;
  opacity: 0.9;
  border-radius"0% 10% 10% 0%"


  :hover {
    cursor: pointer;
    opacity: 1;
  }
`;
export default Login
