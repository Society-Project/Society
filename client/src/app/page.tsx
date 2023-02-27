"use client";
import styled from "styled-components";
import { NavigationBar } from "@/components/NavigationBar/Navigation";
import "../components/Styles/MainPage.scss";
import { MainPageBox } from "@/components/page-box/PageBox";

const Home = () => (
  <>
    <Main className="main-page">
      <NavigationBar />
      <MainPageBox />
    </Main>
  </>
);

export default Home;

const Main = styled.div`
  position: relative;
`;
