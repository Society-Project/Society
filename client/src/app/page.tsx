"use client";
import styled from "styled-components";
import Header from "../components/Header";
import { CreatePost } from "@/components/posts/createPost/CreatePost";
import { NavigationBar } from "@/components/NavigationBar/Navigation";
import { Stories } from "@/components/StoriesBar/Stories";

import { Box } from '@mui/material'

const Home = () => (
  <>
  <Wrapper>
    <Header />
  </Wrapper>
  <Main>
      <NavigationBar />
      <CreatePost />
      <Stories />
  </Main>
  </>
);

export default Home;

const Wrapper = styled.div`
  position: relative;
  `;
  const Main = styled.div`
  position: relative;
`;
