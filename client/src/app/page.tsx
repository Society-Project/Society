"use client";
import { useState, useEffect } from 'react';
import styled from "styled-components";
import { NavigationBar } from "@/components/NavigationBar/Navigation";
import Stories from '../components/StoriesBar/Stories';
import Chat from '../pages/chat/Chat'
import Header from '@/components/Header';
import { CreatePost } from '@/components/posts/createPost/CreatePost';


const Home = () => {
  const [currentWindowScreen, setCurrentWindowScreen]: any = useState({
    width: window.innerWidth,
    height: window.innerHeight
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
      {/* <Wrapper>
        <Header />
      </Wrapper> */}
      <Main>
        <NavigationBar />
        <Stories />

        {/* @dev This creates some problems with the responsive so we should make it responsive first */}
        { currentWindowScreen.width > 900 ? <CreatePost /> : null }
        <Chat />
      </Main>
    </>
  )
};

export default Home;

const Main = styled.div`
  position: relative;
`;
