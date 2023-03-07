"use client";
import styled from "styled-components";
import Header from "../../Header";
import { CreatePost } from "@/components/posts/createPost/CreatePost";
import { NavigationBar } from "@/components/NavigationBar/Navigation";
import Stories from '../../StoriesBar/Stories'

//Code below is temporary
import { useState, useEffect } from 'react';

export const Form = () => {
  const [currentWindowScreen, setCurrentWindowScreen]: any = useState({
    width: '100%',
    height: '100%'
  });

  useEffect(() => {
    window.addEventListener('resize', () => {
      setCurrentWindowScreen({
        width: window.screen.width,
    height: window.screen.height
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

const Wrapper = styled.div`
  position: relative;
  `;
const Main = styled.div`
  position: relative;
`;
