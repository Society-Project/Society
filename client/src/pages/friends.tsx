import React from "react";
import styled from "styled-components";
import Header from "../components/Header";
import { NavigationBar } from "@/components/NavigationBar/Navigation";

import { Box } from "@mui/material";
import "../../src/components/Styles/FriendsStyles.scss";
import { FriendsList } from "@/components/friends/FriendsList";

const friends = () => (
  <>
    <Main className="main-page">
      <NavigationBar />
      <FriendsList />
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
