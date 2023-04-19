"use client";
import { useState } from "react";
import styled from "styled-components";
import ClearIcon from "@mui/icons-material/Clear";
import SearchIcon from "@mui/icons-material/Search";

import { Box, TextField } from '@mui/material';

import "./Styles/Header.scss";

export const SearchBar = () => {
  const [searchValue, setSearchValue] = useState<string>("");
  const [isSearchButtonClicked, setIsSearchButtonClicked] = useState<boolean>(false);

  function onSeachButtonClick() {
    setIsSearchButtonClicked(isSearchButtonClicked => !isSearchButtonClicked);
  }

  return (
        <Box className="search-bar">
            <SearchIcon onClick={onSeachButtonClick} className='search-button' />
            {
               isSearchButtonClicked ? <TextField
               label="Search"
               value={searchValue}
               onChange={(e) => setSearchValue(e.target.value)}
               InputProps={{
                 endAdornment: searchValue && (
                   <CloseIconWrapper onClick={() => setSearchValue("")}>
                     <ClearIcon />
                   </CloseIconWrapper>
                 ),
                 color: "success",
               }}
               InputLabelProps={{ color: "success" }}
             /> : null
            }
        </Box>
  );
};

const CloseIconWrapper = styled.div`
  :hover {
    cursor: pointer;
  }
`;
