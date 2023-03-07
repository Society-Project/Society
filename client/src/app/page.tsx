"use client";
import styled from "styled-components";
import Header from "../components/Header";
import { CreatePost } from "@/components/posts/createPost/CreatePost";
import { NavigationBar } from "@/components/NavigationBar/Navigation";
import Stories from '../components/StoriesBar/Stories'

//Code below is temporary
import { useState, useEffect } from 'react';
import { Test } from "@/components/test";

const Home = () => {
  const [currentWindowScreen, setCurrentWindowScreen]: any = useState({
    // const [width, height] = Test()
  });

  useEffect(() => {
    window.addEventListener('resize', () => {
      setCurrentWindowScreen({
        width: window.innerWidth,
        height: window.innerHeight
      })
    })
  }, [])

  return (
    <>
      <Wrapper>
        <Header />
      </Wrapper>
      <Main>
        <NavigationBar />
        <Stories />

        {/* @dev This creates some problems with the responsive so we should make it responsive first */}
        { currentWindowScreen.width > 900 ? <CreatePost /> : null }
      </Main>
    </>
  )
};

export default Home;

const Wrapper = styled.div`
  position: relative;
  `;
const Main = styled.div`
  position: relative;
`;
