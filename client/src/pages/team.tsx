import React from "react";
import styled from "styled-components";
import { NavigationBar } from "@/components/NavigationBar/Navigation";
import "../../src/components/Styles/TeamPageStyles.scss";
import { TeamPage } from "@/components/teamPage/TeamPage";


const team = () => (
  <>
    <Main className="main-page">
      <NavigationBar />
      <TeamPage />
    </Main>
  </>
)

export default team;

const Wrapper = styled.div`
  position: relative;
`;
const Main = styled.div`
  position: relative;
`;
