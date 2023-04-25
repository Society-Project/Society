import { ChangeEvent, useState } from 'react';
import { Icon, TextField, Button } from '@mui/material'
import PersonOutlineOutlinedIcon from '@mui/icons-material/PersonOutlineOutlined';

import HttpsOutlinedIcon from "@mui/icons-material/HttpsOutlined";
import dayjs, { Dayjs } from "dayjs";
import { DesktopDatePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { PasswordInput, UserSignUpTextFields } from './UserInputTextFields';

import "../../../Styles/LoginRegisterStyles.scss"

export const localhostURL: string = 'http://localhost:8080';

export const UserInput = () => {
  const [firstName, setFirstNameInput] = useState<string>("");
  const [lastName, setLastNameInput] = useState<string>("");
  const [email, setEmailAddressInput] = useState<string>("");
  const [username, setUsernameInput] = useState<string>("");
  const [password, setPasswordInput] = useState<string>("");

  const [birthday, setBirthday] = useState<Dayjs | null>(dayjs("11-03-2023"));

  const [responseMessageHandler, setResponseMessageHandler] = useState<string>("");

  const onSubmitHandler = async () => {
    const objectParameters: object = { firstName, lastName, email, username, password, birthday };

    try {
      //@dev This is for test porpouses only
      if(firstName.length == 0 || lastName.length == 0 || 
        email.length == 0 || username.length == 0 || password.length == 0){
          return setResponseMessageHandler('Please make sure to fill all the fields');
        }

      fetch(`${localhostURL}/api/v1/auth/signup`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(objectParameters)
      })
        .then(response => response.json())
        .then((responseMessage: any) => {
          if (responseMessage.status === 200) {
            setResponseMessageHandler(responseMessage.message);

            return window.location.href = "/login";
          }
        })
    } catch (error: any) {
      console.error(error);
    }
  }

  return (
    <div>
      <p
        data-testid="error-message"
        style={{
          textAlign: 'center',
          fontSize: '17px'
        }}
      >{responseMessageHandler}</p>

      {UserSignUpTextFields('First name', (event: ChangeEvent<HTMLInputElement>) => setFirstNameInput(event.target.value), "first-name-input")}
      {UserSignUpTextFields('Last name', (event: ChangeEvent<HTMLInputElement>) => setLastNameInput(event.target.value), "last-name-input")}
      {UserSignUpTextFields('Email address', (event: ChangeEvent<HTMLInputElement>) => setEmailAddressInput(event.target.value), "email-input")}
      {UserSignUpTextFields('Username', (event: ChangeEvent<HTMLInputElement>) => setUsernameInput(event.target.value), "username-input")}

      <TextField
        className="textField"
        variant="standard"
        InputProps={{
          startAdornment: (
            <Icon sx={{ marginLeft: 3, marginRight: 2 }}>
              <HttpsOutlinedIcon />
            </Icon>
          ),
          disableUnderline: true,
        }}
        margin="dense"
        type={"password"}
        placeholder="Password"
        sx={userInputSx}
        inputProps={{ 'data-testid': 'password-input' }}
        onChange={(event: ChangeEvent<HTMLInputElement>) => setPasswordInput(event.target.value)}
      />


      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <DesktopDatePicker
          value={birthday}
          minDate={dayjs("11-02-1920")}
          onChange={(newValue) => {
            setBirthday(newValue);
          }}
          renderInput={(params) => (
            <TextField
              className="textField"
              margin="dense"
              sx={userInputSx}
              {...params}
            />
          )}
        />
      </LocalizationProvider>

      <div style={{ width: '100%', textAlign: 'center' }}>
        <Button sx={{
          marginTop: 3,
          marginBottom: 3,
          borderRadius: 4,
          backgroundColor: 'darkGreen',
          boxShadow: '2px 4px 4px rgba(74, 122, 99, 0.54)',
          color: "white",
          fontFamily: 'Roboto',
          fontSize: 20,
          letterSpacing: 0.5,
          width: 130,
          height: 50,
          marginLeft: 'auto'
        }}
          variant='contained'
          color='success'
          data-testid="sign-up-submit-button"
          onClick={onSubmitHandler}
        >
          Sign Up
        </Button>
      </div>
    </div>
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

}

export const userTextFieldIcon = {
  startAdornment: (
    <Icon sx={{ marginLeft: 3, marginRight: 2, }}>
      <PersonOutlineOutlinedIcon />
    </Icon>
  ),
  disableUnderline: true,
}