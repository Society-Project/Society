import React from "react";
import styled from "styled-components";
import { NavigationBar } from "@/components/NavigationBar/Navigation";
import "../../src/components/Styles/TeamPageStyles.scss";
import { TeamPage } from "@/components/teamPage/TeamPage";
import ProtectedRoutes from "@/ProtectedRoutes/ProtectedRoutes";

const team = () => {
  return <ProtectedRoutes>
     <Main className="main-page">
      <NavigationBar />
      <TeamPage />
    </Main>
  </ProtectedRoutes>
};

export default team;

const Main = styled.div`
  position: relative;
`;
