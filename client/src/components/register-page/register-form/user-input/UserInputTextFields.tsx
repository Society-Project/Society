import { Icon, TextField } from '@mui/material'
import "../../../Styles/LoginRegisterStyles.scss"
import { userInputSx, userTextFieldIcon } from './UserInput'
import HttpsOutlinedIcon from '@mui/icons-material/HttpsOutlined';
import dayjs, { Dayjs } from 'dayjs';
import { DesktopDatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import React from 'react';

export const UserInputTextField = (placeHolderValue: string) => {
    return (
        <TextField
            variant="standard"
            InputProps={userTextFieldIcon}
            margin='dense'
            type={'text'}
            placeholder={placeHolderValue}
            sx={
                userInputSx
            }
        />
    )
}

export const PasswordInput = () => {
    return (
        <TextField
            variant="standard"
            InputProps={{
                startAdornment: (
                    <Icon sx={{ marginLeft: 3, marginRight: 2, }}>
                        <HttpsOutlinedIcon />
                    </Icon>
                ),
                disableUnderline: true,
            }}
            margin='dense'
            type={'password'}
            placeholder='Password'
            sx={
                userInputSx
            }
        />
    )
}

export const BirthDate = () => {
    const [value, setValue] = React.useState<Dayjs | null>(dayjs('11-03-2023'));
    return (
      <>
        <LocalizationProvider dateAdapter={AdapterDayjs} >
          <DesktopDatePicker
            value={value}
            minDate={dayjs('11-02-1920')}
            onChange={(newValue) => {
              setValue(newValue);
            }}
            renderInput={(params) => <TextField margin='dense' sx={ userInputSx } {...params} />}
          />
        </LocalizationProvider>
      </>
    );
  }