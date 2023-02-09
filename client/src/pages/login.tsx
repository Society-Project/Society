import React from 'react'
import {Box, Button, TextField, Typography} from '@mui/material'

const Login = () => {
  return (
    <div>
      <form>
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
                boxShadow={'5px 5px 5px 5px #ccc'}    
                color={"red"}                  
                >                
                <Typography 
                variant="h2" 
                padding={3} 
                textAlign='center'
                >
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
      </form>
    </div>
  )
}

export default Login
