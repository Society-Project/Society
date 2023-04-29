"use client";
import styled from "styled-components";
import { NavigationBar } from "@/components/NavigationBar/Navigation";
import { MainPageBox } from "@/components/page-box/PageBox";
import useWindowScreenSize from "@/useWindowScreenSize";
import ProtectedRoutes from "@/ProtectedRoutes/ProtectedRoutes";

import "../components/Styles/MainPage.scss";

const Home = () => {
  const [width,height] = useWindowScreenSize();

  return <Main className={ width > 1024 ? "main-page" : "main-mobile-view" }>
    <ProtectedRoutes>
      <NavigationBar />
    </ProtectedRoutes>
    <ProtectedRoutes>
      <MainPageBox />
    </ProtectedRoutes>
  </Main>
};

export default Home;

const Main = styled.div`
  position: relative;
`;
