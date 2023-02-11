"use client";
import Link from "next/link";
import styled from "styled-components";
import { Colors } from "../colors/Colors";
import { Shadows } from "../values/Shadows";
import { Radiuses } from "../values/Radiuses";
import { Spacings } from "../values/Spacings";

import headerLogo from "/src/images/headerLogo.png";
import SearchField from "./SearchField";
import {
  FULL_WIDTH,
  HEADER_LOGO_HEIGHT,
  HEADER_LOGO_WIDTH,
  HEADER_TOP_LINE_HEIGHT,
} from "../values/HardCodedValues";

const Header = () => {
  return (
    <HeaderWrapper>
      <InnerWrapper>
        <TopLine />
        <Link href="/">
          <Logo src={headerLogo.src} alt="Header logo" />
        </Link>
        <SearchField />
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
  width: calc(${FULL_WIDTH} - ${HEADER_LOGO_WIDTH});
  right: 0;
  top: 0;
  border-radius: ${Radiuses.headerTopLineRadius};
`;
