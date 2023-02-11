"use client";
import styled from "styled-components";
import Header from "../components/Header";
import { CreatePost } from "@/components/posts/createPost/CreatePost";

const Home = () => (
  <>
  <Wrapper>
    <Header />
  </Wrapper>
  <Main>
  <CreatePost />
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
