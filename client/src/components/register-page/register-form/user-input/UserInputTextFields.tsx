import { ChangeEvent, useState } from "react";
import { Icon, TextField } from "@mui/material";
import { userInputSx } from "./UserInput";
import HttpsOutlinedIcon from "@mui/icons-material/HttpsOutlined";
import dayjs, { Dayjs } from "dayjs";
import { DesktopDatePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { userTextFieldIcon } from "./UserInput";

import "../../../Styles/LoginRegisterStyles.scss";

export const UserSignUpTextFields = (placeholderText: string, callBackFunction: (event: ChangeEvent<HTMLInputElement>) => void) => {
  return (
    <TextField
    className="textField"
    variant="standard"
    InputProps={userTextFieldIcon}
    margin="dense"
    type={"text"}
    placeholder={placeholderText}
    sx={userInputSx}
    onChange={callBackFunction}
  />
  )
}

export const PasswordInput = (callBackFunction: (event: ChangeEvent<HTMLInputElement>) => void) => {
  return (
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
      onChange={callBackFunction}
    />
  );
};

export const BirthDate = () => {
  const [value, setValue] = useState<Dayjs | null>(dayjs("11-03-2023"));
  return (
    <>
      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <DesktopDatePicker
          value={value}
          minDate={dayjs("11-02-1920")}
          onChange={(newValue) => {
            setValue(newValue);
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
    </>
  );
};
