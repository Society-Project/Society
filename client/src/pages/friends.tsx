import React from "react";
import styled from "styled-components";
import Header from "../components/Header";
import { FriendsList } from "@/components/friends/FriendsList";
import { NavigationBar } from "@/components/NavigationBar/Navigation";
import { Stories } from "@/components/StoriesBar/Stories";

import { Box } from "@mui/material";
import "../components/Styles/MainPage.scss";
import "../../src/components/Styles/FriendsStyles.scss";

const friends = () => (
  <>
    <Wrapper>
      <Header />
    </Wrapper>
    <Main>
      <Box
        sx={{
          display: "flex",
          justifyContent: "space-between",
        }}
      >
        <NavigationBar />
        <FriendsList />
        <Stories />
      </Box>
    </Main>
  </>
);

export default friends;

const Wrapper = styled.div`
  position: relative;
`;
const Main = styled.div`
  position: relative;
`;
