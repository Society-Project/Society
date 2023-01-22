"use client";
import Link from "next/link";
import { useState } from "react";
import styled from "styled-components";
import { Colors } from "../colors/Colors";
import { Shadows } from "../values/Shadows";
import { Radiuses } from "../values/Radiuses";
import { Spacings } from "../values/Spacings";
import { Avatar, TextField } from "@mui/material";
import ClearIcon from "@mui/icons-material/Clear";
import SearchIcon from "@mui/icons-material/Search";
import headerLogo from "/src/images/headerLogo.png";
import {
  FULL_WIDTH,
  HEADER_LOGO_HEIGHT,
  HEADER_LOGO_WIDTH,
  HEADER_TOP_LINE_HEIGHT,
  HEADER_TOP_LINE_WIDTH,
} from "../values/HardCodedValues";

const Header = () => {
  const [searchValue, setSearchValue] = useState<string>("");

  return (
    <HeaderWrapper>
      <InnerWrapper>
        <TopLine />
        <Link href="/">
          <Logo src={headerLogo.src} alt="Header logo" />
        </Link>
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
      </InnerWrapper>
    </HeaderWrapper>
  );
};

export default Header;

const HeaderWrapper = styled.div`
  width: ${FULL_WIDTH};
  background-color: ${Colors.white};
  box-shadow: ${Shadows.headerShadow};
  position: relative;
  top: 0;
  left: 0;
`;

const InnerWrapper = styled.div`
  display: flex;
  direction: row;
  justify-content: space-between;
  padding: ${Spacings.small};
`;

const SearchAndProfilePicWrapper = styled.div`
  display: flex;
  direction: row;
  align-items: center;
  padding-top: ${Spacings.medium};
  gap: ${Spacings.medium};
  padding-right: ${Spacings.small};
`;

const Logo = styled.img`
  height: ${HEADER_LOGO_HEIGHT};
  width: ${HEADER_LOGO_WIDTH};
  opacity: 0.9;

  :hover {
    cursor: pointer;
    opacity: 1;
  }
`;

const TopLine = styled.div`
  position: absolute;
  background-color: ${Colors.caramel};
  height: ${HEADER_TOP_LINE_HEIGHT};
  width: ${HEADER_TOP_LINE_WIDTH};
  right: 0;
  top: 0;
  border-radius: ${Radiuses.headerTopLineRadius};
`;

const CloseIconWrapper = styled.div`
  :hover {
    cursor: pointer;
  }
`;
