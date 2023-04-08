"use client";
import styled from "styled-components";
import { NavigationBar } from "@/components/NavigationBar/Navigation";
import { MainPageBox } from "@/components/page-box/PageBox";
import Stories from "@/components/StoriesBar/Stories";
import useWindowScreenSize from "@/useWindowScreenSize";
import "../components/Styles/MainPage.scss";

const Home = () => {
  const [width,height] = useWindowScreenSize();

  return <Main className={ width > 900 ? "main-page" : "main-mobile-view" }>
    <NavigationBar />
    <MainPageBox />
    { width > 900 ? <Stories /> : null }
  </Main>
};

export default Home;

const Main = styled.div`
  position: relative;
`;
