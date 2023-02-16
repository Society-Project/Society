import React from "react";
import { Box, Button, TextField } from "@mui/material";
import laptopPicture from "/src/images/laptopPicture.png";
import headerLogo from "/src/images/headerLogo.png";
import "../../src/components/Styles/LoginStyles.scss";
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import InputAdornment from '@mui/material/InputAdornment';
import PersonOutlineOutlinedIcon from '@mui/icons-material/PersonOutlineOutlined';

const Login = () => {
  return (
    <Box className="wrapper" margin="-8px">
      <img className="Image" src={laptopPicture.src} alt="Picture" />
      <Box
        className="Form"
        display="flex"
        flexDirection={"column"}
        maxWidth={500}
        alignItems="center"
        margin="auto"
        borderRadius={5}
        boxShadow={"3px 13px 80px rgba(0, 0, 0, 0.25);"}
      >
        <img
          className="Logo"
          src={headerLogo.src}
          width="245px"
          alt="HeaderLogo"
        />
        <TextField
          className="UsernameField"
          variant="standard"
          InputProps={{
            disableUnderline: true,
            startAdornment: (
              <InputAdornment position="start">
                <PersonOutlineOutlinedIcon />
              </InputAdornment>
            ),
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
            width: 250,
            height: 48,
            display: "flex",
            paddingLeft: 1,
            justifyContent: "center",
          }}
        />       
          <TextField
            className="PasswordField"
            variant="standard"
            InputProps={{
              disableUnderline: true,
              startAdornment: (
                <InputAdornment position="start">
                  <LockOutlinedIcon />
                </InputAdornment>
              ),
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
              width: 250,
              height: 48,
              display: "flex",
              paddingLeft: 1,
              justifyContent: "center",
            }}
          />
        <Button className="LoginButton">Log In</Button>
        {/* <Button
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
        </Button> */}
      </Box>
    </Box>
  );
};

export default Login;
