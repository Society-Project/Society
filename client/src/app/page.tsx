"use client";
import { useState, useEffect } from 'react';
import styled from "styled-components";
import { NavigationBar } from "@/components/NavigationBar/Navigation";
import "../components/Styles/MainPage.scss";
import { MainPageBox } from "@/components/page-box/PageBox";
import { Stories } from "@/components/StoriesBar/Stories";


const Home = () => (
  <>
    <Main className="main-page">
      <NavigationBar />
      <MainPageBox />
      <Stories />
    </Main>
  </>
);

export default Home;

const Main = styled.div`
  position: relative;
`;
