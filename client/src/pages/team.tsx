import React from "react";

import styled from "styled-components";
import { NavigationBar } from "@/components/NavigationBar/Navigation";
import "../../src/components/Styles/TeamPageStyles.scss";
import { TeamPage } from "@/components/teamPage/TeamPage";

const team = () => {
  return <Main className="main-page">
    <NavigationBar />
    <TeamPage />
  </Main>
};

export default team;

const Main = styled.div`
  position: relative;
`;
