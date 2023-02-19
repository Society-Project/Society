import { useState } from "react";

import styled from "styled-components";
import { Spacings } from "../values/Spacings";

import { Avatar, TextField } from "@mui/material";
import ClearIcon from "@mui/icons-material/Clear";
import SearchIcon from "@mui/icons-material/Search";

const SearchField = () => {
  const [searchValue, setSearchValue] = useState<string>("");

  return (
    <SearchAndProfilePicWrapper>
      <TextField
        label="Search"
        value={searchValue}
        onChange={(e) => setSearchValue(e.target.value)}
        InputProps={{
          startAdornment: <SearchIcon />,
          endAdornment: searchValue && (
            <CloseIconWrapper onClick={() => setSearchValue("")}>
              <ClearIcon />
            </CloseIconWrapper>
          ),
          color: "success",
        }}
        InputLabelProps={{ color: "success" }}
      />
      <Avatar />
    </SearchAndProfilePicWrapper>
  );
};

const SearchAndProfilePicWrapper = styled.div`
  display: flex;
  direction: row;
  align-items: center;
  padding-top: ${Spacings.medium};
  gap: ${Spacings.medium};
  padding-right: ${Spacings.small};
`;

const CloseIconWrapper = styled.div`
  :hover {
    cursor: pointer;
  }
`;

export default SearchField;
